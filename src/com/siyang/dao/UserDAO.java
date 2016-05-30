package com.siyang.dao;

import java.util.List;

import com.siyang.registration.Person;

public interface UserDAO {
	
	public static final String collectionName="registration";
	public void addUsers(List<Person> persons);
	public List<Person> getAllUsers();
	public void updateUsers(List<Person> persons);
}
