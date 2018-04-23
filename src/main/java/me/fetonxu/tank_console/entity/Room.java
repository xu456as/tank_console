package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class Room {

    private Long id = -1l;
    private User user;
    private BattleMap map;
    private Integer maxChallengers = 0;
    private Integer minChallengers = 0;
    private Long date = System.currentTimeMillis();
    private Long duration = 0l;

}
