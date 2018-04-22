package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class Room {

    private long id = -1;
    private User user;
    private BattleMap map;
    private int maxChallengers = 0;
    private int minChallengers = 0;
    private Date date = new Date();
    private long duration = 0;

}
