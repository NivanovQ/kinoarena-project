package com.example.kino.service;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.kino.dao.KinoDao;
import com.example.kino.dto.AddProjectionDTO;
import com.example.kino.dto.GenreDTO;
import com.example.kino.dto.GenreRepository;
import com.example.kino.dto.InfoRepository;
import com.example.kino.dto.ProjectionByIdDTO;
import com.example.kino.dto.ProjectionsDTO;
import com.example.kino.dto.ProjectionsRepostiory;
import com.example.kino.dto.ReservationRepository;
import com.example.kino.dto.SeatDTO;
import com.example.kino.dto.SeatRepository;
import com.example.kino.dto.TicketDTO;
import com.example.kino.dto.TicketRepository;
import com.example.kino.dto.UserRepository;
import com.example.kino.dto.UserReseravationRepository;
import com.example.kino.dto.UserReseravtionDTO;

import com.example.kino.exeptions.ExpectationFailed;
import com.example.kino.exeptions.InvalidHallExeption;
import com.example.kino.exeptions.NoSuchElementExeption;
import com.example.kino.exeptions.ProjectionAlreadyExistExeption;
import com.example.kino.exeptions.UnauthroizedExeption;
import com.example.kino.model.Genre;
import com.example.kino.model.Info;
import com.example.kino.model.Projection;
import com.example.kino.model.Reservation;
import com.example.kino.model.Seat;
import com.example.kino.model.Ticket;
import com.example.kino.model.User;
import com.example.kino.model.UserReservation;

@Service
public class KinoService { 

	
	private static final String EXPECTATION_MESSAGE = "Projection or Seat is unavailable or Seat is reserved!";

	private static final String PROJECTION_EXCEPTION_MESSAGE = "This projection already exists";

	private static final String SESSION_ATTRIBUTE = "userID";

	private static final String UNAUTHORIZED_MESSAGE_2 = "You dont have permission to do that!";

	private static final String UNAUTHROZIED_MESSAGE = "You have to log in!";

	private static final String NO_SUCH_ELEMENT_EXCEPTION_MESSAGE = "No such film!";

	@Autowired
	private ProjectionsRepostiory projectionRepostiory;
	
	@Autowired
	private InfoRepository infoRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserReseravationRepository userReservationRepository;
	
	@Autowired
	private KinoDao kinoDao;
	
	
	/**
	 * 
	 * @param sortBy date , time , hall , description , genre , duration or by primiery
	 * @return list of projections dto objects;
	 */
	public List<ProjectionsDTO> getAllProjections(String sortBy, String date, String time, String hall, 
			String description, String genre, String duration, String isPrimiery) {
		
		return this.projectionRepostiory.findAll()
				.stream()
				.sorted((p1,p2) -> {
					if(sortBy == null) return 1;
					switch (sortBy) {
					case "date": return p1.getDate().compareTo(p2.getDate());
					case "time" : return p1.getTime().compareTo(p2.getTime());
					case "hall" : return p1.getHall() - p2.getHall();
					case "description" : return p1.getInfo().getDescription().compareTo(p2.getInfo().getDescription());
					case "genre" : return p1.getInfo().getGenre().getGenre().compareTo(p2.getInfo().getGenre().getGenre());
					case "duration" : return p1.getDurationInMinutes().compareTo(p2.getDurationInMinutes());
					case "isPrimiery" : if (p1.isPrimiery()) {
						return 1;
					} else {
						return -1;
					}
					default:
						return 1;
					}
				})
				.map(projection -> 
				new ProjectionsDTO(projection.getId(),projection.getName(),projection.getDate(),projection.getTime(),
						projection.getHall(), projection.getInfo().getDescription(),
						projection.getInfo().getGenre().getGenre(),projection.isPrimiery(),projection.getDurationInMinutes()))
				.collect(Collectors.toList());
				
	}

	
	/**
	 * 
	 * @param id - The id of projection stored in datebase
	 * @return projection dto object
	 * @throws NoSuchElementExeption
	 * @throws SQLException
	 */
	public ProjectionByIdDTO getProjectionById(long id) throws NoSuchElementExeption, SQLException {
		try {
			Projection projection = this.projectionRepostiory.findById(id).get();
			
			return new ProjectionByIdDTO(projection.getId(),projection.getName(), projection.getDate(),
					projection.getTime(), projection.getHall(), projection.getInfo().getDescription(),
					projection.getInfo().getGenre().getGenre(), projection.isPrimiery(), projection.getDurationInMinutes(), seatsDTO(id));
		
		} catch (NoSuchElementException e) {
			throw new NoSuchElementExeption(NO_SUCH_ELEMENT_EXCEPTION_MESSAGE);
		}
		 
	}
	
	
	
