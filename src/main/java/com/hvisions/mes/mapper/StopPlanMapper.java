package com.hvisions.mes.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.StopPlanAuto;
import com.hvisions.mes.domain.StopPlanManual;

public interface StopPlanMapper {

	List<StopPlanAuto>  selectStopPlanAuto(Pagination page
			,@Param("activeBeginDate") String activeBeginDate,@Param("activeEndDate") String activeEndDate);

	List<StopPlanManual>  selectStopPlanManual(Pagination page
			,@Param("stopBeginTime") String stopBeginTime,@Param("stopEndTime") String stopEndTime);

	void insertStopPlanAuto(StopPlanAuto oStopPlanAuto);

	void insertStopPlanManual(StopPlanManual oStopPlanManual);

	void updateStopPlanAuto(StopPlanAuto oStopPlanAuto);

	void updateStopPlanManual(StopPlanManual oStopPlanManual);


	void deleteStopPlanAuto(@Param("serialId") Integer serialId);

	void deleteStopPlanManual(@Param("serialId") Integer serialId);

}
