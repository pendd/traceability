package com.hvisions.mes.service.impl;

import java.util.List;

import com.hvisions.mes.domain.ProcessType;
import com.hvisions.mes.mapper.ProcessTypeMapper;
import com.hvisions.mes.service.IProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

@Service
public class ProcessTypeServiceImpl implements IProcessTypeService {


	@Autowired
	private ProcessTypeMapper processTypeMapper;

	@Override
	public List<ProcessType> showProcessType() {
		List<ProcessType> data = processTypeMapper.SelectProcessType();
		return data;
	}

	@Override
	public Page<ProcessType> showProcessType(Page<ProcessType> page) {
		page.setRecords(processTypeMapper.SelectProcessType(page));
		return page;
	}

	@Override
	public List<ProcessType> queryProcessTypeCombobox() {

		return processTypeMapper.selectProcessTypeCombobox();
	}

	@Override
	public void AddProcessType(ProcessType processType) {
		// TODO Auto-generated method stub
		processTypeMapper.InsertProcessType(processType);
	}

	@Override
	public void ModProcessType(ProcessType processType) {
		// TODO Auto-generated method stub
		processTypeMapper.UpdateProcessType(processType);
	}

	@Override
	public void DelProcessType(Integer ids) {

		processTypeMapper.DeleteProcessType(ids);

	}

	@Override
	public Integer findProcessTypeName(String fullName) {
		// TODO Auto-generated method stub
		return processTypeMapper.selectProcessTypeName(fullName);
	}






}
