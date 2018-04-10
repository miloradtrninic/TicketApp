package com.msmisa.TicketApp.dao.fan;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class BidDaoImpl extends AbstractGenericDao<Bid, Integer> implements BidDao {

	@Autowired
	public BidDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