	private List<SeatDTO> seatsDTO(long id){
		List<Seat> seats =  seatRepository.findAll();
		return seats.stream()
				.filter(seat -> seat.getProjection().getId() == id)
				.map(seat -> new SeatDTO(seat.getNumberOfPlace(), seat.getReservation() == null ? "Not Reserved" : "Reserved"))
						.collect(Collectors.toList());
	}


	/**
	 * 
	 * @return Tickets stored in datebase with price Regular or Premiered depends on price;
	 */
	public List<TicketDTO> getTickets() {
		return this.ticketRepository.findAll()
				.stream()
				.map(t -> new TicketDTO(t.isPrimiery() == false ? "Regular" : "Premiered", t.getPrice()))
				.collect(Collectors.toList());
				
	
	}


	/**
	 * 
	 * @param session - the session of user who is trying to do something
	 * @return - true/false depending if  the user is log in
	 * @throws UnauthroizedExeption - if the the user did not log in
	 */
	public boolean validate(HttpSession session) throws UnauthroizedExeption {
		if (session.getAttribute(SESSION_ATTRIBUTE) == null) {
			throw new UnauthroizedExeption(UNAUTHROZIED_MESSAGE);
		}
		long userID = (Long) session.getAttribute(SESSION_ATTRIBUTE);
		
		User u = this.userRepository.findById(userID).get();
		if (u.isAdmin()) {
			return true;
		}
		throw new UnauthroizedExeption(UNAUTHORIZED_MESSAGE_2);
		
	}

	
	/**
	 * 
	 * @param newProjection - new Projection dto with description written by user
	 * @throws ProjectionAlreadyExistExeption - if the projection with this description is already in datebase
	 * @throws InvalidHallExeption - if user is trying to add projection with invalid hall
	 */
	public void addNewProjection(AddProjectionDTO newProjection) throws ProjectionAlreadyExistExeption, InvalidHallExeption {
		long size = this.projectionRepostiory.findAll().stream()
				.filter(projection -> projection.getDate().equals(newProjection.getDate())
						&& projection.getHall().equals(newProjection.getHall()))
				.collect(Collectors.toList()).size();

		if (size != 0) {
			throw new ProjectionAlreadyExistExeption(PROJECTION_EXCEPTION_MESSAGE);
		}
		
		int hallOfDto = newProjection.getHall();
		this.kinoDao.checkHalls(hallOfDto);
	
		
		Genre genre = this.genreRepository.findByGenre(newProjection.getGenre());
		Info info = this.infoRepository.findByDescription(newProjection.getDescription());
		Projection projection = new Projection(null,newProjection.getName(), newProjection.getDate(), newProjection.getTime(),
				(info == null
						? new Info(null, newProjection.getDescription(),
								genre == null ? new Genre(null, newProjection.getGenre())
										: this.genreRepository.findById(genre.getId()).get())
						: this.infoRepository.findById(info.getId()).get()),
				newProjection.getAllSeats(), newProjection.getDurationInMinutes(), newProjection.getIsPrimiery(),
				newProjection.getHall());
		this.projectionRepostiory.save(projection);
		Projection projectionSeatId = this.projectionRepostiory.findById(projection.getId()).get();
		for (int i = 1; i <= projection.getAllSeats(); i++) {
			Seat seat = new Seat(null, i, null, projectionSeatId);
			this.seatRepository.save(seat);
		}

	}


	/**
	 * 
	 * @param projectionId - id of projection stored in datebase
	 * @param seatId - the number of every seat stored in datebase
	 * @param userId - id of user who is trying to make reservation
	 * @throws ExpectationFailed - if projection or seatId  are invalid
	 */
	public void makeReservation(Long projectionId, Long seatId, Long userId) throws ExpectationFailed {
		validate(projectionId, seatId);
		checkforReserved(seatId,projectionId);
		
		
		List<Reservation> reservations = this.reservationRepository.findByProjectionAndUserId(projectionId, userId);
		
		if(reservations.size() == 0) {
			createReservation(projectionId, seatId, userId);
		} else {
			
			addToReservation(reservations.get(0).getId(),projectionId, seatId, userId);
		}
			
		

	}






