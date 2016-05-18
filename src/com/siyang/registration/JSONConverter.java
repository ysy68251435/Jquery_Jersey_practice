package com.siyang.registration;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONConverter {
	
	public static List<Person> fromJSON(String str){
		System.out.println("JSONConverter fromJSON");
		List<Person> list=new GsonBuilder().create().fromJson(str, new TypeToken<List<Person>>(){}.getType());
		display(list);
		return list;
	}
	
	public static String toJSON(Object obj){
		Gson gson=new GsonBuilder().create();
		return gson.toJson(obj);
	}
	
	private static void display(List<Person> persons){
		for(Person p: persons){
			System.out.println(p.getWeChatId()+" "+p.getName()+" "+p.getBirth());
		}
	}
}
