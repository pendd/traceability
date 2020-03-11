package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.SalesCompany;

import java.util.List;

public interface ISalesCompanyService {
    List<SalesCompany> showSalesCompany();
    Page<SalesCompany> showSalesCompany(Page<SalesCompany> page);

    List<SalesCompany> querySalesCompanyCombobox();

    Integer findSalesCompanyName(String salesCompanyName);

    void AddSalesCompany(SalesCompany salesCompany);


    void ModSalesCompany(SalesCompany salesCompany);


    void DelSalesCompany(Integer ids);
}
