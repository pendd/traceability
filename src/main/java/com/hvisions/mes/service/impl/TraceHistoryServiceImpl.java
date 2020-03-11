package com.hvisions.mes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hvisions.mes.controller.vo.Constant;
import com.hvisions.mes.controller.vo.GraphSeriesData;
import com.hvisions.mes.controller.vo.GraphSeriesLinks;
import com.hvisions.mes.domain.TraceHistory;
import com.hvisions.mes.mapper.TraceHistoryMapper;
import com.hvisions.mes.service.ITraceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Author swang
 * Date 2019/3/23 16:47
 * Version 1.0
 * Description
 **/
@Service
public class TraceHistoryServiceImpl implements ITraceHistoryService {

    @Autowired
    private TraceHistoryMapper traceHistoryMapper;

    @Override
    public JSONObject selectProductTraceByCode(String productCode) {
        JSONObject jsonObject = new JSONObject();
        List<GraphSeriesData> graphSeriesDataList = new ArrayList<>();
        List<GraphSeriesLinks> graphSeriesLinksList = new ArrayList<>();
        //原料库
        Map<String,String> materialRoom = new HashMap<>();
        //线边库
        Map<String,String> lineRoom = new HashMap<>();
        //投料
        Set<TraceHistory> startSet = new HashSet<>();
        //包装
        Set<TraceHistory> packSet = new HashSet<>();
        //分类存储
        List<TraceHistory> traceCenterList = new ArrayList<>();
        //连线使用 记录上一个点名称
        Map<String,String> lastPointMap = new HashMap<>();
        //投料和包装环节的点
        Map<String,List<TraceHistory>> startAndPackMap = new HashMap<>();
        //成品之前的环节
        Date date = new Date();
        List<TraceHistory> afterPutList = traceHistoryMapper.selectByTraceCode(productCode);
        for(TraceHistory th : afterPutList){
            if(date.after(th.getCreateTime())) date = th.getCreateTime();
        }
        List<TraceHistory> beforePutList = deepIntoSelectMaterial(productCode,Constant.TRACE_HISTORY_GOODS_IN_ROOM,date);
        for(TraceHistory th : beforePutList){
            if(th.getTraceType().equals(Constant.TRACE_HISTORY_MATERIAL_ROOM)) materialRoom.put(th.getTraceName(),th.getTraceCode());
            else if(th.getTraceType().equals(Constant.TRACE_HISTORY_LINE_ROOM)) lineRoom.put(th.getTraceName(),th.getTraceCode());
            else {
                if(th.getTraceType().equals(Constant.TRACE_HISTORY_START)) startSet.add(th);
                else if(th.getTraceType().equals(Constant.TRACE_HISTORY_PACK)) packSet.add(th);
                else traceCenterList.add(th);
                classifyNextTrace(startAndPackMap,th);
            }
        }

        traceCenterList.sort(Comparator.comparing(TraceHistory::getCreateTime));

        makeRoomPoint(graphSeriesDataList,materialRoom,50);
        makeRoomPoint(graphSeriesDataList,lineRoom,100);
        makeTurnPoint(graphSeriesDataList,startSet,150);
        int endY = makeCenterPoint(graphSeriesDataList,traceCenterList,200);
        int packY = makeTurnPoint(graphSeriesDataList,packSet,endY);
        //成品之后的点
        if(afterPutList != null && !afterPutList.isEmpty()){
            int width = Math.round(Constant.TRACE_HISTORY_WIDTH/(afterPutList.size()+1));
            int number = 1;
            for(TraceHistory th : afterPutList){
                if(th.getTraceType().equals(Constant.TRACE_HISTORY_START) || th.getTraceType().equals(Constant.TRACE_HISTORY_PACK)) {
                    classifyNextTrace(startAndPackMap,th);
                }
                String before = distinguish(th.getTraceCode());
                graphSeriesDataList.add(new GraphSeriesData(before+th.getTraceName(),number * width,packY,Constant.TRACE_HISTORY_POINT_LIME));
                number++;
            }
        }
        //连点
        for(TraceHistory th : beforePutList){
            linkPoint(lastPointMap,graphSeriesLinksList,startAndPackMap,th);
        }
        for(TraceHistory th : afterPutList){
            linkPoint(lastPointMap,graphSeriesLinksList,startAndPackMap,th);
        }

        jsonObject.put("data",graphSeriesDataList);
        jsonObject.put("links",graphSeriesLinksList);

        return jsonObject;
    }

