package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.BattleLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BattleLogMapper {

    void update(BattleLog battleLog);

    void save(BattleLog battleLog);

    List<BattleLog> findAll();

    BattleLog findById(String logId);

}
