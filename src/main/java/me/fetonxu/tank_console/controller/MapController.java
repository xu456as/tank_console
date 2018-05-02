package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.BattleMapMapper;
import me.fetonxu.tank_console.service.MapService;
import me.fetonxu.tank_console.util.Config;
import me.fetonxu.tank_console.util.FileUtil;
import me.fetonxu.tank_console.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;

@Controller @RequestMapping("/map") public class MapController {

    private final static Logger logger = LoggerFactory.getLogger(MapController.class);

    @Autowired private MapService mapService;

    @Autowired private BattleMapMapper mapMapper;

    @PostMapping("/add") @ResponseBody
    public Object addMap(HttpSession session, MultipartFile mapFile,
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
            String dest = FileUtil.saveFile(baseDir, name, mapFile.getBytes());
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

    @PostMapping("/edit")
    @ResponseBody
    public Object editMap(HttpSession session, @RequestBody String mapJson){
        User user = (User)session.getAttribute("currentUser");
        if(user == null || user.getType() != User.TYPE_MANAGER){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        JSONObject json = JSON.parseObject(mapJson);
        BattleMap battleMap = mapMapper.findById(json.getLong("id"));
        battleMap.setLoseScore(json.getInteger("loseScore"));
        battleMap.setWinScore(json.getInteger("winScore"));
        battleMap.setNumberOfTank(json.getInteger("numberOfTank"));
        battleMap.setTankSpeed(json.getInteger("tankSpeed"));
        battleMap.setShellSpeed(json.getInteger("shellSpeed"));
        battleMap.setTankHp(json.getInteger("tankHp"));
        battleMap.setTankScore(json.getInteger("tankScore"));
        battleMap.setFlagScore(json.getInteger("flagScore"));
        battleMap.setRoundTimeout(json.getInteger("roundTimeout"));
        battleMap.setMaxRound(json.getInteger("maxRound"));
        mapMapper.update(battleMap);
        return JsonUtil.createJson("1", "Map altered successfully");
    }

    @PostMapping("/delete") @ResponseBody
    public Object deleteMap(HttpSession session, @RequestBody String mapJson) {
        User user = (User)session.getAttribute("currentUser");
        if(user == null || user.getType() != User.TYPE_MANAGER){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        JSONObject json = JSON.parseObject(mapJson);
        mapService.deleteMap(json.getLong("mapId"));
        return JsonUtil.createJson("1", "Delete successfully");
    }

    @RequestMapping("/getAll") @ResponseBody public Object getAll() {
        return mapService.getMaps();
    }


}
