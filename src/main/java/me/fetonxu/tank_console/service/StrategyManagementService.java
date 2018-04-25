package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.PlayerProject;
import me.fetonxu.tank_console.mapper.PlayerProjectMapper;
import me.fetonxu.tank_console.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class StrategyManagementService {
    private final static Logger logger = LoggerFactory.getLogger(StrategyManagementService.class);

    @Autowired
    private PlayerProjectMapper projectMapper;

    public boolean save(PlayerProject project) {
        projectMapper.save(project);
        return true;
    }

    public boolean editProject(PlayerProject project) {
        projectMapper.update(project);
        return true;
    }

    public boolean deleteProject(long projectId) {
        projectMapper.deleteById(projectId);
        return true;
    }

    public List<PlayerProject> getProjectsByUser(long userId) {
        return projectMapper.findByUserId(userId);
    }

    public File getActualFile(long projectId) {
        PlayerProject project = projectMapper.findById(projectId);
        return new File(project.getUrl());
    }

}
