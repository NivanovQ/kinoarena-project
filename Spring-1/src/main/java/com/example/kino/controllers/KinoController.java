package com.example.kino.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.kino.dao.KinoDao;
import com.example.kino.dto.AddProjectionDTO;
import com.example.kino.dto.GenreDTO;
import com.example.kino.dto.HomePageKinoDto;
import com.example.kino.dto.ProjectionByIdDTO;
import com.example.kino.dto.ProjectionsDTO;

import com.example.kino.dto.TicketDTO;
import com.example.kino.dto.UserReseravtionDTO;
import com.example.kino.exeptions.ExpectationFailed;
import com.example.kino.exeptions.HomePageExeption;
import com.example.kino.exeptions.InvalidHallExeption;
import com.example.kino.exeptions.NoSuchElementExeption;
import com.example.kino.exeptions.ProjectionAlreadyExistExeption;
import com.example.kino.exeptions.UnauthroizedExeption;
import com.example.kino.model.Kino;
import com.example.kino.model.Ticket;

import com.example.kino.service.KinoService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@RestController
public class KinoController {

	private static final String SESSION_ATTRIBUTE = "userID";

	private static final String UNAUTHROIZED_MESSAGE = "Please log in first!";

	@Autowired
	private KinoDao kinoDao;
	
	@Autowired
	private KinoService kinoSerivce;
	
	
	@GetMapping("/test")
	public void test() throws ProjectionAlreadyExistExeption, InvalidHallExeption, SQLException {
		this.kinoSerivce.test();
	}
	
	@GetMapping("")
	public HomePageKinoDto homePage() throws HomePageExeption {
	
		return kinoDao.homePage();
	}

	@GetMapping("/projections")
	public List<ProjectionsDTO> getAllProjections(@RequestParam(name="sortBy", required = false) String sortBy, 
			@RequestParam(name="date" , required = false) String date,
			@RequestParam(name="time", required = false) String time,
			@RequestParam(name="hall" , required = false)String hall,
			@RequestParam(name="description",required = false) String description ,
			@RequestParam(name="genre", required = false)String genre,
			@RequestParam(name="durationInMinutes", required = false)String duration,
			@RequestParam(name="isPrimiery", required = false) String isPrimiery) {
		return this.kinoSerivce.getAllProjections(sortBy,date,time,hall,description,genre,duration,isPrimiery);
	}
	
	@GetMapping("/projections/{id}")
	public ProjectionByIdDTO getProjectionById(@PathVariable long id , HttpSession session ) throws UnauthroizedExeption, NoSuchElementExeption, SQLException {
		if (session.getAttribute(SESSION_ATTRIBUTE) == null) {
			throw new UnauthroizedExeption(UNAUTHROIZED_MESSAGE);
		}
		return this.kinoSerivce.getProjectionById(id);
		
	}
	
	@GetMapping("/tickets")
	public List<TicketDTO> getAllPricecForTickets() {
		return this.kinoSerivce.getTickets();
	}
	
	@PostMapping("/addProjection")
	public void addNewProjectionDTO(@RequestBody  AddProjectionDTO newProjection , HttpServletRequest request , 
			HttpServletResponse response) throws UnauthroizedExeption, ProjectionAlreadyExistExeption, InvalidHallExeption {
		HttpSession session = request.getSession();
		if (this.kinoSerivce.validate(session)) {
			this.kinoSerivce.addNewProjection(newProjection);
		}
	}
	
	@GetMapping("/reserveProjectionAndSeat")
	public void reservation(@RequestParam(name="projectionId", required = false) Long projectionId,
			@RequestParam(name="numberOfSeat", required = false) Long seatId, HttpServletRequest request) throws UnauthroizedExeption, ExpectationFailed {
		HttpSession session = request.getSession();
		if (session.getAttribute(SESSION_ATTRIBUTE) == null) {
			throw new UnauthroizedExeption(UNAUTHROIZED_MESSAGE);
		}
		Long userId = (Long) session.getAttribute(SESSION_ATTRIBUTE);
		this.kinoSerivce.makeReservation(projectionId,seatId,userId);
	}
	
	@GetMapping("/myReservations") 
	public List<UserReseravtionDTO> getUserReservations(HttpServletRequest request) throws UnauthroizedExeption {
		HttpSession session = request.getSession();
		if (session.getAttribute(SESSION_ATTRIBUTE) == null) {
			throw new UnauthroizedExeption(UNAUTHROIZED_MESSAGE);
		}
		Long userId = (Long) session.getAttribute(SESSION_ATTRIBUTE);
		return this.kinoSerivce.getUserReservations(userId); 
	}
	
	@GetMapping("/genres")
	public List<GenreDTO> getAllGenres() {
		return this.kinoSerivce.getAllGenres();
		
	}

}
