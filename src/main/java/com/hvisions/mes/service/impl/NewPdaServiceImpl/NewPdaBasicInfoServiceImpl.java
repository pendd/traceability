package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaBasicInfoMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dpeng
 * @create 2019-05-21 11:07
 */

@Service
public class NewPdaBasicInfoServiceImpl implements INewPdaBasicInfoService {

    @Autowired
    private NewPdaBasicInfoMapper basicInfoMapper;

    /**
     *  通过物料分类名称或者物料分类码查询物料分类信息
     * @param msg  物料分类名称或者物料分类码
     * @return 物料分类对象集合
     */
    @Override
    public List<Inventoryclass> queryInventoryClassByNameOrCode(String msg) {
        return basicInfoMapper.selectInventoryClassByNameOrCode(msg);
    }

    /**
     * 通过物料分类码和(物料码或物料名称查询物料信息)
     * @param cinvccode 物料分类码
     * @param msg       物料码或物料名称
     * @return 物料对象集合
     */
    @Override
    public List<Inventory> queryInventoryByNameOrCode(String cinvccode, String msg) {
        return basicInfoMapper.selectInventoryByNameOrCode(cinvccode,msg);
    }

    // 获取产线
    @Override
    public List<Line> selectBasicLine() { return basicInfoMapper.selectBasicLine(); }

    // 获取库房
    @Override
    public List<Storeroom> selectBasicStoreroom(Emp emp) {
        return basicInfoMapper.selectBasicStoreroom(emp);
    }

    // 获取工序
    @Override
    public List<OrderDetail> selectBasicOrderDetail() {
        return basicInfoMapper.selectBasicOrderDetail();
    }

    // 获取人员
    @Override
    public List<Emp> selectBasicEmp() {
        return basicInfoMapper.selectBasicEmp();
    }

    // 获取权限   一级菜单 + 二级菜单
    @Override
    public List<Menu> selectMenus(Integer empId) {
        // 一级 + 二级菜单
        List<Menu> list = new ArrayList<>();

        // 一级菜单
        List<Menu> firstMenus = basicInfoMapper.selectFirstMenu(empId);

        for (Menu firstMenu : firstMenus) {
            List<Menu> secondMenus = basicInfoMapper.selectSecondMenu(empId, firstMenu.getMenuId());

            firstMenu.setSecondMenus(secondMenus);

            list.add(firstMenu);
        }
        return list;
    }

    // 获取设备
    @Override
    public List<Equipment> selectBasicEquipment() {
        return basicInfoMapper.selectBasicEquipment();
    }

    // 获取BOM
    @Override
    public List<Material> selectBasicMaterial(Material material) { return basicInfoMapper.selectBasicMaterial(material); }

    // 获取供应商
    @Override
    public List<Supplier> selectBasicSupplier() {
        return basicInfoMapper.selectBasicSupplier();
    }

    // 获取包装类型
    @Override
    public List<PackType> selectBasicPackType() { return basicInfoMapper.selectBasicPackType(); }

    /**
     *  通过原料二维码获取BOM
     * @param materialCode
     * @return
     */
    @Override
    public Material selectBasicMaterialByCode(String materialCode) {
        return basicInfoMapper.selectBasicMaterialByCode(materialCode);
    }
}
