package me.huiwen.example.redis.jedis;

public abstract class KeyUtils {
	static final String UID = "uid:";

	static String following(String uid) {
		return UID + uid + ":following";
	}

	static String followers(String uid) {
		return UID + uid + ":followers";
	}

	static String timeline(String uid) {
		return UID + uid + ":timeline";
	}

	static String mentions(String uid) {
		return UID + uid + ":mentions";
	}

	static String posts(String uid) {
		return UID + uid + ":posts";
	}

	static String auth(String uid) {
		return UID + uid + ":auth";
	}

	static String uid(String uid) {
		return UID + uid;
	}

	static String post(String pid) {
		return "pid:" + pid;
	}

	static String authKey(String auth) {
		return "auth:" + auth;
	}

	public static String user(String name) {
		return "user:" + name + ":uid";
	}

	static String users() {
		return "users";
	}

	static String timeline() {
		return "timeline";
	}

	static String globalUid() {
		return "global:uid";
	}

	static String globalPid() {
		return "global:pid";
	}

	static String alsoFollowed(String uid, String targetUid) {
		return UID + uid + ":also:uid:" + targetUid;
	}

	static String commonFollowers(String uid, String targetUid) {
		return UID + uid + ":common:uid:" + targetUid;
	}

}
