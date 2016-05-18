package com.siyang.registration;

public class Person {
	
	//private int id;
	private String weChatId;
	private String name;
	private String birth;
	
	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}*/
	
	public String getWeChatId() {
		return weChatId;
	}
	
	public void setWeChatId(String weChatId) {
		this.weChatId = weChatId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBirth() {
		return birth;
	}
	
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	@Override
	public int hashCode(){
		String s=this.weChatId+this.name+this.birth;
		return s.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null){
			return false;
		}
		if(!(o instanceof Person)){
			return false;
		}
		Person p=(Person)o;
		/*if(this.getId()==p.getId()){
			return true;
		}*/
		return this.getWeChatId().equals(p.getWeChatId())&&
				this.getName().equals(p.getName())&&
				this.getBirth().equals(p.getBirth());
	}
}
