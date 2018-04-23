package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.BattleLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeLogMapper {
    void save(Long userId, Long logId);
    void delete(Long userId, Long logId);
    List<BattleLog> findByUserId(Long userId);
}
