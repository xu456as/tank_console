package me.fetonxu.tank_console.controller;

import me.fetonxu.tank_console.entity.PlayerProject;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.service.StrategyManagementService;
import me.fetonxu.tank_console.util.Config;
import me.fetonxu.tank_console.util.FileUtil;
import me.fetonxu.tank_console.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller @RequestMapping("/strategyManagement") public class StrategyManagementController {

    private final static Logger logger =
        LoggerFactory.getLogger(StrategyManagementController.class);

    @Autowired private StrategyManagementService managementService;

    @PostMapping("/upload")
    public Object upload(HttpSession session, @RequestParam("name") String name,
        @RequestParam("compressType") String compressType,
        @RequestParam("language") String language,
        @RequestParam(value = "random", defaultValue = "0", required = false) Integer random,
        MultipartFile projectFile) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        String baseDir = Config.getString("project.upload.path");
        baseDir = String.format("%s/%d", baseDir, user.getId());
        File directory = new File(baseDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String code = "0", result = "default";
        try {
            String url = FileUtil.saveFile(baseDir, name, projectFile.getBytes());
            PlayerProject project = new PlayerProject();
            project.setUrl(url);
            project.setUser(user);
            project.setName(name);
            project.setLanguage(language);
            project.setCompressType(compressType);
            project.setRandom(random);
            project.setSize(Float.valueOf(projectFile.getBytes().length));
            managementService.save(project);
            code = "1";
            result = "Project uploaded successfully";
        } catch (Exception e) {
            code = "0";
            result = "Something wrong with the server";
            logger.error(String.format("error: %s", e));
        }
        return JsonUtil.createJson(code, result);
    }

    @PostMapping("/edit") @ResponseBody
    public Object edit(HttpSession session, @RequestParam("projectId") Long projectId,
        @RequestParam("compressType") String compressType,
        @RequestParam(value = "language", required = false) String language,
        @RequestParam(value = "random", required = false) Integer random,
        @RequestPart(required = false) MultipartFile projectFile) {

        PlayerProject project = managementService.getProjectById(projectId);
        User user = (User) session.getAttribute("currentUser");
        if (user == null || !user.getId().equals(project.getUser().getId())) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        if(compressType == null && language == null && random == null && projectFile == null){
            return JsonUtil.createJson("0", "Nothing to change");
        }
        String baseDir = Config.getString("project.upload.path");
        baseDir = String.format("%s/%d", baseDir, user.getId());
        String code = "0", result = "default";
        try {
            FileUtil.saveFile(baseDir, project.getName(), projectFile.getBytes());
            project.setLanguage(language);
            project.setRandom(random);
            project.setCompressType(compressType);
            project.setSize(Float.valueOf(projectFile.getBytes().length));
            managementService.editProject(project);
            code = "1";
            result = "Project edited successfully";
        } catch (Exception e) {
            code = "0";
            result = "Something wrong with the server";
            logger.error(String.format("error: %s", e));
        }
        return JsonUtil.createJson(code, result);
    }

    @PostMapping("/delete") @ResponseBody
    public Object delete(HttpSession session, Long projectId) {
        PlayerProject project = managementService.getProjectById(projectId);
        User user = (User) session.getAttribute("currentUser");
        if (user == null || !user.getId().equals(project.getUser().getId())) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        managementService.deleteProject(projectId);
        return JsonUtil.createJson("1", "Delete successfully");
    }

    @RequestMapping("/getProjects")
    @ResponseBody
    public Object getProjects(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        List<PlayerProject> projects = managementService.getProjectsByUser(user.getId());
        return projects;
    }
}

