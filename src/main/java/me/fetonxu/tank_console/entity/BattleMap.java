package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BattleMap {
    private long id = -1;
    private String name;
    private String url;
    private Date date = new Date();
    private User user;
    private int winScore = 0;
    private int loseScore = 0;
}
