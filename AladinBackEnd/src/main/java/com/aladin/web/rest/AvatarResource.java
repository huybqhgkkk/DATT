package com.aladin.web.rest;

import com.aladin.service.AvatarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api")
public class AvatarResource {
    private final AvatarService avatarService;
    private final Logger log = LoggerFactory.getLogger(AvatarResource.class);

    public AvatarResource(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/avatar")
    public ResponseEntity create( @RequestParam MultipartFile image) throws Exception {
        String name;
        long time = System.currentTimeMillis();
        if (image.getContentType().equals("image/png"))
        {
            name = time+".png";
        } else
        if (image.getContentType().equals("image/jpeg"))
        {
            name =time+".jpg";
        } else
        {
            return new ResponseEntity<Object>("Not Png or jpg", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String linkavatar = avatarService.uploadFile(image,name);
        if (linkavatar.equals("ErrorFTP"))
            return new ResponseEntity<Object>("Error FTP", HttpStatus.INTERNAL_SERVER_ERROR);
         else
        return new ResponseEntity<Object>(linkavatar, HttpStatus.OK);
    }
}
