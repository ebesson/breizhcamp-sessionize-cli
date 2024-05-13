package org.breizhcamp.sessionize.command;

import org.breizhcamp.sessionize.model.sessionize.Evalution;
import org.breizhcamp.sessionize.service.EvaluationService;
import org.breizhcamp.sessionize.service.PdfCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@Command(name = "card", description = "This command generate the pdf file with all cards")
public class CardGeneratorCommand extends BaseCommand {


    private static final Logger LOGGER = LoggerFactory.getLogger(CardGeneratorCommand.class);


    @CommandLine.Option(names = {"-f","--filename"}, description = "card filename (default: cards.pdf)", defaultValue = "cards.pdf",  required = false)
    public String filename;

    @CommandLine.Option(names = {"-evalDir","--evaluations-directory"}, description = "directory which csv files (default: current directory/evaluations)", required = false)
    public String evaluationsDirectory;

    @CommandLine.Option(names = {"-c","--conferences"}, description = "Evaluation file of conferences (default: conferences.csv)", defaultValue = "conferences.csv", required = false)
    public String conferencesFile;

    @CommandLine.Option(names = {"-q","--quickies"}, description = "Evaluation file of quickies (default: quickies.csv)", defaultValue = "quickies.csv", required = false)
    public String quickiesFile;

    @CommandLine.Option(names = {"-u","--universities"}, description = "Evaluation file of universities (default: universities.csv)", defaultValue = "universities.csv", required = false)
    public String universitiesFile;

    @CommandLine.Option(names = {"-tia","--tool-in-action"}, description = "Evaluation file of tool in action (default: tia.csv)", defaultValue = "tia.csv", required = false)
    public String tiaFile;

    @Autowired
    public PdfCardService pdfCardService;
    @Autowired
    public EvaluationService evaluationService;

    public Integer call() throws Exception {

        FileOutputStream cardFile = new FileOutputStream( getPath() +File.separator + filename);
        List<File> evalutonFiles = new ArrayList<>();

        if (evaluationsDirectory == null){
            evaluationsDirectory = System.getenv("PWD") + File.separator + "evaluations";
        }
        evalutonFiles.add(new File(evaluationsDirectory + File.separator + conferencesFile));
        evalutonFiles.add(new File(evaluationsDirectory + File.separator + quickiesFile));
        evalutonFiles.add(new File(evaluationsDirectory + File.separator + universitiesFile));
        evalutonFiles.add(new File(evaluationsDirectory + File.separator + tiaFile));

        List<Evalution> evalutions = evaluationService.extractFromCsvFiles(evalutonFiles);
        pdfCardService.export(evalutions, cardFile);
        return 0;
    }
}
