package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.dao.auditorium.TheatreDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.TheatreCreationDTO;
import com.msmisa.TicketApp.dto.preview.CinemaPreviewDTO;
import com.msmisa.TicketApp.dto.preview.PlayPreviewDTO;
import com.msmisa.TicketApp.dto.preview.TheatrePreviewDTO;
import com.msmisa.TicketApp.dto.update.CinemaUpdateDTO;
import com.msmisa.TicketApp.dto.update.TheatreUpdateDTO;

@RestController
@RequestMapping(value="/theatre")
public class TheatreResource extends AbstractController<Theatre, Integer> {
	
	@Autowired
	private TheatreDao theatreDao;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TheatrePreviewDTO>> getAll(){
		List<Theatre> list = getDao().getAll();
		List<TheatrePreviewDTO> theatreDtoList = list.stream().map(c -> convertToDto(c, TheatrePreviewDTO.class)).collect(Collectors.toList());
		if(list.isEmpty())
			return new ResponseEntity<List<TheatrePreviewDTO>>(theatreDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<TheatrePreviewDTO>>(theatreDtoList,HttpStatus.OK);

	}
	
	@GetMapping(value="getAllPlays/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public List<PlayPreviewDTO> getAllPlays(@PathVariable("id") Integer id) {
		Set<Play> theatres = theatreDao.getAllPlays(id);
		List<PlayPreviewDTO> plays = theatres.stream().map(p -> modelMapper.map(p, PlayPreviewDTO.class)).collect(Collectors.toList());
		return plays;
	}


	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public TheatrePreviewDTO addNew(@DTO(value=TheatreCreationDTO.class) Theatre theatre) {
		return convertToDto(getDao().insert(theatre), TheatrePreviewDTO.class);
	}



	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TheatrePreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), TheatrePreviewDTO.class);
	}
	
	@PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public TheatrePreviewDTO update(@RequestBody TheatreUpdateDTO entity){
		logger.info("ovo je id aaaaaaaaaaaaa : " + entity.getId());
		logger.info("ovo je id aaaaaaaaaaaaa : " + entity.getName());
		logger.info("ovo je id aaaaaaaaaaaaa : " + entity.getDescription());
		Theatre theatre = getDao().get(entity.getId());
		theatre.setAddress(entity.getAddress());
		theatre.setDescription(entity.getDescription());
		theatre.setName(entity.getName());
		theatre.setRatings(entity.getRatings());
		return convertToDto(getDao().update(theatre), TheatrePreviewDTO.class);
	}
}
