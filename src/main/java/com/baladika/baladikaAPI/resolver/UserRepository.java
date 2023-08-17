package com.baladika.baladikaAPI.resolver;

import com.baladika.baladikaAPI.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByIdAndIsActiveTrue(String id);
}
