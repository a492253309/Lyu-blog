package com.blog.controller;

import com.blog.entity.User;
import com.blog.framework.jwt.JWTUtils;
import com.blog.service.impl.UserServiceImpl;
import com.blog.utils.Maps;
import com.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public Result  create(@RequestBody User user){
        user.setStatus("T");
        userService.create(user);
        return Result.ok(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> paramMap){
        String userName=paramMap.get("userName");
        String password=paramMap.get("password");

        System.out.println(userName+":"+password);
        User user = userService.login(userName, password);

        System.out.println(user);

        if (user!= null){
            String token = JWTUtils.sign(user);

            if (user.getStatus().equals("F")){
                return Result.fail("您的账号已被禁用");
            }
            if (user.getStatus().equals("D")){
                return Result.fail("您的账号已被设置为删除");
            }
            return Result.ok(Maps.build().put("token",token).put("user",user).getMap());
        }else{
            return Result.fail("用户名或密码错误");
        }

    }


}
