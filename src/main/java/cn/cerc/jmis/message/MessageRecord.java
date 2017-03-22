package cn.cerc.jmis.message;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jdb.core.IHandle;

/*
 * 专用于消息发送
 */
public class MessageRecord {
	// 收信帐套
	private String corpNo;

	// 收信用户
	private String userCode;

	private String subject;
	private StringBuilder content = new StringBuilder();
	private MessageLevel level = MessageLevel.General;
	private int process;

	public MessageRecord() {

	}

	public MessageRecord(String userCode) {
		this.userCode = userCode;
	}

	public MessageRecord(String userCode, String subject) {
		if (subject == null)
			throw new RuntimeException("消息标题不允许为空！");
		this.userCode = userCode;
		if (subject.length() > 80) {
			this.subject = subject.substring(0, 77) + "...";
			this.content.append(subject);
		} else {
			this.subject = subject;
		}
	}

	public String getContent() {
		return content.toString();
	}

	public void append(String content) {
		this.content.append(content);
	}

	public void append(String format, Object... args) {
		content.append(String.format(format, args));
	}

	public MessageLevel getLevel() {
		return level;
	}

	public void setLevel(MessageLevel level) {
		this.level = level;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getCorpNo() {
		return corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	public String getSubject() {
		return subject;
	}

	public MessageRecord setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public MessageRecord setSubject(String format, Object... args) {
		this.subject = String.format(format, args);
		return this;
	}

	public MessageRecord setContent(String content) {
		this.content = new StringBuilder(content);
		return this;
	}

	public int send(IHandle handle) {
		if (subject == null)
			throw new RuntimeException("消息标题不允许为空！");

		String sendCorpNo = corpNo != null ? corpNo : handle.getCorpNo();
		LocalService svr = new LocalService(handle, "SvrUserMessages.appendRecord");
		if (!svr.exec("corpNo", sendCorpNo, "userCode", this.userCode, "level", this.level.ordinal(), "subject",
				this.subject, "content", this.content.toString(), "process", this.process))
			throw new RuntimeException(svr.getMessage());

		// 返回消息的编号
		return svr.getDataOut().getHead().getInt("msgId");
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

}
