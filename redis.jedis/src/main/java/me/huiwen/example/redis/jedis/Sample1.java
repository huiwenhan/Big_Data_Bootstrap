package me.huiwen.example.redis.jedis;

import redis.clients.jedis.Jedis;

public class Sample1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		jedis.close();
		
	}

}
