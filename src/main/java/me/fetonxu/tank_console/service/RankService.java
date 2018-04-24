package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {

    @Autowired
    private UserMapper userMapper;

    public boolean addScore(long userId, int score){
        User user = userMapper.findById(userId);
        user.setScore(user.getScore() + score);
        userMapper.update(user);
        return true;
    }
    public List<User> getUsersByRank(int page){
        return userMapper.findByScoreRank();
    }
}
