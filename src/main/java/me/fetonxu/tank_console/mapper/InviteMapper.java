package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.InviteMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviteMapper {
    void save(@Param("inviterId") Long inviterId, @Param("inviteeId") Long inviteeId,
        @Param("roomId") Long roomId, @Param("date") Long date, @Param("duration") Long duration,
        @Param("state") Integer state);

    void updateWithAction(@Param("inviterId") Long inviterId, @Param("inviteeId") Long inviteeId,
        @Param("roomId") Long roomId, @Param("action")Integer action);

    void delete(@Param("inviterId") Long inviterId, @Param("inviteeId") Long inviteeId,
        @Param("roomId") Long roomId);

    List<InviteMessage> findByUserId(Long userId);
}
