package com.msmisa.TicketApp;


import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.msmisa.TicketApp.beans.Termin;
import com.msmisa.TicketApp.dto.preview.TerminPreviewDTO;



@SpringBootApplication
public class TicketAppApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TicketAppApplication.class, args);
	}
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper mm = new ModelMapper();
		mm.createTypeMap(Termin.class, TerminPreviewDTO.class).setPostConverter(new Converter<Termin, TerminPreviewDTO>() {
			
			@Override
			public TerminPreviewDTO convert(MappingContext<Termin, TerminPreviewDTO> context) {
				TerminPreviewDTO dto = new TerminPreviewDTO();
				dto.setId(context.getSource().getId());
				dto.setPrice(context.getSource().getPrice());
				dto.setTime(context.getSource().getTime());
				dto.setHallListNames(context.getSource().getHallList().stream().map(h -> h.getName()).collect(Collectors.toList()));
				return dto;
			}
		});
		
		return mm;
	}
	
	
	
}
