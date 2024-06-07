package com.dci.a3m.databaseLoader;

import com.dci.a3m.entity.*;
import com.dci.a3m.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final AdminService adminService;
    private final UserService userService;

    @Autowired
    public DatabaseLoader(PasswordEncoder passwordEncoder, AdminService adminService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {


        try {
            initAdmin();
            initMembers();
        } catch (Exception e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initAdmin() {
        String username = "AdminExample";
        String password = passwordEncoder.encode(username);
        String email = "admin@example.com";
        adminService.createAdmin(username, email, password);
    }


    public void initMembers() {

        String mediaUrl1 = "https://images.unsplash.com/photo-1499063078284-f78f7d89616a?q=80&w=928&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        String mediaUrl2 = "https://images.unsplash.com/photo-1448518340475-e3c680e9b4be?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        String mediaUrl3 = "https://images.unsplash.com/photo-1554058501-f6872d688003?q=80&w=1169&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        String mediaUrl4 = "https://images.unsplash.com/photo-1600628421060-939639517883?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        String mediaUrl5 = "https://images.unsplash.com/photo-1527455102718-437c64ea37ad?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        String mediaUrl6 = "https://images.unsplash.com/photo-1574968583205-1403e3940bc4?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";


        // MEMBER 1

        // password = username
        String username1 = "AliceRiver";
        String password1 = passwordEncoder.encode(username1);
        String email1 = username1 + "@example.com";

        Member member1 = new Member(
                "Alice",
                "River",
                LocalDate.of(1990, 1, 1),
                "https://images.unsplash.com/photo-1479936343636-73cdc5aae0c3?q=80&w=1160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "New York",
                "USA",
                "10101",
                "1234567890");

        member1.addPost("My First Post here. Enjoy.", mediaUrl1);
        member1.addPost("My Second Post here. Enjoy.", mediaUrl2);
        member1.addPost("My Third Post here. Enjoy.", mediaUrl3);
        Post post1 = member1.getPosts().get(0);

        member1.addComment("Thanks for reading my first post. Kudos.", post1);
        member1.addComment("Thanks for reading my second post. Kudos.", post1);

        Comment comment1 = member1.getComments().get(0);
        member1.addLike(post1);
        member1.addLike(comment1);

        Authority authority1 = new Authority(username1, member1.getRole());
        User user = new User(username1, email1, password1, true, authority1, member1);
        userService.save(user);

// MEMBER 2

        // password = username
        String username2 = "ThomasLake";
        String password2 = passwordEncoder.encode(username2);
        String email2 = username2 + "@example.com";

        Member member2 = new Member(
                "Thomas",
                "Lake",
                LocalDate.of(1990, 1, 1),
                "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Oslo",
                "Norway",
                "20202",
                "2234567890");

        member2.addPost("My First Post here. Enjoy.", mediaUrl4);
        member2.addPost("My Second Post here. Enjoy.", mediaUrl5);
        Post post2 = member2.getPosts().get(0);

        member2.addComment("Thanks for reading my first post. Kudos.", post2);
        Comment comment2 = member2.getComments().get(0);

        member2.addLike(post2);
        member2.addLike(comment2);

        Authority authority2 = new Authority(username2, member2.getRole());
        User user2 = new User(username2, email2, password2, true, authority2, member2);
        userService.save(user2);

// MEMBER 3

        // password = username
        String username3 = "WilliamWoods";
        String password3 = passwordEncoder.encode(username3);
        String email3 = username3 + "@example.com";

        Member member3 = new Member(
                "William",
                "Woods",
                LocalDate.of(1990, 1, 1),
                "https://images.unsplash.com/photo-1499996860823-5214fcc65f8f?q=80&w=1966&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Tokyo",
                "Japan",
                "30303",
                "3334567890");

        member3.addPost("My First Post here. Enjoy.", mediaUrl6);
        Post post3 = member3.getPosts().get(0);

        member3.addComment("Thanks for reading my first post. Kudos.", post3);
        Comment comment3 = member3.getComments().get(0);

        member3.addLike(post3);
        member3.addLike(comment3);

        Authority authority3 = new Authority(username3, member3.getRole());
        User user3 = new User(username3, email3, password3, true, authority3, member3);
        userService.save(user3);

    }

}
