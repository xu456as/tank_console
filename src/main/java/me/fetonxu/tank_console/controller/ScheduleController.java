package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.entity.PlayerProject;
import me.fetonxu.tank_console.entity.Room;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.PlayerProjectMapper;
import me.fetonxu.tank_console.mapper.RoomMapper;
import me.fetonxu.tank_console.service.MachinePortService;
import me.fetonxu.tank_console.service.ScheduleService;
import me.fetonxu.tank_console.util.Config;
import me.fetonxu.tank_console.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.PanelUI;
import java.util.concurrent.ConcurrentLinkedDeque;

@Controller @RequestMapping("/schedule") public class ScheduleController {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired private PlayerProjectMapper projectMapper;

    @Autowired private RoomMapper roomMapper;

    @Autowired private ScheduleService scheduleService;

    @Autowired private MachinePortService machinePortService;

    @PostConstruct public void init() {
        machinePortService.registerMachine(Config.getString("player.local.net"));
    }

    @PostMapping("/result") @ResponseBody public Object receiveGameResult() {
        return "1;received";
    }

    @PostMapping("/play") @ResponseBody
    public Object play(HttpSession session, Long roomId, Long projectId) {
        User user = (User) session.getAttribute("currentUser");
        PlayerProject guestProject = projectMapper.findById(projectId);
        Room room = roomMapper.findById(roomId);
        BattleMap map = room.getMap();
        PlayerProject hostProject = room.getProject();
        if (user == null || !user.getId().equals(guestProject.getUser().getId())) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }

        long timestamp = System.currentTimeMillis();

        scheduleService.uploadProject(hostProject.getId());
        scheduleService.uploadProject(guestProject.getId());

        scheduleService.compileProject(hostProject.getId());
        scheduleService.compileProject(guestProject.getId());

        scheduleService.runProject(hostProject.getId(), timestamp);
        scheduleService.runProject(guestProject.getId(), timestamp);

        String playerLocalNet = Config.getString("player.local.net");

        String playerAAddress =
            String.format("%s:%d", playerLocalNet, machinePortService.takePort(playerLocalNet));
        String playerBAddress =
            String.format("%s:%d", playerLocalNet, machinePortService.takePort(playerLocalNet));

        JSONObject json = new JSONObject();
        json.put("mapFile", map.getName());
        json.put("noOfTanks", map.getNumberOfTank());
        json.put("tankSpeed", map.getTankSpeed());
        json.put("shellSpeed", map.getShellSpeed());
        json.put("tankHP", map.getTankHp());
        json.put("tankScore", map.getTankScore());
        json.put("flagScore", map.getFlagScore());
        json.put("maxRound", map.getMaxRound());
        json.put("roundTimeout", map.getRoundTimeout());
        json.put("playerAAddress", playerAAddress);
        json.put("playerBAddress", playerBAddress);
        json.put("aId", hostProject.getId());
        json.put("bId", guestProject.getId());
        json.put("timestamp", timestamp);

        String result = scheduleService.runJudge(json.toJSONString());
        return JsonUtil.createJson("1", result);
    }

}