    /**
     * 产品追溯
     * 生成原料库和线边库的点
     */
    private int makeRoomPoint(List<GraphSeriesData> graphSeriesDataList,Map<String,String> roomSet,int y){
        if(roomSet != null && !roomSet.isEmpty()){
            int roomWidth = Math.round(Constant.TRACE_HISTORY_WIDTH/(roomSet.size()+1));
            int roomNumber = 1;
            for(Map.Entry<String,String> en : roomSet.entrySet()){
                graphSeriesDataList.add(new GraphSeriesData(distinguish(en.getValue())+en.getKey(),roomNumber*roomWidth,y,Constant.TRACE_HISTORY_POINT_GRAY));
                roomNumber++;
            }
        }
        return y + Constant.TRACE_HISTORY_GRID_HEIGHT;
    }
    /**
     * 产品追溯
     * 生成投料和包装的点
     */
    private int makeTurnPoint(List<GraphSeriesData> graphSeriesDataList,Set<TraceHistory> roomSet,int y){
        if(roomSet != null && !roomSet.isEmpty()){
            int roomWidth = Math.round(Constant.TRACE_HISTORY_WIDTH/(roomSet.size()+1));
            int roomNumber = 1;
            for(TraceHistory th : roomSet){
                String before = distinguish(th.getTraceCode());
                graphSeriesDataList.add(new GraphSeriesData(before+th.getTraceName(),roomNumber*roomWidth,y,Constant.TRACE_HISTORY_POINT_GRAY));
                roomNumber++;
            }
        }
        return y + Constant.TRACE_HISTORY_GRID_HEIGHT;
    }

    /**
     * 产品追溯
     * 生成中间环节的点
     */
    private int makeCenterPoint(List<GraphSeriesData> graphSeriesDataList,List<TraceHistory> list,int y){
        int height = 0;
        if(list != null && !list.isEmpty()){
            height = list.size();
            int width = Math.round(Constant.TRACE_HISTORY_WIDTH/3);
            int tempHeight = y;
            for(TraceHistory th : list){
                graphSeriesDataList.add(new GraphSeriesData(distinguish(th.getTraceCode())+th.getTraceName(),width,tempHeight,Constant.TRACE_HISTORY_POINT_BLUE));
                tempHeight += Constant.TRACE_HISTORY_GRID_HEIGHT;
            }
        }
        return y + height * Constant.TRACE_HISTORY_GRID_HEIGHT;
    }


    /**
     * 产品追溯
     * 对不同traceCode的点进行分类
     */
    private void classifyTrace(Map<String,List<TraceHistory>> traceCodeMap,TraceHistory th){
        List<TraceHistory> list = traceCodeMap.get(th.getTraceCode());
        if(list == null) {
            List<TraceHistory> li = new ArrayList<>();
            li.add(th);
            traceCodeMap.put(th.getTraceCode(),li);
        }else  list.add(th);
    }

    private void classifyNextTrace(Map<String,List<TraceHistory>> traceCodeMap,TraceHistory th){
        List<TraceHistory> list = traceCodeMap.get(th.getNextTraceCode());
        if(list == null) {
            List<TraceHistory> li = new ArrayList<>();
            li.add(th);
            traceCodeMap.put(th.getNextTraceCode(),li);
        }else  list.add(th);
    }

    /**
     * 产品追溯/原料追溯
     * 复杂点之间连线
     */
    private void linkPoint(Map<String,String> lastPointMap,List<GraphSeriesLinks> graphSeriesLinksList,Map<String,List<TraceHistory>> startAndPackMap,TraceHistory th){
        String before = distinguish(th.getTraceCode());
        if(startAndPackMap.containsKey(th.getTraceCode())) {
            List<TraceHistory> tempList =  startAndPackMap.get(th.getTraceCode());
            for(TraceHistory spTh : tempList){
                graphSeriesLinksList.add(new GraphSeriesLinks(distinguish(spTh.getTraceCode())+spTh.getTraceName(),before+th.getTraceName()));
            }
            startAndPackMap.remove(th.getTraceCode());
        }
        String lastPoint = lastPointMap.get(th.getTraceCode());
        if(lastPoint == null) lastPointMap.put(th.getTraceCode(),before+th.getTraceName());
        else{
            String tempName = before+th.getTraceName();
            graphSeriesLinksList.add(new GraphSeriesLinks(lastPoint,tempName));
            lastPointMap.put(th.getTraceCode(),tempName);
        }
    }

