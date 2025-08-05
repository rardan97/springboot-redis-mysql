package com.blaccode.spring_redis_mysql.service;

import com.blaccode.spring_redis_mysql.dto.UserReq;
import com.blaccode.spring_redis_mysql.dto.UserRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void testRedisConnection();

    List<UserRes> getUserListAll();

    UserRes getUserFindById(Long userId);

    UserRes addUser(UserReq userReq);

    UserRes updateUser(Long userId, UserReq userReq);

    String deleteUser(Long userId);


}
