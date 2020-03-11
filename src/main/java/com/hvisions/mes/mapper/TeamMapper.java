package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Team;import org.apache.ibatis.annotations.Param;

public interface TeamMapper extends BaseMapper<Team>{

    List<Team> SelectTeam();
    List<Team> SelectTeam(Pagination page);



    Team selectTeamName(String teamName);

    List<Team> selectTeamCombobox(@Param("teamName")String teamName);


    Integer InsertTeam(Team team);


    void UpdateTeam(Team team);


    void DeleteTeam(Integer teamId);

    // 通过班组名查询班组编号
    Integer selectTeamIdByTeamName(String teamName);
}