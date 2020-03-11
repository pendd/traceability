package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.GoodsStoreroomRefDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaCodingMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaGoodsMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaOrderMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaGoodsService;
import com.hvisions.mes.util.RandomNumber;
import com.hvisions.mes.util.StringUtil;
import com.hvisions.mes.util.UUIDGenerator;
import com.hvisions.mes.xa.postgresqlMapper.GoodsPostgreMapper;
import com.hvisions.mes.xa.postgresqlMapper.XaBasicInfoMapper;
import com.hvisions.mes.xa.postgresqlMapper.XaGoodsMapper;
import com.hvisions.mes.xa.sqlServerMapper.GoodsSqlServerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @create 2019-05-30 17:00
 */
@Service
public class NewPdaGoodsServiceImpl implements INewPdaGoodsService {

    @Autowired
    private NewPdaGoodsMapper goodsMapper;

    @Autowired
    private NewPdaOrderMapper orderMapper;

    @Autowired
    private NewPdaCodingMapper codingMapper;

    @Autowired
    private XaGoodsMapper xaGoodsMapper;

    @Autowired
    private GoodsSqlServerMapper goodsSqlServerMapper;

    @Autowired
    private GoodsPostgreMapper goodsPostgreMapper;

    @Autowired
    private XaBasicInfoMapper xaBasicInfoMapper;

    /**
     *  删除成品入库扫码记录
     * @param refDTO
     * @return
     */
    @Override
    public int cutGoodsStoreroomRef(GoodsStoreroomRefDTO refDTO) {
        GoodsStoreroomRef ref = new GoodsStoreroomRef();
        BeanUtils.copyProperties(refDTO,ref);
        return goodsMapper.deleteGoodsStoreroomRef(ref);
    }

