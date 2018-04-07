package com.msmisa.TicketApp.dao.user;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface UserDao extends GenericDao<User, Integer> {

	User getByUserName(String username) throws DaoException;

	User getByEmail(String email) throws DaoException;
}
