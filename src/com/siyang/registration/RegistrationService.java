package com.siyang.registration;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siyang.dao.UserDAO;
import com.siyang.solr.SolrController;

@Path("/")
@Component("RegistrationService")
public class RegistrationService {
	
	@Autowired
	private SolrController solr;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private Cache cache;
	
	@PostConstruct
	public void init(){
		System.out.println("registration service init");
		cache.load();
	}
	
	@Path("test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test(){
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
	
	@Path("allusers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUsers(){
		System.out.println("RegistrationService: get all users");
		
		//get all users from cache
		List<Person> persons=cache.get();  
		String result=JSONConverter.toJSON(persons);
		System.out.println(result);
		return result;
	}
	
	@Path("users")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewUsers(String input){
		System.out.println("add new users");
		System.out.println("input: "+input);
		
		List<Person> persons=JSONConverter.fromJSON(input);
		
		//add users to mongodb
		userDAO.addUsers(persons);
		
		//add documents to solr server
		solr.addDocument(persons);
		
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
	
	@Path("newusers")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(String input){
		System.out.println("update cache and collection");
		System.out.println("input: "+input);
		List<Person> persons=JSONConverter.fromJSON(input);
		solr.addDocument(persons);
		
		//update users in mongdb and cache
		userDAO.updateUsers(persons);
		
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
}
