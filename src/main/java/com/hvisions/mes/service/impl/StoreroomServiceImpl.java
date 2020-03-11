package com.hvisions.mes.service.impl;

import java.util.List;

import com.hvisions.mes.domain.Storeroom;
import com.hvisions.mes.mapper.StoreroomMapper;
import com.hvisions.mes.service.IStoreroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;



@Service
public class StoreroomServiceImpl implements IStoreroomService {


	@Autowired
	private StoreroomMapper storeroomMapper;

	@Override
	public List<Storeroom> showStoreroom() {
		List<Storeroom> data = storeroomMapper.SelectStoreroom();
		return data;
	}

	//加载产线
	@Override
	public Page<Storeroom> showStoreroom(Page<Storeroom> page) {
		page.setRecords(storeroomMapper.SelectStoreroom(page));
		return page;
	}

	@Override
	public List<Storeroom> queryStoreroomCombobox() {

		return storeroomMapper.selectStoreroomCombobox();
	}

	@Override
	public void AddStoreroom(Storeroom storeroom) {

		storeroomMapper.InsertStoreroom(storeroom);
	}

	@Override
	public void ModStoreroom(Storeroom storeroom) {

		storeroomMapper.UpdateStoreroom(storeroom);
	}

	@Override
	public void DelStoreroom(Integer ids) {


		storeroomMapper.DeleteStoreroom(ids);

	}


    @Override
	public Integer findStoreroomName(String storeroomName) {

		return storeroomMapper.selectStoreroomName(storeroomName);
	}

	// 通过库房类型查询库房下拉
	@Override
	public List<Storeroom> queryStoreroomByType(Integer type) {
		return storeroomMapper.selectStoreroomByType(type);
	}




}
