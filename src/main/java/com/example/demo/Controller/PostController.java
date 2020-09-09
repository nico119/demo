package com.example.demo.Controller;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
 public class PostController {
    @Autowired
    UserService userService;

    @GetMapping("/gotoContent")
    public String gotocontent()throws Exception{
        return "post/writepost";
    }

    @PostMapping("/insertContent")
    public String insertcontent(@RequestParam String title, @RequestParam String content, @RequestParam String delete_password, HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");

        System.out.println("확인용 코드입니다.");
        System.out.println("=======================================================");
        System.out.println("title ="+title);
        System.out.println("delete_password ="+delete_password);
        System.out.println("writer ="+writer);
        System.out.println("content ="+content);
        System.out.println("=======================================================");

        PostDTO con = new PostDTO(title,content,writer,delete_password);
        userService.insertContent(con);

        List<PostDTO> postDTOList = userService.getContent();
        for(int i=0;i<postDTOList.size();i++){
            System.out.println(postDTOList.get(i));
        }

        model.addAttribute("postDTOList", postDTOList);
        return "board/boardlist";
    }
}
