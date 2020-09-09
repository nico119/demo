package com.example.demo.Controller;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Service.UserService;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("/show")
    public @ResponseBody
    List<UserDTO> query() throws Exception{
        return userService.getAll();
    }
}