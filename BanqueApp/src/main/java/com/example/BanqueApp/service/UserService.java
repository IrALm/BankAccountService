package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.User;

public interface UserService {

    User createUser(String email , String password ,  String role);
}
