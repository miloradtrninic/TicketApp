package com.msmisa.TicketApp.dao.fan;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dto.preview.MyBidPreviewDTO;

@Repository
@Transactional
public class BidDaoImpl extends AbstractGenericDao<Bid, Integer> implements BidDao {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	public BidDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<MyBidPreviewDTO> getMyBids(String username) throws DaoException{
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("user.username"), "fromUserUsername");
		proList.add(Projections.property("this.id"), "id");
		proList.add(Projections.property("this.offerDate"), "offerDate");
		proList.add(Projections.property("this.offer"), "offer");
		proList.add(Projections.property("ad.name"), "fanAdName");
		List<MyBidPreviewDTO> myBidsPrev = getSessionFactory().getCurrentSession()
											.createCriteria(Bid.class)
											.createAlias("fromUser", "user")
											.createAlias("fanAd", "ad")
											.add(Restrictions.eq("user.username", username))
											.add(Restrictions.isNull("ad.acceptedBid"))
											.setProjection(proList)
											.setResultTransformer(Transformers.aliasToBean(MyBidPreviewDTO.class))
											.list();
		/*List<MyBidPreviewDTO> myBidsPrev = myBids.stream()
										.map(bid -> mapper.map(bid, MyBidPreviewDTO.class))
										.collect(Collectors.toList());*/
		return myBidsPrev;
	}

}
