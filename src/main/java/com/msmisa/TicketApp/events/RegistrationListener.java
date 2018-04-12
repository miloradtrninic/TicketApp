package com.msmisa.TicketApp.events;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.security.JwtAuthenticationRequest;
import com.msmisa.TicketApp.security.JwtTokenUtil;
import com.msmisa.TicketApp.security.UserDetailsCustom;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("mySecret")
	private String secret;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		System.out.println("Okinuta registracija");
		try {
			this.confirmRegistration(event);
		} catch (SendFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) throws SendFailedException {
		User user = event.getUser();
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DATE, 1);
		Date tommorow = c.getTime();
		
		String token = Jwts.builder()
					   .setIssuer(event.getAppUrl())
					   .setSubject(user.getUsername())
					   .setIssuedAt(today)
					   .setExpiration(tommorow)
					   .signWith(SignatureAlgorithm.HS512, secret)
					   .compact();
		String mail = user.getEmail();
		String subject = "Registration confirmation";
		String confirmationUrl = event.getAppUrl() + "/auth/registrationConfirm?token=" + token;
		String message = messages.getMessage("msg.regSucc", null, new Locale("en"));
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mail);
		msg.setSubject(subject);
		msg.setText(message + " \n\n " + "http://localhost:8080" + confirmationUrl + " \n\n\n Enjoy your stay!\n TicketApp Team");
		
		
		mailSender.send(msg);
	}

}