	/**
	 * If the user has a previous reservation for this projection
	 * @param reservationId - id of the new user reservation 
	 * @param projectionId - id of projection user is trying to reserve
	 * @param number_of_place - the number as Integer of seat the user is trying to reserve
	 * @param userId
	 */
	private void addToReservation(Long reservationId, Long projectionId, Long number_of_place, Long userId) {
		Seat dbSeat = this.seatRepository.findBySeatId(number_of_place, projectionId);
		this.seatRepository.updateSeat(reservationId, number_of_place, dbSeat.getSeatId());
		saveToUserReservation(projectionId, userId, this.reservationRepository.findById(reservationId).get());
		
	}


	/**
	 * If the user does not have a previous reservation on this projection
	 * @param projectionId - id of projection the user is trying to reserve
	 * @param seatId - the number of seat the user is trying to reserve
	 * @param userId - id of user
	 */
	private void createReservation(Long projectionId, Long seatId, Long userId) {
		
		Reservation reservation = new Reservation(null, 
				this.projectionRepostiory.findById(projectionId).get(),
				this.userRepository.findById(userId).get());
		this.reservationRepository.save(reservation);
		
		
		
		Reservation dbReservation =  this.reservationRepository.findByProjectionAndUserId(projectionId, userId)
				.stream()
				.filter(reservation2 -> reservation2.getProjection().getId().equals(projectionId) 
						&& reservation2.getUser().getId().equals(userId)).findFirst().get();
		Long dbReservationId = dbReservation.getId();
		Seat dbSeat = this.seatRepository.findBySeatId(seatId, projectionId);
		
		
		this.seatRepository.updateSeat(dbReservationId, seatId, dbSeat.getSeatId());
		
		
		saveToUserReservation(projectionId, userId, dbReservation);
		
		
	}


	/**
	 * 
	 * @param projectionId - id of projection user is trying to reserve
	 * @param userId - id of user
	 * @param dbReservation - Reservation stored in datebase
	 */
	private void saveToUserReservation(Long projectionId, Long userId, Reservation dbReservation) {
		
		UserReservation userReservation = new UserReservation(null, this.projectionRepostiory.findById(projectionId).get(),
				(dbReservation.getProjection().isPrimiery() == true ? this.ticketRepository.findById((long)2).get() :
					this.ticketRepository.findById((long) 1).get()),this.userRepository.findById(userId).get());
		
		this.userReservationRepository.save(userReservation);
	}


	/**
	 * Checking seat and projection stored in datebase if the are available
	 * @param seatId - the number of seat  the user is trying to reserve
	 * @param projectionId - id of projection the users is trying to reserv e
	 * @throws ExpectationFailed - if seat or project are unavailable
	 */
	private void checkforReserved(Long seatId, Long projectionId) throws ExpectationFailed {
		
		Seat seat =this.seatRepository.findBySeatId(seatId,projectionId);
		if (seat.getReservation() != null) 
			throw new ExpectationFailed(EXPECTATION_MESSAGE);
		
	}


	/**
	 * Checking and validating projectionId and seatId 
	 * @param projectionId - the id of projection the user is trying to reserve 
	 * @param seatId - the id of seat the user is trying to reserve
	 * @throws ExpectationFailed - if in datebase is no projection or seat with this id , or they are ununavailable
	 */
	private void validate(Long projectionId, Long seatId) throws ExpectationFailed {
		if (!(projectionCheck(projectionId)))
			throw new ExpectationFailed(EXPECTATION_MESSAGE);
		
		if (!(seatCheck(seatId,projectionId))) 
			throw new ExpectationFailed(EXPECTATION_MESSAGE);
			
	}



	private boolean projectionCheck(Long projectionId) {
		if(projectionId == null) {
			
			return false;
		}
		
		boolean projection = this.projectionRepostiory.findById(projectionId).isPresent();
		
		if (!projection)  {
			
			return false;
		}
		return true;
	}



