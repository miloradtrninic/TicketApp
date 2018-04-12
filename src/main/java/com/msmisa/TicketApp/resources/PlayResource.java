package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.PlayPreviewDTO;

@RestController
@RequestMapping(value="/play")
public class PlayResource extends AbstractController<Play, Integer>{
	
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
	public PlayPreviewDTO addNew(@DTO(value=PlayPreviewDTO.class) Play play) {
		return convertToDto(getDao().insert(play), PlayPreviewDTO.class);
	}
	
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PlayPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), PlayPreviewDTO.class);
	}

	
}
