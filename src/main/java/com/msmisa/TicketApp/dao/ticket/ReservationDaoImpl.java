package com.msmisa.TicketApp.dao.ticket;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class ReservationDaoImpl extends AbstractGenericDao<Reservation, Integer> implements ReservationDao{

	@Autowired
	public ReservationDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Reservation> getAllForUser(Integer reservedBy) {
		SessionFactory sf = getSessionFactory();
		try {
			return sf.getCurrentSession()
					 .createCriteria(Reservation.class)
					 .add(Restrictions.eq("reserved_by", reservedBy))
					 .list();
		} catch(DaoException e) {
			e.printStackTrace();
			return null;
		}
	}

}
