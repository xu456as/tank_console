package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void update(User user);

    void save(User user);

    List<User> findAll();

    User findById(Long userId);

    User findByEmail(String email);

    void deleteById(Long userId);

    List<User> findByScoreRank();
}
