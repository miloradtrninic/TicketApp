package com.msmisa.TicketApp.dao.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Transactional
@Repository
public class UserDaoImpl extends AbstractGenericDao<User, Integer> implements UserDao {

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}



}
