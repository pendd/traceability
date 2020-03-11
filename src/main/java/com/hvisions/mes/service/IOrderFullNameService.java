package com.hvisions.mes.service;

public interface IOrderFullNameService {

    // 通过工序总称名获取工序总称编号
    Integer queryOrderFullNameIdByName(String fullName);
}
