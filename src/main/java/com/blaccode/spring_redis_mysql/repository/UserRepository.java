package com.blaccode.spring_redis_mysql.repository;

import com.blaccode.spring_redis_mysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
