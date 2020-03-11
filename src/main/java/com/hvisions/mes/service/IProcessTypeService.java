package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.domain.ProcessType;


public interface IProcessTypeService {

		List<ProcessType> showProcessType();
		Page<ProcessType> showProcessType(Page<ProcessType> page);

		List<ProcessType> queryProcessTypeCombobox();

		Integer findProcessTypeName(String fullName);

		void AddProcessType(ProcessType processType);


		void ModProcessType(ProcessType processType);


		void DelProcessType(Integer ids);
}
