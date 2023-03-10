package com.chinhnd.recruit.service.impl;

import com.chinhnd.recruit.entity.ResponseObject;
import com.chinhnd.recruit.repository.OTPRepository;
import com.chinhnd.recruit.repository.RoleRepository;
import com.chinhnd.recruit.repository.UserRepository;
import com.chinhnd.recruit.security.jwt.JWTFilter;
import com.chinhnd.recruit.security.jwt.TokenProvider;
import com.chinhnd.recruit.service.mapper.UserMapper;
import com.chinhnd.recruit.core.Constants;
import com.chinhnd.recruit.dto.UserDTO;
import com.chinhnd.recruit.entity.OTP;
import com.chinhnd.recruit.entity.Role;
import com.chinhnd.recruit.entity.User;
import com.chinhnd.recruit.event.IMailService;
import com.chinhnd.recruit.repository.AuthenticateRepository;
import com.chinhnd.recruit.security.jwt.JWTTokenResponse;
import com.chinhnd.recruit.service.AuthenticateService;
import com.chinhnd.recruit.service.UserService;
import com.chinhnd.recruit.web.vm.LoginVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    @Autowired
    UserService userService;
    @Autowired
    IMailService iMailService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    OTPRepository otpRepository;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    TokenProvider tokenProvider;

    public final AuthenticateRepository authenticateRepository;
    public final UserMapper userMapper;
    public final RoleRepository roleRepository;
    public final UserRepository userRepository;

    public AuthenticateServiceImpl(AuthenticateRepository authenticateRepository, UserMapper userMapper, RoleRepository roleRepository, UserRepository userRepository) {
        this.authenticateRepository = authenticateRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User signup(UserDTO dto) {
        try {
            Set<Role> roles = roleRepository.findByCode(Constants.Role.USER);
            User user = userMapper.toEntity(dto);
            user.setDelete(false);
            user.setActive(false);
            user.setActive(false);
            user.setDelete(false);
            user.setRoles(roles);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String enCryptPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(enCryptPassword);
            userRepository.save(user);
            iMailService.sendRegistrationUserConfirm(user.getEmail());
            return user;
        } catch (Exception e) {
            log.error("cannot save to database");
            return null;
        }
    }

    public ResponseObject changePassword(String username, String newPassword, String currentpass) {
        User user = userService.findUserByUserName(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user != null) {
            if (passwordEncoder.matches(currentpass, user.getPassword())) {
                String enCryptPassword = passwordEncoder.encode(newPassword);
                user.setPassword(enCryptPassword);
                userRepository.save(user);
                return new ResponseObject("?????i m???t kh???u th??nh c??ng", "");
            } else {
                return new ResponseObject("M???t kh???u kh??ng kh???p", "");
            }
        }
        return new ResponseObject("Kh??ng t??m th???y user", "");
    }

    public String sendOtpToGmail(String gmail) {
        User user = userService.findUserByEmail(gmail);
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("hulkhulk1245@gmail.com");
            mimeMessageHelper.setTo(gmail);
            String otp = userService.generateOTP(user) + "";
            mimeMessageHelper.setText("Please dont share this token with anyone else: " + otp);
            mimeMessageHelper.setSubject("Verifing forgot password");
            javaMailSender.send(mimeMessage);
            return otp;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeNewPassword(String otpTaken, String password) {
        OTP otp = otpRepository.findByCode(otpTaken);
        User user = userRepository.findUserById(otp.getUser().getId());
        if (otpRepository.findByCode(otpTaken) != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String enCryptPassword = passwordEncoder.encode(password);
            user.setPassword(enCryptPassword);
            userRepository.save(user);
        }
    }

    public JWTTokenResponse login(LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationString = new UsernamePasswordAuthenticationToken(
                loginVM.getUserName(),
                loginVM.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationString);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.getRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, String.format("Bearer %s", jwt));
        User userLogin = userService.findUserByUserName(loginVM.getUserName());
        return new JWTTokenResponse(jwt, userLogin);
    }
}
