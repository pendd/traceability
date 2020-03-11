-- 通过时间差来获取设备故障情况
select
  e.equipment_name,
       ea.reason,
       ea.create_time,
       ea.complete_time
from equipment_abnormal ea,equipment e
where ea.equipment_id = e.equipment_id
and ea.create_time >= '2019-06-07 16:30:20'
and (ea.complete_time <= '2019-06-21 16:30:20' or ea.complete_time is null)

-- 通过时间差来获取设备故障次数情况
select
        e.equipment_name,
       count(*) times
from equipment_abnormal ea,equipment e
where ea.equipment_id = e.equipment_id
  and ea.create_time >= '2019-03-07 16:30:20'
  and (ea.complete_time <= '2019-07-21 16:30:20')
group by e.equipment_name;