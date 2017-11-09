package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.msmisa.TicketApp.dao.GenericDao;

public abstract class AbstractController<Entity,Key> {

	@Autowired
	private GenericDao<Entity, Key> dao;

	@RequestMapping(method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Entity>> getAll(@RequestParam(value="start", required=true) int start,
			@RequestParam(value="results", required=true) int results){
		List<Entity> list = dao.getAll(start,results);

		if(list.isEmpty())
			return new ResponseEntity<List<Entity>>(list,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<Entity>>(list,HttpStatus.OK);

	}

	@RequestMapping(value="/{id}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Entity getID(@PathVariable(value="id") Key id){
		return dao.get(id);
	}

	@RequestMapping(value="/new",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public Entity addNew(@RequestBody Entity entity){
		return dao.insert(entity);
	}

	@RequestMapping(value="/update",
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Entity update(@RequestBody Entity entity){
		return dao.update(entity);
	}

	@RequestMapping(value="/delete",
			method=RequestMethod.DELETE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@RequestBody Entity entity){
		dao.delete(entity);
	}


	protected GenericDao<Entity, Key> getDao() {
		return dao;
	}






}


