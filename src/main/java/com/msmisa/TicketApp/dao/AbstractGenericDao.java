package com.msmisa.TicketApp.dao;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public abstract class AbstractGenericDao <Entity, Key> implements GenericDao<Entity, Key> {

	private SessionFactory sessionFactory;

	private Class<Entity> entityClass;
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
		entityClass = (Class<Entity>) pt.getActualTypeArguments()[0];
		keyClass = (Class<? extends Serializable>) pt.getActualTypeArguments()[1];
	}

	@SuppressWarnings("unchecked")
	@Override
	//@PreAuthorize("hasAuthority(#this.this.className+'_READ_ALL')")
	public List<Entity> getAll() throws DaoException {
		try{
			return sessionFactory.getCurrentSession()
					.createCriteria(entityClass)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	//@PreAuthorize("hasAuthority(#this.this.className+'_READ')")
	public List<Entity> getAll(int firstResult, int maxResults) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return sessionFactory.getCurrentSession()
					.createCriteria(entityClass)
					.setFirstResult(firstResult)
					.setMaxResults(maxResults)
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	public List<Entity> getAllIn(List<Key> keys) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return sessionFactory.getCurrentSession()
					.createCriteria(entityClass)
					.add(Restrictions.in("id", keys))
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	//@PreAuthorize("hasAuthority(#this.this.className+'_GET_ID')")
	public Entity get(Key id) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return sessionFactory.getCurrentSession()
								 .get(entityClass, keyClass.cast(id));
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	//@PreAuthorize("hasAuthority(#this.this.className+'_ADD')")
	public Entity insert(Entity entity) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return saveOrUpdate(entity);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	//@PreAuthorize("hasAuthority(#this.this.className+'_UPDATE')")
	public Entity update(Entity entity) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return saveOrUpdate(entity);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	//@PreAuthorize("hasAuthority(#this.this.className+'_DELETE')")
	public void delete(Key key) throws DaoException {
		// TODO Auto-generated method stub
		try{
			Session session = sessionFactory.getCurrentSession();
			Entity entity = get(key);
			session.delete(session.contains(entity) ? entity : session.merge(entity));
			//sessionFactory.getCurrentSession().delete(entity);
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}
	@Override
	public void initialize(Object proxy) throws DaoException {
		try {
			Hibernate.initialize(proxy);
		} catch(HibernateException e) {
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
	
	public String getClassName() {
		return entityClass.getSimpleName().toUpperCase();
	}

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}


}
