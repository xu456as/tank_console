package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class BattleRecord {

    public static final int WINNER_MYSQLF = 1;
    public static final int WINNER_ENEMY = 2;

    private PlayerProject enemy;
    private BattleLog log;
    private Integer winner = 0;
    private Long date = System.currentTimeMillis();

}
