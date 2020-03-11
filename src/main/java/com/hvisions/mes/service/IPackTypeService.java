package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.PackType;

import java.util.List;

/**
 * @author dpeng
 * @description 包装类型配置
 * @date 2019-09-14 17:19
 */
public interface IPackTypeService {

    Page<PackType> queryPackTypeByPage(Page<PackType> page);

    String appendPackType(PackType packType);

    String changePackType(PackType packType);

    void cutPackTypeByIds(List<Integer> ids);
}
