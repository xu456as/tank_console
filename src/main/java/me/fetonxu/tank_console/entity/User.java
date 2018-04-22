package me.fetonxu.tank_console.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class User {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MANAGER = 1;

    public static final int INVITE_MESSAGE_ACCEPT = 1;
    public static final int INVITE_MESSAGE_DENY = 2;

    private long id = -1;
    private String email;
    private String password;
    private int type = TYPE_NORMAL;
    private String phone;
    private int score = 0;
    private List<User> observeee = Collections.emptyList();
    private List<BattleLog> likeLogs = Collections.emptyList();
    private List<Map<BattleMap, Float>> winRates = Collections.emptyList();
    private List<Map<User,Integer>> inviteMessages = Collections.emptyList();
}
