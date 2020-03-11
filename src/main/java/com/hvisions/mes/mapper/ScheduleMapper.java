package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface ScheduleMapper extends BaseMapper<Schedule> {

    List<Schedule> selectSchedule(Pagination page);

    Integer insertSchedule(Schedule schedule);

    void updateSchedule(Schedule schedule);

    void deleteSchedule(Integer scheduleId);

    /**
     *  通过班组ID查询
     * @param teamId
     * @return
     */
    Schedule selectScheduleByTeamId(Integer teamId);

    /**
     *  查询当天的班组
     * @return
     */
    List<Schedule> selectCurrentSchedule();
}