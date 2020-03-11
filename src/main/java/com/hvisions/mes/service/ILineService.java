package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Line;

/**
*
* 项目名称：quality_traceability_system
* 类名称：ILineService
* 类描述：
* 创建人：宫岩
* 创建时间：2019年2月26日 上午11:28:12
*
*/
public interface ILineService {

		List<Line> showLine();
		Page<Line> showLine(Page<Line> page);

		List<Line> querylineCombobox();

		Line findLineName(String lineName);

		String AddLine(Line line);


		String ModLine(Line line);


		void DelLine(Integer ids);

		// 通过产线名查询产线编号
		Integer queryLineIdByLineName(String lineName);

		Line queryLineByPrincipal(Integer userId);

}