    /**
     * 产品追溯
     * 根据编码多级查询追溯记录
     * 递归调用
     */
    private List<TraceHistory> deepIntoSelectMaterial(String nextTraceCode,Integer traceType,Date date){
        List<TraceHistory> traceHistoryList = traceHistoryMapper.selectMaterialByNextTraceCode(nextTraceCode,traceType,date);
        Set<String> materialSet = new HashSet<>();
        List<TraceHistory> traceCodeList = new ArrayList<>();
        Map<String,TraceHistory> map = new HashMap<>();
        for (TraceHistory th : traceHistoryList) {
            if(th.getTraceType().equals(Constant.TRACE_HISTORY_MATERIAL_ROOM)) materialSet.add(th.getTraceCode());
            //else traceCodeList.add(th);
            else map.put(th.getTraceCode(),th);
        }
        //有原料出库类型的记录不需要再查询
        /*for(TraceHistory tc : traceCodeList){
            if(materialSet.contains(tc.getTraceCode())) continue;
            List<TraceHistory> tempList = deepIntoSelectMaterial(tc.getTraceCode(),tc.getTraceType(),tc.getCreateTime());
            if(tempList != null && !tempList.isEmpty()) traceHistoryList.addAll(tempList);
        }*/
        for(Map.Entry<String,TraceHistory> ent : map.entrySet()){
            if(materialSet.contains(ent.getKey())) continue;
            TraceHistory temp = ent.getValue();
            List<TraceHistory> tempList = deepIntoSelectMaterial(temp.getTraceCode(),temp.getTraceType(),temp.getCreateTime());
            if(tempList != null && !tempList.isEmpty()) traceHistoryList.addAll(tempList);
        }
        return traceHistoryList;
    }

    /**
     * 产品追溯
     * 处理在相同的节点上，做名称的区分
     */
    private String distinguish(String traceCode){
        return Constant.TRACE_HISTORY_ARRAY[Math.round(traceCode.hashCode()%Constant.TRACE_HISTORY_ARRAY.length)];
    }


