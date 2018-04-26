package me.fetonxu.tank_console.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import me.fetonxu.tank_console.entity.BattleMap;
import me.fetonxu.tank_console.mapper.BattleMapMapper;
import me.fetonxu.tank_console.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapService {

    private final static Logger logger = LoggerFactory.getLogger(MapService.class);

    @Autowired
    private BattleMapMapper battleMapMapper;

    @Autowired private RestTemplate restTemplate;

    private String mapUploadPath = Config.getString("api.judge.url.upload_map");

    public boolean uploadMap (BattleMap map){
        battleMapMapper.save(map);

        String url = mapUploadPath + "?mapName={mapName}";
        File file = new File(map.getUrl());
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
            uriMap.put("mapName", map.getName());
            String result = restTemplate.postForObject(url, request, String.class, uriMap);
            return true;
        }catch (Exception e){
            logger.error(String.format("error: %s", e));
        }

        return false;
    }

    public boolean deleteMap (long mapId){
        battleMapMapper.delete(mapId);
        return true;
    }

    public List<BattleMap> getMaps(){
        return battleMapMapper.findAll();
    }

}
