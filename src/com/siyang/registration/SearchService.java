package com.siyang.registration;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siyang.solr.SolrController;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


@Path("/search")
@Component("SearchService")
public class SearchService {
	
	private Client client;
	private WebResource wr;
	
	@Autowired
	private SolrController solr;
	
	/*public SearchService(){
		System.out.println("search service init");
		client=Client.create();
	}*/
	
	
	@PostConstruct
	public void init(){
		System.out.println("search service init");
		client=Client.create();
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(){
		Result r=new Result(200);
		return JSONConverter.toJSON(r);
	}
	
	@GET
	@Path("/{core}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String query(@PathParam("core") String core, @QueryParam("input") String input){
		System.out.println("query: "+input);
		String query=solr.getQuery(input);
		wr=client.resource("http://localhost:8983/solr/"+core+"/select").queryParam("indent", "on")
																		.queryParam("q", query)
																		.queryParam("wt", "json");
		System.out.println(wr.getURI());
		return wr.accept(MediaType.APPLICATION_JSON).get(String.class);
	}
	
	//delete all documents in solr
	@DELETE
	@Path("/{core}")
	public String initSolr(@PathParam("core") String core){
		System.out.println("delete documents from "+core);
		wr=client.resource("http://localhost:8983/solr/"+core+"/update").queryParam("stream.body", "<delete><query>*:*</query></delete>")
																        .queryParam("commit", "true");
		System.out.println(wr.getURI());
		return wr.accept(MediaType.APPLICATION_JSON).delete(String.class);
	}
}
