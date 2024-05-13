package org.breizhcamp.sessionize.command;

import picocli.CommandLine;

@CommandLine.Command(name = "breizhcamp-sessionize-cli",
        subcommands = { CardGeneratorCommand.class, SpeakerCommand.class,ScheduleCommand.class, TalkCommand.class},
        description = "breizhcamp-sessionize-cli")
public class Autocomplete extends BaseCommand {



    @Override
    public Integer call() throws Exception {
        return null;
    }
}
