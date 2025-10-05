package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepo;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

    @Override
    public void run(String... args) {
        // insert sample data if empty
        if (userRepo.count() == 0) {
            userRepo.save(new User("Default User1"));
            userRepo.save(new User("Default User2"));
            userRepo.save(new User("Default User3"));
        }
    }
}


@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private UserRepository repo;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> allUser = repo.findAll();
        if (allUser.isEmpty()) {
            return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = repo.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>("User " + id + " not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping
    public User create(@RequestBody User u) {
        return repo.save(u);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = repo.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>("User with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        repo.delete(optionalUser.get());
        return new ResponseEntity<>("User " + id + " deleted...", HttpStatus.OK);
    }
}

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

interface UserRepository extends JpaRepository<User, Long> {

}
