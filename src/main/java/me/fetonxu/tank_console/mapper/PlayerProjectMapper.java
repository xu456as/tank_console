package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.PlayerProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerProjectMapper {

    void update(PlayerProject project);

    void save(PlayerProject project);

    List<PlayerProject> findAll();

    List<PlayerProject> findByUserId(Long userId);

    PlayerProject findById(Long projectId);

    void deleteById(Long projectId);

}
