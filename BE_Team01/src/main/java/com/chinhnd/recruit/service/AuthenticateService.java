package com.chinhnd.recruit.service;


import com.chinhnd.recruit.entity.ResponseObject;
import com.chinhnd.recruit.entity.User;
import com.chinhnd.recruit.dto.UserDTO;
import com.chinhnd.recruit.security.jwt.JWTTokenResponse;
import com.chinhnd.recruit.web.vm.LoginVM;

public interface AuthenticateService {
    public User signup(UserDTO dto);

    ResponseObject changePassword(String username, String newPasswors, String currentpass);

    String sendOtpToGmail(String gmail);

    void takeNewPassword(String otpTaken, String newPassword);

    JWTTokenResponse login(LoginVM loginVM);
}
