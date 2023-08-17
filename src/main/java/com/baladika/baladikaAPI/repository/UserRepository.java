package com.baladika.baladikaAPI.repository;

import com.baladika.baladikaAPI.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByIdAndIsActiveTrue(Long id);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String id);
}