    /**
     *  成品包装信息批量提交
     * @param goodsPacks  成品包装信息对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
    public void appendGoodsPackList(List<GoodsPack> goodsPacks) {

        Date time = new Date();

        // 获取firstCode为成品码的所有对象
        List<GoodsPack> firstCodes = new ArrayList<>();

        // 获取firstCode为包装编码的所有对象
        List<GoodsPack> packs = new ArrayList<>();

        goodsPacks.forEach(g -> {
            g.setCreateTime(time);
            PackCode packCode = codingMapper.selectPackCodeByCode(g.getFirstCode());
            if (packCode == null) {
                // 不是包装码
                firstCodes.add(g);
            }else {
                // 包装码
                g.setPackTypeId(packCode.getPackTypeId());
                packs.add(g);
            }
        });


        if (firstCodes.size() != 0) {
            // 包装完成以后  包装工序才算完事
            orderMapper.insertBatchOrderHistory(firstCodes);

            // 移除掉成品码对象中的secondCode中为null的对象
            firstCodes.removeIf(e -> StringUtil.isNull(e.getSecondCode()));

            //  剩下来的就是 成品码和第一层包装之间的关系了
            if (firstCodes.size() != 0) {
                goodsMapper.insertGoodsPackRef(firstCodes);
            }

        }

        if (packs.size() != 0) {
            goodsMapper.insertGoodsPack(packs);
        }
    }

    /**
     *  成品入库
     * @param cMoCode     生产订单号
     * @param userId      用户ID
     * @param teamId      班组ID
     * @param storeroomId 库房ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "jtaTransaction")
    public void appendGoodsInStoreroom(String cMoCode,Integer userId,Integer teamId,Integer storeroomId) {

        List<GoodsInStoreroom> list = new ArrayList<>();

        // 通过生产订单号获取所有的已扫过的二维码
        List<GoodsStoreroomRef> refs = xaGoodsMapper.selectQrGoodsCodeByCMoCode(cMoCode);

        List<GoodsStoreroomRef> codeList= new ArrayList<>();

        refs.forEach(e -> {
            // 判断是否是包装码
            int type = guessGoodsCode(e.getQrGoodsCode());
            if (type == 1) {
                // 包装码  获取第一层包装编码
                List<String> firstPackCodes = getMinFirstCode(e.getQrGoodsCode());
                List<String> goodsCodes = goodsMapper.selectGoodsCodeByFirstCode(firstPackCodes.get(0));

                goodsCodes.forEach(g -> {
                    GoodsStoreroomRef ref = new GoodsStoreroomRef();
                    ref.setQrGoodsCode(g);
                    ref.setMaterialSignCode(e.getMaterialSignCode());
                    codeList.add(ref);
                });

            }else {
                // 成品码
                GoodsStoreroomRef goodsRef = new GoodsStoreroomRef();
                goodsRef.setQrGoodsCode(e.getQrGoodsCode());
                goodsRef.setMaterialSignCode(e.getMaterialSignCode());
                codeList.add(goodsRef);
            }
        });

        codeList.forEach(e -> {
            GoodsInStoreroom gis = new GoodsInStoreroom();
            gis.setUserId(userId.longValue());
            gis.setTeamId(teamId.longValue());
            gis.setStoreroomId(storeroomId.longValue());
            gis.setGoodsCode(e.getQrGoodsCode());
            list.add(gis);
        });

        // 成品入库
        xaGoodsMapper.insertGoodsInStoreroom(list);

        // 更新生产订单表为已入库
        xaGoodsMapper.updateMomOrderDeailByCMoCode(cMoCode);

        // 通过生产订单号获取生产工单号
        List<String> workNumbers = xaGoodsMapper.selectWorkNumberByCMoCode(cMoCode);

        // 按产品编码分组
        Map<String, List<GoodsStoreroomRef>> codeByMaterialSignCode = codeList.stream().collect(Collectors.groupingBy(GoodsStoreroomRef::getMaterialSignCode));

        codeByMaterialSignCode.forEach((k,v) -> {
            // 通过产品编号和生产工单号以及时间判断该产品在产品记录表中是否存在
            String workNumber = "";
            GoodsTotal goodsTotal = new GoodsTotal();
            for (String s : workNumbers) {
                GoodsTotal total = xaGoodsMapper.selectGoodsTotalByQrGoodsCode(k,s,new Date());
                if (total != null) {
                    workNumber = s;
                    goodsTotal = total;
                    break;
                }
            }

            if (!"".equals(workNumber)) {
                // 存在 更新
                goodsTotal.setQuantity(goodsTotal.getQuantity().add(new BigDecimal(v.size())));
                xaGoodsMapper.updateGoodsTotal(goodsTotal);
            } else {
                // 不存在 新增
                goodsTotal.setQuantity(new BigDecimal(v.size()));
                goodsTotal.setTypeStatistics(0);
                goodsTotal.setProduceDate(new Date());
                goodsTotal.setProduceNumber(workNumber);
                goodsTotal.setGoodsCode(k);
                xaGoodsMapper.insertGoodsTotal(goodsTotal);
            }
        });

        // 子表
        List<MomOrderDetail> momOrderDetailList = goodsPostgreMapper.selectMomOrderDeailListByCode(cMoCode);

        Person people = goodsPostgreMapper.selectDepCodeByEmpId(userId);

        int maxId = goodsPostgreMapper.selectMaxIdFromRdrecords10();

        String cGuid = UUIDGenerator.getUpperCaseUUID();
        String cCode = RandomNumber.getRandomNumber();

        // 先入子表
        for (MomOrderDetail momOrderDetail : momOrderDetailList) {
            Rdrecords10 rdrecords10 = new Rdrecords10();

            rdrecords10.setCaccId(momOrderDetailList.get(0).getcAccId());
            rdrecords10.setCguid(cGuid);
            rdrecords10.setCcode(cCode);
            rdrecords10.setId(maxId);
            rdrecords10.setCinvcode(momOrderDetail.getCInvCode());
            rdrecords10.setIquantity(momOrderDetail.getiModQty());
            /**
             *  批号 保质期 生产日期
             */
            rdrecords10.setModid(momOrderDetail.getModId());
            rdrecords10.setCmocode(cMoCode);
            rdrecords10.setImodrowno(momOrderDetail.getiModRowNo());
            rdrecords10.setDcreatedate(new Date());
            rdrecords10.setBerpisread(0);
            rdrecords10.setBistoerp(0);

