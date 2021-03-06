package me.fetonxu.tank_console.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpRequest;
import me.fetonxu.tank_console.entity.User;
import me.fetonxu.tank_console.service.UserService;
import me.fetonxu.tank_console.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("logup")
    @ResponseBody
    public Object logUp(@RequestBody String userJson){
        User user = JSON.parseObject(userJson, User.class);
        User userBack = userService.findUser(user.getEmail());
        if(userBack != null){
            return JsonUtil.createJson("0", "The email has been registered");
        }
        userService.addUser(user);
        JSONObject json = new JSONObject();
        json.put("code", "1");
        json.put("result", "logUp successfully!");
        return json;
    }

    @PostMapping("login")
    @ResponseBody
    public Object logIn(HttpSession session, @RequestBody String userJson){
        System.out.println(userJson);
        User user = JSONObject.parseObject(userJson, User.class);
        JSONObject json = new JSONObject();
        User userBack = userService.findUser(user.getEmail());
        if(userBack != null && user.getPassword().equals(userBack.getPassword())){
            session.setAttribute("currentUser", userBack);
            json.put("code",  "1");
            json.put("result",  "Login Successfully!");
        }
        else{
            json.put("code",  "0");
            json.put("result",  "Password not match the account");
        }
        return json;
    }

    @PostMapping("/queryCurrent")
    @ResponseBody
    public Object queryCurrent(HttpSession session){
        return session.getAttribute("currentUser");
    }

    @PostMapping("logout")
    @ResponseBody
    public Object logOut(HttpSession session){
        session.removeAttribute("currentUser");
        JSONObject json = new JSONObject();
        json.put("code", "1");
        json.put("result", "Log out successfully!");
        return json;
    }
}
