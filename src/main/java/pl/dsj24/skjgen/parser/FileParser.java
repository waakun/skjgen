package pl.dsj24.skjgen.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.dsj24.skjgen.generator.Player;

public class FileParser implements Parser {

	private static final Logger log = Logger.getLogger(FileParser.class.getName());

	private static final Pattern COLOR_PATTERN = Pattern.compile("([0-9a-fA-F]{6})");

	private static final String RANDOM_STRING = "R";

	private File file;

	public FileParser(File file) {
		this.file = file;
	}

	@Override
	public List<Player> parse() throws ParserException {
		log.info("Parsing file " + file.getAbsolutePath());

		List<Player> players = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("^") || line.length() == 0) {
					continue;
				}
				Player player = parseLine(line);
				players.add(player);
				log.info("Added player, name: " + player);
			}
		} catch (IOException e) {
			throw new ParserException("Cannot read file.", e);
		}
		return players;
	}

	private Player parseLine(String text) throws ParserException {
		String[] line = text.split("\t");
		try {
			String name = parseName(line);
			String country = parseCountry(line);
			boolean random = parseRandom(line);
			String[] skisColors = parseColors(line);
			return new Player(name, country, random, skisColors);
		} catch (ParserException e) {
			throw e;
		}

	}

	private String parseName(String[] line) throws ParserException {
		if (line[0].length() > 24) {
			String name = line[0].substring(0, 24);
			log.warning("Player name " + line[0] + " is too long, using " + line[0]);
			return name;
		} else
			return line[0];
	}

	private String parseCountry(String[] line) throws ParserException {
		if (line.length < 2) {
			return "";
		} else if (line[1].length() > 3) {
			String country = line[1].substring(0, 3).toLowerCase();
			log.warning("Country code " + line[1] + " is too long, using " + country + ".");
			return country;
		} else {
			return line[1].toLowerCase();
		}
	}

	private boolean parseRandom(String[] line) throws ParserException {
		return line.length >= 3 && line[2].equals(RANDOM_STRING);
	}

	private String[] parseColors(String[] line) throws ParserException {
		String[] colors = new String[11];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = "";
		}

		for (int i = 0; i < colors.length; i++) {
			int lineIndex = i + 3;

			if (line.length < i + 4)
				return colors;

			if (line[lineIndex].length() == 0)
				continue;

			Matcher matcher = COLOR_PATTERN.matcher(line[lineIndex]);
			if (matcher.matches()) {
				colors[i] = line[lineIndex].substring(0, 6).toLowerCase();
			} else {
				log.log(Level.WARNING, "Invalid suit color: " + line[lineIndex] + " for " + line[0] + ". Using default.");
			}
		}
		return colors;
	}
}
