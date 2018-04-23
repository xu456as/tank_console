package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PlayerProject {

    public final static int COMPILE_STATE_PASS = 1;
    public final static int COMPILE_STATE_FAIL = 2;
    public final static int RUN_STATE_PASS = 1;
    public final static int RUN_STATE_FAIL = 2;

    private Long id = -1l;
    private User user;
    private String name;
    private String url;
    private String compressType;
    private String language;
    private Long date = System.currentTimeMillis();
    private Float size = 0.0f;
    private Integer compileState = 0;
    private Integer runState = 0;
    private List<BattleRecord> records = Collections.emptyList();
}
