package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.InviteMessage;

import java.util.List;

public interface InviteMapper {
    void save(Long inviterId, Long inviteeId, Long roomId, Long duration, Integer state);
    void delete(Long inviterId, Long inviteeId, Long roomId);
    List<InviteMessage> findByUserId(Long userId);
}
