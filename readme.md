## Apache POI操作Excel文件
http://blog.csdn.net/chancein007/article/category/3278051

## Easy POI操作Excel文件
http://git.oschina.net/jueyue/easypoi
详细教程: http://git.oschina.net/jueyue/easypoi/blob/master/doc/EasyPoi%E6%95%99%E7%A8%8B.docx?dir=0&filepath=doc%2FEasyPoi%E6%95%99%E7%A8%8B.docx&oid=133ebde5641f128420023dbdc2533ab4b53c7792&sha=4c8d94c80544811e2e358518bad6c689a63c13e9

## 页面端ajax请求
  使用jquery的ajax请求,请使用统一的公共方法进行请求,具体请参考 /page/dashboard/kpi 下的kpi.html和kpi.js

## Spring Web MVC 参数接收和绑定方法:
http://www.cnblogs.com/baobao2010/p/3182199.html

# Mybatis plus 文档:
http://mp.baomidou.com/#/quick-start

## 有几个公共问题大家注意一下:
1. svn的使用方式. a.提交代码前一定要先更新代码,看看有没有冲突,有冲突就去找和你文件冲突的作者解决冲突,一定不要冲突没有解决就继续接下来的工作.  b. 每天上班后第一件事情就是更新svn,不要在没有更新就开始工作,这样非常容易冲突.
2. 大家都更新一下工程的依赖配置. 方法: 在工程根目录上 右键->Gradle -> Refresh Gradle Project.  工程的依赖项目我更新了 数据库的驱动为最新版本, 为Mybatis添加了支持Java8的日期处理 LocalDateTime LocalDate LocalTime .
  ======  这项不需要大家该了,Javascript对新的日期类型支持有些麻烦,还继续使用Date类型吧              大家需要改一下和数据库相关的日期映射类型, 数据库日期相关类型与Java类型对应的是: Timestamp->LocalDateTime date->LocalDate time->LocalTime
3. Mybatis主键问题. 默认情况下Mybatis的主键策略是自增int,如果你的主键不是这个策略,需要在实体的对应主键的字段上添加   @TableId(value = "order_no", type = IdType.INPUT) 更多请参考ProcessOrder

# Mybatis plus 分页相关
  大家可以参考:
  ProcessOrderMapper.xml
  ProcessOrderMapper.java
  ProcessOrder.java
  ProcessOrderService.java
  ProcessOrderController.java

## Java对象的比较
大家注意一下现在项目中风险比较高的Bug:
 String和Integer 的比较 不能使用 ==和!= . 应该使用 equals 方法. 同样,所有java的对象比较都应该使用equals方法.  另外在比较的时候,应该把不可能为null的放在前面.  比如: `"小明".equals(name)`   

## 报表导出流程: 
1. 点击导出后,会执行对应的报表service中的getReprotData方法; 
2. 通过报表ID获得对应的模板; 
3. 使用ReportData中的columns数据生成新的模板,位置在当前用户的临时目录加上时间戳加上原始模板文件名称.例如: C:\Users\Administrator\AppData\Local\Temp\20170215105903444可用时间百分比.xlsx
4. 使用EasyPOI生成带有最终数据的Excel流,输出给控制器的response.
5. 浏览器端显示下载对话框,默认的文件名称为 原始模板名称+时间戳+扩展名 .