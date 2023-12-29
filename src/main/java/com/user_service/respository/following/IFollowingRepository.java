package com.user_service.respository.following;

import com.user_service.entity.Following;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
public interface IFollowingRepository extends JpaRepository<Following, Long> {

    @Modifying
    @Query("DELETE FROM Following WHERE from.id =:userId AND to.id =:followingId")
    @Transactional
    public void unFollowUser(@Param("userId") Long userId, @Param("followingId") Long followingId);
}
