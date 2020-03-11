package com.hvisions.mes.service;

import java.util.List;

import com.hvisions.mes.domain.SystemLanguage;

public interface ISystemLanguageService {


	  List<SystemLanguage> querySystemLanguage();

	  void editSystemLanguage(SystemLanguage osystemLanguage);
}
