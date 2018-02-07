package pl.dsj24.skjgen.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pl.dsj24.skjgen.Application;
import pl.dsj24.skjgen.generator.Generator;
import pl.dsj24.skjgen.generator.GeneratorException;
import pl.dsj24.skjgen.generator.Player;
import pl.dsj24.skjgen.parser.FileParser;
import pl.dsj24.skjgen.parser.Parser;
import pl.dsj24.skjgen.parser.ParserException;

public class UserInterface {

	private JFrame frame;
	private JButton bGenerate;

	private JTextField tfWidth;
	private JTextField tfHeight;

	private File file;
	private List<Player> players;

	public UserInterface() {
		init();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void parseFile() {
		Parser parser = new FileParser(file);
		try {
			players = parser.parse();
		} catch (ParserException e) {
		}
	}

	public void enableGenerateButton() {
		bGenerate.setEnabled(!players.isEmpty());
	}

	private void init() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// frame
		frame = new JFrame("SKJGen");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// layout
		BorderLayout borderLayout = new BorderLayout();
		frame.setLayout(borderLayout);

		// panel with buttons
		JPanel panel = new JPanel(new FlowLayout());
		frame.add(panel, BorderLayout.NORTH);

		// select file button
		JButton bSelectFile = new JButton("Select file...");
		bSelectFile.addActionListener(e -> {
			File workingDirectory = new File(System.getProperty("user.dir"));
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(workingDirectory);
			int returnVal = fileChooser.showOpenDialog(frame);
			file = fileChooser.getSelectedFile();
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				parseFile();
				// log("Loaded file " + file.getPath() + " with " + getPlayers().size() + "
				// players");
				enableGenerateButton();
			}

		});
		panel.add(bSelectFile);

		// generate button
		bGenerate = new JButton("Generate");
		bGenerate.addActionListener(e -> {
			try {
				Generator generator = new Generator(players, getScreenWidth(), getScreenHeight());
				generator.generate();
			} catch (GeneratorException e1) {
			}
		});
		bGenerate.setEnabled(false);
		panel.add(bGenerate);

		// label width
		JLabel lWidth = new JLabel("Screen width:");
		panel.add(lWidth);

		// textfield width
		tfWidth = new JTextField("" + screenSize.width);
		tfWidth.setColumns(4);
		panel.add(tfWidth);

		// label height
		JLabel lHeight = new JLabel("Screen height:");
		panel.add(lHeight);

		// textfield height
		tfHeight = new JTextField("" + screenSize.height);
		tfHeight.setColumns(4);
		panel.add(tfHeight);

		// about button
		JButton aboutButton = new JButton("About");
		aboutButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "skjgen version " + Application.VERSION + "\n");
		});
		panel.add(aboutButton);

		// textarea log
		Logger logger = Logger.getLogger("pl.dsj24.skjgen");
		TextComponentHandler h = new TextComponentHandler();
		JTextArea textArea = new JTextArea();

		h.setTextArea(textArea);
		logger.addHandler(h);
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.add(scrollPane, BorderLayout.CENTER);

		frame.pack();
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(500, 200));
		frame.setVisible(true);
	}

	private int getScreenWidth() {
		return Integer.parseInt(tfWidth.getText());
	}

	private int getScreenHeight() {
		return Integer.parseInt(tfHeight.getText());
	}

}
