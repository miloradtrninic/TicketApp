package com.msmisa.TicketApp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Termin;
import com.msmisa.TicketApp.dto.preview.DirectorPreviewDTO;
import com.msmisa.TicketApp.dto.preview.GenrePreviewDTO;
import com.msmisa.TicketApp.dto.preview.HallPreviewDTO;
import com.msmisa.TicketApp.dto.preview.HallSegmentPreviewDTO;
import com.msmisa.TicketApp.dto.preview.ProjectionPreviewDTO;
import com.msmisa.TicketApp.dto.preview.TerminPreviewDTO;



@SpringBootApplication
@EnableTransactionManagement
public class TicketAppApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TicketAppApplication.class, args);
	}
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private EntityManagerFactory entityManagerFactory;

    @Autowired
    private DataSource dataSource;
	
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



    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        tm.setDataSource(dataSource);
        return tm;
    }
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper mm = new ModelMapper();
		mm.createTypeMap(Hall.class, HallPreviewDTO.class).setPostConverter(new Converter<Hall, HallPreviewDTO>() {
			
			@Override
			public HallPreviewDTO convert(MappingContext<Hall, HallPreviewDTO> context) {
				HallPreviewDTO dto = new HallPreviewDTO();
				logger.info("converting hall");
				dto.setId(context.getSource().getId());
				dto.setName(context.getSource().getName());
				dto.setAuditoriumName(context.getSource().getAuditorium().getName());
				List<HallSegmentPreviewDTO> segments = context.getSource().getHallSegmentList()
						.stream()
						.map(e -> mm.map(e, HallSegmentPreviewDTO.class))
						.collect(Collectors.toList());
				dto.setHallSegmentList(segments);
				return dto;
			}
		});
		
		
		mm.createTypeMap(Termin.class, TerminPreviewDTO.class).setPreConverter(new Converter<Termin, TerminPreviewDTO>() {
			
			@Override
			public TerminPreviewDTO convert(MappingContext<Termin, TerminPreviewDTO> context) {
				TerminPreviewDTO dto = new TerminPreviewDTO();
				logger.info("converting termin");
				logger.info("id is " + context.getSource().getId());
				dto.setId(context.getSource().getId());
				dto.setPrice(context.getSource().getPrice());
				
				logger.info(context.getSource().getHallList().size());
				context.getSource().getHallList().forEach(h -> logger.info(h.getName()));
				List<String> halls = context.getSource().getHallList().stream().map(h -> h.getName()).collect(Collectors.toList());
				
				dto.setHallNames(halls);
				logger.info("halls : " + dto.getHallNames());
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
