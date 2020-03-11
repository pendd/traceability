package com.hvisions.mes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.hvisions.mes.domain.Team;
import com.hvisions.mes.mapper.TeamMapper;
import com.hvisions.mes.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

@Service
public class TeamServiceImpl implements ITeamService {


	@Autowired
	private TeamMapper teamMapper;

	@Override
	public List<Team> showTeam() {
		List<Team> data = teamMapper.SelectTeam();
		return data;
	}

	@Override
	public Page<Team> showTeam(Page<Team> page) {
		page.setRecords(teamMapper.SelectTeam(page));
		return page;
	}

	@Override
	public List<Team> queryTeamCombobox(String teamName) {

		return teamMapper.selectTeamCombobox(teamName == null || Objects.equals(teamName,"") ? "" : teamName.trim());
	}

	@Override
	public void AddTeam(Team team) {

		teamMapper.InsertTeam(team);
	}

	@Override
	public String ModTeam(Team team) {
		String flag;
		team.setUpdateTime(new Date());
        Team newTeam = teamMapper.selectTeamName(team.getTeamName());
        if (newTeam == null || Objects.equals(newTeam.getTeamId(),team.getTeamId())) {
            teamMapper.UpdateTeam(team);
            flag = "true";
        }else {
            flag = "false";
        }
        return flag;
	}

	@Override
	public void DelTeam(Integer ids) {

		teamMapper.DeleteTeam(ids);

	}


	@Override
	public Team findTeamName(String teamName) {

		return teamMapper.selectTeamName(teamName);
	}

	/**
	 *  通过班组名查询班组编号
	 * @param teamName   班组名
	 * @return
	 */
	@Override
	public Integer queryTeamIdByTeamName(String teamName) {
		return teamMapper.selectTeamIdByTeamName(teamName);
	}

}
