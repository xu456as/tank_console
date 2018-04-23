package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(User user);
    void deleteUser(long userId);
    void updateUser(User user);
    User getUser(long userId);
    List<User> getUsers();
}
