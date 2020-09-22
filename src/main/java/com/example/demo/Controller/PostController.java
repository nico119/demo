package com.example.demo.Controller;

import com.example.demo.Dto.Pagination;
import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.SearchDTO;
import com.example.demo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class PostController {
    @Autowired
    UserService userService;

    //게시판 리스트 화면 - searchType 옵션과 keyword 통한 검색  +  페이지네이션
    //required = false, defaultValue로 검색내용 없어도 쿼리 널값 오류 방지

    @GetMapping("/postList")
    public String boardListPage(@RequestParam ("num")int num,@RequestParam(required = false,defaultValue = "title") String searchType,@RequestParam(required = false,defaultValue = "") String keyword, Model model) throws Exception
    {
        Pagination page = new Pagination(searchType, keyword);
        page.setNum(num);

        try {
            page.setCount(userService.countSearchByKeyword(page));
            List<PostDTO> SearchByKeywordList = userService.searchByKeyword(page);
            model.addAttribute("postDTOList", SearchByKeywordList);
            model.addAttribute("page", page);

            if(keyword.equals("")){
                model.addAttribute("select", num);
            }
            else if(!keyword.equals("")&&num>page.getEndPageNum()) {
                model.addAttribute("select", 1);
            }
            else{
                model.addAttribute("select", num);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "/post/list";
    }

    //게시물 작성페이지로 이동
    @GetMapping("/writepost")
    public String writePost()throws Exception{
        return "/post/writePost";
    }

    //게시물 작성후 등록버튼 클릭시 insert작업 후, 게시판 목록으로 이동
    @PostMapping("/insertContent")
    public String insertContent(@RequestParam String title, @RequestParam String content, @RequestParam String deletePassword, HttpServletRequest req,HttpServletResponse response, Model model) throws Exception {
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");
        int userId = (int) session.getAttribute("userid");
        PostDTO con = new PostDTO(title,content,deletePassword,writer,userId);
        try {
            userService.insertContent(con);

            Pagination page= new Pagination();
            page.setNum(1);
            page.setSearchType("title");
            page.setKeyword("");
            page.setCount(userService.countSearchByKeyword(page));
            List <PostDTO> PostDTOList=userService.searchByKeyword(page);

            model.addAttribute("postDTOList", PostDTOList);
            model.addAttribute("page",page);

            model.addAttribute("select", page.getNum());
            return "/post/list";
        }
        catch (IOException e){
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('한글은 입력이 불가합니다'); history.go(-1);</script>");
            out.flush();
            return "post/writePost";
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("게시글 작성 오류");
            return "post/error";
        }
        /*try{
            //에러가 발생할 수 있는 코드
            throw new Exception(); //강제 에러 출력
        }catch (Exception e){
            //에러시 수행
            e.printStackTrace(); //오류 출력(방법은 여러가지)
            throw e; //최상위 클래스가 아니라면 무조건 던져주자
        }finally{
            //무조건 수행
        }*/
    }

    //게시물 제목 클릭 시 게시물 상세보기 페이지로 이동
    //pathvariable 로
    @GetMapping("/posts/{id}")
    public String getPostDetail(@PathVariable("id") int postId,@RequestParam ("num") int num,@ModelAttribute Pagination page,@RequestParam String searchType,@RequestParam String keyword, @ModelAttribute PostDTO PostDTO,HttpServletRequest req, Model model)throws Exception
    {
        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");
        PostDTO con = new PostDTO(postId,writer);
        PostDTO resultCon = new PostDTO();

        resultCon =userService.viewPostDetail(con);
        page.setKeyword(keyword);
        page.setSearchType(searchType);

        model.addAttribute("postDetail",resultCon);
        model.addAttribute("postId",postId);
        model.addAttribute("select",num);
        model.addAttribute("page",page);

        return "/post/postDetail";
    }

    //게시물 상세보기 페이지에서 수정 버튼 클릭 시, 현재 세션의 userid와 게시물의 user_id를 비교하여 일치 시 수정 페이지로 이동
    //TODO 예외 처리
    @GetMapping("/checkWriter")
    public String checkWriter(@RequestParam int postId,@RequestParam int num, HttpServletRequest req,HttpServletResponse response, Model model)throws Exception {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("userid");

        PostDTO con = new PostDTO(postId);
        PostDTO resultCon = userService.viewPostDetail(con);

        if (userId == resultCon.getUser_id()) {
            model.addAttribute("postDetail",resultCon);
            //model.addAttribute("postId",postId);
            model.addAttribute("select",num);
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

    // put : 전체 교체 patch : 부분교체   /{id} -> @pathvarible로 설정

    @PatchMapping("/posts/{id}")
    public String updatePost(@PathVariable ("id") int postId,@RequestParam int num, @RequestParam String title, @RequestParam String content, @ModelAttribute PostDTO PostDTO, HttpServletRequest req,HttpServletResponse response, Model model)
            throws Exception {
        HttpSession session = req.getSession();
        PostDTO con= new PostDTO(postId,title,content);
        userService.updatePost(con);
        Pagination page=new Pagination();
        page.setNum(num);
        page.setSearchType("title");
        page.setKeyword("");
        page.setCount(userService.countSearchByKeyword(page));
        List <PostDTO> PostDTOList=userService.searchByKeyword(page);
        model.addAttribute("postDTOList",PostDTOList);
        model.addAttribute("page",page);
        model.addAttribute("select", num);
        return "/post/list";

    }

    //게시물 상세보기 페이지에서 삭제 비밀번호 입력 후 삭제 버튼 클릭 시 delete_password 비교 후 일치 시 deleteYn을 'Y'로 변경

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable("id") int postId,@RequestParam int num, @RequestParam String deletePassword , HttpServletRequest req, HttpServletResponse response, Model model)throws Exception {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("userid");
        PostDTO con = userService.getPostById(postId);

        if  ((userId==con.getUser_id()) && (con.getDelete_password().equals(deletePassword))) {
            userService.deletePost(postId);
            Pagination page=new Pagination();
            page.setNum(num);
            page.setSearchType("title");
            page.setKeyword("");
            page.setCount(userService.countSearchByKeyword(page));
            List <PostDTO> PostDTOList=userService.searchByKeyword(page);
            model.addAttribute("postDTOList",PostDTOList);
            model.addAttribute("page",page);
            model.addAttribute("select", num);
            return "/post/list";
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
}
//게시판리스트+검색+페이징의  api,쿼리 메소드 통합 위해 검색+페이징 객체 통합 => 반복 코드 생김 / 쪼개는것보다 효율적인지
//          컨트롤러->뷰->컨트롤러 동일객체 전달
//            타임리프 버튼 눌린 표시