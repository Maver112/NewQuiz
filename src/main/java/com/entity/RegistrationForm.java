package com.entity;

import com.entity.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.entity.validator.NotTaken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@PasswordMatches
public class RegistrationForm {

    @NotBlank(message = "Login jest wymagany")
    @NotTaken
    private String username;

    @NotBlank(message = "Hasło jest wymagane")
    @Size(min = 8, max = 30, message = "Hasło musi mieć od 8 do 30 znaków")
    private String password;

    private String confirmPassword;


    @NotBlank(message = "Imię jest wymagane")
    private String firstName;

    @NotBlank(message = "Nazwisko jest wymagane")
    private String lastName;

    public RegistrationForm() {
    }

    public RegistrationForm(@NotBlank(message = "Login jest wymagany") String username, @NotBlank(message = "Hasło jest wymagane") @Size(min = 8, max = 30, message = "Długość hasła musi być od 8 do 30 znaków") String password, String confirmPassword, @NotBlank(message = "Imię jest wymagane") String firstName, @NotBlank(message = "Nazwisko jest wymagane") String lastName) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

}
