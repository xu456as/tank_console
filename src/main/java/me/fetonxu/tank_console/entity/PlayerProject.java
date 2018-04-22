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

    private long id = -1;
    private User user;
    private String name;
    private String url;
    private String compressType;
    private String language;
    private Date date = new Date();
    private float size = 0.0f;
    private int compileState = 0;
    private int runState = 0;
    private List<BattleRecord> records = Collections.emptyList();
}
