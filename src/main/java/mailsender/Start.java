package mailsender;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Start {
	public static void main(String[] args) {
		String HELP_MESSAGE = "To run this program, you need to type:"
				+ "\n\tjava -jar <jar-name>.jar -a [email@address] -m [Here is the message]";

		if (args.length == 0) {
			System.out.println(HELP_MESSAGE);
			System.exit(0);
		}
		if (args.length < 4 || !args[0].equalsIgnoreCase("-a")
				|| !args[2].equalsIgnoreCase("-m")) {
			System.out.println("Invalid parameters!");
			System.out.println(HELP_MESSAGE);
			System.exit(0);
		}

		String to = args[1];
		Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(to);

		if (!m.matches()) {
			System.out.println("Invalid e-mail address format!");
			System.exit(0);
		}

		String text = args[3]; // first message word
		for (int i = 4; i < args.length; i++) { // rest message words
			text += " " + args[i];
		}

		Configurator configger = new Configurator();
		Properties prop;
		try {
			prop = configger.readConfigs().toProperties();
		} catch (Exception e) {
			System.out.println("Can't load config file!");
			return;
		}

		String from = prop.getProperty("senderaddress");
		Client client = new Client(from, to);
		client.setProperties(prop);
		client.setLogin(prop.getProperty("login"));
		client.setPassword(prop.getProperty("password"));
		System.out.println("Sending message...");
		if (client.sendMessage("New message", text)) {
			System.out.println("Message sent sucessfull!");
		} else {
			System.out.println("Message sent unsucessfull!");
		}

	}
}
