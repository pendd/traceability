package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.PackType;
import com.hvisions.mes.mapper.PackTypeMapper;
import com.hvisions.mes.service.IPackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author dpeng
 * @description
 * @date 2019-09-14 17:22
 */
@Service
public class PackTypeServiceImpl implements IPackTypeService {

    @Autowired
    private PackTypeMapper packTypeMapper;

    @Override
    public Page<PackType> queryPackTypeByPage(Page<PackType> page) {
        page.setRecords(packTypeMapper.selectPackTypeByPage(page));
        return page;
    }

    /**
     *  新增包装类型
     * @param packType
     * @return true  增加成功  false  名称已存在
     */
    @Override
    public String appendPackType(PackType packType) {
        // 判断这个类型名称存不存在
        PackType type = packTypeMapper.selectPackTypeByPackTypeName(packType.getPackTypeName());
        if (type != null) {
            // 存在
            return "false";
        }else {
            packTypeMapper.insertPackType(packType);
        }
        return "true";
    }

    @Override
    public String changePackType(PackType packType) {
        // 判读这个名称是不是存在
        PackType type = packTypeMapper.selectPackTypeByPackTypeName(packType.getPackTypeName());
        if (type == null || Objects.equals(type.getPackTypeId() , packType.getPackTypeId())) {
            packTypeMapper.updatePackType(packType);
        }else {
            return "false";
        }
        return "true";
    }

    @Override
    public void cutPackTypeByIds(List<Integer> ids) {
        packTypeMapper.deletePackTypeByIds(ids);
    }
}
