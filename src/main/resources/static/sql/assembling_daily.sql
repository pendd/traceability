select gt.produce_date as 日期,
       pio.emp_num as 操作人数,
       pio.hour as 时间,
       po."cMoCode" as 生产工单号,
       po.plan_amount as 订单数量,
       po.goods_name as 产品名称,
       po.plan_amount as 计划产量,
       gt.quantity as 当日产量
from produce_order po,goods_total gt,(select count(distinct emp_id) emp_num,
                                             round(extract(epoch from sum(out_time-in_time))::numeric/3600::numeric / count(distinct emp_id)) as hour,
                                             in_out_date
                                      from pda_in_out
                                      where line_id = 2
                                      group by in_out_date) pio
where po.work_number = gt.produce_number and po.line_id = 2
  and gt.type_statistics = 0 and pio.in_out_date = gt.produce_date;