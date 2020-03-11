package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.SalesCompany;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface SalesCompanyMapper extends BaseMapper<SalesCompany> {

    List<SalesCompany> selectSalesCompany();
    List<SalesCompany> selectSalesCompany(Pagination page);

    String selectSalesCompanyById(Integer comId);


    Integer selectSalesCompanyName(String salesCompanyName);

    List<SalesCompany> selectSalesCompanyCombobox();


    Integer insertSalesCompany(SalesCompany salesCompany);


    void updateSalesCompany(SalesCompany salesCompany);


    void deleteSalesCompany(Integer salesCompanyId);
}
