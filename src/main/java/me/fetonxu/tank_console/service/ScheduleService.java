package me.fetonxu.tank_console.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import me.fetonxu.tank_console.entity.PlayerProject;
import me.fetonxu.tank_console.mapper.PlayerProjectMapper;
import me.fetonxu.tank_console.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired private PlayerProjectMapper projectMapper;

    @Autowired private RestTemplate restTemplate;

    public String uploadProject(long projectId){
        String url = Config.getString("api.player.url.upload") + "?userId={userId}&encoding={encoding}&timestamp={timestamp}";
        PlayerProject project = projectMapper.findById(projectId);
        File file = new File(project.getUrl());
        try {
            ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(1024 * 1024);
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[1024 * 1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1){
                if(buffer.writableBytes() < len){
                    buffer.capacity(buffer.maxCapacity() * 2);
                }
                buffer.writeBytes(bytes, 0, len);
            }
            HttpEntity<byte[]> request = new HttpEntity<>(ByteBufUtil.getBytes(buffer));
            Map<String, String> uriMap = new HashMap<>();
            uriMap.put("userId", String.valueOf(project.getUser().getId()));
            uriMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
            uriMap.put("encoding", project.getCompressType());
            String result = restTemplate.postForObject(url, request, String.class, uriMap);
            return result;
        }catch (Exception e){
            logger.error(String.format("error: %s", e));
        }
        return "0;error";
    }

    public String compileProject(long projectId){
        return null;
    }
    public String runProject(long projectId, long timestamp){
        return null;
    }
    public boolean stopProject(long projectId, long timestamp){

        return true;
    }
    public String runJudge(long projectAId, long timestampA, long projectBId, long timestampB){
        return null;
    }
    public void receiveLog(File log){

    }
}