	private boolean seatCheck(Long seatId, Long projectionId) {
		if (seatId == null) 
			return false;
		
		Seat seat = this.seatRepository.findBySeatId(seatId,projectionId);
		
		if(seat == null) {
			
			return false;
		}
		
		return true;
	}


	/**
	 * 
	 * @param userId - id of user from his session
	 * @return - List of UserReservation dto objects stored in datebase
	 */
	public List<UserReseravtionDTO> getUserReservations(Long userId) {
		List<Reservation> activeReservations = this.reservationRepository.findByUserIdAllReservations(userId);
		List<UserReseravtionDTO> userReservations = new ArrayList<UserReseravtionDTO>();
		List<Seat> reservedSeatsByThisUserFromDB = new ArrayList<Seat>();
		
		for (int i = 0; i < activeReservations.size(); i++) {
			ProjectionsDTO projectionDTO = null;
			try {
				Projection projection = this.projectionRepostiory
						.findById(activeReservations.get(i).getProjection().getId()).get();
				projectionDTO = new ProjectionsDTO(null,projection.getName(), projection.getDate(), projection.getTime(),
						projection.getHall(), projection.getInfo().getDescription(),
						projection.getInfo().getGenre().getGenre(), projection.isPrimiery(),
						projection.getDurationInMinutes());
			} catch (NoSuchElementException e) {
				return null;
			}

			
			Set<String> reservedSeatsByThisUser = new HashSet<String>();
			reservedSeatsByThisUserFromDB = this.seatRepository
					.findByReservationIdAllSeats(activeReservations.get(i).getId());
			reservedSeatsByThisUserFromDB
					.forEach(seat -> reservedSeatsByThisUser.add(Integer.toString(seat.getNumberOfPlace())));
			
			List<UserReservation> ticket= this.userReservationRepository.findByUserIdProjectionId(userId,activeReservations.get(i).getProjection().getId());
			Integer ticketPrice = ticket.get(0).getTicket().getPrice();
			
			userReservations.add(new UserReseravtionDTO(activeReservations.get(i).getProjection().getId(),
					projectionDTO.getName(),
					projectionDTO.getDate(), projectionDTO.getTime(), projectionDTO.getHall(),
					projectionDTO.getDescription(), projectionDTO.getGenre(), projectionDTO.isPrimiery(),
					projectionDTO.getDurationInMinutes(),ticketPrice, reservedSeatsByThisUser));

		}

		return userReservations;
	}


	/**
	 * 
	 * @return - Genres dto object stored in datebase
	 */
	public List<GenreDTO> getAllGenres() {
		return this.genreRepository.findAll().stream().map(genre -> new GenreDTO(genre.getGenre())).collect(Collectors.toList());
		
	}

	
	
	/**
	 * Test method , automatically adds information to the database
	 */
	public void test() throws ProjectionAlreadyExistExeption, InvalidHallExeption, SQLException {
		User admin = new User(null, "TestAdmin", "TestAdmin", "admin", "testadmin@admin", "0000", "admin", true);
		this.userRepository.save(admin);
		
		Ticket ticket1 = new Ticket(null, 10, false);
		Ticket ticket2 = new Ticket(null, 20, true);
		this.ticketRepository.save(ticket1);
		this.ticketRepository.save(ticket2);
		
		this.kinoDao.testAdd();
		
		LocalDate from = LocalDate.of(2018, 4, 1);
		LocalDate to = LocalDate.of(2018, 12, 30);
		Random generator = new Random();
		String[] durations = {"60","120","180","150"}; 
	
		
		for (int i =1; i<= 10;i++) {
		long days = from.until(to, ChronoUnit.DAYS);
		long randomDays = ThreadLocalRandom.current().nextLong(days + 1);	
		LocalTime time = LocalTime.MIN.plusSeconds(generator.nextLong());
		LocalDate randomDate = from.plusDays(randomDays); 
		
		
		
		this.addNewProjection(new AddProjectionDTO("Film"+i, 
				randomDate, time, i, new Random().nextInt(50)+30, 
				durations[new Random().nextInt(durations.length)],new Random().nextBoolean(),"Description"+i,
				"Genre"+(new Random().nextInt(4)+1)));
		
		
		}
	}
	
	

	




	
}
