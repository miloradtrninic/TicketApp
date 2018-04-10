package com.msmisa.TicketApp.dto;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DTOModelMapper extends RequestResponseBodyMethodProcessor {
	private ModelMapper modelMapper = new ModelMapper();

	private SessionFactory sessionFactory;

	public DTOModelMapper(ObjectMapper objectMapper, SessionFactory sessionFactory) {
		super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
		this.sessionFactory = sessionFactory;
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
			Object persistedObject = sessionFactory.getCurrentSession().get(parameter.getParameterType(), id);

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
		for (Field field : dto.getClass().getDeclaredFields()) {
			if (field.getAnnotation(Id.class) != null) {
				try {
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
	private void resolveForeign(@NotNull Object dto, @NotNull Object mapped) {
		for (Field field : dto.getClass().getDeclaredFields()) {
			ForeignKeyDTO fkAnn = field.getAnnotation(ForeignKeyDTO.class);
			if (field.getAnnotation(ForeignKeyDTO.class) != null) {
				try {
					Object key = null;
					Object value = null;
					// annotated field is list (generic)
					if(field.getGenericType() instanceof ParameterizedType){
						key = field.get(dto);
						value = sessionFactory
								.getCurrentSession()
								.createCriteria(fkAnn.clazzFK())
								.add(Restrictions.in("id", (List<Integer>)key)).list();
					} else {
						key = field.get(dto);
						value = sessionFactory.getCurrentSession().get(fkAnn.clazzFK(), (Serializable) key);
					}
					for(Field fieldMapped : mapped.getClass().getDeclaredFields()) {
						Type typeMapped = fieldMapped.getType();
						// if filed in mapped class is generic (list)
						if(fieldMapped.getGenericType() instanceof ParameterizedType) {
							ParameterizedType pType = (ParameterizedType)fieldMapped.getGenericType();
							typeMapped = pType.getActualTypeArguments()[0];
						} 
						// check if class matches with annotation
						if(typeMapped.equals(fkAnn.clazzFK())) {
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
}

