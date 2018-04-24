package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.PlayerProject;

import java.io.File;
import java.util.List;

public interface StrategyManagementService {
    boolean uploadProject(PlayerProject project);
    boolean editProject(PlayerProject project);
    boolean deleteProject(long projectId);
    List<PlayerProject> getProjectsByUser(long userId);
    File getActualFile (long projectId);

}
