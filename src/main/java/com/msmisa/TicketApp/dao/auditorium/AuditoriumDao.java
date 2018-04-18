package com.msmisa.TicketApp.dao.auditorium;

import java.util.List;
import java.util.Set;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface AuditoriumDao extends GenericDao<Auditorium, Integer>  {
	List<Auditorium> getByAdmin(String username) throws DaoException;
	Set<User> getAdmins(Integer id) throws DaoException;
}