            goodsSqlServerMapper.insertRdrecords10(rdrecords10);
            goodsPostgreMapper.insertRdrecords10(rdrecords10);

        }

        Rdrecord10 rdrecord10 = new Rdrecord10();
        rdrecord10.setId(maxId);
        rdrecord10.setCaccId(momOrderDetailList.get(0).getcAccId());
        rdrecord10.setCguid(cGuid);
        rdrecord10.setCcode(cCode);
        rdrecord10.setDdate(new Date());
        rdrecord10.setCdepcode(people.getCDepCode());
        rdrecord10.setCwhcode(xaBasicInfoMapper.selectStoreroomById(storeroomId).getStoreroomCode());
        rdrecord10.setIsubrows(momOrderDetailList.size());
        rdrecord10.setCmaker(people.getCPersonCode());
        rdrecord10.setDcreatedate(new Date());
        rdrecord10.setBerpisread(0);
        rdrecord10.setBistoerp(0);

        goodsSqlServerMapper.insertRdrecord10(rdrecord10);
        goodsPostgreMapper.insertRdrecord10(rdrecord10);

    }

    /**
     *  查询所有生产订单主表信息
     * @return  生产订单编号
     */
    @Override
    public List<MomOrder> queryMomOrder() {
        return goodsMapper.selectMomOrder();
    }

    /**
     *  通过生产订单编号查询子表信息
     * @param cMoCode 生产订单编号
     * @return        子表信息
     */
    @Override
    public List<MomOrderDetail> queryMomOrderDetailByCMoCode(String cMoCode) {
        List<MomOrderDetail> list = goodsMapper.selectMomOrderDeailByCMoCode(cMoCode);
        list.forEach(e -> e.setScanAmount(goodsMapper.selectCountByCMoCodeAndMaterialSignCode(e.getCInvCode(), cMoCode)));
        return list;
    }

    /**
     *  新增成品入库扫码记录
     * @param ref  记录对象
     */
    @Override
    public void appendGoodsStoreroomRef(GoodsStoreroomRef ref) {
        goodsMapper.insertGoodsStoreroomRef(ref);
    }

    /**
     *  成品入库扫码返回数量
     * @param qrCode 成品码
     * @return       数量
     */
    @Override
    public int selectCountByQrCode(String qrCode) {
        // 判断是否是包装码
        int type = guessGoodsCode(qrCode);
        if (type == 0) {
            // 不是包装码 是成品码
            return 1;
        }else {
            // 包装码  获取第一层包装编码
            int amount = 0;
            List<String> firstCode = getMinFirstCode(qrCode);
            for (String e : firstCode) {
                List<String> goodsCodeList = goodsMapper.selectGoodsCodeByFirstCode(e);
                amount += goodsCodeList.size();
            }
            return amount;
        }
    }


    /**
     *  获取第一层包装编码
     * @param secondCode 包装码
     * @return 第一层包装码集合
     */
    @Override
    public List<String> getMinFirstCode(String secondCode) {

        List<String> list = new ArrayList<>();

        List<GoodsPack> goodsPacks = goodsMapper.selectGoodsPack(secondCode);
        if (goodsPacks.size() == 0) {
            // 表示没有子包装  这是第一层包装
            list.add(secondCode);
        } else {
            // 有子包装
            for (GoodsPack pack : goodsPacks) {
                List<String> minFirstCode = getMinFirstCode(pack.getFirstCode());
                list.addAll(minFirstCode);
            }
        }
        return list;
    }

    /**
     *  通过二维码 判断是否是包装码
     * @param qrCode  二维码
     * @return        0 不是包装码  1 是包装码
     */
    @Override
    public int guessGoodsCode(String qrCode) {
        PackCode packCode = codingMapper.selectPackCodeByCode(qrCode);
        return packCode == null ? 0 : 1;
    }

    /**
     *  成品入库扫码逻辑
     * @param qrCode  成品码或者成品包装码
     * @param cInvCode   产品码即物料码
     * @return flag 0 不匹配  1 匹配  2 此包装码内没有包装成品码
     */
    @Override
    public String scanGoodsCodeLogic(String qrCode, String cInvCode) {

        String flag;

        // 判断是否是包装码
        int type = guessGoodsCode(qrCode);
        if (type == 1) {
            // 包装码  获取第一层包装编码
            List<String> firstPackCodes = getMinFirstCode(qrCode);
            List<String> goodsCodes = goodsMapper.selectGoodsCodeByFirstCode(firstPackCodes.get(0));
            if (goodsCodes.isEmpty()) {
                // 此包装码内没有包装成品码
                flag = "2";
            }else {
                // 有成品
                OrderHistory history = goodsMapper.selectOrderHistroyByProductCode(goodsCodes.get(0));
                if (history != null && Objects.equals(cInvCode,history.getGoodsCode())) {
                    // 匹配
                    flag= "1";
                }else {
                    // 不匹配
                    flag = "0";
                }
            }
        }else {
            // 成品码
            OrderHistory history = goodsMapper.selectOrderHistroyByProductCode(qrCode);
            if (history != null && Objects.equals(cInvCode,history.getGoodsCode())) {
                // 匹配
                flag= "1";
            }else {
                // 不匹配
                flag = "0";
            }
        }
        return flag;
    }
}
