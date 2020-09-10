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

    @GetMapping("/showBoard")
    public String boardpage(HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        //String writer = (String)session.getAttribute("username");
        List<PostDTO> PostDTOList = userService.getContent();
        model.addAttribute("PostList",PostDTOList);
        return "post/postlist";
    }

    @GetMapping("/registerPost")
    public String gotocontent()throws Exception{
        return "post/writepost";
    }

    @PostMapping("/insertContent")
    public String insertcontent(@RequestParam String title, @RequestParam String content, @RequestParam String delete_password, HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");

        PostDTO con = new PostDTO(title,content,delete_password,writer);
        userService.insertContent(con);

        List<PostDTO> postDTOList = userService.getContent();
        for(int i=0;i<postDTOList.size();i++){
            System.out.println(postDTOList.get(i));
        }

        model.addAttribute("postDTOList", postDTOList);
        return "post/postlist";
    }
}
