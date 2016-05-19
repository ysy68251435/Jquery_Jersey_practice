package com.siyang.registration;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.siyang.solr.SolrController;

@Path("/")
public class RegistrationService {
	
	private SolrController solr;
	
	public RegistrationService(){
		solr=new SolrController();
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
		List<Person> persons=JSONConverter.fromJSON(input);
		solr.addDocument(persons);// add documents to solr
		Cache.store(persons);
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
		List<Person> persons=JSONConverter.fromJSON(input);
		solr.addDocument(persons);
		Cache.update(persons);
		Cache.display();
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
}
