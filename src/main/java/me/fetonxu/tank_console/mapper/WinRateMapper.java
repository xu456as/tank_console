package me.fetonxu.tank_console.mapper;

import java.util.List;
import java.util.Map;

public interface WinRateMapper {
    void save(Long userId, Long mapId, Float rate);
    void update(Long userId, Long mapId, Float rate);
    Float find(Long userId, Long mapId);
    List<Map<BattleLogMapper, Float>> findByUserId(Long userId);
}
