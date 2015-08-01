package com.sapient.cabbooking;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sapient.cabbooking.engine.CabBookingEngine;
import com.sapient.cabbooking.model.BookingRequest;
import com.sapient.cabbooking.model.Cab;

/**
 * @author Abdul Sayeed
 *
 */
public class SapientCabBookingSystem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<BookingRequest> request = new LinkedList<BookingRequest>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 2);
		request.add(new BookingRequest("BR001",new Long(100025),new Long(100036),calendar.getTime()));
		calendar.set(Calendar.HOUR, 3);
		request.add(new BookingRequest("BR002",new Long(100056),new Long(100042),calendar.getTime()));
		calendar.set(Calendar.HOUR, 4);
		request.add(new BookingRequest("BR003",new Long(100044),new Long(100056),calendar.getTime()));
		calendar.set(Calendar.HOUR, 5);
		request.add(new BookingRequest("BR004",new Long(100028),new Long(100036),calendar.getTime()));
		
//		request.add(new BookingRequest("BR005",new Long(100025),new Long(100036),calendar.getTime()));
//		request.add(new BookingRequest("BR006",new Long(100025),new Long(100036),calendar.getTime()));

		List<Cab> availableCab = new LinkedList<Cab>();
		availableCab.add(new Cab("DL01HB001",new Long(100020)));
		availableCab.add(new Cab("DL01HB002",new Long(100040)));		
		availableCab.add(new Cab("DL01HB003",new Long(100060)));
		availableCab.add(new Cab("DL01HB004",new Long(100080)));		
		
		CabBookingEngine engine = new CabBookingEngine(request,availableCab);
		Map<String, BookingRequest> bookingStatusMap = engine.processBooking();
		System.out.println("Booking Id\tPickup Area\tDrop Area\tPickUp Time\t\t\tCab\t\tStatus");
		for (BookingRequest request2: bookingStatusMap.values()) {
			System.out.println(request2.getRequestId()+"\t\t"+request2.getPickUpArea()+"\t\t"+request2.getDropArea()+"\t\t"+
					request2.getPickupTime()+"\t"+((request2.getCab()!= null)?request2.getCab().getCabNo():"xxxxxxxx")+"\t"+request2.getStatus());
		}
	}

}
