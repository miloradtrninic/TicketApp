package com.msmisa.TicketApp.dao.fan;

import java.util.List;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface FanItemDao extends GenericDao<FanItem, Integer> {
	List<FanItem> getByZone(Integer zoneID) throws DaoException;
	FanItem reserve(String username, Integer id) throws DaoException;
}
