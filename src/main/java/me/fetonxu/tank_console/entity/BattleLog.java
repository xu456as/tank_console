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
    private Long projectAId;
    private Long projectBId;
    private Long mapId;
    private Integer winner = 0;
    private String url;
    private String compressType;
    private Float size = 0f;
    private Long date = System.currentTimeMillis();
}
