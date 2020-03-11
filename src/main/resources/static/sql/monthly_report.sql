-- 月报表
-- 获取两个日期之间的所有day 和 对应的星期几
SELECT
	to_char( A, 'fmdd' ) AS month_day,
CASE
	EXTRACT (
	DOW
FROM
	CAST ( A AS TIMESTAMP ))
	WHEN 0 THEN
	'日'
	WHEN 1 THEN
	'一'
	WHEN 2 THEN
	'二'
	WHEN 3 THEN
	'三'
	WHEN 4 THEN
	'四'
	WHEN 5 THEN
	'五'
	WHEN 6 THEN
	'六'
	END AS month_week
FROM
	generate_series ( '2019-03-01' :: DATE, '2019-03-31', '1 days' ) AS A;


-- 获取产品所有数据
SELECT
	gt.produce_number,
	g.goods_code,
	g.goods_name,
	po.plan_amount,
	to_char( gt.produce_date, 'fmdd' ) AS month_day,
	gt.quantity
FROM
	goods_total gt,
	produce_order po,
	goods g
WHERE
	gt.produce_number = po.work_number
	AND g.goods_id = gt.goods_code
	AND gt.type_statistics = 1;


-- 通过产品编码和生产工单号分组
SELECT
	gt.produce_number,
	G.goods_code,
	sum(gt.quantity) sum_amount
FROM
	goods_total gt,
	goods G
WHERE
	gt.goods_code = G.goods_id
GROUP BY
	G.goods_code,
	gt.produce_number;


-- 总和统计
-- 产品统计
select
       sum(po.plan_amount) as plan_count,
       to_char(gt.produce_date,'fmdd') as month_day,
       sum(gt.quantity) real_count
from goods_total gt,produce_order po
where gt.produce_number = po.work_number
  and gt.type_statistics = 0
group by month_day;

-- 贴片统计
select
       sum(po.plan_amount) as plan_count,
       to_char(gt.produce_date,'fmdd') as month_day,
       sum(gt.quantity) real_count
from goods_total gt,produce_order po
where gt.produce_number = po.work_number
  and gt.type_statistics = 1
group by month_day;

-- 人数 工时 总工时
select count(emp_id) emp_num,
       to_char(in_out_date,'fmdd') as month_day,
       round(extract(epoch from sum(out_time-in_time))::numeric/3600::numeric,2) sum_hour
from pda_in_out
group by in_out_date;