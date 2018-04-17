package com.msmisa.TicketApp.dao.fan;

import java.util.List;
import java.util.Set;

import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface FanZoneDao extends GenericDao<FanZone, Integer> {
	Set<User> getAdmins(Integer zoneID) throws DaoException;
}
