package com.blaccode.spring_redis_mysql.controller;

import com.blaccode.spring_redis_mysql.dto.UserReq;
import com.blaccode.spring_redis_mysql.dto.UserRes;
import com.blaccode.spring_redis_mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/testRedis")
    public void testRedis() {
        userService.testRedisConnection();
    }


    @GetMapping("/getUserListAll")
    public ResponseEntity<List<UserRes>> getUserListAll(){
        List<UserRes> userResList= userService.getUserListAll();
        return ResponseEntity.ok(userResList);
    }

    @GetMapping("/getUserFindById/{id}")
    public ResponseEntity<UserRes> getUserFindById(@PathVariable("id") Long id){
        UserRes userRes = userService.getUserFindById(id);
        return ResponseEntity.ok(userRes);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserRes> addUser(@RequestBody UserReq userReq){
        UserRes userRes = userService.addUser(userReq);
        return ResponseEntity.ok(userRes);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserRes> updateUser(@PathVariable("id") Long id, @RequestBody UserReq userReq){
        UserRes userRes = userService.updateUser(id, userReq);
        return ResponseEntity.ok(userRes);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        String rtn = userService.deleteUser(id);
        return ResponseEntity.ok(rtn);
    }



}
