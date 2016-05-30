package com.siyang.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.siyang.registration.Cache;
import com.siyang.registration.Person;

@Component("UserDAO")
public class UserDAOImp extends MongoDB  implements UserDAO {
	
	@Autowired
	private Cache cache;
	
	@Override
	public void addUsers(List<Person> persons) {
		System.out.println("UserDAO add users: "+collectionName);
		DBCollection col=db.getCollection(collectionName);
		insertUsersToCollection(persons, col);
	}

	@Override
	public List<Person> getAllUsers() {
		System.out.println("get all users");
		List<Person> persons=new ArrayList<Person>();
		DBCollection col=db.getCollection(collectionName);
		DBCursor docs=col.find();
		while(docs.hasNext()){
			DBObject doc=docs.next();
			Person p=new Person();
			p.setWeChatId(doc.get("wechatid").toString());
			p.setName(doc.get("name").toString());
			p.setBirth(doc.get("dob").toString());
			persons.add(p);
		}
		return persons;
	}

	@Override
	public void updateUsers(List<Person> persons) {
		System.out.println("update collection registration");
		DBCollection col=db.getCollection(collectionName);
		col.drop();
		cache.clear();
		insertUsersToCollection(persons, col);
	}
	
	/*
	 * private methods(helpers)
	 * */
	private void insertUsersToCollection(List<Person> persons, DBCollection col){
		for(Person p: persons){
			if(cache.exist(p)){
				continue;
			}
			
			cache.addUser(p);
			BasicDBObject doc=new BasicDBObject("wechatid", p.getWeChatId())
					.append("name", p.getName())
					.append("dob", p.getBirth());
			col.insert(doc);
		}
	}
}
