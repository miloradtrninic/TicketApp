package com.msmisa.TicketApp.resources;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.dao.fan.BidDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.BidCreationDTO;
import com.msmisa.TicketApp.dto.preview.BidPreviewDTO;
import com.msmisa.TicketApp.dto.preview.MyBidPreviewDTO;

@RestController
@RequestMapping(value="/bids")
public class BidResource extends AbstractController<Bid, Integer>{
	
	@PostMapping(value="new", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BidPreviewDTO> makeBid(@DTO(BidCreationDTO.class) Bid newBid) {
		Bid added = getDao().insert(newBid);
		return new ResponseEntity<BidPreviewDTO>(convertToDto(added, BidPreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="mybids", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MyBidPreviewDTO>> makeBid() {
		BidDao dao = (BidDao) getDao();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<MyBidPreviewDTO> bids = dao.getMyBids(authentication.getName());
		return new ResponseEntity<List<MyBidPreviewDTO>>(bids, HttpStatus.OK);
	}
	@PostMapping(value="update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BidPreviewDTO> makeBid(BidPreviewDTO updateBid) {
		BidDao dao = (BidDao) getDao();
		Bid bid = dao.get(updateBid.getId());
		bid.setOffer(updateBid.getOffer());
		bid = dao.update(bid);
		return new ResponseEntity<BidPreviewDTO>(convertToDto(bid, BidPreviewDTO.class), HttpStatus.OK);
	}
	
}
