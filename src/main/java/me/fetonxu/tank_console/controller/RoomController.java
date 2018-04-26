package me.fetonxu.tank_console.controller;

import com.github.pagehelper.PageHelper;
import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.entity.PlayerProject;
import me.fetonxu.tank_console.entity.Room;
import me.fetonxu.tank_console.mapper.BattleMapMapper;
import me.fetonxu.tank_console.mapper.PlayerProjectMapper;
import me.fetonxu.tank_console.service.MapService;
import me.fetonxu.tank_console.service.RoomService;
import me.fetonxu.tank_console.service.StrategyManagementService;
import me.fetonxu.tank_console.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller @RequestMapping("/room") public class RoomController {
    @Autowired private RoomService roomService;

    @Autowired private PlayerProjectMapper projectMapper;

    @Autowired private BattleMapMapper battleMapMapper;

    @PostMapping("create") @ResponseBody
    public Object createRoom(HttpSession session, Long projectId, Long mapId) {

        PlayerProject project = projectMapper.findById(projectId);
        BattleMap map = battleMapMapper.findById(mapId);

        if (project == null || map == null || session.getAttribute("currentUser") == null) {
            return JsonUtil.createJson("0", "Invalid behavior");
        }

        Room room = new Room();
        room.setMaxChallengers(20);
        room.setDuration(24l * 60 * 60 * 1000);
        room.setStartTime(System.currentTimeMillis());
        room.setProject(project);
        room.setMap(map);
        roomService.createRoom(room);

        return JsonUtil.createJson("1", "Create room successfully");
    }

    @RequestMapping("getRooms") @ResponseBody
    public Object getRooms(@RequestParam(value = "pageIdx", required = false, defaultValue = "1") Integer pageIdx,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return roomService.getRoomsPaged(pageIdx, pageSize);
    }
}
