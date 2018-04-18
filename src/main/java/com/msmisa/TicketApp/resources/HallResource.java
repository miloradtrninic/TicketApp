package com.msmisa.TicketApp.resources;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.websocket.server.PathParam;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.HallSegment;
import com.msmisa.TicketApp.beans.Seating;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dao.hall.HallDao;
import com.msmisa.TicketApp.dao.hall.HallSegmentDao;
import com.msmisa.TicketApp.dao.hall.SeatingDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.HallCreationDTO;
import com.msmisa.TicketApp.dto.preview.HallPreviewDTO;
import com.msmisa.TicketApp.dto.preview.HallSegmentPreviewDTO;
import com.msmisa.TicketApp.dto.preview.SeatingPreviewDTO;
import com.msmisa.TicketApp.dto.update.HallUpdateDTO;

@RestController
@RequestMapping(value="/hall")
public class HallResource extends AbstractController<Hall, Integer> {

	@Autowired
	AuditoriumDao auditoriumDao;
	
	@Autowired
	HallSegmentDao hallSegmentDao;
	
	@Autowired
	SeatingDao seatingDao;
	
	@GetMapping(value="byauditorim/{idAud}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getByAuditoriumID(@PathVariable("idAud") Integer id) {
		HallDao hallDao = (HallDao) getDao();
		logger.info("id is " + id);
		List<Hall> hallList = hallDao.getByAuditoriumId(id);
		logger.info("hallList size is " + hallList.size());
		List<HallPreviewDTO> hallPreview = convertToDto(hallList, HallPreviewDTO.class);
		if(hallList.isEmpty())
			return new ResponseEntity<List<HallPreviewDTO>>(hallPreview,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<HallPreviewDTO>>(hallPreview,HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HallPreviewDTO>> getAll(){
		List<Hall> list = getDao().getAll();
		List<HallPreviewDTO> hallDtoList = convertToDto(list, HallPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<HallPreviewDTO>>(hallDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<HallPreviewDTO>>(hallDtoList,HttpStatus.OK);

	}
	@GetMapping(value = "myhalls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HallPreviewDTO>> getAllOfMyAuds(){
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//List<Hall> list = getAllOfMyAuds(auth.getName());
		List<Hall> list = getDao().getAll();
		List<HallPreviewDTO> hallDtoList = convertToDto(list, HallPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<HallPreviewDTO>>(hallDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<HallPreviewDTO>>(hallDtoList,HttpStatus.OK);

	}
	
	
	@PostMapping(value="new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public HallPreviewDTO addNew(@RequestBody HallCreationDTO hall) {
		Auditorium aud = auditoriumDao.get(hall.getAudId());
		Hall hallNew = new Hall();
		hallNew.setAuditorium(aud);
		hallNew.setId(null);
		hallNew.setHallSegmentList(new HashSet<>());
		hallNew.setName(hall.getName());
		hallNew = getDao().insert(hallNew);
		List<HallSegment> segments = new ArrayList<>();
		for(HallSegmentPreviewDTO seg : hall.getHallSegmentList()) {
			HallSegment segment = modelMapper.map(seg, HallSegment.class);
			segment.setSeatingList(new HashSet<>());
			segment.setHall(hallNew);
			segment = hallSegmentDao.insert(segment);
			for(SeatingPreviewDTO seat : seg.getSeatingList()) {
				Seating seating = modelMapper.map(seat, Seating.class);
				seating.setHallSegment(segment);
				segment.getSeatingList().add(seatingDao.insert(seating));
			}
			hallNew.getHallSegmentList().add(segment);
		}
		return convertToDto(hallNew, HallPreviewDTO.class);
	}
	
	@PutMapping(value="/update",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public HallPreviewDTO update(@RequestBody HallUpdateDTO hallUpdate) {
		Hall hall = getDao().get(hallUpdate.getId());
		hall.setName(hallUpdate.getName());
		Type hallSegmentType = new TypeToken<List<HallSegmentPreviewDTO>>() {}.getType();
		hall.setHallSegmentList(modelMapper.map(hallUpdate.getHallSegmentList(), hallSegmentType));
		/*if(hall.getHallSegmentList().size() != hallUpdate.getHallSegmentList().size()) {
			for(HallSegment segment : hall.getHallSegmentList()) {
				if(hallUpdate.getHallSegmentList().stream().filter(seg -> seg.getId().equals(segment.getId())).findFirst().orElse(null) != null) {
					
				}
			}
		}*/
		return convertToDto(hall, HallPreviewDTO.class);
	}
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public HallPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), HallPreviewDTO.class);
	}
	
	
}