    @Override
    public JSONObject selectMaterialTraceByCode(String materialCode) {
        JSONObject jsonObject = new JSONObject();
        List<GraphSeriesData> graphSeriesDataList = new ArrayList<>();
        List<GraphSeriesLinks> graphSeriesLinksList = new ArrayList<>();
        //记录所有点，防止重复echarts不能显示
        Set<GraphSeriesData> allPointSet = new HashSet<>();
        Set<GraphSeriesLinks> allLinkSet = new HashSet<>();
        //成品之前的环节
        Set<String> materialRoomSet = new HashSet<>();
        Set<String> lineRoomSet = new HashSet<>();
        Set<String> startSet = new HashSet<>();
        Map<String,String> startNextMap = new HashMap<>();
        Map<String,List<TraceHistory>> upList = new HashMap<>();
        //投料及投料之前得操作数据
        List<TraceHistory> materialList = traceHistoryMapper.selectByTraceCode(materialCode);
        if(materialList != null && !materialList.isEmpty()){
            String lastPointName = "";
            for(TraceHistory th : materialList){
                if(th.getTraceType().equals(Constant.TRACE_HISTORY_MATERIAL_ROOM)) materialRoomSet.add(th.getTraceName());
                else if(th.getTraceType().equals(Constant.TRACE_HISTORY_LINE_ROOM)) lineRoomSet.add(th.getTraceName());
                else if(th.getTraceType().equals(Constant.TRACE_HISTORY_START)){
                    startSet.add(th.getTraceName());
                    startNextMap.put(th.getNextTraceCode(),th.getTraceName());
                }
                if(StringUtils.isEmpty(lastPointName)){
                    lastPointName = th.getTraceName();
                }else{
                    if(!lastPointName.equals(th.getTraceName())){
                        GraphSeriesLinks gsl = new GraphSeriesLinks(lastPointName,th.getTraceName());
                        if(!allLinkSet.contains(gsl)) {
                            graphSeriesLinksList.add(gsl);
                            allLinkSet.add(gsl);
                        }
                        lastPointName = th.getTraceName();
                    }
                }
            }
            makeRoomPointMaterial(graphSeriesDataList,materialRoomSet,50);
            makeRoomPointMaterial(graphSeriesDataList,lineRoomSet,150);
            makeRoomPointMaterial(graphSeriesDataList,startSet,250);
        }
        //成品之后的环节
        List<TraceHistory> productList = deepIntoSelectProduct(materialCode);

        if(productList != null && !productList.isEmpty()){
            Map<String,String> orderNameMap = new LinkedHashMap<>();
            Map<String,String> orderCodeMap = new LinkedHashMap<>();//记录工序得种类
            Map<String,TraceHistory> packMap = new HashMap<>();
            Map<String,TraceHistory> packNextMap = new HashMap<>();
            Map<String,List<TraceHistory>> goodsMap = new HashMap<>();
            for(TraceHistory th : productList){
                if(th.getTraceType().equals(Constant.TRACE_HISTORY_WORK_ORDER)){
                    String lastName = startNextMap.get(th.getTraceCode());
                    if(!StringUtils.isEmpty(lastName)){
                        GraphSeriesLinks gsl = new GraphSeriesLinks(lastName,th.getTraceName());
                        if(!allLinkSet.contains(gsl)) {
                            graphSeriesLinksList.add(gsl);
                            allLinkSet.add(gsl);
                        }
                        startNextMap.remove(th.getTraceCode());
                    }
                    orderNameMap.putIfAbsent(th.getTraceName(),th.getTraceName());
                    orderCodeMap.put(th.getTraceCode(),th.getTraceName());
                }else if(th.getTraceType().equals(Constant.TRACE_HISTORY_PACK)){
                    packMap.put(th.getTraceCode(),th);
                    packNextMap.put(th.getNextTraceCode(),th);
                }else{
                    List<TraceHistory> goodsList = goodsMap.get(th.getTraceCode());
                    if(goodsList == null) {
                        goodsList = new ArrayList<>();
                        goodsList.add(th);
                        goodsMap.put(th.getTraceCode(),goodsList);
                    }else{
                        goodsList.add(th);
                    }
                }

            }
            //处理数据
            int orderNumber = makeOrderPointMaterial(graphSeriesDataList,orderNameMap,350);
            String orderLastName = "";
            for(String name : orderNameMap.keySet()){
                if(!StringUtils.isEmpty(orderLastName)){
                    graphSeriesLinksList.add(new GraphSeriesLinks(orderLastName,name));
                }
                orderLastName = name;
            }

            int number = 1;
            int roomWidth = Math.round(Constant.TRACE_HISTORY_WIDTH/(orderCodeMap.size()+3));
            for(Map.Entry<String,String> en : orderCodeMap.entrySet()){
                TraceHistory orderTrace = packMap.get(en.getKey());
                if(orderTrace != null){
                    String before = distinguish(orderTrace.getTraceCode());
                    graphSeriesLinksList.add(new GraphSeriesLinks(en.getValue(),before+orderTrace.getTraceName()));
                    deepIntoPack(graphSeriesDataList,graphSeriesLinksList,orderTrace.getTraceCode(),packMap,number*roomWidth,orderNumber);
                }
                number++;
            }

            int maxNumber = orderNumber + Math.round(productList.size()/goodsMap.size()) * Constant.TRACE_HISTORY_GRID_HEIGHT;
            int geshu = 1;
            for(Map.Entry<String,List<TraceHistory>> ent : goodsMap.entrySet()){
                List<TraceHistory> list = ent.getValue();
                Map<String,TraceHistory> goodLastMap = new HashMap<>();
                int yyy = maxNumber;
                for(TraceHistory t : list){
                    TraceHistory last = goodLastMap.get(t.getTraceCode());
                    if(last == null){
                        TraceHistory th = packNextMap.get(t.getTraceCode());
                        if(th != null){
                            String beforeA = distinguish(th.getTraceCode());
                            String beforeB = distinguish(t.getTraceCode());
                            graphSeriesLinksList.add(new GraphSeriesLinks(beforeA+th.getTraceName(),beforeB+t.getTraceName()));
                            graphSeriesDataList.add(new GraphSeriesData(beforeB+t.getTraceName(),geshu*roomWidth,yyy,Constant.TRACE_HISTORY_POINT_GRAY));
                            packNextMap.remove(t.getTraceCode());
                        }
                        goodLastMap.put(t.getTraceCode(),t);
                    }else{
                        String beforeA = distinguish(last.getTraceCode());
                        String beforeB = distinguish(t.getTraceCode());
                        graphSeriesLinksList.add(new GraphSeriesLinks(beforeA+last.getTraceName(),beforeB+t.getTraceName()));
                        graphSeriesDataList.add(new GraphSeriesData(beforeB+t.getTraceName(),geshu*roomWidth,yyy,Constant.TRACE_HISTORY_POINT_GRAY));
                        goodLastMap.put(t.getTraceCode(),t);
                    }
                    yyy += Constant.TRACE_HISTORY_GRID_HEIGHT;
                }
                geshu++;
            }

        }

        jsonObject.put("data",graphSeriesDataList);
        jsonObject.put("links",graphSeriesLinksList);
        return jsonObject;
    }

