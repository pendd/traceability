package com.hvisions.mes.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hvisions.mes.domain.ProcessType;
import com.hvisions.mes.domain.Team;
import com.hvisions.mes.service.impl.ProcessTypeServiceImpl;
import com.hvisions.mes.service.impl.TeamServiceImpl;
import com.hvisions.mes.service.impl.UserServiceImpl;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.service.impl.LineServiceImpl;


@ApiIgnore
@RestController
@RequestMapping("/json/Team")
public class TeamController extends BaseController {

	@Autowired
	private TeamServiceImpl teamService;


	@RequestMapping(value = "/Teamlistpage", method = RequestMethod.GET)
    public Map<String, Object> TeamListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Team> data = teamService.showTeam(new Page<Team>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }



	@RequestMapping(value =  "/addTeam", method = RequestMethod.POST)
    public String addTeam(HttpServletRequest request, HttpServletResponse respon) {
		Team team = new Team();
        String res = "";
        String msg = null;
        try {
            Team newTeam = teamService.findTeamName(request.getParameter("teamName"));
            if (newTeam != null) {
                res = "2";
            } else {
				team.setCreateTime(new Date());
				team.setTeamName(request.getParameter("teamName"));
				team.setUpdateTime(new Date());
				team.setUpdateUserId(getCurrentUserID());
				team.setUserId(getCurrentUserID());
            	teamService.AddTeam(team);
            	res = "true";
            }
        } catch (Exception ex) {
        	msg = ex.getMessage();
            res = "false";
        }
        return res;
    }



	 @RequestMapping(value = "/removeTeam", method = RequestMethod.POST)
	    public String removeTeam(@RequestParam("delIDs") List<String> delIDs ) {
	        String res = "true";

	        try {

	            for(int i=0;i<delIDs.size();i++)
	            {
	            	teamService.DelTeam(Integer.valueOf(delIDs.get(i)));
	            }
	        } catch (Exception ex) {
	           res = "false";
	        }

	        return res;
	    }


	 @RequestMapping(value =  "/editTeam", method = RequestMethod.POST)
	    public String editTeam(Team team) {
			team.setUpdateUserId(getCurrentUserID());
			return teamService.ModTeam(team);
	    }



}

















