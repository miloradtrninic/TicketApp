package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.msmisa.TicketApp.dao.GenericDao;

public abstract class AbstractController<Entity, Key> {

	@Autowired
	private GenericDao<Entity, Key> dao;
	
	@Autowired
	private ModelMapper modelMapper;
	

	protected <E> E convertToDto(Entity source, Class<E> to) {
		return modelMapper.map(source, to);
	}
	
	protected <E> List<E> convertToDto(List<Entity> source, Class<E> to) {
		return source.stream().map(e -> convertToDto(e, to)).collect(Collectors.toList());
	}
	
	protected GenericDao<Entity, Key> getDao() {
		return dao;
	}



	@RequestMapping(value="/delete/{id}",
			method=RequestMethod.DELETE)
	public void delete(@PathVariable(value="id") Key id){
		dao.delete(id);
	}

	

}


