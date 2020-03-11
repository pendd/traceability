-- 追溯报表   第一层
select
       record_date 日期,
       work_number 订单编号,
       goods_name 产品名称,
       goods_spec 产品规格,
       goods_unit 计量单位,
       goods_quantity 数量,
       material_code 物料编号,
       material_name 物料名称,
       material_spec 物料规格,
       material_unit 计量单位,
       material_quantity 数量,
       material_type 类型,
       equipment_name 名称,
       equipment_error_count 异常数
from report_retrospect
where work_number = '001' and  record_date between '2019-06-07 16:30:20' and '2019-07-04 16:30:20'


-- 第二层
select ROW_NUMBER () OVER (ORDER BY a.product_code ASC) AS 序号,b.goods_code 成品码,b.create_time 入库时间,c.emp_name 入库人员,d.storeroom_name 库房名称 from
(select distinct product_code from order_history a where a.produce_number='GD20190626') a,
goods_in_storeroom b,
emp c,
storeroom d
where a.product_code=b.goods_code and b.user_id=c.emp_id and b.storeroom_id=d.storeroom_id



-- 第三层
select a.emp_name 操作人,date_part('day',a.difftime)*24+date_part('hour',a.difftime)+date_part('minute',a.difftime)/60+date_part('second',a.difftime)/3600  工时,a.order_name 工序
from(select a.order_name,a.emp_id,a.emp_name,max(a.out_time)-min(a.in_time) difftime,a.in_out_date from
(select b.order_name,c.emp_id, c.emp_name ,d.in_time,d.out_time,d.in_out_date from

order_history a
inner join order_detail b on a.order_id=b.order_id
inner join emp c on a.user_id=c.emp_id
inner join pda_in_out d on c.emp_id=d.emp_id


where a.product_code='1%1561947794' and in_out_date='2019-03-01') a

group by a.in_out_date,a.order_name,a.emp_id,a.emp_name) a

order by a.emp_id