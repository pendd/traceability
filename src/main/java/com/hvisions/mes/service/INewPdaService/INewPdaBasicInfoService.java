package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.*;

import java.util.List;

/**
 *  PDA 端基本信息
 *
 * @author dpeng
 * @create 2019-05-21 11:04
 */

public interface INewPdaBasicInfoService {

    /**
     *  通过物料分类名称或者物料分类码查询物料分类信息
     * @param msg  物料分类名称或者物料分类码
     * @return 物料分类对象集合
     */
    List<Inventoryclass> queryInventoryClassByNameOrCode(String msg);

    /**
     * 通过物料分类码和(物料码或物料名称查询物料信息)
     * @param cinvccode 物料分类码
     * @param msg       物料码或物料名称
     * @return 物料对象集合
     */
    List<Inventory> queryInventoryByNameOrCode(String cinvccode, String msg);

    // 获取产线
    List<Line> selectBasicLine();

    // 获取库房
    List<Storeroom> selectBasicStoreroom(Emp emp);

    // 获取工序
    List<OrderDetail> selectBasicOrderDetail();

    // 获取人员
    List<Emp> selectBasicEmp();

    /**
     *  获取权限   一级菜单 + 二级菜单
     */
    List<Menu> selectMenus(Integer empId);

    // 获取设备
    List<Equipment> selectBasicEquipment();

    // 获取BOM
    List<Material> selectBasicMaterial(Material material);

    // 获取供应商
    List<Supplier> selectBasicSupplier();

    // 获取包装类型
    List<PackType> selectBasicPackType();

    /** 通过原料二维码获取BOM */
    Material selectBasicMaterialByCode(String materialCode);
}
