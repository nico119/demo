package com.example.demo.Service;

import com.example.demo.Dto.Pagination;
import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.SearchDTO;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    TestMapper testMapper;

    public void insertUser(UserDTO map) throws Exception{
        testMapper.insertUser(map);
    }

    public UserDTO checkUserInfo(UserDTO map) throws Exception{
        return testMapper.checkUserInfo(map);
    }
    public PostDTO getUserById(int id) throws Exception{
        return testMapper.getPostById(id);
    }

    public int checkUser(UserDTO map) throws Exception{
        return testMapper.checkUser(map);
    }

    public void insertContent(PostDTO con) throws IOException {
        testMapper.insertContent(con);
    }

    public List<PostDTO> getPost() throws Exception{
        return testMapper.getPost();
    }
//
//    public List<PostDTO> listPage(Pagination page) throws Exception {
//        return testMapper.listPage(page);
//    }

    public int getPostCnt() throws Exception{
        return testMapper.getPostCnt();
    }

    public PostDTO viewPostDetail(PostDTO con) throws Exception{
        return testMapper.viewPostDetail(con);
    }

    public PostDTO checkWriter(PostDTO map) throws Exception{
        return testMapper.checkWriter(map);
    }

    public void updatePost(PostDTO con) throws Exception{
        testMapper.updatePost(con);
    }

    public void deletePost(int id) throws Exception{
        testMapper.deletePost(id);
    }
    public PostDTO getPostById(int id) throws Exception{
        return testMapper.getPostById(id);
    }


    public List<PostDTO> searchByKeyword(Pagination con) throws  Exception{
        return  testMapper.searchByKeyword(con);
    }


    public int countSearchByKeyword(Pagination con) throws  Exception{
        return  testMapper.countSearchByKeyword(con);
    }

    public int countBoardListTotal() {
        return testMapper.countBoardList();
    }

//    public int count() throws Exception {
//        return testMapper.count();
//    }

}
