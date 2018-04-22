package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BattleLog {

    public final static int WINNER_A = 1;
    public final static int WINNER_B = 2;

    private String id;
    private PlayerProject projectA;
    private PlayerProject projectB;
    private BattleMap map;
    private int winner = 0;
    private String url;
    private String compressType;
    private float size = 0f;
    private Date date = new Date();
}
