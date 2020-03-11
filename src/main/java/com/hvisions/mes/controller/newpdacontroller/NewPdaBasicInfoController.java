package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.service.INewPdaService.INewPdaBasicInfoService;
import com.hvisions.mes.util.JsonStringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PDA端基础信息 控制器
 *
 * @author dpeng
 * @create 2019-05-21 13:30
 */
@RestController
@RequestMapping(value = "/json/pda/information")
@Api(description = "NewPdaBasicInfoController | PDA基础信息控制器")
public class NewPdaBasicInfoController {

    private PdaResponseData pdaResponseData;
    private Map<String, Object> data;

    @Autowired
    private INewPdaBasicInfoService basicInfoService;

    /**
     *  初始化方法    每次调用该类的其他控制器 都会先走这个方法
     */
    @ModelAttribute
    public void init() {
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }


    @ApiOperation(value = "通过物料分类名称或者物料分类码查询物料分类信息")
    @ApiImplicitParam(paramType = "query",name = "json",value = "json字符串传入物料分类名称或者物料分类编码 格式{'msg':''}",dataType = "String")
    @RequestMapping(value = "/getInventoryClassByNameOrCode",method = RequestMethod.GET)
    public PdaResponseData getInventoryClassByNameOrCode(String json) {
        String msg = JsonStringUtil.analyseJsonString(json,"msg",String.class);
        List<Inventoryclass> list = basicInfoService.queryInventoryClassByNameOrCode(msg);
        data.put("list",list);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    @ApiOperation(value = "通过物料分类码和物料名称或者物料码查询物料信息")
    @ApiImplicitParam(paramType = "query",name = "json",value = "json字符串传入物料分类编码和物料名称或者物料码 格式{'msg':'','cinvccode':''}",dataType = "String")
    @RequestMapping(value = "/getInventoryByNameOrCode",method = RequestMethod.GET)
    public PdaResponseData getInventoryByNameOrCode(String json) {
        String msg = JsonStringUtil.analyseJsonString(json,"msg",String.class);
        String cinvccode = JsonStringUtil.analyseJsonString(json,"cinvccode",String.class);
        List<Inventory> list = basicInfoService.queryInventoryByNameOrCode(cinvccode,msg);
        data.put("list",list);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取产线基础信息
     * @return
     */
    @PostMapping(value = "/prodLine")
    public PdaResponseData getBasicLine() {
        List<Line> lines = basicInfoService.selectBasicLine();
        data.put("lines",lines);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取库房基础信息
     * @return
     */
    @PostMapping(value = "/storeroom")
    public PdaResponseData getStoreroom(@RequestBody(required = false) Emp emp) {
        List<Storeroom> storerooms = basicInfoService.selectBasicStoreroom(emp);
        data.put("storerooms",storerooms);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取工序基础信息
     * @return
     */
    @PostMapping(value = "/technics")
    public PdaResponseData getOrder() {
        List<OrderDetail> orderDetails = basicInfoService.selectBasicOrderDetail();
        data.put("technics",orderDetails);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取人员基础信息
     * @return
     */
    @PostMapping(value = "/users")
    public PdaResponseData getUsers() {
        List<Emp> emps = basicInfoService.selectBasicEmp();
        data.put("users",emps);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取权限   即 获取菜单
     * @param emp  员工
     * @return
     */
    @PostMapping(value = "/permissions")
    public PdaResponseData getPermissions(@RequestBody Emp emp) {
        if (emp == null || emp.getEmpId() == null) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }
        List<Menu> menus = basicInfoService.selectMenus(emp.getEmpId());
        data.put("menus",menus);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取设备基础信息
     * @return
     */
    @PostMapping(value = "/equips")
    public PdaResponseData getEquips() {
        List<Equipment> equipment = basicInfoService.selectBasicEquipment();
        data.put("equips",equipment);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取BOM信息    原料名模糊查询
     * @param material 原料对象  (需要原料名称  和 供应商ID)
     * @return
     */
    @PostMapping(value = "/materials")
    public PdaResponseData getMaterials(@RequestBody(required = false) Material material) {
        List<Material> materials = basicInfoService.selectBasicMaterial(material);
        data.put("materials",materials);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取供应商基础信息
     * @return
     */
    @PostMapping(value = "/suppliers")
    public PdaResponseData getSuppliers() {
        List<Supplier> suppliers = basicInfoService.selectBasicSupplier();
        data.put("suppliers",suppliers);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取包装基础信息
     * @return
     */
    @PostMapping("/goodsPack")
    public PdaResponseData getGoodsPack() {
        List<PackType> packTypes = basicInfoService.selectBasicPackType();
        data.put("goodsPack",packTypes);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  通过原料二维码获取原料信息
     * @param materialCode
     * @return
     */
    @PostMapping("/materialByCode")
    public PdaResponseData getMaterialByCode(@RequestBody String materialCode) {
        Material material = basicInfoService.selectBasicMaterialByCode(materialCode);
        data.put("material",material);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

}
