package me.fetonxu.tank_console.controller;

import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.service.OperationService;
import me.fetonxu.tank_console.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Object observeUser(HttpSession session, Long observeeId, Boolean like){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        operationService.observe(user.getId(), observeeId, like);
        return JsonUtil.createJson("1", "Operation committed");
    }
    @PostMapping("/likeLog")
    @ResponseBody
    public Object likeLog(HttpSession session, String logId, Boolean like){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        operationService.keepLog(user.getId(), logId, like);
        return JsonUtil.createJson("1", "Operation committed");
    }
    @PostMapping("/invite")
    @ResponseBody
    public Object invite(HttpSession session, Long inviteeId, Long roomId){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        operationService.invite(user.getId(), inviteeId, roomId);
        return JsonUtil.createJson("1", "Operation committed");
    }
    @PostMapping("/handleInvitation")
    @ResponseBody
    public Object handleInvation(HttpSession session, Long inviterId, Long roomId, Boolean accept){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        operationService.handleInvitation(inviterId, user.getId(), roomId, accept);
        return JsonUtil.createJson("1", "Operation committed");
    }
}
