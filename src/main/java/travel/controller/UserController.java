package travel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel.entity.Role;
import travel.entity.User;
import travel.model.request.LoginRequest;
import travel.model.request.UserRequest;
import travel.model.response.DataResponse;
import travel.security.jwt.TokenProducer;
import travel.security.jwt.model.JwtPayload;
import travel.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController extends BaseController {



    private UserService userService;
    private final TokenProducer tokenProducer;

    public UserController(
            UserService userService,TokenProducer tokenProducer) {

        this.userService = userService;
        this.tokenProducer = tokenProducer;
    }

    @PostMapping("/signin")
    public ResponseEntity<DataResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User user = null;
        try {
            user = userService.findUserByUserName(loginRequest);
            JwtPayload payload = createJwtPayload(user);

            DataResponse dataResponse = new DataResponse();
            dataResponse.setCode("0000");
            dataResponse.setMessage("SUCCESS");
            dataResponse.setData(tokenProducer.token(payload));

            return ResponseEntity.ok(dataResponse);
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(new DataResponse(), HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("/user")
    public ResponseEntity<Void> insert(@RequestBody UserRequest userRequest) {
        userService.insert(userRequest);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/user")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<User> getUserByUserName(@RequestParam String userName) {
////        return new ResponseEntity<>(userService.findUserByUserName(userName), HttpStatus.OK);
//        return ResponseEntity.ok(userService.findUserByUserName(userName));
//    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    private JwtPayload createJwtPayload(User user) {
        JwtPayload payload = new JwtPayload();
        payload.setId(user.getId());
        payload.setUserName(user.getUserName());

        Set<Role> roles = user.getRoles();


        List<String> roleNames = roles.stream().map(p->p.getName()).collect(Collectors.toList());



        payload.setRole(String.join(",", roleNames));
        return payload;
    }
}
