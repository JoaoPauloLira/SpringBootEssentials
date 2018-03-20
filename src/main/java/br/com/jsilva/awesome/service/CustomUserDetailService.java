package br.com.jsilva.awesome.service;

import br.com.jsilva.awesome.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        val user_find = Optional.ofNullable(userRepository.findByUserName(userName))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        val authoritListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
        val authoritListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new User(user_find.getUserName(),user_find.getPassword(),user_find.isAdmin() ? authoritListAdmin:authoritListUser);
    }
}
