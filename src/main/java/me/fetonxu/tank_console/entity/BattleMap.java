package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BattleMap {
    private Long id = -1l;
    private String name;
    private String url;
    private Integer numberOfTank = 4;
    private Integer tankSpeed = 4;
    private Integer shellSpeed = 6;
    private Integer tankHp = 4;
    private Integer tankScore = 3;
    private Integer flagScore = 4;
    private Integer maxRound = 150;
    private Integer roundTimeout = 3000;
    private Long date = System.currentTimeMillis();
    private User user;
    private Integer winScore = 0;
    private Integer loseScore = 0;
}
