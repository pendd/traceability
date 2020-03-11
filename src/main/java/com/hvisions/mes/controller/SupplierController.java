package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Supplier;
import com.hvisions.mes.service.impl.SupplierServiceImpl;
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
@ApiIgnore
@RestController
@RequestMapping("/json/supplier")
public class SupplierController extends BaseController {

    @Autowired
    private SupplierServiceImpl supplierService;


    @RequestMapping(value = "/supplierListPage", method = RequestMethod.GET)
    public Map<String, Object> supplierListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "supplierName",required = false) String supplierName) {
        Page<Supplier> data = supplierService.showSupplier(new Page<Supplier>(page, rows),supplierName);
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }



    @RequestMapping(value =  "/addSupplier", method = RequestMethod.POST)
    public String addsupplier(HttpServletRequest request, HttpServletResponse respon) {
        Supplier supplier = new Supplier();
        String res = "";
        String msg = null;
        try {
            int num = supplierService.findSupplierName(request.getParameter("supplierName"));
            System.out.println(num);
            if (num != 0) {
                res = "2";
            } else {

                supplier.setCreateTime(new Date());
                supplier.setSupplierName(request.getParameter("supplierName"));
                supplier.setAddress(request.getParameter("address"));
                supplier.setPrincipal(request.getParameter("principal"));
                supplier.setTelephone(request.getParameter("telephone"));
                supplier.setTelephoneBackup(request.getParameter("telephoneBackup"));
                supplier.setRemark(request.getParameter("remark"));
                supplier.setUserId(getCurrentUserID());
                supplier.setUpdateTime(new Date());
                supplier.setUpdateUserId(getCurrentUserID());
                supplier.setSupplierType(request.getParameter("supplierType"));
                supplier.setSupplyYears(Integer.valueOf(request.getParameter("supplyYears")));
                supplierService.AddSupplier(supplier);
                res = "true";
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
           res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/removeSupplier", method = RequestMethod.POST)
    public String removeSupplier(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                supplierService.DelSupplier(Integer.valueOf(delIDs.get(i)));
            }
        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }


    @RequestMapping(value =  "/editSupplier", method = RequestMethod.POST)
    public String editSupplier(Supplier supplier) {
        supplier.setUpdateUserId(getCurrentUserID());
        supplier.setUpdateTime(new Date());

        String res;
        try {
            supplierService.ModSupplier(supplier);
            res = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            res = "false";
        }
        return res;
    }
}
