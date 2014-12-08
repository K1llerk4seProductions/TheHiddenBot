package me.k1llerk4se;

import me.k1llerk4se.command.Commands;
import me.k1llerk4se.libs.GuiApp;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mikel on 12/5/2014.
 */
public class TheHiddenBot {
	public static final String VERSION = "V1.0";
	public static boolean runGUI = true;
	public static Map<String, Object> conf, strings;
	static Yaml yaml = new Yaml();
	public static Logger logger = LoggerFactory.getLogger(TheHiddenBot.class);
	public static void main(String[] args){
		if (!GraphicsEnvironment.isHeadless()) {
			new GuiApp(args);
		}
		new TheHiddenBot();
	}
	static File serverConfig = new File("config/server.yml");
	static File stringsFile = new File("config/strings.yml");
	public static void setupDefaultConfigs(){
		try{
			File f = new File("database");
			File g = new File("config");
			f.mkdir();
			g.mkdir();
		} catch(Exception e){
			e.printStackTrace();
		}
		CheckStrings();
		CheckServer();
		try {
			conf = (Map<String, Object>) yaml.load(new FileInputStream(
					                                                          serverConfig));
			strings = (Map<String, Object>) yaml.load(new FileInputStream(stringsFile));
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage());
		}
	}
	private static void CheckServer() {
		if(!serverConfig.exists()){
			logger.info("First Time Config Setup, Please edit the config after it got written...");
			try {
				serverConfig.createNewFile();
				Scanner scanner = new Scanner(TheHiddenBot.class.getResourceAsStream(
						                                                                  "./defaultServerConfig.yml"));
				FileWriter fileWriter = new FileWriter(serverConfig);
				while (scanner.hasNextLine()) {
					fileWriter.write(scanner.nextLine() + '\n');
				}
				fileWriter.close();
				scanner.close();
				logger.info("Finished writing default config.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void CheckStrings() {
		if(!stringsFile.exists()){
			try {
				stringsFile.createNewFile();
				System.out.println(TheHiddenBot.class.getPackage());
				Scanner scanner = new Scanner(TheHiddenBot.class.getResourceAsStream(
						                                                                  "./defaultStrings.yml"));
				FileWriter fileWriter = new FileWriter(stringsFile);
				while (scanner.hasNextLine()) {
					fileWriter.write(scanner.nextLine() + '\n');
				}
				fileWriter.close();
				scanner.close();
				logger.info("Finished writing strings");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	Configuration server = new Configuration.Builder()
											.setEncoding(Charset.forName("UTF8"))
											.setName((String) conf.get("nick"))
											.setAutoNickChange(true)
											.setServerHostname((String) conf.get("serverHost"))
											.setServerPassword((String) conf.get("OAuth"))
											.setServerPort(6667)
											.addAutoJoinChannel("#" + conf.get("autoJoinChannel"))
											.addListener(new Commands())
											.buildConfiguration();
	public TheHiddenBot(){
		try {
			PircBotX myBot = new PircBotX(server);
			myBot.startBot();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
