package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.BattleLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeLogMapper {
    void save(@Param("userId") Long userId, @Param("logId") Long logId, @Param("date") Long date);
    void delete(@Param("userId") Long userId, @Param("logId") Long logId);
    List<BattleLog> findByUserId(Long userId);
}
