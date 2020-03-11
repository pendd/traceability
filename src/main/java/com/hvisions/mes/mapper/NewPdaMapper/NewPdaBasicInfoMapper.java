package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  PDA 端基本信息 Mapper
 *
 * @author dpeng
 * @create 2019-05-20 13:50
 */

@Mapper
@Repository
public interface NewPdaBasicInfoMapper {

    /**
     *  通过物料分类名称或者物料分类码查询物料分类信息
     * @param msg  物料分类名称或者物料分类码
     * @return 物料分类对象集合
     */
    List<Inventoryclass> selectInventoryClassByNameOrCode(String msg);

    /**
     * 通过物料分类码和(物料码或物料名称查询物料信息)
     *
     * @param cinvccode 物料分类码
     * @param msg       物料码或物料名称
     * @return 物料对象集合
     */
    List<Inventory> selectInventoryByNameOrCode(@Param("cinvccode")String cinvccode, @Param("msg")String msg);

    /** 获取产线 */
    List<Line> selectBasicLine();

    /** 获取库房 */
    List<Storeroom> selectBasicStoreroom(Emp emp);

    /** 获取工序 */
    List<OrderDetail> selectBasicOrderDetail();

    /** 获取人员 */
    List<Emp> selectBasicEmp();

    /**
     *  获取权限   一级菜单 + 二级菜单
     */
    // 一级菜单
    List<Menu> selectFirstMenu(Integer empId);

    // 二级菜单
    List<Menu> selectSecondMenu(@Param("empId") Integer empId, @Param("menuId") String menuId);

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

    /** 通过原料ID 获取原料基本信息 */
    Material selectBasicMaterialById(Integer materialId);

    /** 通过库房ID 获取库房编码 */
    Storeroom selectStoreroomById(Integer storeroomId);

    /** 往库存表中添加记录 */
    void insertStock(Stock stock);

    /** 通过code和库房编码查询库存表中是否存在该记录 */
    Stock selectStockByCode(@Param("code") String code,@Param("storeroomCode")String storeroomCode);

    /** 更新库存表 */
    void updateStock(Stock stock);

    /**
     *  通过员工ID查询员工信息
     * @param userId 员工ID
     * @return       员工信息
     */
    Emp selectEmpByUserId(Integer userId);

    /**
     *  通过物料二维码 获取物料信息
     * @param code
     * @return
     */
    Material selectMaterialFromMaterialCode(String code);

    /**
     *  通过成品二维码获取成品信息
     * @param goodsCode  成品二维码
     * @return
     */
    Material selectMaterialByGoodsCode(String goodsCode);

}
