package com.user_service.controller;

import com.user_service.dto.PostTO;
import com.user_service.dto.converter.PostConverter;
import com.user_service.service.IPostService;
import com.user_service.utilities.UserServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserServiceConstants.USER_BASE_ROUTE + "/timeline")
@CrossOrigin("http://localhost:4200")
public class TimelineController {

    @Autowired
    private IPostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostTO>> getTimeLinePosts(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(PostConverter.INSTANCE.convertToPostTO(postService.getTimelinePosts(userId), userId), HttpStatus.OK);
    }

}
