package com.msmisa.TicketApp.dao.fan;

import java.util.List;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;
import com.msmisa.TicketApp.dto.preview.MyBidPreviewDTO;

public interface BidDao extends GenericDao<Bid, Integer> {

	List<MyBidPreviewDTO> getMyBids(String username) throws DaoException;

}
