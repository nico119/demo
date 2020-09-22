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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

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
        return "user/signUp.html";
    }

    //throw catch를 적용할때 if/else 느낌으로 적용?
    //catch는 조건?  조건을 아는법
    //throw,throws 쓰는 사례
    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String password,Model model, HttpServletResponse response) throws Exception {
        UserDTO mv = new UserDTO(name,password);
        if(userService.checkUser(mv)==0){
            userService.insertUser(mv);
            return "user/login.html";
        }
        else{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('동일 아이디/비밀번호가 이미 존재합니다!'); history.go(-1);</script>");
            out.flush();
            return "redirect:/user/signUp.html";
        }
    }

//    @Valid를 통한 유효성검사
//    @ControllerAdvice를 이용한 Exception 헨들링
//    ErrorCode 에러 메시지 통합
    @PostMapping("/checkUser")
    public String ckeckUser(@RequestParam String name, @RequestParam String password, HttpServletRequest req, HttpServletResponse response) throws Exception {

        UserDTO mv = new UserDTO(name, password);
        if(userService.checkUser(mv)==1){
            //session 생성 후 id 저장
            HttpSession session = req.getSession();
            UserDTO user=userService.checkUserInfo(mv);
            String sessionname = name;
            int sessionid = user.getId();
            session.setAttribute("username",sessionname);
            session.setAttribute("userid",sessionid);
            return "post/loginSuccess.html";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('입력정보가 일치하지 않습니다!'); history.go(-1);</script>");
            out.flush();
            return "redirect:/user/login.html";
        }
    }
}
