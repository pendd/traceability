package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Supplier;import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierMapper extends BaseMapper<Supplier> {

//    List<Supplier> selectSupplier();

    List<Supplier> selectSupplier(@Param("page")Pagination page, @Param("supplierName")String supplierName);


    Integer selectSupplierName(String supplierName);

    List<Supplier> selectSupplierCombobox();


    Integer insertSupplier(Supplier supplier);


    void updateSupplier(Supplier supplier);


    void deleteSupplier(Integer supplierId);


}
