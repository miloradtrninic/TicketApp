package com.msmisa.TikcetApp.dao.fan;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Transactional
@Repository
public class FanItemDaoImpl extends AbstractGenericDao<FanItem, Integer> {

	@Autowired
	public FanItemDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
