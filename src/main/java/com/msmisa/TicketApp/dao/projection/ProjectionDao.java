package com.msmisa.TicketApp.dao.projection;

import java.util.List;

import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface ProjectionDao extends GenericDao<Projection, Integer> {

	List<Projection> getWatchedBy(String username) throws DaoException;

	void rateProjection(String username, Integer projectionId, Double rating) throws DaoException;

}
