package com.msmisa.TikcetApp.dao.fan;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class FanAdDaoImpl extends AbstractGenericDao<FanAd, Integer> implements FanAdDao {

	@Autowired
	public FanAdDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
