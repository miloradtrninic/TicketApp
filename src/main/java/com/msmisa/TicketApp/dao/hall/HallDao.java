package com.msmisa.TicketApp.dao.hall;

import java.util.List;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface HallDao extends GenericDao<Hall, Integer> {
	List<Hall> getByAuditoriumId(Integer id) throws DaoException;
	List<Hall> getAllOfMyAuds(String username) throws DaoException;
}
