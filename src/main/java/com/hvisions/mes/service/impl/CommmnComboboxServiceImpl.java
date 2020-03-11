package com.hvisions.mes.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hvisions.mes.domain.Line;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.OrderDetail;
import com.hvisions.mes.domain.Storeroom;
import com.hvisions.mes.domain.Supplier;
import com.hvisions.mes.mapper.CommmnComboboxMapper;
import com.hvisions.mes.service.ICommmnComboboxService;
/**
*
* 实现类
* @author mtyu
* @since 2019-03-13
*/
@Service
public class CommmnComboboxServiceImpl implements ICommmnComboboxService{
    @Autowired
    private CommmnComboboxMapper commmnComboboxMapper;

   /**
     *供应商列表
     */
    @Override
    public List<Supplier>  querySupplierCombox()
    {

        return commmnComboboxMapper.selectSupplierCombox();
    }

    /**
     *原料列表
     */
    @Override
    public List<Material>  queryMaterialCombox(Integer supplierId)
    {

        return commmnComboboxMapper.selectMaterialCombox(supplierId);
    }
    /**
     *库房列表
     */
    @Override
    public List<Storeroom>  queryStoreroomCombox(Integer type)
    {

        return commmnComboboxMapper.selectStoreroomCombox(type);
    }
    /**
     *工序总称列表
     */
    @Override
    public List<OrderDetail> queryOrderFullNameCombobox()
    {
        return commmnComboboxMapper.selectOrderFullNameCombobox();

    }

    /**
     *工序列表
     */
    @Override
    public List<OrderDetail> queryOrderDetailCombobox()
    {
        return commmnComboboxMapper.selectOrderDetailCombobox();
    }

    /**
     *产线列表
     */
    @Override
    public List<Line> queryLineCombobox()
    {
        return commmnComboboxMapper.selectLineCombobox();

    }


}
