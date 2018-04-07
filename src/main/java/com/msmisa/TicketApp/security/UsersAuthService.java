package com.msmisa.TicketApp.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.user.UserDao;



@Service("myAppUserDetailsService")
public class UsersAuthService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			User user = userDao.getByUserName(username);
			if(user==null)
				throw new UsernameNotFoundException("Username not found.");
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			Set<UserRole> roles = user.getUserRoles();
			List<SimpleGrantedAuthority> roleAuths = new ArrayList<>();
			for(UserRole role:roles) {
				roleAuths.addAll(
						role.getPrivileges().stream()
						.map(priv -> {
							
							SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority(priv.getName());
							return grantedAuth;
						})
						.collect(Collectors.toList()));
			}
			UserDetails userDetails = new UserDetailsCustom(user.getUsername(), user.getPassword(), user.isEnabled(), roleAuths);
			return userDetails;
		}catch(DaoException e) {
			throw new UsernameNotFoundException("Username not found. Error with database.");
		}
	}
	
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			User user = userDao.getByEmail(email);
			if(user==null)
				throw new UsernameNotFoundException("E-mail not found.");
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			Set<UserRole> roles = user.getUserRoles();
			List<SimpleGrantedAuthority> roleAuths = new ArrayList<>();
			for(UserRole role:roles) {
				roleAuths.addAll(
						role.getPrivileges().stream()
						.map(priv -> {
							
							SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority(priv.getName());
							return grantedAuth;
						})
						.collect(Collectors.toList()));
			}
			UserDetails userDetails = new UserDetailsCustom(user.getUsername(), user.getPassword(), user.isEnabled(), roleAuths);
			return userDetails;
		}catch(DaoException e) {
			throw new UsernameNotFoundException("Username not found. Error with database.");
		}
	}

}
