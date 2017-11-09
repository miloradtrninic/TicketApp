package com.msmisa.TicketApp.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.msmisa.TicketApp.beans.User;

public class CustomUserSerializer extends StdSerializer<List<User>> {

	public CustomUserSerializer() {
		this(null);
	}
	
	public CustomUserSerializer(Class<List<User>> t) {
		super(t);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(List<User> users, JsonGenerator generator, SerializerProvider arg2) throws IOException {
		 List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
		 generator.writeObject(emails);
	}

}
