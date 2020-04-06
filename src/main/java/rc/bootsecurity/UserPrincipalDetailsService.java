package rc.bootsecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // extracting user from the database
        User user = userRepository.findByUsername(s);
        // converting User to userPrincipal
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }

}
