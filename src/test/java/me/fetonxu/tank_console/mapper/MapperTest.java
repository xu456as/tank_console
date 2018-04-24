package me.fetonxu.tank_console.mapper;


import me.fetonxu.tank_console.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.groups.ConvertGroup;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {
    @Autowired
    private BattleRecordMapper battleRecordMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private BattleMapMapper mapMapper;

    @Test
    public void testInsert(){
        User user = new User();
        userMapper.save(user);
        userMapper.save(user);

        battleRecordMapper.save(1l, 2l, "123", 1, System.currentTimeMillis());
    }

    @Test
    public void testSelect() {

        //        battleRecordMapper.save(1l, 2l, "123", 1, System.currentTimeMillis());
        //        int result = battleRecordMapper.find(1l, 2l, "123");
        List<BattleRecord> records = battleRecordMapper.findByProjectId(1l);
        System.out.println(records.get(0).getDate());
        System.out.println(records.get(0).getWinner());
        System.out.println(records.get(0).getLog().getId());
        System.out.println(records.get(0).getProjectA().getId());
        System.out.println(records.get(0).getProjectB().getId());
    }

    @Test
    public void testRoomMapper(){

        Room room = roomMapper.findById(1l);
        System.out.println(room.getDuration());
    }
    @Test
    public void testMapMapper(){
        BattleMap battleMap = new BattleMap();
        battleMap.setId(1l);
        battleMap.setUrl("map/456.txt");
        mapMapper.update(battleMap);
    }
}
