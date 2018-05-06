package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.*;
import me.fetonxu.tank_console.mapper.BattleLogMapper;
import me.fetonxu.tank_console.mapper.BattleMapMapper;
import me.fetonxu.tank_console.mapper.PlayerProjectMapper;
import me.fetonxu.tank_console.mapper.RoomMapper;
import me.fetonxu.tank_console.service.MachinePortService;
import me.fetonxu.tank_console.service.ScheduleService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Controller @RequestMapping("/schedule") public class ScheduleController {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired private PlayerProjectMapper projectMapper;

    @Autowired private RoomMapper roomMapper;

    @Autowired private BattleMapMapper mapMapper;

    @Autowired private ScheduleService scheduleService;

    @Autowired private MachinePortService machinePortService;

    @Autowired private BattleLogMapper battleLogMapper;

    private String machineIp = Config.getString("player.local.net");

    private ExecutorService threadpool = Executors.newFixedThreadPool(2);

    @PostConstruct public void init() {

        machinePortService.registerMachine(machineIp);
    }

    @PostMapping("/result") @ResponseBody
    public Object receiveGameResult(@RequestBody String resultJson) {
        String baseDir = Config.getString("battle_log.upload.path");
        JSONObject json = JSONObject.parseObject(resultJson);
        String metaInfo = json.getString("metaInfo");
        String[] metaInfos = metaInfo.split("\\.");
        String state = json.getString("state");
        logger.info(String.format("BattleLog received, metaInfo: %s", metaInfo));
        long timestamp = Long.parseLong(metaInfos[0]);
        String mapName = metaInfos[1];
        long aId = Long.parseLong(metaInfos[2]);
        int aPort = Integer.parseInt(metaInfos[3]);
        long bId = Long.parseLong(metaInfos[4]);
        int bPort = Integer.parseInt(metaInfos[5]);

        String[] states = state.split(",");
        int aScore = Integer.parseInt(states[0].split("\\:")[2].trim());
        int bScore = Integer.parseInt(states[1].split("\\:")[2].trim());

        int reclaimCnt = 0;

        Future<Integer> aResult = threadpool.submit(new Callable<Integer>() {

            @Override public Integer call() throws Exception {
                return scheduleService.stopProject(aPort).charAt(0) - '0';
            }
        });
        Future<Integer> bResult = threadpool.submit(new Callable<Integer>() {

            @Override public Integer call() throws Exception {
                return scheduleService.stopProject(bPort).charAt(0) - '0';
            }
        });
        try {
            if (aResult.get() == 1 && bResult.get() == 1) {
                machinePortService.recyclePort(machineIp, aPort);
                machinePortService.recyclePort(machineIp, bPort);
            }
        }catch (Exception e){
            logger.error(String.format("error: %s", e));
        }

        String battleLogUploadPath = Config.getString("battle_log.upload.path");
        BattleMap battleMap = mapMapper.findByName(mapName);

        BattleLog battleLog = new BattleLog();
        battleLog.setId(metaInfo);
        battleLog.setMapId(battleMap.getId());
        battleLog.setProjectAId(aId);
        battleLog.setProjectBId(bId);
        battleLog.setAScore(aScore);
        battleLog.setBScore(bScore);
        battleLog.setWinner(json.getInteger("result"));
        battleLog.setUrl(battleLogUploadPath + "/" + metaInfo);

        battleLogMapper.save(battleLog);

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

        scheduleService.uploadProject(hostProject.getId(), timestamp);
        scheduleService.uploadProject(guestProject.getId(), timestamp);

        scheduleService.compileProject(hostProject.getId(), timestamp);
        scheduleService.compileProject(guestProject.getId(), timestamp);

        String playerLocalNet = Config.getString("player.local.net");

        int aPort = machinePortService.takePort(playerLocalNet);
        int bPort = machinePortService.takePort(playerLocalNet);

        scheduleService.runProject(hostProject.getId(), timestamp, aPort);
        scheduleService.runProject(guestProject.getId(), timestamp, bPort);


        String playerAAddress =
            String.format("%s:%d", playerLocalNet, aPort);
        String playerBAddress =
            String.format("%s:%d", playerLocalNet, bPort);

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
