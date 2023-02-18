package com.chinhnd.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.chinhnd.recruit.entity.Job;
import com.chinhnd.recruit.entity.StatusJobRegister;
import com.chinhnd.recruit.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobRegisterDTO {

    @NotNull
    Long id;
    @NotNull
    String addressInterview;
    @NotNull
    String cvFile;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Date dateInterview ;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date dateRegister;
    @NotNull
    Job job;
    @NotNull
    String mediaType;
    @NotNull
    String methodInterview;
    @NotNull
    User user;
    @NotNull
    StatusJobRegister statusJobRegister;
    @NotNull
    String reason;







}
