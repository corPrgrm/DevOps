package com.boot;

import java.util.List;

public interface UserService {

    List<UserJpa> getAllUsers();

    UserJpa addUser(UserJpa user);

}
