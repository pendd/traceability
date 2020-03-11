package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Supplier;

import java.util.List;

public interface ISupplierService {
//    List<Supplier> showSupplier();
    Page<Supplier> showSupplier(Page<Supplier> page,String supplierName);

    List<Supplier> querySupplierCombobox();

    Integer findSupplierName(String supplierName);

    void AddSupplier(Supplier supplier);


    void ModSupplier(Supplier supplier);


    void DelSupplier(Integer ids);
}
