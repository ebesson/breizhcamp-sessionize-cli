package org.breizhcamp.sessionize.command;

import picocli.CommandLine;

import java.util.concurrent.Callable;

public abstract class BaseCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-d","--directory"}, description = "output directory (default: current directory)", required = false)
    public String path;

    @CommandLine.Option(names = {"?", "-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    protected String getPath(){
        if (path == null){
            path = System.getenv("PWD");
        }
        return path;
    }

}
