package com.user_service.respository.following;

import com.user_service.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFollowingRepository extends JpaRepository<Following, Long> {
}
