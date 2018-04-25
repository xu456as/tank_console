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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller @RequestMapping("/strategyManagement") public class StrategyManagementController {

    private final static Logger logger = LoggerFactory.getLogger(StrategyManagementController.class);

    @Autowired private StrategyManagementService managementService;

    @PostMapping("/uploadProject")
    public Object uploadProject(HttpSession session, @RequestParam("name") String name,
        @RequestParam("compressType") String compressType,
        @RequestParam("language") String language,
        @RequestParam(value = "random", defaultValue = "0", required = false) Integer random,
        @RequestBody byte[] body) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        String baseDir = Config.getString("project.upload.path");
        baseDir = String.format("%s/%d", baseDir, user.getId());
        File directory = new File(baseDir);
        if(!directory.exists()){
            directory.mkdirs();
        }
        String code = "0", result="default";
        try {
            FileUtil.saveFile(baseDir, name, body);
            PlayerProject project = new PlayerProject();
            project.setUser(user);
            project.setName(name);
            project.setLanguage(language);
            project.setCompressType(compressType);
            project.setRandom(random);
            project.setSize(Float.valueOf(body.length));
            managementService.save(project);
            code = "1";
            result = "Project uploaded successfully";
        }catch (Exception e){
            code = "0";
            result = "Something wrong with the server";
            logger.error(String.format("error: %s", e));
        }
        return JsonUtil.createJson(code, result);
    }

}

