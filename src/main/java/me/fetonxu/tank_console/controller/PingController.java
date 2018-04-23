package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.BattleMapMapper;
import me.fetonxu.tank_console.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller @RequestMapping("/ping") public class PingController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BattleMapMapper battleMapMapper;

    @RequestMapping @ResponseBody
    public Object ping(@RequestParam(required = false, defaultValue = "Success") String value, HttpSession session) {

        User user = (User)session.getAttribute("user");
        if(user == null){
            session.setAttribute("user", new User());
            System.out.println("no user");
        }
        else {
            System.out.println(user.getId());
        }

        List<BattleMap> map = battleMapMapper.findAll();


        JSONObject obj = new JSONObject();
        obj.put("Ping", value);
        return map;
    }

}
