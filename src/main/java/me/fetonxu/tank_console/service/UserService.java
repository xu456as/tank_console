package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean addUser(User user) {
        userMapper.save(user);
        return true;
    }

    public boolean deleteUser(long userId) {
        userMapper.deleteById(userId);
        return true;
    }

    public boolean editUser(User user) {
        userMapper.update(user);
        return true;
    }

    public User findUser(long userId) {
        return userMapper.findById(userId);
    }

    public User findUser(String email) {
        return userMapper.findByEmail(email);
    }

    public List<User> getUsers(int page) {
        return userMapper.findAll();
    }

}
