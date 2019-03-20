package com.example.kino.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.kino.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	public static final String RESERVATION_QUERY_BY_USER = "select * from reservation where user_id=?1";
	public static final String RESERVATION_QUERY_BY_PROJECTION_AND_USER = "select * from reservation where projection_id = ?1 and user_id = ?2";

	@Query(value = RESERVATION_QUERY_BY_PROJECTION_AND_USER, nativeQuery = true)
	List<Reservation> findByProjectionAndUserId(long projectionId, long userId);
	
	@Query(value = RESERVATION_QUERY_BY_USER, nativeQuery = true)
	List<Reservation> findByUserIdAllReservations(long userId);
}
