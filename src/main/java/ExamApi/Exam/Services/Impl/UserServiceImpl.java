package ExamApi.Exam.Services.Impl;

import ExamApi.Exam.Model.User;
import ExamApi.Exam.Model.UserRole;
import ExamApi.Exam.Repositories.RoleRepository;
import ExamApi.Exam.Repositories.UserRepository;
import ExamApi.Exam.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User saveUser(User user, Set<UserRole> userRoles) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());

//        Validate user
        if(localUser != null){
            System.out.println("The user is exits");
            throw new Exception("The user already");
        }
//        Save role using for and save user
        else{
            for (UserRole userRole: userRoles){
                roleRepository.save(userRole.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            localUser = userRepository.save(user);
        }
        return localUser;
    }

}
