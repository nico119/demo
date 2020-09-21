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

    //검색 내용(keyword)이 존재하면(not null) option(title,contenet,writer)에 따라 keyword로 검색
    //else시, 전체 게시판 목록만 뷰로 전달
//    @GetMapping("/postList")
//    public String boardPage(@RequestParam(required = false) String keyword,HttpServletRequest req, Model model) throws Exception
//    {
//        if(keyword!=null){
//        String searchType =req.getParameter("searchType");
//        SearchDTO con = new SearchDTO(searchType,keyword);
//        List<PostDTO> SearchByKeywordList = userService.searchByKeyword(con);
//        model.addAttribute("postDTOList",SearchByKeywordList);}
//        else{
//            List<PostDTO> PostDTOList = userService.getPost();
//            model.addAttribute("postDTOList", PostDTOList);
//        }
//        return "/post/postList";
//    }
//////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/postList")
    public String boardListPage(@RequestParam ("num")int num,@RequestParam(required = false) String keyword,HttpServletRequest req, Model model) throws Exception
    {
        if(keyword!=null) {
            String searchType = req.getParameter("searchType");
            Pagination page = new Pagination(searchType, keyword);
            page.setNum(num);
            page.setCount(userService.countSearchByKeyword(page));

            List<PostDTO> SearchByKeywordList = userService.searchByKeyword(page);
            model.addAttribute("postDTOList", SearchByKeywordList);
            model.addAttribute("page", page);
        }
        else{
            Pagination page = new Pagination();
            page.setNum(num);
            page.setCount(userService.count());
           // page.setPageNum((page.getCount()/page.getPostNum()));
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("displayPost",page.getDisplayPost());
//            map.put("postNum",page.getPostNum());


//            List <PostDTO> PostDTOList= userService.listPage(map);
            List <PostDTO> PostDTOList= userService.listPage(page);

            model.addAttribute("postDTOList", PostDTOList);
            model.addAttribute("page",page);
//
//            model.addAttribute("startPageNum", page.getStartPageNum());
//            model.addAttribute("endPageNum", page.getEndPageNum());
//
//            model.addAttribute("prev", page.getPrev());
//            model.addAttribute("next", page.getNext());
        }
        model.addAttribute("select", num);
        return "/post/list";

//        model.addAttribute("pageNum", page.getPageNum());
//
//        model.addAttribute("startPageNum", page.getStartPageNum());
//        model.addAttribute("endPageNum", page.getEndPageNum());
//
//        model.addAttribute("prev", page.getPrev());
//        model.addAttribute("next", page.getNext());

//        if(keyword!=null){
//            String searchType =req.getParameter("searchType");
//            SearchDTO con = new SearchDTO(searchType,keyword);
//            List<PostDTO> SearchByKeywordList = userService.searchByKeyword(con);
//            model.addAttribute("postDTOList",SearchByKeywordList);}
//        else{
//            List<PostDTO> PostDTOList = userService.getPost();
//            model.addAttribute("postDTOList", PostDTOList);
//        }
    }


//    @GetMapping("/postList")
//    public String boardListPage(@RequestParam ("num")int num,@RequestParam(required = false) String keyword,HttpServletRequest req, Model model) throws Exception
//    {
//        if(keyword!=null){
//        String searchType =req.getParameter("searchType");
//        SearchDTO con = new SearchDTO(searchType,keyword);
//        List<PostDTO> SearchByKeywordList = userService.searchByKeyword(con);
//        model.addAttribute("postDTOList",SearchByKeywordList);}
//        else{
//            List<PostDTO> PostDTOList = userService.getPost();
//            model.addAttribute("postDTOList", PostDTOList);
//        }
//        return "/post/postList";
//        Pagination page=new Pagination();
//
//        page.setNum(num);
//        page.setCount(userService.count());
//        page.setPageNum((page.getCount()/page.getPostNum()));
//
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("displayPost",page.getDisplayPost());
//        map.put("postNum",page.getPostNum());
//
//        List <PostDTO> PostDTOList= userService.listPage(map);
//        model.addAttribute("postDTOList", PostDTOList);
////        model.addAttribute("pageNum", page.getPageNum());
////
////        model.addAttribute("startPageNum", page.getStartPageNum());
////        model.addAttribute("endPageNum", page.getEndPageNum());
////
////        model.addAttribute("prev", page.getPrev());
////        model.addAttribute("next", page.getNext());
//
//        model.addAttribute("page",page);
//        model.addAttribute("select", num);
//
////        if(keyword!=null){
////            String searchType =req.getParameter("searchType");
////            SearchDTO con = new SearchDTO(searchType,keyword);
////            List<PostDTO> SearchByKeywordList = userService.searchByKeyword(con);
////            model.addAttribute("postDTOList",SearchByKeywordList);}
////        else{
////            List<PostDTO> PostDTOList = userService.getPost();
////            model.addAttribute("postDTOList", PostDTOList);
////        }
//        return "/post/list";
//    }
//
//    @GetMapping("/postList")
//    public String boardListPage(@RequestParam ("num")int num,@RequestParam(required = false) String keyword,HttpServletRequest req, Model model) throws Exception
//    {
//
//        // 게시물 총 갯수
//        int count = userService.count();
//
//        // 한 페이지에 출력할 게시물 갯수
//        int postNum = 10;
//
//        // 하단 페이징 번호 ([ 게시물 총 갯수 ÷ 한 페이지에 출력할 갯수 ]의 올림)
//        int pageNum = (int)Math.ceil((double)count/postNum);
//        // 한번에 표시할 페이징 번호의 갯수
//        int pageNum_cnt = 10;
//
//// 표시되는 페이지 번호 중 마지막 번호
//        int endPageNum = (int)(Math.ceil((double)num / (double)pageNum_cnt) * pageNum_cnt);
//
//// 표시되는 페이지 번호 중 첫번째 번호
//        int startPageNum = endPageNum - (pageNum_cnt - 1);
//        int endPageNum_tmp = (int)(Math.ceil((double)count / (double)pageNum_cnt));
//
//        if(endPageNum > endPageNum_tmp) {
//            endPageNum = endPageNum_tmp;
//        }
//        boolean prev = startPageNum == 1 ? false : true;
//        boolean next = endPageNum * pageNum_cnt >= count ? false : true;
//
//
//
//        // 출력할 게시물
//        int displayPost = (num - 1) * postNum;
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("displayPost",displayPost);
//        map.put("postNum",postNum);
//
//        List <PostDTO> PostDTOList= userService.listPage(map);
//        model.addAttribute("postDTOList", PostDTOList);
//        model.addAttribute("pageNum", pageNum);
//        // 시작 및 끝 번호
//        model.addAttribute("startPageNum", startPageNum);
//        model.addAttribute("endPageNum", endPageNum);
//
//// 이전 및 다음
//        model.addAttribute("prev", prev);
//        model.addAttribute("next", next);
//
//        model.addAttribute("select",num);
//
////        if(keyword!=null){
////            String searchType =req.getParameter("searchType");
////            SearchDTO con = new SearchDTO(searchType,keyword);
////            List<PostDTO> SearchByKeywordList = userService.searchByKeyword(con);
////            model.addAttribute("postDTOList",SearchByKeywordList);}
////        else{
////            List<PostDTO> PostDTOList = userService.getPost();
////            model.addAttribute("postDTOList", PostDTOList);
////        }
//       return "post/list";
//    }



