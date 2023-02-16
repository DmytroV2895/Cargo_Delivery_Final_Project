package com.varukha.webproject.command;

import com.varukha.webproject.command.impl.base.DefaultCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * CommandProvider class
 * @author Dmytro Varukha
 *
 */
public class CommandProvider {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Defines command
	 * @param command(command name)
	 */
	public static Command defineCommand(String command) {
		Command current = null;
		logger.log(Level.INFO, "Define command: Command from controller: " + command);
		if (command == null || command.isEmpty()) {
			logger.log(Level.INFO, "Define command: empty command ");
			return new DefaultCommand();
		}
		try {
			CommandType currentType = CommandType.valueOf(command.toUpperCase());
			current = currentType.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			logger.log(Level.ERROR, "Define command: empty command from catch ");
			current = new DefaultCommand();
		}
		return current;
	}
}
