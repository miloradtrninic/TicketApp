package com.msmisa.TicketApp.dao.fan;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Transactional
@Repository
public class FanZoneDaoImpl extends AbstractGenericDao<FanZone, Integer> implements FanZoneDao {
	@Autowired
	public FanZoneDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}
}
