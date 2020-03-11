package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  配料
 *
 * @author dpeng
 * @create 2019-06-10 16:32
 */
@Mapper
@Repository
public interface NewPdaAssignMaterialMapper {

    /** 库房配料任务列表 */
    List<CallOff> selectCallOffList(Integer storeroomId);

    /** 一个任务对应的具体的物料明细 */
    List<Material> selectCallOffMaterialList(@Param("produceNumber") String produceNumber,@Param("storeroomId") Integer storeroomId);

    /** 通过二维码 判断是是否是原料包装关联表中的第一层包装码 */
    MaterialPackRef selectMaterialPackRefByMaterialCode(String materialCode);

    /** 通过二维码 判断是否是原料包装表中的包装码 */
    MaterialPack selectMaterialPackByCode(String code);

    /** 在原料包装表中获取子包装 */
    List<MaterialPack> selectMaterialPack(String secondCode);

    /**  批量查询 通过第一层包装编码获取所有的原料码 */
    List<MaterialPackRef> selectMaterialPackRef(List<String> firstCode);

    /** 原料出库 */
    void insertMaterialHistory(List<MaterialStoreroomHistory> list);

    /** 产线配料任务列表 */
    List<CallOff> selectCallOffOrderList(Integer userId);

    /**
     * 通过用户ID 获取配料列表
     */
    List<CallOff> selectCallOffListByUserId(@Param("userId")Integer userId, @Param("workNumber")String workNumber);

    /** 更新配料状态 */
    void updateCallOffState(@Param("produceNumber") String produceNumber,@Param("state") Integer state);

    /** 更新备料状态 */
    void updateCallOffState1(@Param("produceNumber") String produceNumber,@Param("state") Integer state,@Param("materialId")Integer materialId,@Param("storeroomId") Integer storeroomId);

    /**
     * 通过叫料表ID更新出库状态
     *
     * @param callOffId 叫料表ID
     * @param state     出库状态
     */
    void updateCallOffStateByCallOffId(@Param("callOffId")Integer callOffId, @Param("state")Integer state);
    
    /**
     *  新增配料表记录
     * @param callOff
     */
    void insertCallOff(List<CallOff> callOff);

    /**
     *  从原料出入库历史表中通过原料二维码获取对应的数量
     * @param qrCode   原料二维码
     * @return         袋子中还剩余数量
     */
    double selectAmountByQrCode(String qrCode);

    /**
     *  通过原料二维码表关联原料表获取原料规格
     * @param qrCode  原料二维码
     * @return  原料规格
     */
    double selectSpecsByQrCode(String qrCode);

    /**
     * 通过原料二维码判断是否存在 并且供应商ID和原批次号不为null   如果查询出来表明该码已入库
     * @param qrCode  原料二维码
     * @return        原料二维码对象
     */
    MaterialCode selectMaterialCodeByQrCode(String qrCode);

    /**
     *  在配料二维码关系表中通过配料表ID查询记录数
     * @param callOffId   叫料表ID
     * @return            记录数
     */
    Integer selectAssignRefCountByCallOffId(Integer callOffId);

    /**
     *  判断这个原料二维码是否被扫过
     * @param qrCode  原料二维码
     * @return        对象
     */
    AssignMaterialQrRef selectAssignMaterialQrRefByQrCode(String qrCode);

    /**
     *  新增配料任务原料二维码记录
     * @param qrRef 记录对象
     */
    void insertAssignMaterialQrRef(AssignMaterialQrRef qrRef);

    /**
     * 通过生产订单编号和子件表ID查询出库单记录
     *
     * @param cMoCode 生产订单编号
     * @param modId   子件表ID
     * @return 子件表记录
     */
    Rdrecords11 selectRdrecordsByCMoCodeAndModId(@Param("cMoCode")String cMoCode, @Param("modId")Integer modId);

    /**
     *  通过工单号获取生产订单号
     * @param workNumber  工单号
     * @return            订单号
     */
    String selectCMoCodeByWorkNumber(String workNumber);

    /**
     * 通过库房ID和工单号获取所有的子件ID
     *
     * @param workNumber  工单号
     * @param storeroomId 库房ID
     * @return            叫料表对象
     */
    List<CallOff> selectAllocateIdByWorkNumberAndStoreroomId(@Param("workNumber")String workNumber, @Param("storeroomId")Integer storeroomId);

    /**
     *  通过叫料表ID查询原料二维码以及出库数量
     * @param callOffId  叫料表ID
     * @return           原料二维码和出库数量
     */
    List<AssignMaterialQrRef> selectQrRefByCallOffId(Integer callOffId);

    /**
     *  通过原料二维码获取原料信息(入库所需的所有信息)
     * @param qrCode  原料二维码
     * @return        原料入库所需信息
     */
    MaterialStoreroomHistory selectMaterialHistoryByQrCode(String qrCode);

    /**
     * 通过原料ID在原料出入库历史表中查询所有时间小于createTime的入库和回库的物料二维码
     *
     * @param materialId 物料ID
     * @param createTime 时间
     * @param batchId    批次ID
     * @return 原料二维码集合
     */
    List<String> selectQrCodeByMaterialId(@Param("materialId") Integer materialId, @Param("createTime") Date createTime, @Param("batchId")Integer batchId);

    /**
     *  通过物料二维码查询物料的最早入库时间以及批次ID
     * @param qrCode 物料二维码
     * @return       最早入库时间和批次ID
     */
    MaterialStoreroomHistory selectOneByQrCodeTop(String qrCode);

    /**
     * 通过物料二维码查询原料入库+回库  或 出库 数据
     *
     * @param qrCode    物料二维码n
     * @param inOutType 0 入库+回库  1 出库
     * @return 记录
     */
    MaterialStoreroomHistory selectAllByQrCode(@Param("qrCode")String qrCode, @Param("inOutType")Integer inOutType);

    /**
     *  删除配料出库扫码记录
     * @param ref
     * @return
     */
    int deleteAssignMaterialQrRef(AssignMaterialQrRef ref);

}
