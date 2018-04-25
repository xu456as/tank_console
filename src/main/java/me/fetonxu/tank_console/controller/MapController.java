package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.service.MapService;
import me.fetonxu.tank_console.util.Config;
import me.fetonxu.tank_console.util.FileUtil;
import me.fetonxu.tank_console.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;

@Controller @RequestMapping("/map") public class MapController {

    private final static Logger logger = LoggerFactory.getLogger(MapController.class);

    @Autowired private MapService mapService;

    @PostMapping("/add") @ResponseBody
    public Object addMap(HttpSession session, @RequestBody byte[] body,
        @RequestParam("name") String name,
        @RequestParam(value = "winScore", defaultValue = "0", required = false) Integer winScore,
        @RequestParam(value = "loseScore", defaultValue = "0", required = false)
            Integer loseScore) {
        User user = (User) session.getAttribute("currentUser");
        String code;
        String result;
        if (user == null || user.getType() != User.TYPE_MANAGER) {
            code = "0";
            result = "Invalid behavior!";
            return JsonUtil.createJson(code, result);
        }
        long timestamp = System.currentTimeMillis();
        String baseDir = Config.getString("map.upload.path");
        try {
            String dest = FileUtil.saveFile(baseDir, name, body);
            BattleMap map = new BattleMap();
            map.setUser(user);
            map.setName(name);
            map.setWinScore(winScore);
            map.setLoseScore(loseScore);
            map.setDate(timestamp);
            map.setUrl(dest);
            mapService.uploadMap(map);
            code = "1";
            result = "Add map successfully";
        } catch (Exception e) {
            code = "0";
            result = "Something wrong with server!";
            logger.error(String.format("error: %s", e));
        }
        return JsonUtil.createJson(code, result);
    }

    @PostMapping("/delete") @ResponseBody
    public Object deleteMap(Long mapId) {
        mapService.deleteMap(mapId);
        return JsonUtil.createJson("1", "Delete successfully");
    }


    @RequestMapping("/getAll") @ResponseBody public Object getAll() {
        return mapService.getMaps();
    }

}
