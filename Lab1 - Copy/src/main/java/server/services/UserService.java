package server.services;

import model.User;
import server.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public boolean findUser(User user) {
        return userRepository.findUser(user);
    }
}
