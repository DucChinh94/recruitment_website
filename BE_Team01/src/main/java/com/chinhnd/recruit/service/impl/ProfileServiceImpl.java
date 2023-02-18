package com.chinhnd.recruit.service.impl;

import com.chinhnd.recruit.repository.ProfileRepository;
import com.chinhnd.recruit.entity.Profiles;
import com.chinhnd.recruit.entity.User;
import com.chinhnd.recruit.repository.UserRepository;
import com.chinhnd.recruit.service.ProfilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfilesService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Profiles getProfileByUsername(String username) {
        User user = userRepository.findByUserName(username);
        return profileRepository.findByUser(user);
    }
}
