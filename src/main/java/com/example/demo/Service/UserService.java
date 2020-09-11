package com.example.demo.Service;

import com.example.demo.Dto.PostDTO;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    TestMapper testMapper;

    public void insertUser(UserDTO map) throws Exception{
        testMapper.insertUserList(map);
    }

    public UserDTO checkUserInfo(UserDTO map) throws Exception{
        return testMapper.checkUserInfo(map);
    }

    public void insertContent(PostDTO con) throws Exception{
        testMapper.insertContent(con);
    }

    public List<PostDTO> getAllContent() throws Exception{
        return testMapper.getAllContent();
    }

    public List<PostDTO> getPost() throws Exception{
        return testMapper.getPost();
    }

    public PostDTO viewPostDetail(PostDTO con) throws Exception{
        return testMapper.viewPostDetail(con);
    }
}
