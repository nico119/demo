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

    @GetMapping("/postlist")
    public String boardpage(HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");
        List<PostDTO> PostDTOList = userService.getPost();
        model.addAttribute("postDTOList",PostDTOList);
        return "post/postlist";
    }

    @GetMapping("/writepost")
    public String gotocontent()throws Exception{
        return "post/writepost";
    }

    @PostMapping("/insertContent")
    public String insertcontent(@RequestParam String title, @RequestParam String content, @RequestParam String delete_password, HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");
        int user_id = (int) session.getAttribute("userid");

        PostDTO con = new PostDTO(title,content,delete_password,writer,user_id);
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

        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");

    PostDTO con = new PostDTO(postId,writer);
    PostDTO resultCon = new PostDTO();

    resultCon =userService.viewPostDetail(con);
    resultCon.setId(postId);
    //TODO SELECT문 한번에 통합

        model.addAttribute("postDetail",resultCon);
        model.addAttribute("postId",postId);

        return "post/postdetail";
    }

    @GetMapping("/checkWriter")
    public String checkWriter(@RequestParam int postId, @ModelAttribute PostDTO PostDTO, HttpServletRequest req, Model model )throws Exception {
        HttpSession session = req.getSession();
        int user_id = (int) session.getAttribute("userid");

        PostDTO con = new PostDTO(postId);
        PostDTO resultCon = new PostDTO();

        resultCon = userService.viewPostDetail(con);
        resultCon.setId(postId);

        if (user_id == con.getUser_id()) {
            model.addAttribute("postDetail",resultCon);
            model.addAttribute("postId",postId);
            return "post/updatePost";
        }
        else {
            System.out.println("권한없음");
            return "redirect:/post/postlist";
        }

        /*
        public String ckeckUser(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest req) throws Exception {

        UserDTO mv = new UserDTO(name, password);
        UserDTO userinfo = userService.checkUserInfo(mv);
*//*
        if (userinfo.getName().equals(name) && userinfo.getPassword().equals(password)) {
            //session 생성 후 id 저장
            HttpSession session = req.getSession();
            String sessionname = userinfo.getName();
            int sessionid = userinfo.getId();
            session.setAttribute("username",sessionname);
            session.setAttribute("userid",sessionid);
            return "post/loginsuccess.html";
             */

    }

}