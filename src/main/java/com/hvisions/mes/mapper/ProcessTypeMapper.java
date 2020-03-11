package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.domain.ProcessType;

public interface ProcessTypeMapper extends BaseMapper<ProcessType>{

	List<ProcessType> SelectProcessType();
	List<ProcessType> SelectProcessType(Pagination page);



	Integer selectProcessTypeName(String fullName);

	List<ProcessType> selectProcessTypeCombobox();


	Integer InsertProcessType(ProcessType processType);


	void UpdateProcessType(ProcessType processType);


	void DeleteProcessType(Integer fullId);
}
