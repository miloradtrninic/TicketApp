package com.msmisa.TicketApp.dao;

import java.util.List;


public interface GenericDao<Entity,Key>  {

	List<Entity> getAll() throws DaoException;
	
	List<Entity> getAll(int firstResult, int maxResults) throws DaoException;
	
	Entity get(Key id) throws DaoException;
	
	Entity insert(Entity entity) throws DaoException;
	
	Entity update(Entity entity) throws DaoException;
	
	void delete(Entity entity) throws DaoException;

}
