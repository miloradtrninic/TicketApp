package com.msmisa.TicketApp.resources;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.fan.FanAdDao;
import com.msmisa.TicketApp.dao.fan.FanZoneDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.FanAdPreviewDTO;
import com.msmisa.TicketApp.dto.preview.FanItemPreviewDTO;

@RestController
@RequestMapping(value="/fanad")
public class FanAdResource extends AbstractController<FanAd, Integer> {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	FanZoneDao zoneDao;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FanAdPreviewDTO>> getAll(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<FanAd> list = ((FanAdDao)getDao()).getFromMyZone(authentication.getName());
		List<FanAdPreviewDTO> fanadDtoList = convertToDto(list, FanAdPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<FanAdPreviewDTO>>(fanadDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanAdPreviewDTO>>(fanadDtoList,HttpStatus.OK);
	}
	
	@GetMapping(value="getall/{zoneID}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<FanAdPreviewDTO>> getAllByZone(@PathVariable("zoneID") Integer zoneID) {
		FanAdDao dao = (FanAdDao) getDao();
		List<FanAd> items = dao.getByZone(zoneID);
		if(items.isEmpty())
			return new ResponseEntity<List<FanAdPreviewDTO>>(convertToDto(items, FanAdPreviewDTO.class),HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanAdPreviewDTO>>(convertToDto(items, FanAdPreviewDTO.class),HttpStatus.OK);
	}
	
	@GetMapping(value="toapprove", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FanAdPreviewDTO>> toapprove(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<FanAd> list = ((FanAdDao)getDao()).getMyToApprove(authentication.getName());
		List<FanAdPreviewDTO> fanadDtoList = convertToDto(list, FanAdPreviewDTO.class);
		if(fanadDtoList.isEmpty())
			return new ResponseEntity<List<FanAdPreviewDTO>>(fanadDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanAdPreviewDTO>>(fanadDtoList,HttpStatus.OK);
	}
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> approve(@PathVariable("id") Integer id) {
		FanAd fanAd = getDao().get(id);
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="myads", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FanAdPreviewDTO>> myAds(){
		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
		FanAdDao dao = (FanAdDao) getDao();
		List<FanAd> ads = dao.getFromUser(username);
		
		return new ResponseEntity<List<FanAdPreviewDTO>>(convertToDto((ads), FanAdPreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="acceptBid/{id}")
	public FanAdPreviewDTO acceptBid(@PathVariable("id") Integer bidId) {
		FanAdDao dao = (FanAdDao) getDao();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		FanAd ad = dao.acceptBid(bidId, authentication.getName());
		return convertToDto(ad, FanAdPreviewDTO.class);
	}
	
	@PostMapping(value="new")
	public ResponseEntity<?> createItem(@RequestParam("name") String name, 
						   @RequestParam("description") String description, 
						   @RequestParam("fanzoneId") Integer fanzoneId, 
						   @RequestParam("expirationDate") String expirationDate,
						   @RequestParam("image") MultipartFile file) {
		if (!file.isEmpty()) {
	        try {
	        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        	User user = userDao.getByUserName(authentication.getName());
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	        	FanZone zone = zoneDao.get(fanzoneId);
				FanAd fanAd = new FanAd();
				fanAd.setDescription(description);
				fanAd.setDateCreated(new Date());
				fanAd.setFanZone(zone);
				fanAd.setPostedBy(user);
				fanAd.setName(name);
				fanAd.setExpirationDate(sdf.parse(expirationDate));
				fanAd.setImagePath(storageService.store(file, name));
				fanAd = getDao().insert(fanAd);
	        	return new ResponseEntity<FanItemPreviewDTO>(convertToDto(fanAd, FanItemPreviewDTO.class), HttpStatus.OK);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
        } else {
        	return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
		
	}
	
	
	@PutMapping(value="approve", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> approve(@DTO(FanAdPreviewDTO.class) FanAd fanAdPrev) {
		FanAdDao dao = (FanAdDao) getDao();
		FanAd fanAd = dao.changeAppove(fanAdPrev.getId(), true);
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}

	@PutMapping(value="disapprove", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> disapprove(FanAd fanAdPrev)  {
		FanAdDao dao = (FanAdDao) getDao();
		FanAd fanAd = dao.changeAppove(fanAdPrev.getId(), false);
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}
	
	@PutMapping(value="assign", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> assignToMe(@DTO(FanAdPreviewDTO.class) FanAd fanAd) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		FanAdDao dao = (FanAdDao) getDao();
		fanAd = dao.assignTo(fanAd.getId(), authentication.getName());
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}
	
	
}
