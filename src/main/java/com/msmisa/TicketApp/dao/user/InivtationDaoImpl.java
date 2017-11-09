package com.msmisa.TicketApp.dao.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Invitation;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class InivtationDaoImpl extends AbstractGenericDao<Invitation, Integer> implements InvitationDao{

	@Autowired
	public InivtationDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
