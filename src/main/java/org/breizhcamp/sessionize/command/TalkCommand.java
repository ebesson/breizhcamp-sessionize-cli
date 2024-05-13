package org.breizhcamp.sessionize.command;

import org.breizhcamp.sessionize.service.ScheduleService;
import org.breizhcamp.sessionize.service.TalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Component
@Command(name = "talk",  description = "This command generate the file with all talks")
public class TalkCommand extends BaseCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(TalkCommand.class);


    @CommandLine.Option(names = {"-f","--filename"}, description = "talks filename (default: talks.json)", defaultValue = "talks.json",  required = false)
    public String filename;

    @Autowired
    public TalkService talkService;

    public Integer call() throws Exception {
        LOGGER.info("Export talks to JSON");
        if (path == null){
            path = System.getenv("PWD");
        }
        return talkService.export(filename);
    }

}
