package pl.dsj24.skjgen.generator;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.logging.Logger;

import static java.awt.event.KeyEvent.*;

public class Generator {

	private static final Logger log = Logger.getLogger(Generator.class.getName());

	private final List<Player> players;
	private final Robot robot;

	private final int screenWidth;
	private final int screenHeight;

	private static final double[] SUIT_COORDS_X = { 0.0703, 0.0703, 0.0703, 0.0703, 0.1713, 0.1713, 0.1713, 0.1713,
			0.3236, 0.3236, 0.3236 };
	private static final double[] SUIT_COORDS_Y = { 0.5234, 0.5547, 0.5859, 0.6172, 0.5234, 0.5547, 0.5859, 0.6172,
			0.5234, 0.5547, 0.5859 };

	private static final int DELAY = 30;

	public Generator(List<Player> players, int screenWidth, int screenHeight) throws GeneratorException {
		this.players = players;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			throw new GeneratorException("Cannot initialize mouse robot.");
		}
	}

	/**
	 * Creates new players in dsj4
	 *
	 * @return amount of players added
	 */
	public void generate() {

		log.info("Please go to DSJ4 main menu in 5 seconds!");
		robot.delay(5000);
		log.info("Start");
		// World Cup
		click(0.0996, 0.1849);
		// Select competitors
		click(0.0996, 0.2266);
		// Create players
		for (Player player : players) {

			log.info("Generating ski jumper, name: " + player.getName());

			// Button "new player"
			click(0.5681, 0.9479);

			// Name
			click(0.1713, 0.1823);
			typeText(player.getName());

			// Country
			click(0.1713, 0.2240);
			typeText(player.getCountry());

			// Random
			if (player.isRandomSuit()) {
				click(0.3236, 0.4844);
			}

			// Suit
			for (int i = 0; i < player.getColors().length; i++) {
				String color = player.getColors()[i];
				if (!color.isEmpty()) {
					click(SUIT_COORDS_X[i], SUIT_COORDS_Y[i]);
					click(0.3236, 0.6615);
					erase(6);
					typeText(color);
				}
			}

			// Back
			click(0.1713, 0.9479);
		}
	}

	private void move(double x, double y) {
		int screenX = (int) (x * (double) screenWidth);
		int screenY = (int) (y * (double) screenHeight);
		robot.mouseMove(screenX, screenY);
	}

	private void click() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	private void click(double x, double y) {
		move(x, y);
		click();
		robot.delay(DELAY);
	}

	private void type(int keyEvent) {
		robot.keyPress(keyEvent);
		robot.keyRelease(keyEvent);
	}

	private void type(int keyEvent, boolean shift) {
		if (shift)
			robot.keyPress(VK_SHIFT);
		type(keyEvent);
		if (shift)
			robot.keyRelease(VK_SHIFT);
	}

	private void typeText(String text) {
		for (int i = 0; i < text.length(); i++) {
			typeChar(text.charAt(i));
		}
	}

	private void erase(int amount) {
		for (int i = 0; i < amount; i++) {
			type(VK_BACK_SPACE);
		}
	}

	private void typeChar(char character) {
		if (character >= 'a' && character <= 'z')
			type(character - 32);
		else if (character >= 'A' && character <= 'Z')
			type(character, true);
		else if (character >= '0' && character <= '9') {
			type(character);
		} else
			switch (character) {
			case ' ':
				type(VK_SPACE);
				break;
			case '!':
				type(VK_1, true);
				break;
			case '@':
				type(VK_2, true);
				break;
			case '#':
				type(VK_3, true);
				break;
			case '$':
				type(VK_4, true);
				break;
			case '%':
				type(VK_5, true);
				break;
			case '&':
				type(VK_7, true);
				break;
			case '*':
				type(VK_8, true);
				break;
			case '(':
				type(VK_9, true);
				break;
			case ')':
				type(VK_0, true);
				break;
			case '.':
				type(VK_PERIOD, true);
				break;
			case ',':
				type(VK_COMMA, true);
				break;
			case '-':
				type(VK_MINUS, true);
				break;
			case '~':
				type(VK_BACK_QUOTE, true);
				break;
			}
	}
}
