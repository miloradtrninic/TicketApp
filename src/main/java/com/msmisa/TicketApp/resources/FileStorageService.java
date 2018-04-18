package com.msmisa.TicketApp.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements StorageService {

	@Override
	public String store(MultipartFile file, String name) throws IOException {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	String fileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        byte[] bytes = file.getBytes();
        String timeStamp = Long.toString(new Date().getTime());

        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File("src\\main\\resources\\static\\"+fileName+timeStamp+'.'+extension)));
        stream.write(bytes);
        stream.close();
        return fileName+timeStamp+'.'+extension;
	}

}
