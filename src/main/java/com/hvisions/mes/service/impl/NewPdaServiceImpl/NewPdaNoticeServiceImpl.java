package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.alibaba.fastjson.JSON;
import com.hvisions.mes.config.WebSocketServer;
import com.hvisions.mes.controller.vo.PdaMessage;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaAssignMaterialMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaNoticeMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaWithdrawalMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaNoticeService;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @description PDA通知消息接口
 * @date 2019-07-17 11:09
 */
@Service
@Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
public class NewPdaNoticeServiceImpl implements INewPdaNoticeService {

    private PdaResponseData pdaResponseData = new PdaResponseData();
    private Map<String,Object> data = new HashMap<>(16);

    @Autowired
    private NewPdaNoticeMapper noticeMapper;

    @Autowired
    private NewPdaWithdrawalMapper withdrawalMapper;

    @Autowired
    private NewPdaAssignMaterialMapper assignMaterialMapper;

    /**
     *  定时通知任务数据插入
     */
    @Override
    public void noticeTask() {
        // 判断是否有不在安全库存范围的物料  有添加到消息表中
        addMaterialStockAlarm();

        // 判断是否有物料过期 如果过期 就添加到消息表中
//        addShelfLife();
    }

    /**
     *  把通知表信息提供接口给PDA调用
     * @return
     */
    @Override
    public PdaResponseData noticeToPda(Integer userId) {

        // 查询所有消息 历史
        List<Notice> notices = noticeMapper.selectNotice(userId);
        data.put("notices",notices);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  更新消息表为已读
     * @param notice
     */
    @Override
    public PdaResponseData modifyNotice(Notice notice) {
        noticeMapper.updateNotice(notice);
        return pdaResponseData;
    }


    /**
     *  往消息表中插入原料库存不在上下限范围数据
     */
    private void addMaterialStockAlarm() {
        // 超过安全库存上限
        List<Material> upMaterial = noticeMapper.selectAlarmUpMaterial();
        // 低于安全库存下限
        List<Material> downMaterial = noticeMapper.selectAlarmDownMaterial();

        Notice notice;
        String msg;

        for (Material material : upMaterial) {
            notice = new Notice();
            notice.setStoreroomId(material.getStoreroomId());
            msg = "物料 " + material.getMaterialName() + " 当前库存为 " + material.getActualCount() + " ,安全库存上限为 " + material.getAlarmStockUp() + " ,超过安全库存上限 " + (material.getActualCount() - material.getAlarmStockUp());
            notice.setNoticeMessage(msg);
            noticeSocket(notice);
        }

        for (Material material : downMaterial) {
            notice = new Notice();
            notice.setStoreroomId(material.getStoreroomId());
            msg = "物料 " + material.getMaterialName() + " 当前库存为 " + material.getActualCount() + " ,安全库存下限为 " + material.getAlarmStockDown() + " ,低于安全库存下限 " + (material.getAlarmStockDown() - material.getActualCount());
            notice.setNoticeMessage(msg);

            noticeSocket(notice);
        }
    }

    /**
     *  新增退料表记录
     * @param withdrawal
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
    public void appendWithdrawal(List<Withdrawal> withdrawal) {
        withdrawalMapper.insertWithdrawal(withdrawal);

        withdrawal.forEach(e -> {
            String storeroomName = withdrawalMapper.selectStoreroomNameByStoreroomId(e.getStoreroomId());
            Notice notice = new Notice();
            notice.setStoreroomId(e.getStoreroomId());
            String msg = storeroomName + "收到来自于工单号" + e.getWorkNumber() + "的退料任务";
            notice.setNoticeMessage(msg);

            noticeSocket(notice);
        });
    }

    /**
     *  新增配料表记录
     * @param callOff
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
    public void appendCallOff(List<CallOff> callOff,ProduceOrder produceOrder) {
        assignMaterialMapper.insertCallOff(callOff);

        Goods goods = withdrawalMapper.selectGoodsByGoodsCode(produceOrder.getCode());

        produceOrder.setGoodsId(goods.getGoodsId().longValue());
        produceOrder.setGoodsName(goods.getGoodsName());
        produceOrder.setOrderFullNameId(goods.getGoodsId().longValue());
        withdrawalMapper.insertProduceOrder(produceOrder);

        withdrawalMapper.updateMomDetail(produceOrder.getModId(),produceOrder.getCmocode());

        callOff.forEach(e -> {
            String storeroomName = withdrawalMapper.selectStoreroomNameByStoreroomId(e.getStoreroomId());
            Notice notice = new Notice();
            notice.setStoreroomId(e.getStoreroomId());
            String msg = storeroomName + "收到来自于工单号" + e.getWorkNumber() + "的备料任务";
            notice.setNoticeMessage(msg);

            noticeSocket(notice);
        });
    }

    /**
     *  退料超期通知
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
    public void noticeWithdrawalOverTime() {
        // 查询所有未到达并且未发送通知的退料任务
        List<String> workNumberList = noticeMapper.selectWithdrawalGroupByProduceNumber();

        workNumberList.forEach(w -> {
            // 通过工单号查询退料超期时间 判断当前记录是否已超期
            ProduceOrder produceOrder = noticeMapper.selectProduceOrderByWorkNumber(w);

            // 通过工单号查询退料表记录
            Withdrawal withdrawal = noticeMapper.selectWithdrawalByWorkNumber(w);

            int hours = Hours.hoursBetween(new DateTime(withdrawal.getCreateTime()), DateTime.now()).getHours();

            if (hours > produceOrder.getHoursWithdrawal()) {
                // 超过了退料超期时间 通知
                Notice notice = new Notice();
                notice.setStoreroomId(withdrawal.getStoreroomId());
                String msg = "工单号" + w + "的退料任务已经超期";
                notice.setNoticeMessage(msg);

                noticeSocket(notice);

                // 更新退料表表示已通知
                noticeMapper.updateWithdrawalSendOrNotByWithdrawalId(withdrawal.getWithdrawalId());
            }
        });
    }

    /**
     *  通知json处理
     * @param notice    消息 对象 (把消息插入到消息记录表中)
     */
    private void noticeSocket(Notice notice) {
        Notice isExist = noticeMapper.selectNoticeByNotice(notice);
        if (isExist == null) {
            // 不存在这条消息 插入
            noticeMapper.insertNotice(notice);
            PdaMessage pdaMessage = new PdaMessage();
            pdaMessage.setId(notice.getId());
            pdaMessage.setMsg(notice.getNoticeMessage());
            WebSocketServer.sendInfo(String.valueOf(noticeMapper.selectUserIdByStoreroomId(notice.getStoreroomId())), JSON.toJSONString(pdaMessage));
        }
    }

    /**
     *  往消息表中插入过了保质期的物料
     */
    private void addShelfLife(){
        // 查询所有入库+回库的物料信息
        List<MaterialStoreroomHistory> inList = noticeMapper.selectHistory(0);
        // 查询所有出库的物料信息
        List<MaterialStoreroomHistory> outList = noticeMapper.selectHistory(1);

        // 遍历 数量做减法 得到目前库房还有的物料信息
        List<MaterialStoreroomHistory> materials = inList.stream()
                .map(in -> outList.stream()
                        .filter(out -> Objects.equals(in.getMaterialCode(), out.getMaterialCode()))
                        .findFirst()
                        .map(out -> {
                            in.setAmount(in.getAmount() - out.getAmount());
                            return in;
                        }).orElse(in)).collect(Collectors.toList());

        // 去除数量约等于0的信息
        materials.removeIf(m -> m.getAmount() >= 0 && m.getAmount() <= 0.01);

        // 通过原料二维码查询原料保质期信息和创建时间
        materials.forEach(m -> {
            Material material = noticeMapper.selectMaterialByCode(m.getMaterialCode());
            long day = (System.currentTimeMillis() - material.getCreateTime().getTime()) / 1000 / 60 / 60 / 24;
            if (day >= material.getShelfLife()){
                // 过期了
                Notice notice = new Notice();
                notice.setStoreroomId(material.getStoreroomId());
                String msg = "物料二维码为 " + material.getMaterialCode() + " 的 " + material.getMaterialName() + " 已过期";
                notice.setNoticeMessage(msg);

                noticeSocket(notice);
            }
        });
    }
}
