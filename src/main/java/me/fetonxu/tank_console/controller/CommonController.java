package me.fetonxu.tank_console.controller;

import me.fetonxu.tank_console.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

    private final static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("/download")
    public Object download(String url){
        if(!url.startsWith("/data")){
            return JsonUtil.createJson("0", "Invalid resource");
        }
        FileSystemResource file = new FileSystemResource(url);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("File-Name", file.getFilename());


        ResponseEntity entity = null;
        try {
            entity = ResponseEntity.ok().headers(headers).contentLength(file.contentLength()).contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
        }catch (Exception e){
            entity = ResponseEntity.ok(JsonUtil.createJson("0", "Something wrong with server"));
            logger.error(String.format("error: %s", e));
        }
        return entity;
    }

}
