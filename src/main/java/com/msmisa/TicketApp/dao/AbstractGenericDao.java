package com.msmisa.TicketApp.dao;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class AbstractGenericDao <Entity, Key> implements GenericDao<Entity, Key> {

	private SessionFactory sessionFactory;

	private Class<Entity> entitiyClass;
	private Class<? extends Serializable> keyClass;




	public AbstractGenericDao(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
		resolveGenericType();
	}

	@SuppressWarnings("unchecked")
	private void resolveGenericType() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		entitiyClass = (Class<Entity>) pt.getActualTypeArguments()[0];
		keyClass = (Class<? extends Serializable>) pt.getActualTypeArguments()[1];
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> getAll() throws DaoException {
		try{
			return sessionFactory.getCurrentSession()
					.createCriteria(entitiyClass)
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> getAll(int firstResult, int maxResults) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return sessionFactory.getCurrentSession()
					.createCriteria(entitiyClass)
					.setFirstResult(firstResult)
					.setMaxResults(maxResults)
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public Entity get(Key id) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return sessionFactory.getCurrentSession()
								 .get(entitiyClass, keyClass.cast(id));
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public Entity insert(Entity entity) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return saveOrUpdate(entity);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public Entity update(Entity entity) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return saveOrUpdate(entity);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void delete(Entity entity) throws DaoException {
		// TODO Auto-generated method stub
		try{
			Session session = sessionFactory.getCurrentSession();
			session.delete(session.contains(entity) ? entity : session.merge(entity));
			//sessionFactory.getCurrentSession().delete(entity);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	private Entity saveOrUpdate(Entity entity) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(entity);
			return entity;
		} catch (HibernateException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
