package com.example.demo.Mapper;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.UserDTO;

import java.util.List;

public interface TestMapper {
    public List<UserDTO> getAll() throws Exception;
    void insertUserList(UserDTO map) throws Exception;
    UserDTO checkUserInfo(UserDTO map) throws Exception;
    void insertContent(PostDTO con) throws Exception;
    List<PostDTO> getAllContent() throws Exception;
    List<PostDTO> getContent() throws Exception;
}
