package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Line;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
*
* 项目名称：quality_traceability_system
* 类名称：LineMapper
* 类描述：
* 创建人：宫岩
* 创建时间：2019年2月25日 下午3:20:35
*
*/
@Mapper
@Repository
public interface LineMapper extends BaseMapper<Line>{
	//查询所有产线信息
	List<Line> SelectLine();
	List<Line> SelectLine(Pagination page);

	//查询产线名是否存在

	Line selectLineName(String lineName);
	//查询产线下拉
	List<Line> selectLineCombobox();

	//增加产线信息
	Integer InsertLine(Line line);

	//修改产线信息
	void UpdateLine(Line line);

	//删除产线信息
	void DeleteLine(Integer lineId);

	// 通过产线名查询产线编号
	Integer selectLineIdByLineName(String lineName);

	// 通过产线负责人查询产线信息
	Line selectLineByPrincipal(Integer userId);
}
