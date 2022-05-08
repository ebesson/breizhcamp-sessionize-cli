package org.breizhcamp.sessionize.command;

import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;
@Command
public class EmptyCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"?", "-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    public Integer call() throws Exception {
        return 0;
    }
}
