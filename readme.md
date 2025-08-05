## Spring Boot - Redis, Mysql, Docker

## System Requirements

- Java openjdk : Version 17.0.2
- Spring Boot : version 3.4.1
- Database : MySQL
- Maven : Apache Maven 3.9.3
- Editor : Intellij IDEA 2023.1.1 Community Edition
- Docker

## Dependencies

- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- Mysql Driver
- Lombok
- Spring Data Redis (Access+Driver)

## Run Project

1. clone project Spring Boot
```
git clone https://github.com/rardan97/springboot-redis-mysql.git
```
2. Run Project spring boot using docker-compose
```
docker-compose up --build
```
waiting until prossess build and deploy success




3. Access Endpoint Api

access endpoint using postman for testing spring boot curd with redis

#### Add Data User
```
- url: http://localhost:8080/api/user/addUser
- method : POST
- request Body : 
 {
    "name":"test",
    "email":"test@gmail.com"
 }
```

#### Get List All Data User
```
- url: http://localhost:8080/api/user/getUserListAll
- method : GET
```


#### Get Data User By Id
```
- url: localhost:8080/api/user/getUserFindById/{id}
- method: GET
```

#### Update Data User By ID
```
- url: http://localhost:8080/api/user/updateUser/{id}
- method : POST
- request Body : 
 {
    "name":"test",
    "email":"test@gmail.com"
 }
```


#### Delete Data User By ID
```
- url: http://localhost:8080/api/user/deleteUserById/{id}
- method: DELETE
```


