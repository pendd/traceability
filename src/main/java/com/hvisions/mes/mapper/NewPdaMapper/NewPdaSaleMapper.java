package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.DispatchList;
import com.hvisions.mes.domain.DispatchLists;
import com.hvisions.mes.domain.SaleCodeRef;
import com.hvisions.mes.domain.SaleOutStoreroom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dpeng
 * @description 销售出库mapper
 * @date 2019-08-12 17:17
 */
public interface NewPdaSaleMapper {

    int deleteSaleCodeRef(SaleCodeRef ref);

    /**
     *  获取销售发货单主表信息
     * @param storeroomCode 库房编码
     * @return  主表信息
     */
    List<DispatchList> selectDispatchList(String storeroomCode);

    /**
     * 通过单据编号查询销售发货单子表信息
     * @param storeroomCode 库房编码
     * @param cCode 主表单据编号
     * @return 子表信息
     */
    List<DispatchLists> selectDispatchListsByCCode(@Param("storeroomCode")String storeroomCode, @Param("cCode")String cCode);

    /**
     *  新增销售发货扫码记录
     * @param ref 对象
     */
    void insertSaleCodeRef(SaleCodeRef ref);

    List<SaleCodeRef> selectSaleCodeRefByParentIdAndStoreroomId(@Param("parentId")Integer parentId, @Param("storeroomId")Integer storeroomId);

    /**
     * 通过主表ID和子表ID查询总记录数
     *
     * @param parentId 主表ID
     * @param childId  子表ID
     * @return 记录数
     */
    int selectCountByParentIdAndChildId(@Param("parentId")Integer parentId, @Param("childId")Integer childId);

    /**
     *  通过主表ID和子表ID查询子表信息
     * @param parentId  主表ID
     * @param childId   子表ID
     * @return          子表信息
     */
    DispatchLists selectDispatchListsByParentIdAndChildId(@Param("parentId")Integer parentId, @Param("childId")Integer childId);

    /**
     *  通过成品二维码获取产品编码
     * @param qrCode  成品二维码
     * @return        产品编码
     */
    String selectMaterialSignCodeByQrCode(String qrCode);

    /**
     *  销售出库历史记录
     * @param saleOutStoreroom
     */
    void insertSaleOutStoreroom(SaleOutStoreroom saleOutStoreroom);

}
