package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.controller.vo.newpdavo.SaleScanCodeVo;
import com.hvisions.mes.domain.DispatchList;
import com.hvisions.mes.domain.DispatchLists;
import com.hvisions.mes.domain.SaleCodeRef;
import com.hvisions.mes.dto.SaleCodeRefDTO;

import java.util.List;

/**
 * @author dpeng
 * @description 销售出库接口
 * @date 2019-08-12 17:18
 */
public interface INewPdaSaleService {

    int cutSaleCodeRef(SaleCodeRefDTO refDTO);

    /**
     *  获取销售发货单主表信息
     * @param storeroomId 库房ID
     * @return            主表信息
     */
    List<DispatchList> queryDispatchList(Integer storeroomId);

    /**
     *  通过单据编号查询销售发货单子表信息
     * @param storeroomId  库房ID
     * @param cCode 主表单据编号
     * @return      子表信息
     */
    List<DispatchLists> queryDispatchListsByCCode(Integer storeroomId,String cCode);

    /**
     *  销售出库扫二维码逻辑
     * @param qrCode       成品码(扫的二维码)
     * @param parentId     主表ID
     * @param childId      子表ID
     * @return             是否匹配和数量
     */
    SaleScanCodeVo saleScanCodeLogic(String qrCode,Integer parentId,Integer childId);

    void appendSaleCodeRef(SaleCodeRef ref);

    /**
     *  销售出库
     * @param parentId     主表ID
     * @param storeroomId  子表ID
     * @param userId       用户ID
     */
    void saleOutStoreroom(Integer parentId,Integer storeroomId,Integer userId,Integer teamId);

}
