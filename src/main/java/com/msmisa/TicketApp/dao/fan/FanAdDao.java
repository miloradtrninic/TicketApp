package com.msmisa.TicketApp.dao.fan;

import java.util.List;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface FanAdDao extends GenericDao<FanAd, Integer>{

	List<FanAd> getFromMyZone(String username) throws DaoException;
	List<FanAd> getMyToApprove(String username) throws DaoException;
	List<FanAd> getByZone(Integer zoneID) throws DaoException;
	List<FanAd> getFromUser(String username) throws DaoException;
	FanAd changeAppove(Integer id, boolean approved) throws DaoException;
	FanAd assignTo(Integer id, String username) throws DaoException;
	FanAd acceptBid(Integer bidId, String username);
}
