package com.msmisa.TicketApp.dao.user;

import java.util.List;

import com.msmisa.TicketApp.beans.Invitation;
import com.msmisa.TicketApp.dao.GenericDao;

public interface InvitationDao extends GenericDao<Invitation, Integer>  {
	List<Invitation> getInvitationsForUser(Integer id);
}
