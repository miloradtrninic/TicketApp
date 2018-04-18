package com.msmisa.TicketApp.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.fan.FanItemDao;
import com.msmisa.TicketApp.dao.fan.FanZoneDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.preview.FanItemPreviewDTO;

@RestController
@RequestMapping(value="/fanitem")
public class FanItemResource extends AbstractController<FanItem, Integer>{
	@Autowired
	UserDao userDao;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	FanZoneDao zoneDao;
	
	@GetMapping(value="getAll/{zoneID}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<FanItemPreviewDTO>> getAllByZone(@PathVariable("zoneID") Integer zoneID) {
		FanItemDao dao = (FanItemDao) getDao();
		List<FanItem> items = dao.getByZone(zoneID);
		if(items.isEmpty())
			return new ResponseEntity<List<FanItemPreviewDTO>>(convertToDto(items, FanItemPreviewDTO.class),HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanItemPreviewDTO>>(convertToDto(items, FanItemPreviewDTO.class),HttpStatus.OK);
	}
	
	@PostMapping(value="new")
	public ResponseEntity<?> createItem(@RequestParam("name") String name, 
						   @RequestParam("description") String description, 
						   @RequestParam("fanzoneId") Integer fanzoneId, 
						   @RequestParam("image") MultipartFile file) {
		if (!file.isEmpty()) {
	        try {
	        	FanZone zone = zoneDao.get(fanzoneId);
				FanItem item = new FanItem();
				item.setDescription(description);
				item.setFanzone(zone);
				item.setName(name);
	        	item.setImagePath(storageService.store(file, name));
	        	item = getDao().insert(item);
	        	logger.info("inserted");
	        	return new ResponseEntity<FanItemPreviewDTO>(convertToDto(item, FanItemPreviewDTO.class), HttpStatus.OK);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
        } else {
        	return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
		
	}
	
	@PostMapping(value="/update")
	public ResponseEntity<?> update(@RequestParam("name") String name, 
									@RequestParam("description") String description, 
									@RequestParam("fanitemId") Integer fanioneId, 
									@RequestParam(value="image", required=false) MultipartFile file) {
		FanItem item = getDao().get(fanioneId);
		item.setDescription(description);
		item.setName(name);
		if (file != null && !file.isEmpty()) {
	        try {
	        	item.setImagePath(storageService.store(file, name));
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
        }
		item = getDao().update(item);
    	return new ResponseEntity<FanItemPreviewDTO>(convertToDto(item, FanItemPreviewDTO.class), HttpStatus.OK);
	}
	@GetMapping(value="{itemId}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<FanItemPreviewDTO> getOne(@PathVariable("itemId") Integer id) {
		FanItem item = getDao().get(id);
		return new ResponseEntity<FanItemPreviewDTO>(convertToDto(item, FanItemPreviewDTO.class), HttpStatus.OK);
	}	
	@GetMapping(value="reserve/{itemId}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<FanItemPreviewDTO> reserve(@PathVariable("itemId") Integer id) {
		FanItemDao dao = (FanItemDao) getDao();
		//TO Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		FanItem item = dao.reserve("admin", id);
		return new ResponseEntity<FanItemPreviewDTO>(convertToDto(item, FanItemPreviewDTO.class), HttpStatus.OK);
	}
}
