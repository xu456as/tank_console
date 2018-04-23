package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.User;

import java.util.List;

public interface ObserverMapper {
    void save(Long observerId, Long observeeId);
    void delete(Long observerId, Long observeeId);
    List<User> findByUserId(Long userId);
}
