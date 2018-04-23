package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {

    void update(Room room);

    void save(Room room);

    List<Room> findAll();

    Room findById(Long roomId);
}
