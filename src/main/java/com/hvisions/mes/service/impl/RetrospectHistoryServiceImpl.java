package com.hvisions.mes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hvisions.mes.controller.vo.Constant;
import com.hvisions.mes.controller.vo.GraphSeriesData;
import com.hvisions.mes.controller.vo.NewGraphSeriesLinks;
import com.hvisions.mes.domain.AssignRefCode;
import com.hvisions.mes.domain.GoodsInStoreroom;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import com.hvisions.mes.domain.OrderHistory;
import com.hvisions.mes.mapper.RetrospectHistoryMapper;
import com.hvisions.mes.service.IRetrospectHistoryService;
import com.hvisions.mes.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @description
 * @date 2019-07-25 9:14
 */
@Service
public class RetrospectHistoryServiceImpl implements IRetrospectHistoryService {

    @Autowired
    private RetrospectHistoryMapper historyMapper;

    /**
     *  成品码追溯流程
     * @param code  壳子上的码
     * @return json对象
     */
    @Override
    public JSONObject queryGoodsRetrospect(String code) {

        JSONObject jsonObject = new JSONObject();

        List<GraphSeriesData> data = new ArrayList<>();
        List<NewGraphSeriesLinks> links = new ArrayList<>();

        // 通过壳子上的码获取线路板的码
        AssignRefCode refCode = historyMapper.selectAssignRefCodeByGoodsQrCode(code);

        String goodsCode;

        if (refCode == null) {
            jsonObject.put("status",0);
            return jsonObject;
        }else {
            goodsCode = refCode.getMaterialQrCode();
        }

        // 通过成品码获取成品库房
        GoodsInStoreroom goodsInStoreroom = historyMapper.selectStoreroomNameByGoodsCode(goodsCode);

        // 通过成品码获取成品所经过的所有工序
        List<OrderHistory> orderHistories = historyMapper.selectOrderHistoryByGoodsCode(goodsCode);

        // 处理相同名称问题
        List<OrderHistory> histories = new ArrayList<>();

        // 没有工序历史 代表要么这个码不对  要么这个码还没有被记录
        if (orderHistories.isEmpty()) {
            jsonObject.put("status",0);
            return jsonObject;
        }

        orderHistories.forEach(e -> {

            // 遍历新的集合 判断当前要加入的元素是否存在  存在即是返工经过的工序
            for (OrderHistory history : histories) {
                if (Objects.equals(history.getOrderName(),e.getOrderName())) {
                    // 存在相同的
                    history.setReworkTimes(history.getReworkTimes() == null ? 1 : history.getReworkTimes() + 1);
                    e.setOrderName(e.getOrderName() + "(返工" + history.getReworkTimes() + ")");
                    break;
                }
            }
            histories.add(e);
        });

        // 通过生产工单号获取原料库房
        List<MaterialStoreroomHistory> materialStoreroomHistoryList = historyMapper.selectStoreroomNamesByProduceNumber(histories.get(0).getProduceNumber());

        // 原料库房去重
        materialStoreroomHistoryList = materialStoreroomHistoryList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()
                -> new TreeSet<>(Comparator.comparing(MaterialStoreroomHistory::getStoreroomName))),ArrayList::new));

        int count = 0;
        int yHeight;

        int dataIndex = 0;
        int reworkIndex = dataIndex;

        int size = histories.size();
        for (int i = 0; i < size; i++) {
            if (histories.get(i).getOrderName().contains("返工")) {
                yHeight = count % 2 == 0 ? 80 : 400;
                handleGraphSeriesData(data,histories.get(i).getOrderName(),size,yHeight,Constant.TRACE_HISTORY_POINT_RED,reworkIndex -1,histories.get(i).getCreateTime(),histories.get(i).getUserName());

                // 判断前一个工序是不是返工工序
                if (histories.get(i + 1).getOrderName().contains("返工")) {
                    reworkIndex ++;
                }

                if (i < size -1) {
                    links.add(new NewGraphSeriesLinks(histories.get(i).getOrderName(), histories.get(i+1).getOrderName(),Constant.TRACE_HISTORY_LINE_RED,3));
                    // 返工的工序
                    // 判断后一个工序是不是返工工序
                    if (!histories.get(i + 1).getOrderName().contains("返工")) {
                        count ++;
                    }
                }
            }else {
                handleGraphSeriesData(data, histories.get(i).getOrderName(), size, 240, Constant.TRACE_HISTORY_POINT_BLUE, dataIndex ++, histories.get(i).getCreateTime(), histories.get(i).getUserName());
                if (i < size -1) {
                    links.add(new NewGraphSeriesLinks(histories.get(i).getOrderName(), histories.get(i+1).getOrderName(),Constant.TRACE_HISTORY_LINE_BLUE,3));
                }
                reworkIndex = dataIndex;
            }

        }

        // 添加原料库房data
        for (int i = 0; i < materialStoreroomHistoryList.size(); i++) {
            handleGraphSeriesData(data,materialStoreroomHistoryList.get(i).getStoreroomName(),size,400,Constant.TRACE_HISTORY_POINT_LIME,i,materialStoreroomHistoryList.get(i).getCreateTime(),materialStoreroomHistoryList.get(i).getUserName());
            links.add(new NewGraphSeriesLinks(materialStoreroomHistoryList.get(i).getStoreroomName(),histories.get(0).getOrderName(),Constant.TRACE_HISTORY_LINE_BLUE,3));
        }

        links.add(new NewGraphSeriesLinks(histories.get(size-1).getOrderName(),goodsInStoreroom.getStoreroomName(),Constant.TRACE_HISTORY_LINE_BLUE,3));

        // 添加成品库房data
        handleGraphSeriesData(data,goodsInStoreroom.getStoreroomName(),size,80,Constant.TRACE_HISTORY_POINT_GRAY,(reworkIndex - 1),goodsInStoreroom.getCreateTime(),goodsInStoreroom.getUserName());

        jsonObject.put("status",1);
        jsonObject.put("data",data);
        jsonObject.put("links",links);



        return jsonObject;
    }

    /**
     *  处理GraphSeriesData数据
     * @param data         GraphSeriesData对象的集合
     * @param seriesName   系列名
     * @param size         同一类别的个数
     * @param y            y轴坐标
     * @param num          倍数
     */
    private void handleGraphSeriesData(List<GraphSeriesData> data, String seriesName, int size, int y, String color, int num, Date createTime,String userName) {
        int x = Constant.TRACE_HISTORY_WIDTH / (size + 1) * num;
        if (StringUtil.isNotNull(seriesName)) {
            data.add(new GraphSeriesData(seriesName,x,y,color,createTime,userName));
        }
    }
}
