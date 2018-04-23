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

    private Long id = -1l;
    private String email;
    private String password;
    private Integer type = TYPE_NORMAL;
    private String phone;
    private Integer score = 0;
    private List<User> observeee = Collections.emptyList();
    private List<BattleLog> likeLogs = Collections.emptyList();
    private List<Map<BattleMap, Float>> winRates = Collections.emptyList();
    private List<InviteMessage> inviteMessages = Collections.emptyList();
}
