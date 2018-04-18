package com.msmisa.TicketApp.resources;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService  {
	String store(MultipartFile file, String name) throws IOException;
}
