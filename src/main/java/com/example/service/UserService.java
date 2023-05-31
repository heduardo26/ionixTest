package com.example.service;


import com.example.entity.UserEntity;
import com.example.exception.DataDuplicateException;
import com.example.repository.UserRepo;
import com.example.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public List<UserEntity>getAllUsers(){
        return userRepo.findAll();
    }

    public UserEntity getByEmail(String email){
        UserEntity user = userRepo.findByEmail(email);
        if(Objects.isNull(user)){
            throw new DataNotFoundException("Email "+email+" not found.");
        }

        return user;
    }

    @Transactional
    public void deleteUser(String email){
        UserEntity user = userRepo.findByEmail(email);
        if(Objects.isNull(user)){
            throw new DataNotFoundException("Email "+email+" not found.");
        }

        userRepo.deleteByEmail(email);
    }

    public void addUser(UserEntity user) {
        UserEntity userAux = userRepo.findByEmail(user.getEmail());
        if(!Objects.isNull(userAux)){
            throw new DataDuplicateException("Email "+user.getEmail()+" already exist.");
        }

        userRepo.save(user);
    }
}
