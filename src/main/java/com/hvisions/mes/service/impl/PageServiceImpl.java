package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hvisions.mes.mapper.PageMapper;
import com.hvisions.mes.service.IPageService;
@Service
public class PageServiceImpl implements IPageService{

    @Autowired
    private PageMapper pageMapper;

    @Override
    public String queryMenuId(String url) {

        return pageMapper.selectMenuId(url);
    }

}
