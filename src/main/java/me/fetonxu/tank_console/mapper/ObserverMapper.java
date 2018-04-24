package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObserverMapper {
    void save(@Param("observerId") Long observerId, @Param("observeeId")Long observeeId, @Param("date")Long date);
    void delete(@Param("observerId")Long observerId, @Param("observeeId")Long observeeId);
    List<User> findByUserId(Long userId);
}
