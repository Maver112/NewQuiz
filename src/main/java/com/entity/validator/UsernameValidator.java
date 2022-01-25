package com.entity.validator;

import org.springframework.beans.factory.annotation.Autowired;
import com.entity.User;
import com.repository.UserRepository;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<NotTaken, String> {

    private UserRepository userRepository;

    @Autowired
    public UsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(NotTaken constraint) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return true;
        }
        return false;
    }

    public static boolean isUserNameValid(String username) {

        String regex = "^[a-zA-Z0-9]{4,10}$";
        if(username == null){
            return false;
        }
        return username.matches(regex);
    }
}
