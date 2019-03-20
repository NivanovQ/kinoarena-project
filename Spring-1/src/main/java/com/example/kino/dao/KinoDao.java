package com.example.kino.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.kino.dto.HomePageKinoDto;
import com.example.kino.dto.ProjectionsDTO;
import com.example.kino.dto.ProjectionsRepostiory;
import com.example.kino.exeptions.HomePageExeption;
import com.example.kino.exeptions.InvalidHallExeption;
import com.example.kino.model.Kino;
import com.example.kino.model.Projection;

@Component
public class KinoDao {
	
	private static final String TEST_QUERY = "insert into kino values (null,'Some address','Some name',10,10)";
	private static final String HALL_EXCEPTION_MESSAGE = "Invalid number of hall!";
	private static final String HOME_PAGE_MESSAGE = "Something gone wrong contact with our administration";
	private static final String SELECT_FROM_KINO = "select * from kino";
	private static final String SELECT_NUMBER_OF_HALLS_FROM_KINO = "select number_of_halls from kino";
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	
	
	@Autowired
	private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public HomePageKinoDto homePage() throws HomePageExeption {
		Connection con = null;
		HomePageKinoDto homePage = null;
		try {
			con = this.jdbcTemplate.getDataSource().getConnection();
			ResultSet result = con.createStatement().executeQuery(SELECT_FROM_KINO);
			while (result.next()) {
				homePage = new HomePageKinoDto(result.getString(3),result.getString(2), result.getInt(4), result.getInt(5));
			}
		} catch (SQLException e) {
			throw new HomePageExeption(HOME_PAGE_MESSAGE);
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return homePage;
		
	}

	public void checkHalls(int hallOfDto) throws InvalidHallExeption {
		Connection con = null; 
		try {
			con = this.jdbcTemplate.getDataSource().getConnection();
			ResultSet result = con.createStatement().executeQuery(SELECT_NUMBER_OF_HALLS_FROM_KINO);
			while (result.next()) {
				Integer hall = result.getInt(1);
				if(hall < hallOfDto) {
					
					throw new InvalidHallExeption(HALL_EXCEPTION_MESSAGE);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		}
		
	}
	
	/**
	 * Test method , automatically adds information to the database
	 */
	public void testAdd() throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		con.createStatement().executeUpdate(TEST_QUERY);
	}

	
	
	
}
