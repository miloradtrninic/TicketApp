package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.fan.FanAdDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.FanAdPreviewDTO;

@RestController
@RequestMapping(value="/fanad")
public class FanAdResource extends AbstractController<FanAd, Integer> {
	
	@Autowired
	UserDao userDao;
	
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
	
	
	@PutMapping(value="approve", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> approve(@DTO(FanAdPreviewDTO.class) FanAd fanAd) {
		fanAd.setAccepted(true);
		fanAd = getDao().update(fanAd);
		getDao().update(fanAd);
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}

	@PutMapping(value="disapprove", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> disapprove(FanAd fanAd)  {
		fanAd.setAccepted(false);
		fanAd = getDao().update(fanAd);
		getDao().update(fanAd);
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}
	@PutMapping(value="assign", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanAdPreviewDTO> assignToMe(@DTO(FanAdPreviewDTO.class) FanAd fanAd) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userDao.getByUserName(authentication.getName());
		fanAd.setAdmin(user);
		fanAd = getDao().update(fanAd);
		return new ResponseEntity<FanAdPreviewDTO>(convertToDto(fanAd, FanAdPreviewDTO.class), HttpStatus.OK);
	}
	
	
}
