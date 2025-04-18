package com.user_service.service;

import com.user_service.dto.FollowStates;
import com.user_service.dto.LikeStates;
import com.user_service.entity.Following;
import com.user_service.entity.Post;
import com.user_service.entity.User;
import com.user_service.respository.comment.ICommentRepository;
import com.user_service.respository.following.IFollowingRepository;
import com.user_service.respository.post.IPostRepository;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IUserRespository userRespository;

    @Autowired
    private IFollowingRepository followingRepository;

    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Post createPost(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public Post editPost(Post post) {
        Post existingPost = postRepository.findById(post.getId()).get();
        existingPost.setCaption(post.getCaption());
        existingPost.setLocation(post.getLocation());
        postRepository.save(existingPost);
        return existingPost;
    }

    @Override
    public void deletePost(Long postId) {
        commentRepository.deleteByPostId(postId);
        postRepository.deletePostById(postId);
    }

    @Override
    public Post getPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.get();
    }

    @Override
    public void likePost(Long postId, Long userId, LikeStates action) {
        Post post = postRepository.findById(postId).get();
        User user = userRespository.findById(userId).get();
        if(action.equals(LikeStates.LIKE)) {
            if(!post.getLikes().contains(user)) {
                post.getLikes().add(user);
                postRepository.save(post);
            }
        } else {
            if(post.getLikes().contains(user)) {
                post.getLikes().remove(user);
                postRepository.save(post);
            }
        }
    }

    @Override
    public List<Post> findPostByUserId(Long userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public List<Post> findPostByUserName(String userName, Long loggedInUserId) {
        boolean operationAllowed = false;
        User loggedInUser = userRespository.findById(loggedInUserId).get();
        User postsRequestedUser = userRespository.findByUserName(userName);
        Following following = followingRepository.findFollowingByFromAndTo(loggedInUser.getId(), postsRequestedUser.getId());
        if(postsRequestedUser.getId() == loggedInUser.getId()) {
            operationAllowed = true;
        } else if(postsRequestedUser.isPrivate() && isLoogedInUserFollowing(postsRequestedUser, loggedInUser) && following.getFollowRequest().equals(FollowStates.ACCEPTED)) {
            operationAllowed = true;
        } else if(!postsRequestedUser.isPrivate()) {
            operationAllowed = true;
        }
        if(operationAllowed) {
            List<Post> postByUserUserName = postRepository.findPostByUserUserName(userName);
            return postByUserUserName;
        } else {
            return Collections.emptyList();
        }
    }

    private boolean isLoogedInUserFollowing(User postsRequestedUser, User loggedInUser) {
        if(postsRequestedUser.getFollowers() != null) {
            return postsRequestedUser.getFollowers().stream().anyMatch(user1 -> user1.getFrom().getId().equals(loggedInUser.getId()));
        } else {
            return false;
        }
    }

    @Override
    public List<Post> getTimelinePosts(Long userId) {
        return postRepository.findTimelinePosts(userId);
    }
}
