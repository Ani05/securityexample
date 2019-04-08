package com.example.securityexample.controller;


import com.example.securityexample.model.User;
import com.example.securityexample.model.UserType;
import com.example.securityexample.repositori.UserRepasitory;
import com.example.securityexample.security.SpringUser;
import com.example.securityexample.service.EmailService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownServiceException;
import java.util.List;

@Controller
public class MainController {
    @Value("${image.upload.dir}")
    private String imageUploadDir;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepasitory userRepasitory;

    @Autowired
    private EmailService emailService;
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepasitory.save(user);
        emailService.sendSimpleMessage(user.getEmail(), "Բարի գալուստ" +user.getName(), "Դուք հաջողությամբ գրանցել եք");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/user")
    public String userPage(ModelMap modelMap,
                           @AuthenticationPrincipal
                                   SpringUser springUser) {
        List<User> all = userRepasitory.findAll();
        modelMap.addAttribute("users", springUser.getUser());

        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap map, @AuthenticationPrincipal SpringUser springUser) {
        List<User> all = userRepasitory.findAll();
        map.addAttribute("users", springUser.getUser());


        return "admin";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal
                                       SpringUser springUser) {
        if (springUser.getUser().getUserType() == UserType.ADMIN) {
            return "redirect:/admin";
        }
        return "redirect:/user";

    }

    @GetMapping("/getImage")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }


}
