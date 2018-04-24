package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.BattleMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BattleMapMapper {

    void update(BattleMap battleMap);

    void save(BattleMap battleMap);

    void delete(Long mapId);

    List<BattleMap> findAll();

    BattleMap findById(Long mapId);

}
