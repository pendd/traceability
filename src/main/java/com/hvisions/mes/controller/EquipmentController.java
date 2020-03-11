package com.hvisions.mes.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.domain.Supplier;
import com.hvisions.mes.service.impl.EquipmentServiceImpl;
import com.hvisions.mes.service.impl.LineServiceImpl;
import com.hvisions.mes.service.impl.SupplierServiceImpl;
import com.hvisions.mes.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/json/equipment")
@ApiIgnore
public class EquipmentController extends BaseController {
    @Autowired
    private EquipmentServiceImpl equipmentServiceImpl;
    @Autowired
    private LineServiceImpl lineService;
    @Autowired
    private SupplierServiceImpl supplierService;
    @Autowired
    private UserServiceImpl userService;

    // 分页查询设备
    @RequestMapping(value = "/equipmentlistpage", method = RequestMethod.GET)
    public Map<String, Object> equipmentListPage(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Equipment> data = equipmentServiceImpl.showEquipment(new Page<Equipment>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }

    // 增加设备
    @RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
    public String addEquipment(HttpServletRequest request, HttpServletResponse respon) {
        Equipment equipment = new Equipment();
        String res = "";
        String msg = null;
        try {
            int num = equipmentServiceImpl.findEquipmentName(request.getParameter("equipmentName"));

            if (num != 0) {
                res = "2";
            } else {
                equipment.setCreateTime(new Date());
                equipment.setLineId(Integer.valueOf(request.getParameter("lineId")));
                equipment.setEquipmentName(request.getParameter("equipmentName"));
                equipment.setEqptSn(request.getParameter("eqptSn"));
                equipment.setEqptType(request.getParameter("eqptType"));
                equipment.setEqptModel(request.getParameter("eqptModel"));
                equipment.setDeptName(request.getParameter("deptName"));
                equipment.setManufacturer(request.getParameter("manufacturer"));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date releaseDate = new Date();
                boolean isReleaseDateValid = true;
                try {
                    releaseDate = df.parse(request.getParameter("releaseDate"));
                } catch (Exception e) {
                    isReleaseDateValid = false;
                }
                if (isReleaseDateValid) {
                    equipment.setReleaseDate(releaseDate);
                }
                Date serviceLimit = new Date();
                boolean isServiceLimitValid = true;
                try {
                    serviceLimit = df.parse(request.getParameter("serviceLimit"));
                } catch (Exception e) {
                    isServiceLimitValid = false;
                }
                if (isServiceLimitValid) {
                    equipment.setServiceLimit(serviceLimit);
                }
                equipment.setEqptWeight(request.getParameter("eqptWeight"));
                equipment.setQuantityUnit(request.getParameter("quantityUnit"));
                equipment.setUserId(getCurrentUserID());
                equipment.setUpdateTime(new Date());
                equipment.setUpdateUserId(getCurrentUserID());
                equipment.setSupplierId(Integer.valueOf(request.getParameter("supplierId")));
//                equipment.setPrincipal(userService.QueryByUserAccount(request.getParameter("principal")).getEmpId());
                equipment.setPrincipal(Integer.valueOf(request.getParameter("principal")));
                equipment.setTheoreticalYield(Integer.valueOf(request.getParameter("theoreticalYield")));
                equipmentServiceImpl.AddEquipment(equipment);
                res = "true";
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
            System.out.println(msg);
            res = "false";
        }
        return res;
    }

    // 删除设备
    @RequestMapping(value = "/removeEquipment", method = RequestMethod.POST)
    public String removeEquipment(@RequestParam("delIDs") List<String> delIDs) {
        String res = "true";

        try {

            for (int i = 0; i < delIDs.size(); i++) {
                equipmentServiceImpl.DelEquipment(Integer.valueOf(delIDs.get(i)));
            }

        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }

    // 修改设备
    @RequestMapping(value = "/editEquipment", method = RequestMethod.POST)
    public String editEquipment(String equipmentName, HttpServletRequest request,
            HttpServletResponse respon) {
        Equipment equipment = new Equipment();
        String res = "";
        String msg = null;
        try {
            equipment.setEquipmentId(Integer.valueOf(request.getParameter("equipmentId")));
            equipment.setEquipmentName(request.getParameter("equipmentName"));
            equipment.setLineId(Integer.valueOf(request.getParameter("lineId")));
            equipment.setUpdateTime(new Date());
            equipment.setUpdateUserId(getCurrentUserID());
            equipment.setSupplierId(Integer.valueOf(request.getParameter("supplierId")));
//                equipment.setPrincipal(userService.QueryByUserAccount(request.getParameter("principal")).getEmpId());
            equipment.setPrincipal(Integer.valueOf(request.getParameter("principal")));
            equipment.setEqptSn(request.getParameter("eqptSn"));
            equipment.setEqptType(request.getParameter("eqptType"));
            equipment.setEqptModel(request.getParameter("eqptModel"));
            equipment.setDeptName(request.getParameter("deptName"));
            equipment.setManufacturer(request.getParameter("manufacturer"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date releaseDate = new Date();
            boolean isReleaseDateValid = true;
            try {
                releaseDate = df.parse(request.getParameter("releaseDate"));
            } catch (Exception e) {
                isReleaseDateValid = false;
            }
            if (isReleaseDateValid) {
                equipment.setReleaseDate(releaseDate);
            }
            Date serviceLimit = new Date();
            boolean isServiceLimitValid = true;
            try {
                serviceLimit = df.parse(request.getParameter("serviceLimit"));
            } catch (Exception e) {
                isServiceLimitValid = false;
            }
            if (isServiceLimitValid) {
                equipment.setServiceLimit(serviceLimit);
            }
            equipment.setEqptWeight(request.getParameter("eqptWeight"));
            equipment.setQuantityUnit(request.getParameter("quantityUnit"));
            equipment.setTheoreticalYield(Integer.valueOf(request.getParameter("theoreticalYield")));
            equipmentServiceImpl.ModEquipment(equipment);
            res = "true";

        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }

    // 设备下拉框
    @RequestMapping(value = "/combox", method = RequestMethod.GET)
    public List<Line> getCombox(HttpServletRequest request, HttpServletResponse response) {

        return lineService.querylineCombobox();
    }

    // 供应商下拉框
    @RequestMapping(value = "/comsubox", method = RequestMethod.GET)
    public List<Supplier> getComsubox(HttpServletRequest request, HttpServletResponse response) {

        return supplierService.querySupplierCombobox();
    }

    @GetMapping("/getEquipmentByOrderId")
    public List<Equipment> getEquipmentByOrderId(@RequestParam(value = "orderId",required = false) Integer orderId) {
        return equipmentServiceImpl.queryEquipmentByOrderId(orderId);
    }
}
