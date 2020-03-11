-- 电子日报表
select
       record_date,
       work_group,
       workers_count,
       work_hours,
       work_number,
       order_count,
       goods_name,
       plan_total,
       day_total,
       percentage,
       wrong_count,
       wrong_percentage,
       complete_count,
       surplus_count,
       remark
from report_goods_day_total