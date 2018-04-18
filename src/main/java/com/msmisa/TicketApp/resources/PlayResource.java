package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.dao.auditorium.TheatreDao;
import com.msmisa.TicketApp.dao.projection.PlayDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.PlayCreationDTO;
import com.msmisa.TicketApp.dto.preview.MoviePreviewDTO;
import com.msmisa.TicketApp.dto.preview.PlayPreviewDTO;
import com.msmisa.TicketApp.dto.update.PlayUpdateDTO;

@RestController
@RequestMapping(value="/play")
public class PlayResource extends AbstractController<Play, Integer>{
	
	@Autowired
	TheatreDao theatreDao;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlayPreviewDTO>> getAll(){
		List<Play> list = getDao().getAll();
		List<PlayPreviewDTO> playDtoList = list.stream().map(c -> convertToDto(c, PlayPreviewDTO.class)).collect(Collectors.toList());
		if(list.isEmpty())
			return new ResponseEntity<List<PlayPreviewDTO>>(playDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<PlayPreviewDTO>>(playDtoList,HttpStatus.OK);

	}
	
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public PlayPreviewDTO addNew(@DTO(value=PlayCreationDTO.class) Play play) {
		play.setId(null);
		play.getActors().forEach(act -> logger.info(act.getId() + act.getName() + act.getLastName()));
		logger.info("Director " + play.getDirector().getName());
		play.getGenres().forEach(gen -> logger.info(gen.getId() + gen.getName()));
		PlayDao playDao = (PlayDao) getDao();
		return convertToDto(playDao.insert(play), PlayPreviewDTO.class);
	}
	
	@GetMapping(value="allFromTheatre/{theatreId}")
	public ResponseEntity<?> allFromTheatre(@PathVariable("theatreId") Integer theatreId) {
		
		return new ResponseEntity<List<PlayPreviewDTO>>
				(convertToDto(theatreDao.getAllPlays(theatreId), PlayPreviewDTO.class), HttpStatus.OK);
	}
	
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PlayPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), PlayPreviewDTO.class);
	}

	
	@PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Play update(@DTO(value=PlayUpdateDTO.class) Play entity){
		return getDao().update(entity);
	}
	
}
