package com.siyang.registration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Cache {
	
	private static HashSet<Person> cache=new HashSet<Person>();
	
	public static void store(List<Person> persons){
		System.out.println("cache store");
		for(Person p: persons){
			cache.add(p);
		}
	}
	public static void update(List<Person> persons){
		System.out.println("cache update");
		cache.clear();
		for(Person p:persons){
			cache.add(p);
		}
	}
	public static List<Person> get(){
		System.out.println("cache get");
		List<Person> persons=new ArrayList<Person>();
		for(Person p: cache){
			persons.add(p);
		}
		return persons;
	}
	public static void display(){
		System.out.println("Cache: "+cache.size());
		for(Person p: cache){
			System.out.println(p.getWeChatId()+" "+
							   p.getName()+" "+p.getBirth());
		}
	}
}
