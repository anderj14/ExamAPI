package ExamApi.Exam.Services;

import ExamApi.Exam.Model.User;
import ExamApi.Exam.Model.UserRole;

import java.util.Set;

public interface UserService {

    public User saveUser(User user, Set<UserRole> userRoles) throws Exception;
}
