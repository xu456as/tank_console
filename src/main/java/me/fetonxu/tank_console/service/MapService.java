package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.mapper.BattleMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    @Autowired
    private BattleMapMapper battleMapMapper;

    public boolean uploadMap (BattleMap map){
        battleMapMapper.save(map);
        return true;
    }

    public boolean deleteMap (long mapId){
        battleMapMapper.delete(mapId);
        return true;
    }

    public List<BattleMap> getMaps(){
        return battleMapMapper.findAll();
    }

}
