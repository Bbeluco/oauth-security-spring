package com.authandautenticate.JwtProject.services;

import com.authandautenticate.JwtProject.DTO.UserDTO;
import com.authandautenticate.JwtProject.entitites.UserEntity;
import com.authandautenticate.JwtProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String saveDTOInDB(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(convertRawPasswordToBcrypt(userDTO.getPassword()));
        return userRepository.save(userEntity).getId() + "";
    }

    private String convertRawPasswordToBcrypt(String password){
        return bCryptPasswordEncoder.encode(password);
    }
}
