package me.fetonxu.tank_console.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WinRateMapper {
    void save(@Param("userId") Long userId, @Param("mapId") Long mapId, @Param("rate") Float rate);
    void update(@Param("userId") Long userId,@Param("mapId") Long mapId, @Param("rate") Float rate);
    Float find(@Param("userId") Long userId, @Param("mapId") Long mapId);
    Map<Long, Float> findByUserId(Long userId);
}
