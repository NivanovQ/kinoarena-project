package com.example.kino.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.kino.model.UserReservation;

@Repository
public interface UserReseravationRepository extends JpaRepository<UserReservation, Long> {
	
	public static final String USER_RESERVATION_QUERY_BY_USER_AND_PROJECTION = "select * from user_reservation where user_id = ?1 and projection_id = ?2 ";

	@Query(value= USER_RESERVATION_QUERY_BY_USER_AND_PROJECTION,nativeQuery = true)
	List<UserReservation> findByUserIdProjectionId(Long userId, Long projectionId);

	
}
