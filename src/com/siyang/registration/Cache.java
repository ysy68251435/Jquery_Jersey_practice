package com.siyang.registration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siyang.dao.UserDAO;

@Component
public class Cache {
	
	private static HashSet<Person> cache=new HashSet<Person>();
	
	@Autowired
	private UserDAO userDAO;
	
	public void load(){
		List<Person> persons=userDAO.getAllUsers();
		cache.clear();
		for(Person p: persons){
			cache.add(p);
		}
	}
	
	public void store(List<Person> persons){
		System.out.println("cache store");
		for(Person p: persons){
			cache.add(p);
		}
	}
	
	public void addUser(Person person){
		cache.add(person);
	}
	
	public void update(List<Person> persons){
		System.out.println("cache update");
		cache.clear();
		for(Person p:persons){
			cache.add(p);
		}
	}
	public List<Person> get(){
		System.out.println("cache get");
		List<Person> persons=new ArrayList<Person>();
		for(Person p: cache){
			persons.add(p);
		}
		return persons;
	}
	
	public boolean exist(Person p){
		return cache.contains(p);
	}
	
	public void clear(){
		cache.clear();
	}
	
	public void display(){
		System.out.println("Cache: "+cache.size());
		for(Person p: cache){
			System.out.println(p.getWeChatId()+" "+
							   p.getName()+" "+p.getBirth());
		}
	}
}
