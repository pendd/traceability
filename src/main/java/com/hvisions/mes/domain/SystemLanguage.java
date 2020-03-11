package com.hvisions.mes.domain;

public class SystemLanguage {

	/**
     *系统语言
     */
    private String systemLanguage;

    /**
     * 是否显示（0：不可见、1：可见）
     */
    private Integer systemVisible;

    private String languageName;

    private Integer currentLanguage;



	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public Integer getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(Integer currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public String getSystemLanguage() {
		return systemLanguage;
	}

	public void setSystemLanguage(String systemLanguage) {
		this.systemLanguage = systemLanguage;
	}

	public Integer getSystemVisible() {
		return systemVisible;
	}

	public void setSystemVisible(Integer systemVisible) {
		this.systemVisible = systemVisible;
	}




}
