package com.chinhnd.recruit.dto;

import com.chinhnd.recruit.validation.EmailFormatValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.chinhnd.recruit.validation.PasswordlFormatValidation;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserDTO {


    String fullName;

    @NotNull
    @EmailFormatValidation
    String email;

    @NotNull
    String userName;

    @NotNull
    @PasswordlFormatValidation
    String password;

    String phoneNumber;

    String homeTown;

    String avatarName;

    String gender;

    @NotNull
    @PasswordlFormatValidation
    String newPassword;

    String name;

    boolean isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date birthDay;
}
