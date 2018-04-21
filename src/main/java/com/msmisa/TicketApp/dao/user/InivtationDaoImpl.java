package com.msmisa.TicketApp.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Invitation;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class InivtationDaoImpl extends AbstractGenericDao<Invitation, Integer> implements InvitationDao{

	@Autowired
	public InivtationDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Invitation> getInvitationsForUser(Integer id) {
		SessionFactory sf = getSessionFactory();
		try {
			return sf.getCurrentSession()
					.createCriteria(Invitation.class)
					.add(Restrictions.eq("user_id", id))
					.list();
		} catch(DaoException e) {
			e.printStackTrace();
			return null;
		}
	}

}
