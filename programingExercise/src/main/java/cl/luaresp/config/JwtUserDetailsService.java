package cl.luaresp.config;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * service who implement logical to return the password asociate at username
 * 
 * @author luaresp
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		BCryptPasswordEncoder enco = new BCryptPasswordEncoder();
		String pass;

		switch (username) {
		case "luaresp":
			pass = enco.encode("luaresp123");
			return new User(username, pass, new ArrayList<>());
		case "prueba":
			pass = enco.encode("prueba123");
			return new User(username, pass, new ArrayList<>());
		default:
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

	}

}
