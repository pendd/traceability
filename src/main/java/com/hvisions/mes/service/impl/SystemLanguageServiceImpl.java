package com.hvisions.mes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hvisions.mes.domain.SystemLanguage;
import com.hvisions.mes.mapper.SystemLanguageMapper;
import com.hvisions.mes.service.ISystemLanguageService;
@Service
public class SystemLanguageServiceImpl implements ISystemLanguageService
{
	 @Autowired
	 private  SystemLanguageMapper systemLanguageMapper;
	 @Override
	 public List<SystemLanguage> querySystemLanguage()
	 {
		 return systemLanguageMapper.selectSystemLanguage();
	 }
	 @Override
	 public void editSystemLanguage(SystemLanguage osystemLanguage)
	 {
		 systemLanguageMapper.updateSystemLanguage(osystemLanguage);
	 }
}
