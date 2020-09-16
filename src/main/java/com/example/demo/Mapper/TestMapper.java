package com.example.demo.Mapper;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.SearchDTO;
import com.example.demo.Dto.UserDTO;

import java.io.IOException;
import java.util.List;

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

    List<PostDTO> searchByKeyword(SearchDTO con) throws Exception;
}
