package org.breizhcamp.sessionize.command;

import org.breizhcamp.sessionize.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Component
@Command(name = "schedule", description = "This command generate the schedule file")
public class ScheduleCommand extends BaseCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCommand.class);


    @CommandLine.Option(names = {"-f","--filename"}, description = "schedule filename (default: schedule.json)", defaultValue = "schedule.json",  required = false)
    public String filename;

    @Autowired
    public ScheduleService scheduleService;

    public Integer call() throws Exception {
        LOGGER.info("Export schedule to JSON");
        if (path == null){
            path = System.getenv("PWD");
        }
        return scheduleService.export(filename);
    }



}
