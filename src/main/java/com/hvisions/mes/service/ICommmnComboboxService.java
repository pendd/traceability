package com.hvisions.mes.service;

import java.util.List;

import com.hvisions.mes.domain.Line;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.OrderDetail;
import com.hvisions.mes.domain.Storeroom;
import com.hvisions.mes.domain.Supplier;

public interface ICommmnComboboxService {

    /**
     *供应商列表
     */
    List<Supplier>  querySupplierCombox();


    /**
     *原料列表
     */
    List<Material>  queryMaterialCombox(Integer supplierId);

    /**
     *库房列表
     */
    List<Storeroom>  queryStoreroomCombox(Integer type);

    /**
     *工序总称列表
     */
     List<OrderDetail> queryOrderFullNameCombobox();


    /**
     *工序列表
     */
     List<OrderDetail> queryOrderDetailCombobox();

    /**
     *产线列表
     */
    List<Line> queryLineCombobox();
}
