package com.msmisa.TicketApp.resources;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.fan.BidDao;
import com.msmisa.TicketApp.dao.fan.FanAdDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.BidCreationDTO;
import com.msmisa.TicketApp.dto.preview.BidPreviewDTO;
import com.msmisa.TicketApp.dto.preview.MyBidPreviewDTO;

@RestController
@RequestMapping(value="/bids")
public class BidResource extends AbstractController<Bid, Integer>{
	
	@Autowired
	FanAdDao adDao;
	@Autowired
	UserDao userDao;
	
	@PostMapping(value="new", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BidPreviewDTO> makeBid(@RequestBody BidCreationDTO newBid) {
		Bid bid = new Bid();
		logger.info("offer + " + newBid.getOffer());
		logger.info("adFk + " + newBid.getAdFK());
		
		bid.setAccepted(false);
		bid.setOffer(newBid.getOffer());
		bid.setOfferDate(new Date());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	User user = userDao.getByUserName(authentication.getName());
		bid.setFromUser(user);
		FanAd ad = adDao.get(newBid.getAdFK());
		bid.setFanAd(ad);
		Bid added = getDao().insert(bid);
		return new ResponseEntity<BidPreviewDTO>(convertToDto(added, BidPreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="mybids", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MyBidPreviewDTO>> makeBid() {
		BidDao dao = (BidDao) getDao();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MyBidPreviewDTO> bids = dao.getMyBids(authentication.getName());
		return new ResponseEntity<List<MyBidPreviewDTO>>(bids, HttpStatus.OK);
	}
	@PutMapping(value="update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BidPreviewDTO> makeBid(@RequestBody BidPreviewDTO updateBid) {
		BidDao dao = (BidDao) getDao();
		Bid bid = dao.get(updateBid.getId());
		bid.setOffer(updateBid.getOffer());
		bid = dao.update(bid);
		return new ResponseEntity<BidPreviewDTO>(convertToDto(bid, BidPreviewDTO.class), HttpStatus.OK);
	}
	
}
