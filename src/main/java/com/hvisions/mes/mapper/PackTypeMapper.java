package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.PackType;

import java.util.List;

/**
 * @author dpeng
 * @description 包装配置
 * @date 2019-09-14 16:29
 */
public interface PackTypeMapper {

    List<PackType> selectPackTypeByPage(Pagination page);

    void insertPackType(PackType packType);

    void updatePackType(PackType packType);

    void deletePackTypeByIds(List<Integer> ids);

    PackType selectPackTypeByPackTypeName(String packTypeName);
}
