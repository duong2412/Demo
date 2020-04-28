package travel.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import travel.converter.tranform.UserTransformer;
import travel.entity.Role;
import travel.entity.User;
import travel.exception.ObjectNotFoundException;
import travel.exception.ValidateException;
import travel.model.request.LoginRequest;
import travel.model.request.UserRequest;
import travel.repository.RoleRepository;
import travel.repository.UserRepository;
import travel.security.UserAuthentication;
import travel.service.UserService;
import travel.util.ErrorCode;
import travel.util.PasswordHasher;
import travel.validation.Validator;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserTransformer transformer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserTransformer transformer) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.transformer = transformer;
    }

    public UserAuthentication  getUser(){
        return ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }

    public String getName(){
        return getUser().getName();
    }

    public void insert(UserRequest userRequest) {
        User user = new User();

        BeanUtils.copyProperties(validate(userRequest), user);
        Set<Role> roles = new HashSet<Role>();
        for (Long id : userRequest.getIds()) {
            Role role = roleRepository.getOne(id);
            roles.add(role);
        }
        user.setRoles(roles);
        user.setCreatedDate(new Date());
        userRepository.save(user);

    }


    private UserRequest validate(UserRequest userRequest) {
        Validator<UserRequest> validator = Validator.of(userRequest);

        validator.validate(Objects::isNull, () -> new ValidateException(ErrorCode.Code.OBJECT_IS_NULL, "UserRequest is null"))
                .validate((userRequest1) -> userRequest1.getUserName(), username -> userRepository.countByUserName(username) > 0, () -> new ValidateException(ErrorCode.Code.USER_NAME_EXISTED, userRequest.getUserName() + "Username existed = "))
                .validate(userRequest1 -> userRequest1.getPassword(), password -> (Objects.isNull(password) || password.isEmpty()), () -> new ValidateException(ErrorCode.Code.NOT_BLANK, "password is empty"));

        return validator.get();
    }

    @Override
    public void update(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);

        //        user.setCreatedDate(userRepository.findUserNameById(userRequest.getId()).getCreatedDate());
        Set<Role> roles = new HashSet<>();
        for (Long id : userRequest.getIds()) {
            Role role = roleRepository.getOne(id);
            roles.add(role);
        }
        user.setRoles(roles);
        user.setModifiedDate(new Date());
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.getOne(id);
        for (Role role : user.getRoles()) {
            role.getUsers().remove(user);
        }
        userRepository.delete(user);
    }

    @Override
    public User findUserByUserName(LoginRequest loginRequest) throws NoSuchAlgorithmException {
        String password = PasswordHasher.hash(loginRequest.getPassword());
        User user = userRepository.findUserByUserNameAndPassword(loginRequest.getUserName(), password);

        if (user == null)
            throw new ObjectNotFoundException(ErrorCode.Code.OBJECT_NOT_FOUND, "User Not Found With username = " + loginRequest.getUserName());

        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.getOne(id);
    }
}
