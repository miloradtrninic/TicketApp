package com.msmisa.TicketApp.events;

import java.util.ArrayList;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.security.JwtAuthenticationRequest;
import com.msmisa.TicketApp.security.JwtTokenUtil;
import com.msmisa.TicketApp.security.UserDetailsCustom;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (SendFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) throws SendFailedException {
		User user = event.getUser();
		UserDetailsCustom udt = new UserDetailsCustom(user.getUsername(), user.getPassword(), 
										user.isEnabled(), new ArrayList<SimpleGrantedAuthority>());
		String token = tokenUtil.generateToken(udt);
		
		String mail = user.getEmail();
		String subject = "Registration confirmation";
		String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
		String message = messages.getMessage("message.regSucc", null, event.getLocale());
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mail);
		msg.setSubject(subject);
		msg.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
		
		mailSender.send(msg);
	}

}
