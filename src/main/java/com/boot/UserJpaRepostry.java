package com.boot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepostry extends JpaRepository<UserJpa, String> {

    List<UserJpa> findAll();

    UserJpa saveAndFlush(UserJpa user);

}