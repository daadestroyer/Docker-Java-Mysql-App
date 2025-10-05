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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
			userRepo.save(new User("Alice"));
			userRepo.save(new User("Bob"));
		}
	}
}
@RestController
@RequestMapping("/users")
class UserController {

	@Autowired
	private UserRepository repo;

	@GetMapping
	public List<User> getAll() {
		return repo.findAll();
	}

	@PostMapping
	public User create(@RequestBody User u) {
		return repo.save(u);
	}
}
@Entity
class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	public User() {}
	public User(String name) { this.name = name; }

	public Long getId() { return id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
interface UserRepository extends JpaRepository<User,Long>{

}
