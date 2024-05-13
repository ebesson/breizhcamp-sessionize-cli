package org.breizhcamp.sessionize;

import org.breizhcamp.sessionize.command.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@SpringBootApplication
public class BreizhcampCliApplication implements CommandLineRunner, ExitCodeGenerator {

	private IFactory factory;
	private CardGeneratorCommand cardGeneratorCommand;
	private SpeakerCommand speakerGeneratorCommand;
	private ScheduleCommand scheduleCommand;
	private TalkCommand talkCommand;
	private int exitCode;

	// constructor injection
	BreizhcampCliApplication(IFactory factory, CardGeneratorCommand cardGeneratorCommand,
							 SpeakerCommand speakerGeneratorCommand, ScheduleCommand scheduleCommand,
							 TalkCommand talkCommand) {
		this.factory = factory;
		this.cardGeneratorCommand = cardGeneratorCommand;
		this.speakerGeneratorCommand = speakerGeneratorCommand;
		this.scheduleCommand = scheduleCommand;
		this.talkCommand = talkCommand;

	}
	@Override
	public void run(String... args) {
		// let picocli parse command line args and run the business logic
		CommandLine commandLine = new CommandLine(new EmptyCommand());
		commandLine.addSubcommand("card", cardGeneratorCommand);
		commandLine.addSubcommand("speaker", speakerGeneratorCommand);
		commandLine.addSubcommand("schedule", scheduleCommand);
		commandLine.addSubcommand("talk", talkCommand);

		commandLine.parseArgs(args);
		if (commandLine.isUsageHelpRequested()) {
			commandLine.usage(System.out);
			return;
		}
		else
		{
			exitCode = commandLine.execute(args);
		}
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}

	public static void main(String[] args) {
		// let Spring instantiate and inject dependencies
		System.exit(SpringApplication.exit(SpringApplication.run(BreizhcampCliApplication.class, args)));
	}
}
