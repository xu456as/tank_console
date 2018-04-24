package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.BattleLog;
import me.fetonxu.tank_console.mapper.BattleLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleLogService {

    @Autowired private BattleLogMapper battleLogMapper;

    public void saveLog(BattleLog battleLog) {
        battleLogMapper.save(battleLog);
    }

    public List<BattleLog> findLogByUserId(long userId){
        return battleLogMapper.findByUserId(userId);
    }

}
