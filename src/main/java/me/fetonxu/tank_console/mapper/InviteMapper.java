package me.fetonxu.tank_console.mapper;

import me.fetonxu.tank_console.entity.InviteMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviteMapper {
    void save(@Param("inviterId") Long inviterId, @Param("inviteeId") Long inviteeId, @Param("roomId")Long roomId, @Param("duration")Long duration, @Param("state")Integer state);
    void delete(@Param("inviterId")Long inviterId, @Param("inviteeId")Long inviteeId, @Param("roomId")Long roomId);
    List<InviteMessage> findByUserId(Long userId);
}
