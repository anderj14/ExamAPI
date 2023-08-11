package ExamApi.Exam.Controllers;

import ExamApi.Exam.Model.Role;
import ExamApi.Exam.Model.User;
import ExamApi.Exam.Model.UserRole;
import ExamApi.Exam.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) throws Exception{
        user.setPerfil("default.png");
        Set<UserRole> userRoles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(2L);
        role.setName("Normal");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoles.add(userRole);

        return userService.saveUser(user, userRoles);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId ){
        userService.deleteUser(userId);
    }
}
