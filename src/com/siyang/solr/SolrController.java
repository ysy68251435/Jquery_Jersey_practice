package com.siyang.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import com.siyang.registration.Person;;

public class SolrController {
	
	private final String url="http://localhost:8983/solr/core2";
	private SolrClient client;
	
	public SolrController(){
		client=new HttpSolrClient(url);
	}
	
	public void addDocument(List<Person> persons){
		System.out.println("solr add documents");
		
		try {
			for(Person p:persons){
				SolrInputDocument doc=new SolrInputDocument();
				doc.addField("wechatid", p.getWeChatId());
				doc.addField("name", p.getName());
				doc.addField("birth", p.getBirth());
				client.add(doc);
				client.commit();
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			System.out.println("solr add documents error: "+e.getMessage());
		}
	}
	
	public static String getQuery(String input){
		StringBuilder sb=new StringBuilder();
		String[] strs=input.split(" ");
		for(String s:strs){
			if(isBirthDate(s)){
				sb.append("birth:"+s+" OR ");
				continue;
			}
			sb.append("wechatid:"+s+" OR ");
			sb.append("name:"+s+" OR ");
		}
		String result=sb.toString();
		System.out.println("query: "+result);
		return result.length()==0?"":result.substring(0, result.length()-4);
	}
	
	private static boolean isBirthDate(String s){
		return s.length()==10&&s.charAt(2)=='/'&&s.charAt(5)=='/';
	}
}
