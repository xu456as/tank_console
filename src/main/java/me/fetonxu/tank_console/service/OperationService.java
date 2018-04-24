package me.fetonxu.tank_console.service;

import me.fetonxu.tank_console.entity.BattleLog;
import me.fetonxu.tank_console.entity.InviteMessage;
import me.fetonxu.tank_console.mapper.BattleLogMapper;
import me.fetonxu.tank_console.mapper.InviteMapper;
import me.fetonxu.tank_console.mapper.LikeLogMapper;
import me.fetonxu.tank_console.mapper.ObserverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service public class OperationService {

    public static final long ONE_DAY_lONG = 24 * 60 * 60 * 1000;

    @Autowired private ObserverMapper observerMapper;

    @Autowired private LikeLogMapper likeLogMapper;

    @Autowired private InviteMapper inviteMapper;

    public boolean observe(long observerId, long observeeId, boolean like) {
        if (like) {
            observerMapper.save(observerId, observeeId, System.currentTimeMillis());
        } else {
            observerMapper.delete(observerId, observeeId);
        }
        return true;
    }

    public boolean keepLog(long userId, String logId, boolean like) {
        if (like) {
            likeLogMapper.save(userId, logId, System.currentTimeMillis());
        } else {
            likeLogMapper.delete(userId, logId);
        }
        return true;

    }

    public boolean invite(long inviterId, long inviteeId, long roomId) {
        inviteMapper
            .save(inviterId, inviteeId, roomId, System.currentTimeMillis(), ONE_DAY_lONG, 0);
        return true;
    }

    public boolean handleInvitation(long inviterId, long inviteeId, long roomId, boolean accept) {

        inviteMapper.updateWithAction(inviterId, inviteeId, roomId,
            accept ? InviteMessage.INVITE_MESSAGE_ACCEPT : InviteMessage.INVITE_MESSAGE_DENY);

        return true;
    }


}
