package pl.dsj24.skjgen.parser;

import java.util.List;

import pl.dsj24.skjgen.generator.Player;

public interface Parser {

	/**
	 * @return List of players read by parser.
	 * @throws ParserException
	 *             When there was an error while parsing file.
	 */
	List<Player> parse() throws ParserException;

}
