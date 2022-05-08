package org.breizhcamp.sessionize.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Component
@Command(name = "speaker")
public class SpeakerCommand extends BaseCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeakerCommand.class);


    @CommandLine.Option(names = {"-f","--filename"}, description = "speakers filename (default: speaker.json)", defaultValue = "speakers.json",  required = false)
    public String filename;

    public Integer call() throws Exception {
        LOGGER.info("Export speakers to JSON");
        if (path == null){
            path = System.getenv("PWD");
        }
        return 0;
    }
}
