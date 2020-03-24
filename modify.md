1. 修改stock库存表
    code 类型从 text -> varchar(64)
    storeroom_code 类型从 text -> varchar(64)
    actualcount 类型从 integer -> numeric(12,2)   not null
    新增batch批次字段  类型 varchar(64)  not null
       
2. 修改 sale_out_storeroom 销售出库表
    新增batch批号字段 类型 varchar(64)
    新增amount出库数量字段 类型 numeric(12,2)
    设置team_id  可以为null
    
3. 修改goods_in_storeroom 成品入库表
    新增batch 字段  类型 varchar(64)
    新增amount 字段  类型 numeric(12,2)