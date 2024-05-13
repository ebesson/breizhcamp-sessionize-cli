package org.breizhcamp.sessionize.command;

import org.breizhcamp.sessionize.model.sessionize.all.Speaker;
import org.breizhcamp.sessionize.service.ScheduleService;
import org.breizhcamp.sessionize.service.SpeakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Component
@Command(name = "speaker", description = "This command generate the list of speaker")
public class SpeakerCommand extends BaseCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeakerCommand.class);


    @CommandLine.Option(names = {"-f","--filename"}, description = "speakers filename (default: speaker.json)", defaultValue = "speakers.json",  required = false)
    public String filename;

    @Autowired
    public SpeakerService speakerService;

    public Integer call() throws Exception {
        LOGGER.info("Export speakers to JSON");
        if (path == null){
            path = System.getenv("PWD");
        }
        return speakerService.export(filename);

    }
}