////////////////////////////////////////////////////////////////////////////////


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
            page.setCount(userService.count());
//            page.setPageNum((page.getCount()/page.getPostNum()));
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("displayPost",page.getDisplayPost());
//            map.put("postNum",page.getPostNum());
            List <PostDTO> PostDTOList= userService.listPage(page);

            model.addAttribute("postDTOList", PostDTOList);
            model.addAttribute("page",page);
//
//            model.addAttribute("startPageNum", page.getStartPageNum());
//            model.addAttribute("endPageNum", page.getEndPageNum());
//
//            model.addAttribute("prev", page.getPrev());
//            model.addAttribute("next", page.getNext());

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
    public String getPostDetail(@PathVariable("id") int postId,@RequestParam ("num") int num, @ModelAttribute PostDTO PostDTO,HttpServletRequest req, Model model)throws Exception
    {

        HttpSession session = req.getSession();
        String writer = (String)session.getAttribute("username");
        PostDTO con = new PostDTO(postId,writer);
        PostDTO resultCon = new PostDTO();

    resultCon =userService.viewPostDetail(con);

        model.addAttribute("postDetail",resultCon);
        model.addAttribute("postId",postId);
        model.addAttribute("select",num);


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
    //form action에서 url에 값을 넣는법?

    @PatchMapping("/posts/{id}")
    public String updatePost(@PathVariable ("id") int postId,@RequestParam int num, @RequestParam String title, @RequestParam String content, @ModelAttribute PostDTO PostDTO, HttpServletRequest req,HttpServletResponse response, Model model)
    throws Exception {
        HttpSession session = req.getSession();
        PostDTO con= new PostDTO(postId,title,content);
        userService.updatePost(con);
        Pagination page=new Pagination();
        page.setNum(num);
        page.setCount(userService.count());
        List <PostDTO> PostDTOList=userService.listPage(page);
        model.addAttribute("postDTOList",PostDTOList);
        model.addAttribute("page",page);
        model.addAttribute("select", num);
        return "/post/list";


//        page.setCount(userService.count());
//        page.setPageNum((page.getCount()/page.getPostNum()));
////            HashMap<String, Object> map = new HashMap<String, Object>();
////            map.put("displayPost",page.getDisplayPost());
////            map.put("postNum",page.getPostNum());
//
//
////            List <PostDTO> PostDTOList= userService.listPage(map);
//        List <PostDTO> PostDTOList= userService.listPage(page);
//
//        model.addAttribute("postDTOList", PostDTOList);
//        model.addAttribute("page",page);
////
////            model.addAttribute("startPageNum", page.getStartPageNum());
////            model.addAttribute("endPageNum", page.getEndPageNum());
////
////            model.addAttribute("prev", page.getPrev());
////            model.addAttribute("next", page.getNext());
//    }
//        model.addAttribute("select", num);
//        return "/post/list";

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
                page.setCount(userService.count());
                List <PostDTO> PostDTOList=userService.listPage(page);
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

    //option(title,contenet,writer)에 따라 keyword로 검색
//    @GetMapping("/searchByKeyword")
//    public String searchByKeyword(@RequestParam String keyword,HttpServletRequest request, Model model) throws Exception{
//        String searchType =request.getParameter("searchType");
//        SearchDTO con = new SearchDTO(searchType,keyword);
//        List<PostDTO> SearchByKeywordList = userService.searchByKeyword(con);
//        model.addAttribute("postDTOList",SearchByKeywordList);
//        return "/post/postList";
//    }
}