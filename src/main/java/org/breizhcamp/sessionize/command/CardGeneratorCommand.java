package org.breizhcamp.sessionize.command;

import org.breizhcamp.sessionize.service.PdfCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@Command(name = "card")
public class CardGeneratorCommand extends BaseCommand {


    private static final Logger LOGGER = LoggerFactory.getLogger(CardGeneratorCommand.class);


    @CommandLine.Option(names = {"-f","--filename"}, description = "card filename (default: cards.pdf)", defaultValue = "cards.pdf",  required = false)
    public String filename;

    @Autowired
    public PdfCardService pdfCardService;

    public Integer call() throws Exception {

        FileOutputStream output = new FileOutputStream( getPath() +File.separator + filename);
        pdfCardService.export(output);
        return 0;
    }
}
