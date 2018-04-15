package com.msmisa.TicketApp;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Termin;
import com.msmisa.TicketApp.dto.preview.DirectorPreviewDTO;
import com.msmisa.TicketApp.dto.preview.GenrePreviewDTO;
import com.msmisa.TicketApp.dto.preview.MoviePreviewDTO;
import com.msmisa.TicketApp.dto.preview.ProjectionPreviewDTO;
import com.msmisa.TicketApp.dto.preview.TerminPreviewDTO;



@SpringBootApplication
@EnableTransactionManagement
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
	    SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
	    try {
	    	sessionFactory.getCurrentSession();
	    } catch (HibernateException e) {
	    	sessionFactory.openSession();
	    }
	    return sessionFactory;
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
		mm.createTypeMap(Projection.class, ProjectionPreviewDTO.class).setPostConverter(new Converter<Projection, ProjectionPreviewDTO>() {
			@Override
			public ProjectionPreviewDTO convert(MappingContext<Projection, ProjectionPreviewDTO> context) {
				ProjectionPreviewDTO dto = new ProjectionPreviewDTO();
				dto.setId(context.getSource().getId());
				dto.setCoverPath(context.getSource().getCoverPath());
				dto.setDescription(context.getSource().getDescription());
				dto.setDirector(mm.map(context.getSource().getDirector(), DirectorPreviewDTO.class));
				dto.setDurationMinutes(context.getSource().getDurationMinutes());
				List<GenrePreviewDTO> genres = context.getSource().getGenres().stream().map(e -> mm.map(e, GenrePreviewDTO.class)).collect(Collectors.toList());
				dto.setGenres(genres);
				dto.setName(context.getSource().getName());
				dto.setRatings(context.getSource().getRatings());
				List<TerminPreviewDTO> time = context.getSource().getProjectionTime().stream().map(e -> mm.map(e, TerminPreviewDTO.class)).collect(Collectors.toList());
				dto.setProjectionTime(time);
				return dto;
			}
		});
		
		
		
		return mm;
	}
	
	
	
}
