package com.example.ding.controller;

import com.example.ding.entity.Cai;
import com.example.ding.entity.File;
import com.example.ding.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.example.ding.service.CaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
public class CaiController {
    @Autowired
    private CaiService caiService;
    @Autowired
    private FileService fileService;
    @GetMapping("/caiinfo")
    public String getCaiInfo(@RequestParam("id")int id,Model model){
        Cai cai = caiService.selectById(id);
        model.addAttribute("caiinfo",cai);
        model.addAttribute("imageURL","/image?id="+id);
        return "index";
    }
    @GetMapping("/image")
    public ResponseEntity<ByteArrayResource> queryResult(@RequestParam("id") int id) {
        System.out.println(id);
        File file = fileService.selectfilebyid(id);
        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }
    @GetMapping ("/all")
    public String page(Model model){
        List<Cai> caiList = caiService.selectAll();
        model.addAttribute("caiList",caiList);
        model.addAttribute("imageURL","/image?id=");
        return "index";
    }
    @GetMapping ("/pageInfo")
    public String page(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        int size = 2;  // 每页10条数据
        System.out.println(page);
        int pageset = 2*(page-1);
        int totalpage = caiService.selectAll().size();
        int pageNum = totalpage/size;
        if (totalpage%size!=0){
            pageNum++;
        }
        List<Cai> caiList = caiService.selectByPage(pageset, size);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("caiList", caiList);
        model.addAttribute("imageURL","/image?id=");
        model.addAttribute("pagelocal",page);
        System.out.println(caiList);
        return "index";
    }

    @GetMapping("/change")
    public String change(@RequestParam("price")int price,
                         @RequestParam("name")String name,
                         @RequestParam("fenlei")String fenlei,
                         Model model)
    {
        Cai cai = new Cai();
        cai.setName(name);
        cai.setFenlei(fenlei);
        cai.setPrice(price);
        caiService.insertCai(cai);
        System.out.println(cai);
        model.addAttribute("newCai",cai);
        return "index";
    }
    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> delete(@RequestParam("id")int id) {
        Map<String,String> response = new HashMap<>();
        caiService.deleteById(id);
        response.put("message","删除成功");
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/update")
    public String bianji(@RequestParam("id")int id,@RequestParam("price")int price,
                         @RequestParam("name")String name,@RequestParam("fenlei")String fenlei, Model model){
        System.out.println(price);
        Cai oldcai = caiService.selectById(id);
        oldcai.setPrice(price);
        oldcai.setName(name);
        oldcai.setFenlei(fenlei);
        System.out.println(oldcai);
        caiService.updateById(oldcai);
        model.addAttribute("message","修改成功");
        return "index";
    }

}
