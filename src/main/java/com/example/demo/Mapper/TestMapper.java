package com.example.demo.Mapper;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface TestMapper {
    void insertUserList(UserDTO map) throws Exception;
    UserDTO checkUserInfo(UserDTO map) throws Exception;
    void insertContent(PostDTO con) throws Exception;
    List<PostDTO> getPost() throws Exception;
    PostDTO viewPostDetail (PostDTO con) throws Exception;
    PostDTO checkWriter(PostDTO map) throws Exception;
    void deletePost(int id) throws Exception;
    PostDTO updatePost(PostDTO con) throws  Exception;
}
