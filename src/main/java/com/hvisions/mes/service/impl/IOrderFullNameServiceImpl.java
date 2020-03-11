package com.hvisions.mes.service.impl;

import com.hvisions.mes.mapper.OrderFullNameMapper;
import com.hvisions.mes.service.IOrderFullNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dpeng
 * @create 2019-03-30 9:56
 */
@Service
public class IOrderFullNameServiceImpl implements IOrderFullNameService {

    @Autowired
    private OrderFullNameMapper mapper;
    /**
     *  通过工序总称名获取工序总称编号
     * @param fullName    工序总称名
     * @return
     */
    @Override
    public Integer queryOrderFullNameIdByName(String fullName) {
        return mapper.selectOrderFullNameIdByName(fullName);
    }
}
