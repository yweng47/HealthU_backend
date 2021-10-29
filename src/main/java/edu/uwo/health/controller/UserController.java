package edu.uwo.health.controller;

import edu.uwo.health.entity.ResponseEntity;
import edu.uwo.health.entity.User;
import edu.uwo.health.service.UserService;
import edu.uwo.health.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Api(value = "User", tags = "User")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "user login")
    @PostMapping("/login")
    public ResponseEntity Login(
            @ApiParam(value = "username", required=true) @Valid @RequestParam String username,
            @ApiParam(value = "password", required=true) @Valid @RequestParam String password,
            HttpServletResponse response
    ) {
        org.springframework.security.core.userdetails.User loginUser =
                userService.getUserByUsernameAndPassword(username, password);
        if (loginUser == null) {
            return ResponseEntity.fail("invalid username or password");
        }

        String token = jwtUtils.createToken(loginUser.getUsername(), loginUser.getUsername());
        response.addHeader("Authorization", "Bearer " + token);

        return ResponseEntity.success(loginUser);
    }

    @ApiOperation(value = "user register")
    @PostMapping("/register")
    public ResponseEntity Register(
            @ApiParam(value = "user data" , required=true )@Valid @RequestBody User user
    ) {
        User findUser = userService.getUserByUsername(user.getUsername());

        if (findUser != null) {
            return ResponseEntity.fail("username already exists");
        }

        String bCryptPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(bCryptPassword);
        User saveUser = userService.createUser(user);

        return ResponseEntity.success(saveUser);
    }
}
