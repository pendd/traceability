package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.OrderDetailVo;
import com.hvisions.mes.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail>{

    List<OrderDetail> selectOrderDetail(@Param("page")Pagination page, @Param("goodsName")String goodsName, @Param("orderName")String orderName);

    //增加工序信息
    Integer InsertOrderDetail(OrderDetail orderDetail);

    //修改工序信息
    void UpdateOrderDetail(OrderDetail orderDetail);

    //删除工序信息
    void DeleteOrderDetail(Integer orderId);

    // 查询工序总数量
    List<OrderDetailVo> selectOrderDetailAllCount();

    /**
     * 获取工序ID 和工序名称
     *
     * @return
     */
    List<OrderDetail> selectOrderIdOrderName(@Param("goodsId")Integer goodsId, @Param("orderName")String orderName);

    List<OrderDetail> selectOrderDetailByGoodsId(Integer goodsId);

    OrderDetail selectOrderDetailByGoodsIdAndType(Integer goodsId);

    /**
     *  获取产品的所有工序
     * @param goodsId 产品ID  parentId
     * @return
     */
    List<OrderDetail> selectAllOrderDetailByGoodsId(Integer goodsId);

    /**
     *  通过工序ID获取工序信息
     * @param orderId
     * @return
     */
    OrderDetail selectOrderDetailByOrderId(Integer orderId);
}
