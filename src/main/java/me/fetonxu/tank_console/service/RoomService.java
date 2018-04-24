package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.Room;
import me.fetonxu.tank_console.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomMapper roomMapper;

    public boolean createRoom(Room room) {
        roomMapper.save(room);
        return true;
    }

    public List<Room> getRooms() {
        return roomMapper.findAll();
    }

    public List<Room> getRoomsByUsers(long userId) {
        throw new RuntimeException("method not implemented");
    }

    public List<Room> deleteExpiredRooms() {
        throw new RuntimeException("method not implemented");
    }
}
