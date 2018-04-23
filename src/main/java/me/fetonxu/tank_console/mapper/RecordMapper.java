package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.BattleRecord;

import java.util.List;

public interface RecordMapper {
    void save(Long playerAId, Long playerBId, String LogId, Integer winner);
    Integer find(Long playerAId, Long playerBId, String LogId);
    List<BattleRecord> findByProjectId(Long projectId);
}
