package me.fetonxu.tank_console.controller;

import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.BattleLogMapper;
import me.fetonxu.tank_console.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private BattleLogMapper logMapper;


    @RequestMapping("/getLog")
    @ResponseBody
    public Object getUserLogs(HttpSession session){
        User user = (User)session.getAttribute("currentUser");
        if(user == null){
            return JsonUtil.createJson("0", "Invalid behavior");
        }
        return logMapper.findByUserId(user.getId());
    }

}
