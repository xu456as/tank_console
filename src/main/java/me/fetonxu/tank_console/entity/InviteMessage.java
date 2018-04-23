package me.fetonxu.tank_console.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class InviteMessage {

    public static final int INVITE_MESSAGE_ACCEPT = 1;
    public static final int INVITE_MESSAGE_DENY = 2;

    User invitee;
    Room room;
    Long date = System.currentTimeMillis();
    Long duration = 0l;
    Integer state = 0;
}
