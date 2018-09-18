package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilderParser;

/**
 * The MassrenameShellCommand is a shell command used for renaming multiple files at once.
 * @author Alen Carin
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	/** The name of the command. */
	private String name;

	/** The description of the command. */
	private List<String> description;

	{
		name = "massrename";

		description = new LinkedList<>();
		description.add("The massrename command renames multiple files at once.");
		description.add("There are 4 subcommands that this command can execute: filter, groups, show or execute.");
		description.add("Arguments must contain four or five sequences of characters between them are spaces.");
	}

	/**
	 * Executes one of the four sub-commands which this command has. 
	 * Sub-command is specified in the arguments.
	 * Renames, moves and prints out the new names of the files.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] argumentsArray = Util.splitArguments(arguments, env);

		String commandName = argumentsArray[2];
		String mask = argumentsArray[3];
		String givenPattern = null;
		if (argumentsArray.length == 5) {
			givenPattern = argumentsArray[4];
		}

		Path sourceDir = Paths.get(argumentsArray[0]);
		Path destDir = Paths.get(argumentsArray[1]);

		Pattern pattern;

		if (argumentsArray.length != 4 && (commandName.equals("groups") || (commandName.equals("filter")))
				|| argumentsArray.length != 5 && (commandName.equals("show") || (commandName.equals("execute")))) {
			env.writeln("Invalid number of arguments for the massrename command!");
			return ShellStatus.CONTINUE;
		}

		if (commandName.equals("filter")) {

			pattern = Pattern.compile(mask);
			try {
				Files.list(sourceDir).filter(e -> pattern.matcher(e.getFileName().toString()).matches())
						.forEach(e -> env.writeln(e.getFileName().toString()));
			} catch (IOException e) {
				env.writeln("IOException happened while trying to filter");
				return ShellStatus.CONTINUE;
			}

		} else if (commandName.equals("groups")) {

			pattern = Pattern.compile(mask);

			try {
				Files.list(sourceDir).forEach(e -> {
					Matcher m = pattern.matcher(e.toString());
					env.write(String.format("%s ", e.getFileName().toString()));

					while (m.find()) {
						int groupsNumber = m.groupCount();
						for (int i = 0; i <= groupsNumber; i++) {
							env.write(String.format("%d: %s ", i, m.group(i)));
						}
					}
					env.writeln("");
				});
			} catch (IOException e) {
				env.writeln("IOException happened while trying to group");
				return ShellStatus.CONTINUE;
			}
			;

		} else if (commandName.equals("show")) {

			List<Path> files = null;
			NameBuilderParser parser = null;
			try {
				files = Files.list(sourceDir).collect(Collectors.toList());
				parser = new NameBuilderParser(givenPattern);

			} catch (IOException e) {
				env.writeln("IOException in show subcommand in massrename command. " + e.getMessage());
				return ShellStatus.CONTINUE;

			} catch (IllegalArgumentException e) {
				env.writeln("Exception while parsing the expression.");
				return ShellStatus.CONTINUE;
			}
			
			NameBuilder builder = parser.getNameBuilder();
			pattern = Pattern.compile(mask);

			for (Path file : files) {
				Matcher matcher = pattern.matcher(file.getFileName().toString());

				if (!matcher.find()) {
					continue;
				}
				NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
				builder.execute(info);
				String novoIme = info.getStringBuilder().toString();

				env.writeln(file.getFileName() + " => " + novoIme);
			}

		} else if (commandName.equals("execute")) {

			List<Path> files = null;
			NameBuilderParser parser = null;
			try {
				files = Files.list(sourceDir).collect(Collectors.toList());
				parser = new NameBuilderParser(givenPattern);
			} catch (IOException e) {
				env.writeln("IOException in execute subcommand in massrename command. " + e.getMessage());
				return ShellStatus.CONTINUE;
			} catch (IllegalArgumentException e) {
				env.writeln("Exception while parsing the expression.");
				return ShellStatus.CONTINUE;
			}

			
			NameBuilder builder = parser.getNameBuilder();
			pattern = Pattern.compile(mask);

			for (Path file : files) {
				Matcher matcher = pattern.matcher(file.getFileName().toString());

				if (!matcher.find()) {
					continue;
				}
				NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
				builder.execute(info);
				String newName = info.getStringBuilder().toString();
				
				env.writeln(sourceDir.toString() + "/" + file.getFileName() + 
						" => " + destDir.toString() + "/" + newName);
				
				try {
					Files.move(sourceDir.resolve(file.getFileName()).normalize()
							, destDir.resolve(newName).normalize());
				} catch (IOException e) {
					env.writeln("IOException in execute subcommand in massrename command. " + e.getMessage());
					return ShellStatus.CONTINUE;
				}
			}

		} else {
			env.writeln("Invalid command type for massrename!");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
