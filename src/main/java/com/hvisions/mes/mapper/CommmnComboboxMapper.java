package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
*
* Mapper接口
* @author mtyu
* @since 2019-03-08
*/
public interface CommmnComboboxMapper {
    /**
     *供应商列表
     */
    List<Supplier>  selectSupplierCombox();
    /**
     *原料列表
     */
    List<Material>  selectMaterialCombox(@Param("supplierId") Integer supplierId);
    /**
     *库房列表
     */
    List<Storeroom>  selectStoreroomCombox(@Param("roomType") Integer roomType);
    /**
     *工序总称列表
     */
    List<OrderDetail> selectOrderFullNameCombobox();

    /**
     *工序列表
     */
    List<OrderDetail> selectOrderDetailCombobox();
    /**
     *产线列表
     */
    List<Line> selectLineCombobox();
}
