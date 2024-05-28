//package com.dci.a3m.databaseLoader;
//
//
//import com.dci.a3m.entity.User;
//import com.dci.a3m.service.UserServiceImpl;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//public class UserDatabaseLoader {
//
//    // UserService
//    private UserServiceImpl userServiceImpl;
//
//    public UserDatabaseLoader(UserServiceImpl userServiceImpl) {
//        this.userServiceImpl = userServiceImpl;
//    }
//
//    public void run(String... args) throws Exception {
//        userServiceImpl.save(new User(
//                "johnmountain@example.com",
//                "JohnMountain",
//                "Abcd1234!",
//                "John",
//                "Mountain",
//                LocalDate.of(1975, 5, 20),
//                "Oslo",
//                "Norway",
//                "02468",
//                "045987654321",
//                "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//                "ADMIN",
//                LocalDate.now()));
//
//        userServiceImpl.save(new User(
//                "aliceriver@example.com",
//                "aliceriver",
//                "Abcd1234!",
//                "Alice",
//                "River",
//                LocalDate.of(1988, 6, 15),
//                "New York",
//                "USA",
//                "10101",
//                "1234567890",
//                "https://images.unsplash.com/photo-1479936343636-73cdc5aae0c3?q=80&w=1160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//                "USER",
//                LocalDate.now()
//        ));
//    }
//}
