package me.fetonxu.tank_console.service;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    public String uploadProject(long projectId, long timestamp){
        String url = Config.getString("api.player.url.upload") + "?projectId={projectId}&encoding={encoding}&timestamp={timestamp}";
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
            uriMap.put("projectId", String.valueOf(projectId));
            uriMap.put("timestamp", String.valueOf(timestamp));
            uriMap.put("encoding", project.getCompressType());
            String result = restTemplate.postForObject(url, request, String.class, uriMap);
            return result;
        }catch (Exception e){
            logger.error(String.format("error: %s", e));
        }
        return "0;error";
    }

    public String compileProject(long projectId, long timestamp){
        String url = Config.getString("api.player.url.compile") + "?projectId={projectId}&timestamp={timestamp}";
        Map<String, String> uriMap = new HashMap<>();
        uriMap.put("projectId", String.valueOf(projectId));
        uriMap.put("timestamp", String.valueOf(timestamp));
        String result = restTemplate.postForObject(url, null, String.class, uriMap);
        return result;
    }
    public String runProject(long projectId, long timestamp, int port){
        String url = Config.getString("api.player.url.run") + "?projectId={projectId}&timestamp={timestamp}&port={port}";
        Map<String, String> uriMap = new HashMap<>();
        uriMap.put("projectId", String.valueOf(projectId));
        uriMap.put("timestamp", String.valueOf(timestamp));
        uriMap.put("port", String.valueOf(port));
        String result = restTemplate.postForObject(url, null, String.class, uriMap);
        return result;
    }
    public String stopProject(int port){
        String url = Config.getString("api.player.url.reclaim") + "?port={port}";
        Map<String, String> uriMap = new HashMap<>();
        uriMap.put("port", String.valueOf(port));
        String result = restTemplate.postForObject(url, null, String.class, uriMap);
        return result;
    }
    public String runJudge(String strJson){
        String url = Config.getString("api.judge.url.run");

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(strJson,headers);
        String result = restTemplate.postForObject(url, request, String.class);
        return result;
    }
    public void receiveLog(File log){

    }
}
