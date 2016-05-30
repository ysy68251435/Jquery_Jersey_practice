package com.siyang.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import com.siyang.registration.Person;;

@Component
public class SolrController {
	
	private final String url="http://localhost:8983/solr/core2";
	private SolrClient client;
	
	public SolrController(){
		System.out.println("connect to solr");
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
	
	public String getQuery(String input){
		StringBuilder sb=new StringBuilder();
		input=input.toLowerCase();
		String[] strs=input.split(" ");
		for(int i=0;i<strs.length;i++){
			if(isBirthDate(strs[i])){
				sb.append("birth:"+strs[i]+" ");
				continue;
			}
			if(i>0&&strs[i-1].equals("wechatid")){
				sb.append("wechatid:"+strs[i]+" ");
				continue;
			}
			if(i>0&&strs[i-1].equals("name")){
				sb.append("name:"+strs[i]+" ");
				continue;
			}
			sb.append(strs[i]+" ");
		}
		String result=sb.toString();
		System.out.println("query: "+result);
		return result;
	}
	
	private boolean isBirthDate(String s){
		return s.length()==10&&s.charAt(2)=='/'&&s.charAt(5)=='/';
	}
}
