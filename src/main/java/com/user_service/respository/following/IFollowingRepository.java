package com.user_service.respository.following;

import com.user_service.entity.Following;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin("http://localhost:4200")
public interface IFollowingRepository extends JpaRepository<Following, Long> {

    @Modifying
    @Query("DELETE FROM Following WHERE from.id =:userId AND to.id =:followingId")
    @Transactional
    void unFollowUser(@Param("userId") Long userId, @Param("followingId") Long followingId);

    List<Following> findFollowingByFromUserName(@Param("userName") String userName);

    @Query("SELECT follow FROM Following follow WHERE from.id =:fromId AND to.id =:toId")
    Following findFollowingByFromAndTo(@Param("fromId") Long fromId, @Param("toId") Long toId);

    @Query("SELECT follow FROM Following follow WHERE to.id =:userId AND followRequest = 1")
    List<Following> getFollowRequests(@Param("userId") Long userId);

    @Query("SELECT follow FROM Following follow WHERE to.userName =:userName AND followRequest = 2")
    List<Following> findFollowers(@Param("userName") String userName);

    @Query("SELECT follow FROM Following follow WHERE from.userName =:userName AND followRequest = 2")
    List<Following> findFollowing(@Param("userName") String userName);
}
