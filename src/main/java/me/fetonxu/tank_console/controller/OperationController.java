package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.service.OperationService;
import me.fetonxu.tank_console.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @PostMapping("/observe")
    @ResponseBody
    public Object observeUser(HttpSession session, @RequestBody String strJson){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        JSONObject json = JSONObject.parseObject(strJson);
        Long observeeId = json.getLong("observeeId");
        Boolean like = json.getBoolean("like");
        operationService.observe(user.getId(), observeeId, like);
        return JsonUtil.createJson("1", "Operation committed");
    }
    @PostMapping("/likeLog")
    @ResponseBody
    public Object likeLog(HttpSession session, @RequestBody String strJson){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        JSONObject json = JSONObject.parseObject(strJson);
        String logId = json.getString("logId");
        Boolean like = json.getBoolean("like");
        operationService.keepLog(user.getId(), logId, like);
        return JsonUtil.createJson("1", "Operation committed");
    }
    @PostMapping("/invite")
    @ResponseBody
    public Object invite(HttpSession session, @RequestBody String strJson){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        JSONObject json = JSONObject.parseObject(strJson);
        Long inviteeId = json.getLong("inviteeId");
        Long roomId = json.getLong("roomId");
        operationService.invite(user.getId(), inviteeId, roomId);
        return JsonUtil.createJson("1", "Operation committed");
    }

    @PostMapping("/handleInvitation")
    @ResponseBody
    public Object handleInvation(HttpSession session, @RequestBody String strJson){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        JSONObject json = JSONObject.parseObject(strJson);
        Long inviterId = json.getLong("inviterId");
        Long roomId = json.getLong("roomId");
        Boolean accept = json.getBoolean("accept");
        operationService.handleInvitation(inviterId, user.getId(), roomId, accept);
        return JsonUtil.createJson("1", "Operation committed");
    }
}
