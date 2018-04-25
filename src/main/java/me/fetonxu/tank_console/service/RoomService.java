package me.fetonxu.tank_console.service;

import com.github.pagehelper.PageHelper;
import me.fetonxu.tank_console.entity.Room;
import me.fetonxu.tank_console.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomMapper roomMapper;

    public boolean createRoom(Room room) {
        roomMapper.save(room);
        return true;
    }

    public List<Room> getRoomsPaged(int pageIdx, int pageSize) {
        PageHelper.startPage(pageIdx, pageSize);
        return roomMapper.findAll();
    }

    public List<Room> getRoomsByUsers(long userId) {
        return roomMapper.findByUserId(userId);
    }

    public List<Room> deleteExpiredRooms() {
        List<Room> expiredRooms = roomMapper.findByExpiredTime(System.currentTimeMillis());
        List<Long> ids = new ArrayList<>(expiredRooms.size());
        for (int i = 0; i < expiredRooms.size(); i++) {
            ids.add(expiredRooms.get(i).getId());
        }
        roomMapper.deleteByIds(ids);
        return expiredRooms;
    }
}
