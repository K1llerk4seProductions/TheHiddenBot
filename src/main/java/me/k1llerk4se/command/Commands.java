package me.k1llerk4se.command;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by mikel on 12/5/2014.
 */
public class Commands extends ListenerAdapter<PircBotX> {
@Override
public void onMessage(MessageEvent<PircBotX> event) throws Exception {
	if (event.getMessage().split(" ")[0].equalsIgnoreCase("!welcome")){

	}
}
}
