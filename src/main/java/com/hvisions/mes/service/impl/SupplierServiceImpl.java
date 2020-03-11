package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Supplier;
import com.hvisions.mes.mapper.SupplierMapper;
import com.hvisions.mes.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupplierServiceImpl implements ISupplierService{
   @Autowired
   private SupplierMapper supplierMapper;

    /*@Override
    public List<Supplier> showSupplier() {
        return supplierMapper.selectSupplier();
    }*/

    @Override
    public Page<Supplier> showSupplier(Page<Supplier> page,String supplierName) {
        page.setRecords(supplierMapper.selectSupplier(page,supplierName));
        return page;
    }

    @Override
    public List<Supplier> querySupplierCombobox() {
        return supplierMapper.selectSupplierCombobox();
    }

    @Override
    public Integer findSupplierName(String supplierName) {
        return supplierMapper.selectSupplierName(supplierName);
    }

    @Override
    public void AddSupplier(Supplier supplier) {
        supplierMapper.insertSupplier(supplier);
    }

    @Override
    public void ModSupplier(Supplier supplier) {
        supplierMapper.updateSupplier(supplier);
    }

    @Override
    public void DelSupplier(Integer ids) {
        supplierMapper.deleteSupplier(ids);
    }
}