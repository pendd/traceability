package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.LineVo;
import com.hvisions.mes.controller.vo.ProduceOrderVo;
import com.hvisions.mes.domain.ProduceOrder;
import com.hvisions.mes.service.*;
import com.hvisions.mes.util.excelUtil.ImportExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dpeng
 * @create 2019-03-21 16:22
 */
@RestController
@RequestMapping("/json/produceOrder")
@ApiIgnore
public class ProduceOrderController extends BaseController {

    @Autowired
    private IProduceOrderService orderService;
    @Autowired
    private IOrderFullNameService orderFullNameService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private ILineService lineService;
    @Autowired
    private IGoodsService goodsService;

    /**
     *  导入excel
     * @param excel
     * @return
     */
    @RequestMapping("/addExcelOrder")
    public List<ProduceOrder> addExcelOrder(MultipartFile excel) {
        List<ProduceOrderVo> ordersVo;
        List<ProduceOrder> orders = new ArrayList<>();
        String rs;
        // 获取当前用户id
        Integer userID = getCurrentUserID();
        try {
            ordersVo = ImportExcelUtil.insertExcel(excel, ProduceOrderVo.class);

            if (ordersVo == null) {
                rs = "false";
            }

            for (Iterator<ProduceOrderVo> iterator = ordersVo.iterator(); iterator.hasNext(); ) {
                ProduceOrderVo orderVo =  iterator.next();
                // 判断数据库是否有该生产工单号 有就移除
                ProduceOrder p = orderService.getOrderByWorkNumber(orderVo.getWorkNumber(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                if (p != null) {
                    iterator.remove();
                }else {
                    // 等于null 表示数据库没有该记录
                    Integer lineId = lineService.queryLineIdByLineName(orderVo.getLineName());
                    Integer teamId = teamService.queryTeamIdByTeamName(orderVo.getTeamName());
                    Integer orderFullNameId = orderFullNameService.queryOrderFullNameIdByName(orderVo.getOrderFullName());
                    Integer goodsId = goodsService.queryGoodsIdByGoodsName(orderVo.getGoodsName());

                    // 转换到bean中
                    ProduceOrder order = new ProduceOrder();
                    // 把orderVo中的值赋给order
                    BeanUtils.copyProperties(order,orderVo);
                    order.setGoodsId(goodsId.longValue());
                    order.setLineId(lineId.longValue());
                    order.setOrderFullNameId(orderFullNameId.longValue());
                    order.setTeamId(teamId.longValue());
                    order.setCreateTime(new Date());
                    order.setUserId(userID.longValue());
                    order.setUpdateTime(new Date());
                    order.setUpdateUserId(userID.longValue());
                    // 可运行状态
                    order.setStatus(1);
                    order.setGoodsName(orderVo.getGoodsName());
                    orders.add(order);
                }
            }

            // 批量插入数据到数据库
            if (orders.size() != 0) {
                orderService.addOrder(orders);
                rs = "true";
            } else {
                rs = "false";
            }
        } catch (Exception e) {
            rs = "false";
            e.printStackTrace();
        }
        for (ProduceOrder order : orders) {
            order.setRet(rs);
        }
        return orders;
    }

    /**
     *  修改工单状态
     * @param produceId
     * @param state
     * @return
     */
    @RequestMapping("/modifyOderState")
    public Integer modifyOrderState(Integer produceId,Integer state){
        int count = orderService.modifyOrderState(produceId, state);
        // 1修改成功    0当前产线已存在正在运行的工单
        return count;
    }

    /**
     *   获取产线生产效率
     * @return
     */
    @RequestMapping(value = "/getAllLineEfficiency")
    public List<LineVo> getAllLineEfficiency(){
        return orderService.getLineEfficiency();
    }

    /**
     *  获取所有工单
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/getAllProduceOrder")
    public Map<String,Object> getAllProduceOrder(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "15") Integer rows,
                                                 @RequestParam(value = "goodsName",required = false)String goodsName,
                                                 @RequestParam(value = "realStartTime",required = false)String realStartTime,
                                                 @RequestParam(value = "realEndTime",required = false)String realEndTime){

        Page<ProduceOrder> data = orderService.queryAllProduceOrder(new Page<>(page, rows), goodsName, realStartTime, realEndTime);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    @GetMapping("/getProduceOrderByGoodsId")
    public List<ProduceOrder> getProduceOrderByGoodsId(@RequestParam(value = "goodsId")Integer goodsId,String q) {
        return orderService.queryProduceOrderByGoodsId(goodsId,q == null || Objects.equals("",q) ? "" : q.trim());
    }

}
