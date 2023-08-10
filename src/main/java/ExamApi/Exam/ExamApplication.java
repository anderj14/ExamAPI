package ExamApi.Exam;

import ExamApi.Exam.Model.Role;
import ExamApi.Exam.Model.User;
import ExamApi.Exam.Model.UserRole;
import ExamApi.Exam.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExamApplication implements CommandLineRunner {

//	Inject the service

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setFirstname("Ander");
		user.setLastname("Frias");
		user.setUsername("ander");
		user.setPassword("12345");
		user.setEmail("ander@gmail.com");
		user.setTelephone("8099283355");
		user.setPerfil("pic.png");

		Role role = new Role();
		role.setRoleId(1L);
		role.setName("ADMIN");

		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		userRoles.add(userRole);

		User saveUser = userService.saveUser(user, userRoles);
		System.out.println(saveUser.getUsername());
	}
}
