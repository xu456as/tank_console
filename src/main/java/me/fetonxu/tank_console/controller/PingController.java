package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller @RequestMapping("/ping") public class PingController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping @ResponseBody
    public Object ping(@RequestParam(required = false, defaultValue = "Success") String value) {


        User user = userMapper.getUser(1);


        JSONObject obj = new JSONObject();
        obj.put("Ping", value);
        return user;
    }

}
