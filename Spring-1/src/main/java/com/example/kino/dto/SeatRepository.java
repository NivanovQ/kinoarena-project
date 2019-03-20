package com.example.kino.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.kino.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
	
	public static final String SEAT_QUERY_BY_RESERVATION_WITH_NUMBER_OF_PLACE = "update seats set reservation_id =?1 where number_of_place =?2 and seat_id =?3 ";
	public static final String SEAT_RESERVATION_BY_RESERVATION = "select * from seats where reservation_id = ?1";
	public static final String SEAT_QUERY_BY_NUMBER_OF_PLACE = "SELECT * FROM SEATS WHERE number_of_place = ?1 and projection_id = ?2";


	@Query(value = SEAT_QUERY_BY_NUMBER_OF_PLACE, nativeQuery = true)
	Seat findBySeatId(long id,long projectionId);
	
	@Query(value =SEAT_RESERVATION_BY_RESERVATION , nativeQuery = true)
	List<Seat> findByReservationIdAllSeats(long reservationId);
	

	@Transactional
	@Modifying
	@Query(value = SEAT_QUERY_BY_RESERVATION_WITH_NUMBER_OF_PLACE , nativeQuery = true)
	int updateSeat(Long dbReservationId, Long number_of_place, Long seatId);
	


}
