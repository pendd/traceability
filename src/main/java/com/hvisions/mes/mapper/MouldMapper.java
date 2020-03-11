package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Mould;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MouldMapper extends BaseMapper<Mould>{
    List<Mould> selectMould();
    List<Mould> selectMould(Pagination page);


    Integer selectMouldName(String typeName);

    List<Mould> selectMouldCombobox();


    Integer insertMould(Mould mould);


    void updateMould(Mould mould);


    void deleteMould(Integer mouldId);

    Integer selectEquipmentById(Integer moldId);
}
