package com.msmisa.TicketApp.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Transactional
@Repository
public class UserDaoImpl extends AbstractGenericDao<User, Integer> implements UserDao {

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public User getByUserName(String username) throws DaoException {
		// TODO Auto-generated method stub
		SessionFactory sf = getSessionFactory();
		try{
			List<User> users = sf.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("username", username))
				.list();
			if(users.isEmpty())
				return null;
			return users.get(0);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public User getByEmail(String email) throws DaoException {
		// TODO Auto-generated method stub
		SessionFactory sf = getSessionFactory();
		try{
			List<User> users = sf.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.list();
			if(users.isEmpty())
				return null;
			return users.get(0);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public List<User> getByRole(String role) throws DaoException {
		SessionFactory sf = getSessionFactory();
		try {
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(User.class, "user");
			criteria.createAlias("user.userRoles", "role");
			criteria.add(Restrictions.eq("role.name",role));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		} catch(HibernateException e) {
			
		}
		
		return null;
	}
}
