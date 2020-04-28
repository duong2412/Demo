package travel.service;

import travel.entity.User;
import travel.model.request.LoginRequest;
import travel.model.request.UserRequest;

import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface UserService {
    void insert(UserRequest userRequest);

    void update(UserRequest userRequest);

    void delete(Long id);

    User findUserByUserName(LoginRequest loginRequest) throws NoSuchAlgorithmException;

    List<User> findAll();

    User findUserById(Long id);
}
