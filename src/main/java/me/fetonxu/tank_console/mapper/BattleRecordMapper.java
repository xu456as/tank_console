package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.BattleRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BattleRecordMapper {
    void save(@Param("projectAId") Long projectAId, @Param("projectBId") Long projectBId,
        @Param("logId") String LogId, @Param("winner") Integer winner, @Param("date") Long date);

    Integer find(@Param("projectAId") Long projectAId, @Param("projectBId") Long projectBId,
        @Param("logId") String LogId);

    List<BattleRecord> findByProjectId(Long projectId);
}
