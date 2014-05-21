package me.huiwen.example.redis.jedis;

public class Post {

	private String content;
	private String uid;
	private String time = String.valueOf(System.currentTimeMillis());
	private String replyPid;
	private String replyUid;

	/**
	 * Returns the content.
	 *
	 * @return Returns the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * Returns the uid.
	 *
	 * @return Returns the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid The uid to set.
	 */
	public void setUid(String author) {
		this.uid = author;
	}

	/**
	 * Returns the time.
	 *
	 * @return Returns the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time The time to set.
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Returns the replyPid.
	 *
	 * @return Returns the replyPid
	 */
	public String getReplyPid() {
		return replyPid;
	}

	/**
	 * @param replyPid The replyPid to set.
	 */
	public void setReplyPid(String replyPid) {
		this.replyPid = replyPid;
	}

	/**
	 * Returns the replyUid.
	 *
	 * @return Returns the replyUid
	 */
	public String getReplyUid() {
		return replyUid;
	}

	/**
	 * @param replyUid The replyUid to set.
	 */
	public void setReplyUid(String replyUid) {
		this.replyUid = replyUid;
	}

	@Override
	public String toString() {
		return "Post [content=" + content + ", replyPid=" + replyPid + ", replyUid=" + replyUid
				+ ", time=" + time + ", uid=" + uid + "]";
	}
}