package pl.dsj24.skjgen;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import pl.dsj24.skjgen.gui.UserInterface;

public class Application {

	public static final String VERSION = "0.1";
	
	public static void main(String[] args) {
		setLogger();
		new UserInterface();
	}

	private static void setLogger() {
    	Logger mainLogger = Logger.getLogger("pl.dsj24.skjgen");
    	mainLogger.setUseParentHandlers(false);
    	ConsoleHandler handler = new ConsoleHandler();
    	handler.setFormatter(new SimpleFormatter() {
    		private static final String format = "[%1$-7s] %2$s %n";
    		
    		@Override
    		public synchronized String format(LogRecord lr) {
                return String.format(format, lr.getLevel().getLocalizedName(), 
                		lr.getMessage());
            }
    	});
    	mainLogger.addHandler(handler);
    }

}
