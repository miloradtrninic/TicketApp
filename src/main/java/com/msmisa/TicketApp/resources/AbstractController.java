package com.msmisa.TicketApp.resources;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.msmisa.TicketApp.dao.GenericDao;

public abstract class AbstractController<Entity, Key> {

	@Autowired
	private GenericDao<Entity, Key> dao;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected <E> E convertToDto(Entity source, Class<E> to) {
		return modelMapper.map(source, to);
	}
	
	protected <E> List<E> convertToDto(Collection<Entity> source, Class<E> to) {
		return source.stream().map(e -> convertToDto(e, to)).collect(Collectors.toList());
	}
	
	protected GenericDao<Entity, Key> getDao() {
		return dao;
	}



	@RequestMapping(value="/delete/{id}",
			method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable(value="id") Key id){
		Entity ent = dao.get(id);
		dao.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	

}


