package com.msmisa.TicketApp.dao.ticket;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.OptimisticLockException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class TicketDaoImpl extends AbstractGenericDao<Ticket, Integer> implements TicketDao {

	@Autowired
	public TicketDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Ticket update(Ticket ticket) {
		try {
			SessionFactory sf = getSessionFactory();
			Ticket t = sf.getCurrentSession().get(Ticket.class, ticket.getId());
			sf.getCurrentSession().saveOrUpdate(ticket);
			return t;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Ticket> getTicketsForProjection(Integer id) {
		try {
			SessionFactory sf = getSessionFactory();
			return sf.getCurrentSession()
					.createCriteria(Ticket.class)
					.add(Restrictions.eq("projection_id", id))
					.list();
		} catch(Exception e) {
			e.printStackTrace();;
			return null;
		}
	}
}
