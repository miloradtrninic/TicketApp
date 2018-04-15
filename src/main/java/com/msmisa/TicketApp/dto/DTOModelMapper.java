package com.msmisa.TicketApp.dto;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.validation.constraints.NotNull;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Predicate;
import com.msmisa.TicketApp.beans.Membership;
import com.msmisa.TicketApp.dto.creation.MovieCreationDTO;

import ch.qos.logback.core.Context;

public class DTOModelMapper extends RequestResponseBodyMethodProcessor {
	private ModelMapper modelMapper = new ModelMapper();

	private SessionFactory sessionFactory;

	private EntityManager entityManager;
	
	public DTOModelMapper(ObjectMapper objectMapper, SessionFactory sessionFactory, EntityManager entityManager) {
		super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
		this.sessionFactory = sessionFactory;
		this.entityManager = entityManager;
		
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(DTO.class);
	}

	@Override
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
		binder.validate();
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		Serializable id = (Serializable) getEntityId(dto);
		if (id == null) {
			logger.info("id is null");
			logger.info(parameter.getParameterType());
			Object mapped = modelMapper.map(dto, parameter.getParameterType());
			resolveForeign(dto, mapped);
			return mapped;
		} else {
			logger.info("persisted object id is " + id);
			logger.info("class is " + parameter.getParameterType());
			Object persistedObject = entityManager.find(parameter.getParameterType(), id);
			logger.info("persisted object is " + persistedObject.getClass());
			modelMapper.map(dto, persistedObject);
			return persistedObject;
		}
	}

	@Override
	protected Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
		for (Annotation ann : parameter.getParameterAnnotations()) {
			DTO dtoType = AnnotationUtils.getAnnotation(ann, DTO.class);
			if (dtoType != null) {
				logger.info("not runtime ex" + dtoType.getClass());
				return super.readWithMessageConverters(inputMessage, parameter, dtoType.value());
			}
		}
		logger.info("runtime ex");
		throw new RuntimeException();
	}

	private Object getEntityId(@NotNull Object dto) {
		List<Field> allDTOFields = getAllFields(new ArrayList<Field>(),dto.getClass());
		logger.info("fields in getEnt: " + allDTOFields.size());
		for (Field field : allDTOFields) {
			if (field.getAnnotation(Id.class) != null) {
				try {
					logger.info("fields has id - field name" + field.getName());
					field.setAccessible(true);
					return field.get(dto);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@javax.transaction.Transactional
	public void resolveForeign(@NotNull Object dto, @NotNull Object mapped) {
		List<Field> allDTOFields = getAllFields(new ArrayList<Field>(),dto.getClass());
		logger.info("resolve foreigns size: " + allDTOFields.size());
		for (Field field : allDTOFields) {
			logger.info("checking field " + field.getName());
			ForeignKeyDTO fkAnn = field.getAnnotation(ForeignKeyDTO.class);
			logger.info("has annotation" + fkAnn);
			if (fkAnn != null) {
				try {
					logger.info(field.getName() + "has ForeignKeyDTO");
					Object key = null;
					Object value = null;
					// annotated field is list (generic)
					if(field.getGenericType() instanceof ParameterizedType){
						logger.info(field.getName() + " field is generic");
						field.setAccessible(true);
						key = field.get(dto);
						logger.info("key of generic " + key);
						/*Session session = sessionFactory.getCurrentSession();
						
						value = session
								.createCriteria(fkAnn.clazzFK())
								.add(Restrictions.in("id", (List<Integer>)key)).list();
						session.close();*/
						CriteriaBuilder cb = entityManager.getCriteriaBuilder();
						CriteriaQuery<?> query = cb.createQuery(fkAnn.clazzFK());
						Root root = query.from(fkAnn.clazzFK());
						query.select(root);
						query.where(root.get("id").in((Collection)key));
						value = entityManager.createQuery(query).getResultList();
						logger.info("generic values " + value);
					} else {
						logger.info(field.getName() + " field is simple");
						field.setAccessible(true);
						key = field.get(dto);
						logger.info("key of simple " + key);
						value = entityManager.find(fkAnn.clazzFK(), (Serializable) key);
						logger.info("simple value " + value);
					}
					List<Field> allMappedFields = getAllFields(new ArrayList<Field>(), mapped.getClass());
					for(Field fieldMapped : allMappedFields) {
						logger.info("mapped field " + fieldMapped.getName());
						Type typeMapped = fieldMapped.getType();
						// if filed in mapped class is generic (list)
						if(fieldMapped.getGenericType() instanceof ParameterizedType) {
							ParameterizedType pType = (ParameterizedType)fieldMapped.getGenericType();
							typeMapped = pType.getActualTypeArguments()[0];
							logger.info("mapped field is generic " +typeMapped.getTypeName());
						} 
						// check if class matches with annotation
						logger.info("checking if field " + fieldMapped.getName() + " matches " + fkAnn.clazzFK());
						if(typeMapped.equals(fkAnn.clazzFK())) {
							logger.info("same class " +typeMapped.getTypeName());
							logger.info("setting values for mapped field " + fieldMapped.getName() + " value " + value);
							fieldMapped.setAccessible(true);
							fieldMapped.set(mapped, value);
							break;
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
}

