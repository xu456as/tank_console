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
    private Long date = System.currentTimeMillis();
    private User user;
    private Integer winScore = 0;
    private Integer loseScore = 0;
}
