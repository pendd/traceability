package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.SalesCompany;
import com.hvisions.mes.service.impl.SalesCompanyServiceImpl;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/json/salesCompany")
@ApiIgnore
public class SalesCompanyController extends BaseController {

    @Autowired
    private SalesCompanyServiceImpl salesCompanyService;


    @RequestMapping(value = "/SalesCompanyListPage", method = RequestMethod.GET)
    public Map<String, Object> supplierListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<SalesCompany> data = salesCompanyService.showSalesCompany(new Page<SalesCompany>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }



    @RequestMapping(value =  "/addSalesCompany", method = RequestMethod.POST)
    public String addSalesCompany(HttpServletRequest request, HttpServletResponse respon) {
        SalesCompany salesCompany = new SalesCompany();
        String res = "";
        String msg = null;
        try {
            int num = salesCompanyService.findSalesCompanyName(request.getParameter("salesCompanyName"));
            if (num != 0) {
                res = "2";
            } else {

                salesCompany.setCreateTime(new Date());
                salesCompany.setComName(request.getParameter("comName"));
                salesCompany.setAddress(request.getParameter("address"));
                salesCompany.setPrincipal(request.getParameter("principal"));
                salesCompany.setTelephone(request.getParameter("telephone"));
                salesCompany.setTelephoneBackup(request.getParameter("telephoneBackup"));
                salesCompany.setRemark(request.getParameter("remark"));
                salesCompany.setUserId(getCurrentUserID());
                salesCompany.setUpdateTime(new Date());
                salesCompany.setUpdateUserId(getCurrentUserID());
                salesCompany.setCity(request.getParameter("city"));
                salesCompanyService.AddSalesCompany(salesCompany);
                res = "true";
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
           res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/removeSalesCompany", method = RequestMethod.POST)
    public String removeSalesCompany(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                salesCompanyService.DelSalesCompany(Integer.valueOf(delIDs.get(i)));
            }
        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }


    @RequestMapping(value =  "/editSalesCompany", method = RequestMethod.POST)
    public String editSalesCompany(HttpServletRequest request, HttpServletResponse respon) {

        SalesCompany salesCompany = new SalesCompany();
        String res = "";
        String msg = null;
        try {
            salesCompany.setComId(Integer.valueOf(request.getParameter("comId")));
            salesCompany.setComName(request.getParameter("comName"));
            salesCompany.setAddress(request.getParameter("address"));
            salesCompany.setPrincipal(request.getParameter("principal"));
            salesCompany.setTelephone(request.getParameter("telephone"));
            salesCompany.setTelephoneBackup(request.getParameter("telephoneBackup"));
            salesCompany.setRemark(request.getParameter("remark"));
            salesCompany.setUpdateTime(new Date());
            salesCompany.setUpdateUserId(getCurrentUserID());
            salesCompany.setCity(request.getParameter("city"));
            salesCompanyService.ModSalesCompany(salesCompany);
                res = "true";

        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }

    /**
     *  查询销售公司下拉
     * @return
     */
    @RequestMapping(value = "getSalesCompanyCombobox")
    public List<SalesCompany> getSalesCompanyCombobox() {
        return salesCompanyService.querySalesCompanyCombobox();
    }
}
