package com.msmisa.TicketApp.dao.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class UserRoleDaoImpl extends AbstractGenericDao<UserRole, Integer> implements UserRoleDao {
	
	@Autowired
	public UserRoleDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
