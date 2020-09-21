package com.example.demo.Mapper;

import com.example.demo.Dto.Pagination;
import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.SearchDTO;
import com.example.demo.Dto.UserDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TestMapper {
    void insertUser(UserDTO map) throws Exception;
    UserDTO checkUserInfo(UserDTO map) throws Exception;
    int checkUser(UserDTO map) throws Exception;

    void insertContent(PostDTO con) throws IOException;
    List<PostDTO> getPost() throws Exception;
    int getPostCnt() throws Exception;
    PostDTO viewPostDetail (PostDTO con) throws Exception;
    PostDTO getPostById (int id) throws Exception;
    PostDTO checkWriter(PostDTO map) throws Exception;

    void deletePost(int id) throws Exception;

    void updatePost(PostDTO con) throws  Exception;

    List<PostDTO> searchByKeyword(Pagination con) throws Exception;

    int countBoardListTotal();

    int countBoardList();

    int count() throws Exception;

    List <PostDTO> listPage(Pagination page) throws Exception;

    int countSearchByKeyword(Pagination con);
}
