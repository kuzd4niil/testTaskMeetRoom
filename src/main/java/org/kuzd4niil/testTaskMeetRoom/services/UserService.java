package org.kuzd4niil.testTaskMeetRoom.services;

import org.kuzd4niil.testTaskMeetRoom.entities.User;
import org.kuzd4niil.testTaskMeetRoom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AvatarCreationService imageCreationService;

    @Autowired
    public UserService(UserRepository userRepository, AvatarCreationService avatarCreationService) {
        this.userRepository = userRepository;
        this.imageCreationService = avatarCreationService;
    }

    public User findByUsername(String username) throws NoSuchAlgorithmException, IOException {
        User user = userRepository.findByUsername(username);

        // Generate avatar for user if him doesn't have it
        if ((user.getAvatar() == null) || (user.getAvatar().length == 0)) {
            user.setAvatar(imageCreationService.genAvatar(user.getUsername()));
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = findByUsername(username);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority("USER"))));
    }

    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return userList;
    }
}