package com.msmisa.TicketApp.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements StorageService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public String store(MultipartFile file, String name) throws IOException {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	String fileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        byte[] bytes = file.getBytes();
        String timeStamp = Long.toString(new Date().getTime());
        File toSave = new File("src\\main\\resources\\static\\"+fileName+timeStamp+'.'+extension);
        logger.info(toSave.getAbsolutePath());
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(toSave));
        stream.write(bytes);
        stream.close();
        return fileName+timeStamp+'.'+extension;
	}

}
