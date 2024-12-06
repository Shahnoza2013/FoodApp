package uz.pdp.fastfoodapp.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.pdp.fastfoodapp.model.User;
import uz.pdp.fastfoodapp.repository.UserRepository;

import java.util.Objects;

@Component
@SuppressWarnings("unused")
public class SessionUser {
    private final UserRepository userRepository;

    public SessionUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User user(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication==null){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof User user){
            return user;
        } else if(principal instanceof org.springframework.security.core.userdetails.User springUser){
            return userFromUsername(springUser.getUsername());
        }
        return   null;

    }

    private User userFromUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Integer id() {
        User user = user();
        if (Objects.isNull(user))
            return -1;
        return user.getId();
    }
}

