package com.example.demo.Controller;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    UserService userService;

    //로그인 성공 이후 게시판목록으로 이동
    @GetMapping("/postList")
    public String boardpage(HttpServletRequest req, Model model) throws Exception
    {
        HttpSession session = req.getSession();
        List<PostDTO> PostDTOList = userService.getPost();
        model.addAttribute("postDTOList",PostDTOList);
        return "/post/postList";
    }

    //게시물 작성페이지로 이동
    @GetMapping("/writepost")
    public String gotocontent()throws Exception{
        return "/post/writePost";
    }

    //게시물 작성후 등록버튼 클릭시 insert작업 후, 게시판 목록으로 이동
    @PostMapping("/insertContent")
    public String insertcontent(@RequestParam String title, @RequestParam String content, @RequestParam String delete_password, HttpServletRequest req, Model model)
            throws Exception{
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");
        int user_id = (int) session.getAttribute("userid");

        PostDTO con = new PostDTO(title,content,delete_password,writer,user_id);
        userService.insertContent(con);
        List<PostDTO> PostDTOList = userService.getPost();
        model.addAttribute("postDTOList",PostDTOList);
        return "/post/postList";
    }

    //게시물 제목 클릭 시 게시물 상세보기 페이지로 이동
    @GetMapping("/viewPost")
    public String getPostDetail(@RequestParam int postId, @ModelAttribute PostDTO PostDTO,HttpServletRequest req, Model model)throws Exception
    {

        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");

    PostDTO con = new PostDTO(postId,writer);
    PostDTO resultCon = new PostDTO();

    resultCon =userService.viewPostDetail(con);

        model.addAttribute("postDetail",resultCon);
        model.addAttribute("postId",postId);


        return "/post/postDetail";
    }

  //게시물 상세보기 페이지에서 수정 버튼 클릭 시, 현재 세션의 userid와 게시물의 user_id를 비교하여 일치 시 수정 페이지로 이동
    //TODO 예외 처리
    @GetMapping("/checkWriter")
    public String checkWriter(@RequestParam int postId, @ModelAttribute PostDTO PostDTO, HttpServletRequest req,HttpServletResponse response, Model model)throws Exception {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("userid");

        PostDTO con = new PostDTO(postId);
        PostDTO resultCon = userService.viewPostDetail(con);

        if (userId == resultCon.getUser_id()) {
            model.addAttribute("postDetail",resultCon);
            //model.addAttribute("postId",postId);
            return "post/updatePost";
        }
        else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('수정 권한이 없습니다.'); history.go(-1);</script>");
            out.flush();
            return "post/postDetail";

        }
    }
    
    //게시물 title이나 content 재작성 후 수정버튼 클릭 시 update작업
    //TODO @PatchMapping("/updatePost") / [updatePost.html] <form action="/updatePost" method="patch"> 선언해도 오류
    //Request method 'GET' not supported
    //org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported

    @PostMapping("/updatePost")
    public String updatePost(@RequestParam int postId, @RequestParam String title, @RequestParam String content, @ModelAttribute PostDTO PostDTO, HttpServletRequest req, Model model)
    throws Exception {
        HttpSession session = req.getSession();
        PostDTO con= new PostDTO(postId,title,content);
        userService.updatePost(con);
        List<PostDTO> PostDTOList = userService.getPost();
        model.addAttribute("postDTOList",PostDTOList);
        return "/post/postList";
    }

    //게시물 상세보기 페이지에서 삭제 비밀번호 입력 후 삭제 버튼 클릭 시 delete_password 비교 후 일치 시 deleteYn을 'Y'로 변경

    @PostMapping("/deletePost")
        public String deletePost(@RequestParam int postId, @RequestParam String deletepassword , HttpServletRequest req, HttpServletResponse response, Model model)throws Exception {
            HttpSession session = req.getSession();
            int userId = (int) session.getAttribute("userid");
           PostDTO con = userService.getPostById(postId);

            if  ((userId==con.getUser_id()) && (con.getDelete_password().equals(deletepassword))) {
                userService.deletePost(postId);
                List<PostDTO> PostDTOList = userService.getPost();
                model.addAttribute("postDTOList",PostDTOList);
                return "/post/postList";
            }
            else if (userId!= con.getUser_id()){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('삭제 권한이 없습니다.'); history.go(-1);</script>");
            out.flush();
            return "/post/postDetail";
        }
            else  {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('삭제 비밀번호가 틀렸습니다.'); history.go(-1);</script>");
                out.flush();
                return "/post/postDetail";
            }
        }

    //작성자로 content 검색하기
    /*@GetMapping("/searchByWriter")
    public String searchByWriter(@RequestParam String user_id, Model model) throws Exception{

        contentVO con = new contentVO();

        //contentVO객체에 작성자 id값으로 검색한 결과 리스트 넣기
        List<contentVO> contentVOList =  testService.searchContentByWriter(user_id);

        model.addAttribute("contentVOList",contentVOList);
        return "board/boardlist";
    }*/

    //키워드로 title,content 검색
    //TODO 검색창 - 제목,내용 / 작성자 나눠서 선택하게
    //검색 기능- 새로운 객체 모델 생성해서 띄우기 or deleteYn을 y로 변경
    @GetMapping("/searchByKeyword")
    public String searchByKeyword(@RequestParam String keyword, Model model) throws Exception{
        List<PostDTO> SearchList = userService.searchByKeyword(keyword);
        model.addAttribute("postDTOList", SearchList);
        return "/post/postList";


    }

}