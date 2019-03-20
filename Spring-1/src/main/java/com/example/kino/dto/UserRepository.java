package com.example.kino.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kino.model.User;
import com.example.kino.model.UserReservation;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
