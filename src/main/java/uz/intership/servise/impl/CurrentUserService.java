package uz.intership.servise.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.intership.model.CurrentUserModel;
import uz.intership.model.User;
import uz.intership.repository.CurrentUserRepository;
import uz.intership.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final CurrentUserRepository currentUserRepository;
    private final UserRepository userRepository;
    public void currentUser(){
        User user = userRepository.findFirstByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        currentUserRepository.save(new CurrentUserModel(1, "current", user.getId()));
    }
}
