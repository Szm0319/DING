package com.example.ding.controller;

import com.example.ding.entity.File;
import com.example.ding.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("id")int id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!file.isEmpty()) {
                System.out.println(id);
                // 获取文件名
                File file1 = new File(); // 注意这里，建议重新命名您的File类
                file1.setFilename(file.getOriginalFilename());
                file1.setFiledata(file.getBytes());
                file1.setCai_id(id);
                if(!fileService.existsbyid(id)){
                    fileService.save(file1);
                    response.put("message", "文件保存成功");
                    response.put("评价","wa!这也太厉害了");
                    return ResponseEntity.ok(response);
                }
                response.put("message","此菜品已存在产品图");
                return ResponseEntity.badRequest().body(response);
            } else {
                response.put("message", "上传的文件为空");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Error during file upload:", e);
            response.put("message", "上传失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

}

