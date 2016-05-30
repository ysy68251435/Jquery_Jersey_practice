package com.siyang.dao;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDB {
	
	protected MongoClient client;
	protected DB db;
	
	public MongoDB(){
		System.out.println("connect mongodb server");
		try {
			client=new MongoClient("localhost",27017);
		} catch (UnknownHostException e) {
			System.out.println("fail to create mongodb client"+e.getMessage());
		}
		db=client.getDB("GeneralYao");
	}
}