    public void deepIntoPack(List<GraphSeriesData> graphSeriesDataList,List<GraphSeriesLinks> graphSeriesLinksList,String orderCode,Map<String,TraceHistory> packMap,int width,int y){
        TraceHistory th = packMap.get(orderCode);
        if(th != null){
            TraceHistory nextTh = packMap.get(th.getNextTraceCode());
            String beforeA = distinguish(th.getTraceCode());
            graphSeriesDataList.add(new GraphSeriesData(beforeA+th.getTraceName(),width,y,Constant.TRACE_HISTORY_POINT_GRAY));
            if(nextTh != null){
                String beforeB = distinguish(nextTh.getTraceCode());
                graphSeriesLinksList.add(new GraphSeriesLinks(beforeA+th.getTraceName(),beforeB+nextTh.getTraceName()));
                y +=  Constant.TRACE_HISTORY_GRID_HEIGHT;
                deepIntoPack(graphSeriesDataList,graphSeriesLinksList,th.getNextTraceCode(),packMap,width,y);
            }
        }
    }


    /**
     * 产品追溯
     * 生成原料库和线边库的点
     */
    private int makeRoomPointMaterial(List<GraphSeriesData> graphSeriesDataList,Set<String> roomSet,int y){
        if(roomSet != null && !roomSet.isEmpty()){
            int roomWidth = Math.round(Constant.TRACE_HISTORY_WIDTH/(roomSet.size()+3));
            int roomNumber = 1;
            for(String name : roomSet){
                graphSeriesDataList.add(new GraphSeriesData(name,roomNumber*roomWidth,y,Constant.TRACE_HISTORY_POINT_GRAY));
                roomNumber++;
            }
        }
        return y + Constant.TRACE_HISTORY_GRID_HEIGHT;
    }

    /**
     * 产品追溯
     * 生成原料库和线边库的点
     */
    private int makeOrderPointMaterial(List<GraphSeriesData> graphSeriesDataList,Map<String,String> map,int y){
        if(map != null && !map.isEmpty()){
            int roomWidth = Math.round(Constant.TRACE_HISTORY_WIDTH/(map.size()+1));
            for(String name : map.keySet()){
                graphSeriesDataList.add(new GraphSeriesData(name,roomWidth,y,Constant.TRACE_HISTORY_POINT_GRAY));
                y += Constant.TRACE_HISTORY_GRID_HEIGHT;
            }
        }
        return y + Constant.TRACE_HISTORY_GRID_HEIGHT;
    }

    /**
     * 原料追溯
     * 根据编码多级查询追溯记录
     * 递归调用
     */
    private List<TraceHistory> deepIntoSelectProduct(String traceCode){
        List<TraceHistory> traceHistoryList = traceHistoryMapper.selectProductByTraceCode(traceCode);
        if(traceHistoryList == null || traceHistoryList.isEmpty()) return traceHistoryList;
        //Set<String> materialSet = new HashSet<>();
        Set<String> traceCodeSet = new HashSet<>();
        for (TraceHistory th : traceHistoryList) {
            traceCodeSet.add(th.getTraceCode());
        }
        for(String tc : traceCodeSet){
            List<TraceHistory> tempList = deepIntoSelectProduct(tc);
            if(tempList != null && !tempList.isEmpty())traceHistoryList.addAll(tempList);
        }
        return traceHistoryList;
    }

}
