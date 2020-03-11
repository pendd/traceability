package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Team;


public interface ITeamService {

		List<Team> showTeam();
		Page<Team> showTeam(Page<Team> page);

		List<Team> queryTeamCombobox(String teamName);

		Team findTeamName(String teamName);

		void AddTeam(Team team);


		String ModTeam(Team team);


		void DelTeam(Integer ids);

		// 通过班组名查询班组编号
		Integer queryTeamIdByTeamName(String teamName);


}
