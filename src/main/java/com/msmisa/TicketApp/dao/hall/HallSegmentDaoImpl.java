package com.msmisa.TicketApp.dao.hall;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.HallSegment;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class HallSegmentDaoImpl extends AbstractGenericDao<HallSegment, Integer> implements HallSegmentDao {

	@Autowired
	public HallSegmentDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
