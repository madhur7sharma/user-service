package com.user_service.controller;

import com.user_service.dto.FollowRequest;
import com.user_service.dto.FollowingTO;
import com.user_service.dto.TokenResponse;
import com.user_service.dto.UserTO;
import com.user_service.dto.Response;
import com.user_service.dto.converter.IFollowingConverter;
import com.user_service.dto.converter.IUserConverter;
import com.user_service.entity.User;
import com.user_service.service.IFollowingService;
import com.user_service.service.IUserService;
import com.user_service.service.JwtService;
import com.user_service.utilities.UserServiceConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(UserServiceConstants.USER_BASE_ROUTE)
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFollowingService followingService;

    @Autowired
    private JwtService jwt;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User user1 = userService.registerUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@PathVariable("userId") Long userId, @RequestBody User newUserDetails) {
        if(userId != newUserDetails.getId()) {
            return new ResponseEntity<>(new Response("Bad request"), HttpStatus.BAD_REQUEST);
        }
        User user1 = userService.editUser(userId, newUserDetails);
        return new ResponseEntity<>(IUserConverter.INSTANCE.convertToUserTO(user1, userId), HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@PathVariable("userId") Long userId, @RequestBody User user) {
        TokenResponse response = new TokenResponse();
        User validatedUser = userService.validateUser(user);
        if(validatedUser != null) {
            response.setToken(userService.generateToken(validatedUser));
            response.setUser(IUserConverter.INSTANCE.convertToUserTO(validatedUser, userId));
            response.setMessage("User authenticated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Invalid credentials");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get-loggedin-user")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null) {
            token = request.getParameter("Authorization");
        }
        token = token.substring(7);
        if(!jwt.isTokenExpired(token)) {
            User user = userService.findByEmail(jwt.extractEmail(token));
            return new ResponseEntity<UserTO>(IUserConverter.INSTANCE.convertToUserTO(user, userId), HttpStatus.OK);
        }
        return new ResponseEntity<>("Unauthenticated", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<UserTO> getAllUsers(@PathVariable("userId") Long userId) {
        return IUserConverter.INSTANCE.convertToUserTO(userService.getAllUsers(), userId);
    }

    @PostMapping("/follow")
    public ResponseEntity<Response> follow(@PathVariable("userId") Long userId, @RequestBody FollowRequest followRequest) {
        followingService.updateFollowAction(userId, followRequest);
        return new ResponseEntity<>(new Response("Success"), HttpStatus.OK);
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<Set<FollowingTO>> getFollowersByUserName(@PathVariable("userId") Long userId, @PathVariable("username") String username) {
        User loggedInUser = userService.findById(userId);
        return new ResponseEntity<>(IFollowingConverter.INSTANCE.convertToFollowingTO(userService.findByUserName(username).getFollowers(), "followers", loggedInUser.getFollowing()), HttpStatus.OK);
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<Set<FollowingTO>> getFollowingByUserName(@PathVariable("userId") Long userId, @PathVariable("username") String username) {
        User loggedInUser = userService.findById(userId);
        return new ResponseEntity<>(IFollowingConverter.INSTANCE.convertToFollowingTO(userService.findByUserName(username).getFollowing(), "following", loggedInUser.getFollowing()), HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<List<FollowingTO>> getUsersByNameContaining(@PathVariable("userId") Long userId, @PathVariable("name") String name) {
        User loggedInUser = userService.findById(userId);
        List<UserTO> userTOS = IUserConverter.INSTANCE.convertToUserTO(userService.findByNameContaining(name), userId);
        List<FollowingTO> followingTOS = userTOS.stream().map(user -> {
            FollowingTO followingTO = new FollowingTO();
            followingTO.setIsUserFollowedByLoggedInUser(user.getIsUserFollowedByLoggedInUser());
            followingTO.setUser(IUserConverter.INSTANCE.convertToUser(user));
            return followingTO;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(followingTOS, HttpStatus.OK);
    }

    @GetMapping("/get-user/{username}")
    public ResponseEntity<UserTO> getUserByUserName(@PathVariable("userId") Long userId, @PathVariable("username") String username) {
        return new ResponseEntity<>(IUserConverter.INSTANCE.convertToUserTO(userService.findByUserName(username), userId), HttpStatus.OK);
    }
}
