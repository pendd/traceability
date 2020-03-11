package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Storeroom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StoreroomMapper extends BaseMapper<Storeroom>{
	//查询所有库房信息
	List<Storeroom> SelectStoreroom();
	List<Storeroom> SelectStoreroom(Pagination page);

	String selectStoreroomById(Integer storeroomId);

	//查询库房名是否存在

	Integer selectStoreroomName(String storeroomName);
	//查询库房下拉
	List<Storeroom> selectStoreroomCombobox();

	//增加库房信息
	Integer InsertStoreroom(Storeroom storeroom);

	//修改库房信息
	void UpdateStoreroom(Storeroom storeroom);

	//删除库房信息
	void DeleteStoreroom(Integer storeroomId);

	// 通过库房类型查询库房下拉
    List<Storeroom> selectStoreroomByType(Integer type);

    // 查询所有库房信息(id + name)
	List<Storeroom> selectAllStoreroom();
}
