package me.fetonxu.tank_console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller @RequestMapping("/schedule") public class ScheduleController {
    @PostMapping("/result") @ResponseBody public Object receiveGameResult() {
        return "1;received";
    }
}
