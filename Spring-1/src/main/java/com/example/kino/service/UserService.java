package com.example.kino.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kino.dto.LoginDTO;
import com.example.kino.dto.RegisterDTO;
import com.example.kino.dto.UserProfileDTO;
import com.example.kino.dto.UserRepository;
import com.example.kino.exeptions.ExpectationFailed;
import com.example.kino.exeptions.LoginExeption;
import com.example.kino.exeptions.NoSuchElementExeption;
import com.example.kino.exeptions.UnauthroizedExeption;
import com.example.kino.model.User;

@Service
public class UserService {

	private static final String UNAUTHOROIZED_MESSAGE = "You dont have permission to do that";
	private static final String EXPECTATION_MESSAGE = "The user name or email already exists";
	private static final String LOGIN_EXCEPTION_MESSAGE = "Invalid regstration try again!";
	private static final String NO_SUCH_USER_MESSAGE = "Invalid username or password!";
	
	@Autowired
	private UserRepository userRepository;

	

	public User login(LoginDTO user1) throws NoSuchElementExeption {
		User u = null;
		try {
		return u = userRepository.findAll().stream()
				.filter(user -> user.getUserName().equals(user1.getUserName()) && 
			user.getPassword().equalsIgnoreCase(user1.getPassword())).findFirst().get();
		} catch(NoSuchElementException e) {
			throw new NoSuchElementExeption(NO_SUCH_USER_MESSAGE);
		}
		
	}



	public User register(RegisterDTO user) throws LoginExeption {
		if (validateUser(user) && checkForExistingUser(user)) {
			User newUser = new User(null, user.getFirstName(), user.getLastName(), user.getUserName(),
					user.getEmail(), user.getGSM(), user.getPassword(), false);
			this.userRepository.save(newUser);
			return newUser;
		}
		
		throw new LoginExeption(LOGIN_EXCEPTION_MESSAGE);
		
	}



	private boolean validateUser(RegisterDTO user) {
		return user.getFirstName().trim().length() > 0
				&& user.getLastName().trim().length() > 0 
				&& user.getUserName().trim().length() > 0 
				&& user.getPassword().trim().length() > 0 
				&& user.getEmail().trim().length() > 0 
				&& user.getGSM().length() == 10;
	}



	public UserProfileDTO getUserProfile(long userId) {
		User u = this.userRepository.findById(userId).get();
		return new UserProfileDTO(u.getFirstName(), u.getLastName(), u.getGSM(), u.getEmail());
	}



	public void registerAnAdmin(RegisterDTO registerDTO, long userId) throws UnauthroizedExeption, LoginExeption, ExpectationFailed {
		if(!checkForExistingUser(registerDTO)) {
			throw new ExpectationFailed(EXPECTATION_MESSAGE);
		}
		
		User user = this.userRepository.findById(userId).get();
		if(!user.isAdmin()) {
			throw new UnauthroizedExeption(UNAUTHOROIZED_MESSAGE);
		}
		if(validateUser(registerDTO)) {
			User userAdmin = new User(null, registerDTO.getFirstName(), registerDTO.getLastName(), registerDTO.getUserName(),
					registerDTO.getEmail(), registerDTO.getGSM(), registerDTO.getPassword(), true);
			this.userRepository.save(userAdmin);
			return;
		}
		throw new LoginExeption(LOGIN_EXCEPTION_MESSAGE);
		
	}



	private boolean checkForExistingUser(RegisterDTO registerDTO) {
		User user = new User(null, registerDTO.getFirstName(), registerDTO.getLastName(), registerDTO.getUserName(),
					registerDTO.getEmail(), registerDTO.getGSM(), registerDTO.getPassword(), false);
		List<User> dbUsers = this.userRepository.findAll().stream().filter(dbUser -> dbUser.getUserName().equals(user.getUserName()) 
				&& dbUser.getEmail().equals(user.getEmail())).collect(Collectors.toList());
		if(dbUsers.size() == 0) {
			return true;
		}
		return false;
		
		
	}
	
	
}
