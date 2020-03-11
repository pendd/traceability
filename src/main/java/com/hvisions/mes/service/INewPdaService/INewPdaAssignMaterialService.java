package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.controller.vo.newpdavo.AssignMaterialScanQrCodeVo;
import com.hvisions.mes.domain.AssignMaterialQrRef;
import com.hvisions.mes.domain.CallOff;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.Stock;
import com.hvisions.mes.dto.AssignMaterialQrRefDTO;

import java.util.List;

/**
 * 配料
 *
 * @author dpeng
 * @create 2019-06-10 16:33
 */
public interface INewPdaAssignMaterialService {

    /** 配料任务列表 */
    List<CallOff> selectCallOffStoreList(Integer storeroomId);

    /** 一个任务对应的具体的物料明细 */
    List<Material> selectCallOffMaterialList(String produceNumber, Integer storeroomId);

    /** 扫二维码出库逻辑 */
    AssignMaterialScanQrCodeVo selectCallOffLogic(Integer storeroomId,String qrCode, Integer materialId);

    /** 配料任务列表 产线 */
    List<CallOff> selectCallOffOrderList(Integer userId);

    /** 配料出库列表 （工序人员）*/
    List<CallOff> selectCallOffList(Integer userId,String workNumber);

    /** 更新配料状态 */
    void updateCallOffState(String produceNumber,Integer state);

    /**
     *  通过原料二维码获取袋子中剩余数量
     * @param qrCode   原料二维码
     * @return         袋子中还剩余数量
     */
    int queryAmountPlusSpecsByQrCode(String qrCode);

    /**
     *  新增配料任务原料二维码扫码记录
     * @param qrRef  记录对象
     */
    void appendAssignMaterialQrRef(AssignMaterialQrRef qrRef);

    /**
     *  备料出库
     * @param produceNumber  生产工单号
     * @param storeroomId    库房ID
     * @param userId         用户ID
     * @param teamId         班组ID
     * @return               是否出库成功 0 成功  1 出库失败,没有发现材料出库单
     */
    int assignMaterialOut(String produceNumber,Integer storeroomId,Integer userId,Integer teamId);

    /**
     *  判断库房对象是否存在  存在则修改 不存在新增
     * @param materialSignCode   原料编码
     * @param stock              传入的库房对象
     * @param amount             数量
     */
    void isExistStock(String materialSignCode, Stock stock, int amount);

    /**
     *  判断是否有这种物料比当前时间还小的并且不是一个批次的
     * @param materialId 物料ID
     * @param qrCode     物料二维码
     * @return           0 没有  1有
     */
    int isHaveEarly(Integer materialId, String qrCode);

    int cutAssignMaterialQrRef(AssignMaterialQrRefDTO refDTO);
}
