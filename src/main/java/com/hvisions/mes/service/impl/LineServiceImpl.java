package com.hvisions.mes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.mapper.LineMapper;
import com.hvisions.mes.service.ILineService;

/**
*
* 项目名称：quality_traceability_system
* 类名称：LineServiceImpl
* 类描述：
* 创建人：宫岩
* 创建时间：2019年2月26日 上午11:31:00
*
*/

@Service
public class LineServiceImpl implements ILineService {


	@Autowired
	private LineMapper lineMapper;

	@Override
	public List<Line> showLine() {
		List<Line> data = lineMapper.SelectLine();
		return data;
	}

	//加载产线
	@Override
	public Page<Line> showLine(Page<Line> page) {
		page.setRecords(lineMapper.SelectLine(page));
		return page;
	}

	@Override
	public List<Line> querylineCombobox() {
		// TODO Auto-generated method stub
		return lineMapper.selectLineCombobox();
	}

	@Override
	public String AddLine(Line line) {
		String res;
		Line newLine = lineMapper.selectLineName(line.getLineName());

		if (newLine == null) {
			// 不存在相同名称的产线
			// 判断该负责人是否还负责其他产线
			Line lineByPrincipal = lineMapper.selectLineByPrincipal(line.getPrincipal());
			if (lineByPrincipal == null) {
				// 该负责人不负责其他产线
				line.setUpdateTime(new Date());
				lineMapper.InsertLine(line);
				res = "true";
			}else {
				res = "1";
			}
		}else {
			res = "false";
		}
		return res;
	}

	@Override
	public String ModLine(Line line) {
		String res;
		Line newLine = lineMapper.selectLineName(line.getLineName());

		if (newLine == null || Objects.equals(newLine.getLineId(),line.getLineId())) {
			// 不存在相同名称的产线
			// 判断该负责人是否还负责其他产线
			Line lineByPrincipal = lineMapper.selectLineByPrincipal(line.getPrincipal());
			if (lineByPrincipal == null || Objects.equals(lineByPrincipal.getLineId(),line.getLineId())) {
				// 该负责人不负责其他产线
				line.setUpdateTime(new Date());
				lineMapper.UpdateLine(line);
				res = "true";
			}else {
				res = "1";
			}
		}else {
			res = "false";
		}
		return res;
	}

	@Override
	public void DelLine(Integer ids) {
		// TODO Auto-generated method stub

			lineMapper.DeleteLine(ids);

	}


	@Override
	public Line findLineName(String lineName) {
		// TODO Auto-generated method stub
		return lineMapper.selectLineName(lineName);
	}

	/**
	 *  通过产线名查询产线编号
	 * @param lineName   产线名
	 * @return
	 */
	@Override
	public Integer queryLineIdByLineName(String lineName) {
		return lineMapper.selectLineIdByLineName(lineName);
	}

    @Override
    public Line queryLineByPrincipal(Integer userId) {
        return lineMapper.selectLineByPrincipal(userId);
    }


}
