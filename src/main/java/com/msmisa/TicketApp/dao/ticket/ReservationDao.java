package com.msmisa.TicketApp.dao.ticket;

import java.util.List;

import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.dao.GenericDao;

public interface ReservationDao extends GenericDao<Reservation, Integer> {

	List<Reservation> getAllForUser(Integer reservedBy);
}
