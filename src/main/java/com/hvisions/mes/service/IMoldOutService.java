package com.hvisions.mes.service;

import com.hvisions.mes.domain.MoldOut;

import java.util.List;

public interface IMoldOutService {
    List<MoldOut> findAllMoldOut();
    void addMoldOut (MoldOut moldOut);
}
