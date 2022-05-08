package org.breizhcamp.sessionize.service;

import org.breizhcamp.sessionize.model.sessionize.Evalution;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.map.property.RenameProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluationService.class);
    public List<Evalution> extractFromCsvFiles(List<File> files) throws IOException {
        List<Evalution> evalutions = new ArrayList<>();

        for(File file : files){
            CsvParser
                    .mapTo(Evalution.class)
                    .columnProperty("Rank", new RenameProperty("rank"))
                    .columnProperty("Session Id", new RenameProperty("sessionId"))
                    .columnProperty("Session", new RenameProperty("session"))
                    .columnProperty("Average Rating", new RenameProperty("averageRating"))
                    .columnProperty("Strong Opinions", new RenameProperty("strongOpinions"))
                    .forEach(file, row -> evalutions.add(row));
        }
        LOGGER.info("{} evalutions found", evalutions.size());
        return evalutions;
    }
}
