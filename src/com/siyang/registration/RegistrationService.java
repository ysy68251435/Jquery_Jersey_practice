package com.siyang.registration;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/")
public class RegistrationService {
	
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
		System.out.println("get all users");
		List<Person> persons=Cache.get();
		String result=JSONConverter.toJSON(persons);
		System.out.println(result);
		return result;
	}
	
	@Path("users")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewUser(String input){
		System.out.println("add new users");
		System.out.println("input: "+input);
		Cache.store(JSONConverter.fromJSON(input));
		Cache.display();
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
	
	@Path("newusers")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateCache(String input){
		System.out.println("update cache");
		System.out.println("input: "+input);
		Cache.update(JSONConverter.fromJSON(input));
		Cache.display();
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
}
