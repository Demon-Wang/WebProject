package com.example.demo.controller;

import com.example.demo.annotation.UserLoginToken;
import com.example.demo.utils.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"*","null"})
public class ToolController {
    @Value("${file.uploadFolder}")
    String filePath;

    @UserLoginToken
    @PostMapping(value = "/base64Upload")
    public String uploadImage(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
        if (file.isEmpty()) {
            throw new APIException("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        if(!suffixName.equals(".jpg") && !suffixName.equals(".png")){
            throw new APIException("请选择图片！！！");
        }
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getServerIPPort(request)+"/api/img/"+fileName;
    }
    public static String getServerIPPort(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
