package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.SalesCompany;
import com.hvisions.mes.mapper.SalesCompanyMapper;
import com.hvisions.mes.service.ISalesCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SalesCompanyServiceImpl implements ISalesCompanyService {
   @Autowired
   private SalesCompanyMapper salesCompanyMapper;


    @Override
    public List<SalesCompany> showSalesCompany() {
        return salesCompanyMapper.selectSalesCompany();
    }

    @Override
    public Page<SalesCompany> showSalesCompany(Page<SalesCompany> page) {
        page.setRecords(salesCompanyMapper.selectSalesCompany(page));
        return page;
    }

    @Override
    public List<SalesCompany> querySalesCompanyCombobox() {
        return salesCompanyMapper.selectSalesCompanyCombobox();
    }

    @Override
    public Integer findSalesCompanyName(String salesCompanyName) {
        return salesCompanyMapper.selectSalesCompanyName(salesCompanyName);
    }

    @Override
    public void AddSalesCompany(SalesCompany salesCompany) {
        salesCompanyMapper.insertSalesCompany(salesCompany);
    }

    @Override
    public void ModSalesCompany(SalesCompany salesCompany) {
        salesCompanyMapper.updateSalesCompany(salesCompany);
    }

    @Override
    public void DelSalesCompany(Integer ids) {
        salesCompanyMapper.deleteSalesCompany(ids);
    }
}