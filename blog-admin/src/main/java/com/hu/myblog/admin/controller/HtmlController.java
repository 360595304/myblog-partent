package com.hu.myblog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author suhu
 * @createDate 2022/3/14
 */
@Controller
public class HtmlController {
    @GetMapping("/")
    public String html() {
        return "forward:/login.html";
    }
}
