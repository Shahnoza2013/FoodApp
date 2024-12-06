package uz.pdp.fastfoodapp.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.model.User;
import uz.pdp.fastfoodapp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findFirstByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));
        UserDetails build = org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .build();
        return build;
    }
}
