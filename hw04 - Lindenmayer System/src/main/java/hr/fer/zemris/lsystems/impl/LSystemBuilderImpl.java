package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * A class used for creating a configuration of the LSystem and building the LSystem.
 * @author Alen Carin
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
//	configuration directive strings
	public static final String ORIGIN = "origin";
	public static final String ANGLE = "angle";
	public static final String UNIT_LENGTH = "unitLength";
	public static final String UNIT_LENGTH_DEGREE_SCALER = "unitLengthDegreeScaler";
	public static final String COMMAND = "command";
	public static final String AXIOM = "axiom";
	public static final String PRODUCTION = "production";
	
//	command constant strings
	public static final String DRAW = "draw";
	public static final String SKIP = "skip";
	public static final String SCALE = "scale";
	public static final String ROTATE = "rotate";
	public static final String PUSH = "push";
	public static final String POP = "pop";
	public static final String COLOR = "color";
	
	private Dictionary commands;
	private Dictionary productions;
	
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	

	/**
	 * Constructor. Sets field values to the given argument values.
	 */
	public LSystemBuilderImpl() {
		this.commands = new Dictionary();
		this.productions = new Dictionary();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	/**
	 * Method which builds the LSystem from the current configuration.
	 * Returns created Lindermayer system.
	 */
	@Override
	public LSystem build() {
		
		/**
		 * Nested class which implements the LSystem and provides two essential methods
		 * for building the LSystem: draw and generate.
		 * @author Alen Carin
		 *
		 */
		class LSystemImpl implements LSystem {

			/**
			 * Method used for drawing the Lyndermayer system.
			 * Calls method generate to generate all of the productions and 
			 * executes all the commands that are in the generated string.
			 */
			@Override
			public void draw(int depth, Painter painter) {
				TurtleState state = new TurtleState(
						origin.copy(), 
						new Vector2D(1, 0).rotated(angle), 
						Color.BLACK,
						unitLength * Math.pow(unitLengthDegreeScaler, depth)
						);
				Context context = new Context();
				context.pushState(state);
				char[] sequence = generate(depth).toCharArray();
				
				executeAllCommands(context, painter, sequence);
			}

			/**
			 * Recursive method which generates a string of actions 
			 * which are generated from productions for the given depth.
			 */
			@Override
			public String generate(int depth) {
				StringBuilder builder = new StringBuilder();
				if(depth == 0) {
					return axiom;
				}
				builder.append(generate(depth-1));
				
				char[] array = builder.toString().toCharArray();
				String result = new String();
				for(int i = 0; i < array.length; i++) {
					String production = (String) productions.get(array[i]);
					if(production != null) {
						result += production;
					}
					else {
						result += array[i];
					}
				}
				return result;
			}
			
		}
		return new LSystemImpl();
	}

	/**
	 * Method which iterates through all the commands and calls the execute method of each one.
	 * @param context of the system.
	 * @param painter used for actual painting.
	 * @param sequence of characters that represent commands i.e. actions.
	 */
	protected void executeAllCommands(Context context, Painter painter, char[] sequence) {
		for(int i = 0; i < sequence.length; i++) {
			String commandString = (String) commands.get(sequence[i]);
			if(commandString == null || commandString.equals("")) {
				continue;
			}
			
			Command command = createCommand(commandString);
			command.execute(context, painter);
		}
	}

	/**
	 * Checks if the given command is valid and creates the related command.
	 * @param command which represents an action that should be executed.
	 * @return
	 */
	private Command createCommand(String command) {
		String[] tokens = command.split(" ");
		switch(tokens[0].toLowerCase()) {
		case DRAW:
			return createDrawCommand(command);
		
		case SKIP:
			return createSkipCommmand(command);
			
		case SCALE:
			return createScaleCommand(command);
			
		case ROTATE:
			return createRotateCommand(command);
			
		case PUSH:
			return createPushCommand();
			
		case POP:
			return createPopCommand();
			
		case COLOR:
			return createColorCommand(command);
			
		default:
			throw new IllegalArgumentException("Invalid command argument");
		}
	}

	/**
	 * Creates an instance of the <code>DrawCommand</code> and passes it the step value.
	 * @param command which represents an action that should be executed.
	 * @return a newly created instance of the <code>DrawCommand</code>
	 */
	private Command createDrawCommand(String command) {
		String[] tokens = command.split(" ");
		return new DrawCommand(Double.parseDouble(tokens[1]));
	}

	/**
	 * Creates an instance of the <code>SkipCommand</code> and passes it the step value.
	 * @param command which represents an action that should be executed.
	 * @return a newly created instance of the <code>SkipCommand</code>
	 */
	private Command createSkipCommmand(String command) {
		String[] tokens = command.split(" ");
		return new SkipCommand(Double.parseDouble(tokens[1]));
	}

	/**
	 * Creates an instance of the <code>ScaleCommand</code> and passes it the factor value.
	 * @param command which represents an action that should be executed.
	 * @return a newly created instance of the <code>ScaleCommand</code>
	 */
	private Command createScaleCommand(String command) {
		String[] tokens = command.split(" ");
		return new ScaleCommand(Double.parseDouble(tokens[1]));
	}

	/**
	 * Creates an instance of the <code>RotateCommand</code> and passes it the angle value.
	 * @param command which represents an action that should be executed.
	 * @return a newly created instance of the <code>RotateCommand</code>
	 */
	private Command createRotateCommand(String command) {
		String[] tokens = command.split(" ");
		return new RotateCommand(Double.parseDouble(tokens[1]));
	}

	/**
	 * Creates an instance of the <code>PushCommand</code>.
	 * @return a newly created instance of the <code>PushCommand</code>
	 */
	private Command createPushCommand() {
		return new PushCommand();
	}

	/**
	 * Creates an instance of the <code>PopCommand</code>.
	 * @return a newly created instance of the <code>PopCommand</code>
	 */
	private Command createPopCommand() {
		return new PopCommand();
	}

	/**
	 * Creates an instance of the <code>ColorCommand</code> and passes it the color value.
	 * @param command which represents an action that should be executed.
	 * @return a newly created instance of the <code>ColorCommand</code>
	 */
	private Command createColorCommand(String command) {
		String[] tokens = command.split(" ");
		Color color = new Color(Integer.decode("#" + tokens[1]));
		return new ColorCommand(color);
	}

	/**
	 * Method which takes an array of Strings and parses it. 
	 * Each element of the array represents a single line and 
	 * each line represents a single command that should be executed.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].equals("")) {
				continue;
			}
			if(lines[i].startsWith(ORIGIN)) {
				parseOrigin(lines[i]);
			}
			else if(lines[i].startsWith(ANGLE)) {
				parseAngle(lines[i]);
			}
			else if(lines[i].startsWith(UNIT_LENGTH_DEGREE_SCALER)) {
				parseUnitLengthDegreeScaler(lines[i]);
			}
			else if(lines[i].startsWith(UNIT_LENGTH)) {
				parseUnitLength(lines[i]);
			}
			else if(lines[i].startsWith(COMMAND)) {
				parseCommand(lines[i]);
			}
			else if(lines[i].startsWith(AXIOM)) {
				parseAxiom(lines[i]);
			}
			else if(lines[i].startsWith(PRODUCTION)) {
				parseProduction(lines[i]);
			}
			else {
				throw new IllegalArgumentException("Invalid configuration line");
			}
		}
		return this;
	}

	/**
	 * Private method which is used for parsing of the origin line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseOrigin(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		if(tokens.length != 3) {
			throw new IllegalArgumentException("Invalid origin arguments");
		}
		double x = Double.parseDouble(tokens[1]);
		double y = Double.parseDouble(tokens[2]);
		this.setOrigin(x, y);
	}

	/**
	 * Private method which is used for parsing of the angle line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseAngle(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		if(tokens.length != 2) {
			throw new IllegalArgumentException("Invalid angle argument");
		}
		this.setAngle(Double.parseDouble(tokens[1]));
	}

	/**
	 * Private method which is used for parsing of the unit length line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseUnitLength(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		if(tokens.length != 2) {
			throw new IllegalArgumentException("Invalid unitLength argument");
		}
		this.setUnitLength(Double.parseDouble(tokens[1]));
	}

	/**
	 * Private method which is used for parsing of the unit length degree scaler line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseUnitLengthDegreeScaler(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		if(tokens.length == 2) {
			this.setUnitLengthDegreeScaler(Double.parseDouble(tokens[1]));
		}
		else if(tokens.length == 3) {
			double firstNumber = Double.parseDouble(tokens[1].replace("/", ""));
			double secondNumber = Double.parseDouble(tokens[2].replace("/", ""));
			double degreeScaler = firstNumber / secondNumber;
			this.setUnitLengthDegreeScaler(degreeScaler);
		}
		else if(tokens.length == 4 && tokens[2].equals("/")) {
			double degreeScaler = Double.parseDouble(tokens[1]) / Double.parseDouble(tokens[3]);
			this.setUnitLengthDegreeScaler(degreeScaler);
		}
		else {
			throw new IllegalArgumentException("Invalid unitLengthDegreeScaler");
		}
	}

	/**
	 * Private method which is used for parsing of the command line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseCommand(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		String commandString;
		if(tokens.length == 4) {
			commandString = tokens[2] + " " + tokens[3];
		}
		else if(tokens.length == 3) {
			commandString = tokens[2];
		}
		else {
			throw new IllegalArgumentException("Invalid command argument");
		}
		this.registerCommand(tokens[1].charAt(0), commandString);
	}

	/**
	 * Private method which is used for parsing of the axiom line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseAxiom(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		if(tokens.length != 2) {
			throw new IllegalArgumentException("Invalid unitLength argument");
		}
		this.setAxiom(tokens[1]);
	}

	/**
	 * Private method which is used for parsing of the production line.
	 * @param line from the text that should be parsed.
	 * @throws IllegalArgumentException if the given line is invalid.
	 */
	private void parseProduction(String line) throws IllegalArgumentException {
		String[] tokens = line.split("\\s+");
		if(tokens.length != 3) {
			throw new IllegalArgumentException("Invalid production arguments");
		}
		registerProduction(tokens[1].charAt(0), tokens[2]);
	}

	/**
	 * Puts the given command in the dictionary of commands.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String command) {
		commands.put(symbol, command);
		return this;
	}

	/**
	 * Puts the given production in the dictionary of productions.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Sets the field value of the angle to the passed argument value.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets the field value of the axiom to the passed argument value.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets the field value of the origin to the passed argument value.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets the field value of the unitLength to the passed argument value.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the field value of the unitLengthDegreeScaler to the passed argument value.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
