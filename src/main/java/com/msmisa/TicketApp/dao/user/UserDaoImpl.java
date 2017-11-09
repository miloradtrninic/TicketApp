package com.msmisa.TicketApp.dao.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Transactional
@Repository
public class UserDaoImpl extends AbstractGenericDao<UserDao, Integer> {

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
