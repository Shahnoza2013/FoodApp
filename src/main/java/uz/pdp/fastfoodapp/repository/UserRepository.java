package uz.pdp.fastfoodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findFirstByUsernameAndPassword(String username, String password);

    Optional<User> findFirstByUsername(String username);
    Optional <User> findById(Integer id);

    @Query("SELECT au.id FROM User au WHERE au.username = :username")
    Integer getIdWithUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);
}
