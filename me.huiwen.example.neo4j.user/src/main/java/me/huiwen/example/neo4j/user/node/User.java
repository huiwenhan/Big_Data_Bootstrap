/** 
 * IBM Confidential
 * IBM CIO WebAhead Steer
 * Copyright IBM Corp. 2009
 */
package me.huiwen.example.neo4j.user.node;


/**
 * Data object for mapping of database table user
 * 
 * @author Charlie Li (Li Cheng Hong)
 * @version 0.1
 * 
 */
public class User {
	private static final long serialVersionUID = -386635962736809811L;
	private String name;
	public User() {
	}








	public User(String name) {
		super();
		this.name = name;
	}








	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
