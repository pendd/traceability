package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Storeroom;


public interface IStoreroomService {

		List<Storeroom> showStoreroom();
		Page<Storeroom> showStoreroom(Page<Storeroom> page);

		List<Storeroom> queryStoreroomCombobox();

		Integer findStoreroomName(String storeroomName);

		void AddStoreroom(Storeroom storeroom);


		void ModStoreroom(Storeroom storeroom);


		void DelStoreroom(Integer ids);

		// 通过库房类型查询库房下拉
		List<Storeroom> queryStoreroomByType(Integer type);
}
