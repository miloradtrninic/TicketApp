package com.msmisa.TicketApp.dao.user;

import java.util.List;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface UserDao extends GenericDao<User, Integer> {

	User getByUserName(String username) throws DaoException;

	User getByEmail(String email) throws DaoException;
	
	List<User> getByRole(String role) throws DaoException;
	
	
}
