package mailsender;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Client {

	private String addressFrom;
	private String addressTo;
	private String login;
	private String password;
	private Properties properties;

	public Client(String from, String to) {
		this.addressFrom = from;
		this.addressTo = to;
	}

	public Client(String from, String to, String login, String pwd) {
		this.addressFrom = from;
		this.addressTo = to;
		this.login = login;
		this.password = pwd;
	}

	public String getAdress() {
		return addressTo;
	}

	public void setAdress(String adress) {
		this.addressFrom = adress;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public boolean sendMessage(String subject, String messageText) {
		boolean sendResult = true;
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(this.addressFrom));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					this.addressTo));
			message.setSubject(subject);
			message.setText(messageText, "UTF-8");
			Transport.send(message, login, password);
		} catch (AddressException e) {
			sendResult = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			sendResult = false;
			e.printStackTrace();
		}
		return sendResult;
	}

}
