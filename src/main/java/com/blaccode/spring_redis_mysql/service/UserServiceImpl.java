package com.blaccode.spring_redis_mysql.service;

import com.blaccode.spring_redis_mysql.dto.UserReq;
import com.blaccode.spring_redis_mysql.dto.UserRes;
import com.blaccode.spring_redis_mysql.model.User;
import com.blaccode.spring_redis_mysql.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void testRedisConnection() {
        String check = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println("Redis Response : "+check);

    }

    @Override
    public List<UserRes> getUserListAll() {
        String key = "user:all";
        Object dataCached = redisTemplate.opsForValue().get(key);
        if(dataCached != null){
            return objectMapper.convertValue(dataCached, new TypeReference<List<UserRes>>() {});
        }

        List<User> userList = userRepository.findAll();
        List<UserRes> rtn = new ArrayList<>();

        userList.forEach(user -> {
            UserRes userRes = new UserRes();
            userRes.setId(user.getId());
            userRes.setName(user.getName());
            userRes.setEmail(user.getEmail());
            rtn.add(userRes);

        });

        redisTemplate.opsForValue().set(key, rtn, 10, TimeUnit.MINUTES);
        return rtn;
    }

    @Override
    public UserRes getUserFindById(Long userId) {
        String key = "user:"+userId;
        Object dataCached = redisTemplate.opsForValue().get(key);
        if(dataCached != null){
            logger.info("Get User {} Form redis ", userId);
            return objectMapper.convertValue(dataCached, new TypeReference<UserRes>() {});
        }

        Optional<User> dataUser = userRepository.findById(userId);
        if(dataUser.isEmpty()){
            throw new RuntimeException("User not found id : "+userId);
        }

        User user = dataUser.get();
        UserRes userRes = new UserRes();
        userRes.setId(user.getId());
        userRes.setName(user.getName());
        userRes.setEmail(user.getEmail());

        redisTemplate.opsForValue().set(key, userRes, 10, TimeUnit.MINUTES);
        logger.info("Save user {} in redis", userId);
        return userRes;
    }

    @Override
    public UserRes addUser(UserReq userReq) {
        User user = new User();
        user.setName(userReq.getName());
        user.setEmail(userReq.getEmail());
        User dataUser = userRepository.save(user);

        UserRes userRes = new UserRes();
        userRes.setId(dataUser.getId());
        userRes.setName(dataUser.getName());
        userRes.setEmail(dataUser.getEmail());

        String key = "user:"+user.getId();
        redisTemplate.opsForValue().set(key, userRes, 10, TimeUnit.MINUTES);
        logger.info("Save user in redis : {}",key);

        String userListKey = "user:all";
        redisTemplate.delete(userListKey);
        logger.info("Delete cache list user");

        return userRes;
    }

    @Override
    public UserRes updateUser(Long userId, UserReq userReq) {
        Optional<User> dataUser = userRepository.findById(userId);
        if(dataUser.isEmpty()){
            throw new RuntimeException("User not found id :"+userId);
        }

        User user = dataUser.get();
        user.setName(userReq.getName());
        user.setEmail(userReq.getEmail());
        User userUpdate = userRepository.save(user);

        UserRes userRes = new UserRes();
        userRes.setId(userUpdate.getId());
        userRes.setName(userUpdate.getName());
        userRes.setEmail(userUpdate.getEmail());

        String key = "user:"+userId;
        redisTemplate.opsForValue().set(key, userRes, 10, TimeUnit.MINUTES);
        logger.info("Update user cache in redis: {}", key);

        String userListKey = "user:all";
        redisTemplate.delete(userListKey);
        logger.info("Deleted user list cache : {}", userListKey);

        return userRes;
    }

    @Override
    public String deleteUser(Long userId) {
        Optional<User> dataUser = userRepository.findById(userId);
        if(dataUser.isEmpty()){
            throw new RuntimeException("User not found id :"+userId);
        }

        userRepository.deleteById(userId);
        logger.info("Delete user with id : {}", userId);

        String key = "user:"+userId;
        redisTemplate.delete(key);
        logger.info("Delete user cache in redis: {}", key);

        String userListKey = "user:all";
        redisTemplate.delete(userListKey);
        logger.info("Deleted user list cache: {}", userListKey);
        return "User with id "+userId+ " deleted successfully";
    }
}
