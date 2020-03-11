package com.hvisions.mes.mapper;

import java.util.List;

import com.hvisions.mes.domain.SystemLanguage;

public interface SystemLanguageMapper {

    List<SystemLanguage> selectSystemLanguage();

    void updateSystemLanguage(SystemLanguage osystemLanguage);

}
