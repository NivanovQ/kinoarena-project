package com.example.kino.controllers;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kino.dto.LoginDTO;
import com.example.kino.dto.RegisterDTO;
import com.example.kino.dto.UserProfileDTO;
import com.example.kino.dto.UserReseravtionDTO;
import com.example.kino.exeptions.ExpectationFailed;
import com.example.kino.exeptions.LoginExeption;
import com.example.kino.exeptions.NoSuchElementExeption;
import com.example.kino.exeptions.UnauthroizedExeption;
import com.example.kino.model.User;
import com.example.kino.service.UserService;
import com.example.kino.service.UserService;




@RestController
public class UserController {

	private static final String SESSION_ATTRIBUTE = "userID";
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDTO user, HttpServletRequest request) throws NoSuchElementExeption {
		User loginUser = userService.login(user);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30);
		session.setAttribute(SESSION_ATTRIBUTE,	loginUser.getId());
		
	}
	
	@PostMapping("/register")
	public void register(@RequestBody RegisterDTO user) throws LoginExeption {
		userService.register(user);
	}
	
	@PostMapping("/profile")
	public UserProfileDTO getUserProfile(HttpServletRequest request , HttpServletResponse response) {
		HttpSession session  = request.getSession();
		if (session.getAttribute(SESSION_ATTRIBUTE) == null) {
			response.setStatus(401);
			return null;
		}
		long userId = (Long) session.getAttribute(SESSION_ATTRIBUTE);
		return this.userService.getUserProfile(userId);
	}
	
	@PostMapping("/logout")
	public void logOut(HttpServletRequest request , HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute(SESSION_ATTRIBUTE) == null) {
			response.setStatus(400);
			return;
		}
		session.invalidate();
	}
	
	@PostMapping("/adminRegister")
	public void registeAnADmin(@RequestBody RegisterDTO registerDTO ,HttpServletRequest request, HttpServletResponse response) throws UnauthroizedExeption, LoginExeption, ExpectationFailed {
		HttpSession session  = request.getSession();
		if(session.getAttribute(SESSION_ATTRIBUTE)== null) {
			response.setStatus(400);
			return;
		}
		long userId = (long) session.getAttribute(SESSION_ATTRIBUTE);
		this.userService.registerAnAdmin(registerDTO, userId);
	}
	
	

}
