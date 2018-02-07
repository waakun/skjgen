package pl.dsj24.skjgen.gui;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;

public class TextComponentHandler extends Handler {

	private JTextArea textArea;

	public TextComponentHandler() {
		super();
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void publish(LogRecord record) {
		if (isLoggable(record)) {
			synchronized (textArea) {
				textArea.append(record.getMessage() + '\n');
			}
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

}
