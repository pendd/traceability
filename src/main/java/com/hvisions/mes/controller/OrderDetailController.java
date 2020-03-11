package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.OrderDetailVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.service.ICommmnComboboxService;
import com.hvisions.mes.service.impl.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/json/OrderDetail")
@ApiIgnore
public class OrderDetailController extends BaseController {
    @Autowired
    private OrderDetailServiceImpl orderDetailServiceImpl;
    @Autowired
    private EquipmentServiceImpl equipmentService;
    @Autowired
    private ProcessTypeServiceImpl processTypeService;
    @Autowired
    private ICommmnComboboxService commmnComboboxService;

    //分页查询工序
    @RequestMapping(value = "/OrderDetaillistpage", method = RequestMethod.GET)
    public Map<String, Object> OrderDetailListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(required = false)String goodsName,
            @RequestParam(required = false)String orderName) {
        Page<OrderDetail> data = orderDetailServiceImpl.showOrderDetail(new Page<>(page, rows),goodsName,orderName);
        Map<String, Object> result = new HashMap<>(16);
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }


    //增加工序
    @RequestMapping(value =  "/addOrderDetail", method = RequestMethod.POST)
    public String addOrderDetail(OrderDetail detail) {

        detail.setUserId(getCurrentUserID());
        detail.setUpdateUserId(getCurrentUserID());
        return orderDetailServiceImpl.addOrderDetail(detail);
    }


    //删除工序
    @RequestMapping(value = "/removeOrderDetail", method = RequestMethod.POST)
    public String removeOrderDetail(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                orderDetailServiceImpl.DelOrderDetail(Integer.valueOf(delIDs.get(i)));
            }


        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }

    //修改工序
    @RequestMapping(value =  "/editOrderDetail", method = RequestMethod.POST)
    public String editOrderDetail(OrderDetail detail) {
        detail.setUserId(getCurrentUserID());
        detail.setUpdateUserId(getCurrentUserID());
        detail.setUpdateTime(new Date());
        return orderDetailServiceImpl.modOrderDetail(detail);
    }


    //工序类型下拉框
    @RequestMapping(value = "/combox",method = RequestMethod.GET)
    public List<ProcessType> getCombox(HttpServletRequest request, HttpServletResponse response){

        return processTypeService.queryProcessTypeCombobox();
    }
    //设备下拉框
    @RequestMapping(value = "/comsubox",method = RequestMethod.GET)
    public List<Equipment> getComsubox(HttpServletRequest request, HttpServletResponse response){

        return equipmentService.queryEquipmentCombobox();
    }

    /**
     *  查询未完成工序量最多的四个
     * @return
     */
    @RequestMapping(value = "/getUnfinishedOrder")
    public List<OrderDetailVo> getUnfinishedOrder(){
        return orderDetailServiceImpl.queryUnfinishedOrder();
    }

    @RequestMapping(value = "/orderCombox",method = RequestMethod.GET)
    public List<OrderDetail> getOrderCombox() {
        return commmnComboboxService.queryOrderDetailCombobox();
    }

    /**
     *  获取工序ID 和工序名称   做下拉列表使用
     * @return
     */
    @RequestMapping("/getOrderIdOrderName")
    public List<OrderDetail> getOrderIdOrderName(@RequestParam(required = false)Integer goodsId,@RequestParam(value = "q",required = false) String orderName) {
        return orderDetailServiceImpl.queryOrderIdOrderName(goodsId,orderName);
    }

    @GetMapping("/getOrderDetailByGoodsId")
    public List<OrderDetail> getOrderDetailByGoodsId(@RequestParam(required = false)Integer goodsId) {
        return orderDetailServiceImpl.queryOrderDetailByGoodsId(goodsId);
    }
}
