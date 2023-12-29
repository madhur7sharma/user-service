package com.user_service.controller;

import com.user_service.dto.FollowRequest;
import com.user_service.dto.TokenResponse;
import com.user_service.dto.UserTO;
import com.user_service.dto.converter.IUserConverter;
import com.user_service.entity.User;
import com.user_service.service.IFollowingService;
import com.user_service.service.IUserService;
import com.user_service.utilities.UserServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(UserServiceConstants.USER_BASE_ROUTE)
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFollowingService followingService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User user1 = userService.registerUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@RequestBody User user) {
        TokenResponse response = new TokenResponse();
        User validatedUser = userService.validateUser(user);
        if(validatedUser != null) {
            response.setToken(userService.generateToken(validatedUser));
            response.setUser(IUserConverter.INSTANCE.convertToUserTO(validatedUser));
            response.setMessage("User authenticated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Invalid credentials");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/hey")
    public String getUser(@PathVariable("userId") Long userId) {
        return "user";
    }

    @GetMapping("/all")
    public List<UserTO> getAllUsers() {
        return IUserConverter.INSTANCE.convertToUserTO(userService.getAllUsers());
    }

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@PathVariable("userId") Long userId, @RequestBody FollowRequest followRequest) {
        followingService.updateFollowAction(userId, followRequest);
        return new ResponseEntity<>("OKK", HttpStatus.OK);
    }

}
