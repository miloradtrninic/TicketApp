package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.fan.FanItemDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.FanItemCreationDTO;
import com.msmisa.TicketApp.dto.preview.FanItemPreviewDTO;

@RestController
@RequestMapping(value="/fanitem")
public class FanItemResource extends AbstractController<FanItem, Integer>{
	@Autowired
	UserDao userDao;
	
	@GetMapping(value="getAll/{zoneID}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<FanItemPreviewDTO>> getAllByZone(@PathVariable("zoneID") Integer zoneID) {
		FanItemDao dao = (FanItemDao) getDao();
		List<FanItem> items = dao.getByZone(zoneID);
		if(items.isEmpty())
			return new ResponseEntity<List<FanItemPreviewDTO>>(convertToDto(items, FanItemPreviewDTO.class),HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanItemPreviewDTO>>(convertToDto(items, FanItemPreviewDTO.class),HttpStatus.OK);
	}
	
	
	@PostMapping(value="new", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<FanItemPreviewDTO> createItem(@DTO(FanItemCreationDTO.class) FanItem fanItem) {
		fanItem.setId(null);
		FanItem added = getDao().insert(fanItem);
		return new ResponseEntity<FanItemPreviewDTO>(convertToDto(added, FanItemPreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="reserve/{itemId}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<FanItemPreviewDTO> reserve(@PathVariable("itemId") Integer id) {
		FanItem item = getDao().get(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userDao.getByUserName(authentication.getName());
		user.getFanItems().add(item);
		userDao.update(user);
		return new ResponseEntity<FanItemPreviewDTO>(convertToDto(item, FanItemPreviewDTO.class), HttpStatus.OK);
	}
}
