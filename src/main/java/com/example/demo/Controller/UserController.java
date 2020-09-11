package com.example.demo.Controller;

import com.example.demo.Dto.UserDTO;
import com.example.demo.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class UserController
{
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String firstview() {
        return "user/login.html";
    }

    @GetMapping("/signup")
    public String goToMemberRegister() {
        return "user/signup.html";
    }

    /*@RequestMapping(value="/login", method = RequestMethod.POST)
    public String test(@RequestParam String name, @RequestParam String password, Model model) throws Exception {
        return "post/postlist.html";
    }*/

    //Requestbody 사용하지 않아도 되는가 ?
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@RequestParam String name, @RequestParam String password,Model model) throws Exception {

        UserDTO mv = new UserDTO(name,password);
        /*model.addAttribute("name", name);
        model.addAttribute("password", password);*/
        userService.insertUser(mv);
        return "user/login.html";
    }

    @PostMapping("/checkUser")
    public String ckeckUser(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest req) throws Exception {

        UserDTO mv = new UserDTO(name, password);
        UserDTO userinfo = userService.checkUserInfo(mv);

        if (userinfo.getName().equals(name) && userinfo.getPassword().equals(password)) {
            //session 생성 후 id 저장
            HttpSession session = req.getSession();
            String sessionname = userinfo.getName();
            int sessionid = userinfo.getId();
            session.setAttribute("username",sessionname);
            session.setAttribute("userid",sessionid);
            return "post/loginsuccess.html";
        } else {
            //model.addAttribute("result", 0);
            return "redirect:/user/login.html";
        }
    }
}
