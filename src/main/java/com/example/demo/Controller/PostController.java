package com.example.demo.Controller;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        String writer = (String)session.getAttribute("username");
        List<PostDTO> PostDTOList = userService.getPost();
        model.addAttribute("PostDTOList",PostDTOList);
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

        List<PostDTO> postDTOList = userService.getPost();
        /*for(int i=0;i<postDTOList.size();i++){
            System.out.println(postDTOList.get(i));
        }*/

        model.addAttribute("postDTOList", postDTOList);
        return "post/postlist";
    }

    @GetMapping("/viewPost")
    public String getPostDetail(@RequestParam int postId, @ModelAttribute PostDTO PostDTO,HttpServletRequest req, Model model)throws Exception
    {
        //modelattribute 개념 확인하기
      //  System.out.println(titleVO.getid());
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");

    PostDTO con = new PostDTO(postId);
    PostDTO resultCon = new PostDTO();

    resultCon =userService.viewPostDetail(con);
    resultCon.setId(postId);

        System.out.println("it is for test");
        System.out.println("============================");
        System.out.println(resultCon.getTitle());
        System.out.println(resultCon.getWriter());
        System.out.println(resultCon.getDelete_password());
        System.out.println(resultCon.getContent());
        System.out.println("============================");

        model.addAttribute("postDetail",resultCon);
        model.addAttribute("postId",postId);

        return "post/postdetail";
    }
}