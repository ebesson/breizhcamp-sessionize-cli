package org.breizhcamp.sessionize.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.breizhcamp.sessionize.model.sessionize.grid.Grid;
import org.breizhcamp.sessionize.model.sessionize.grid.Room;
import org.breizhcamp.sessionize.model.sessionize.session.Speaker;
import org.breizhcamp.sessionize.model.website.Session;
import org.breizhcamp.sessionize.service.sessionize.SessionizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TalkService {

    @Autowired
    private SessionizeService sessionizeService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public Integer export(String filename) {

        List<Session> webSiteSessions = new ArrayList<>();

        List<Grid> grids = sessionizeService.getGrids();
        for (Grid grid : grids) {
            for (Room room : grid.getRooms()) {
                for (org.breizhcamp.sessionize.model.sessionize.session.Session sessionizeSession : room.getSessions()) {
                    Session session = new Session();
                    String speakers = sessionizeSession.getSpeakers().stream()
                            .map(Speaker::getName)
                            .collect(Collectors.joining(", "));

                    session.setId(sessionizeSession.getId());
                    session.setDescription(sessionizeSession.getDescription());
                    session.setName(sessionizeSession.getTitle());
                    session.setVenue(room.getName());
                    session.setSpeakers(speakers);

                    if ("Amphi A".equals(room.getName())) {
                        session.setVenue_id("1");
                    }
                    if ("Amphi B".equals(room.getName())) {
                        session.setVenue_id("2");
                    }
                    if ("Amphi C".equals(room.getName())) {
                        session.setVenue_id("3");
                    }
                    if ("Amphi D".equals(room.getName())) {
                        session.setVenue_id("4");
                    }


                    session.setEventStart(sessionizeSession.getStartsAt().replace("Z", ""));
                    session.setEventEnd(sessionizeSession.getEndsAt().replace("Z", ""));


                    if (!sessionizeSession.getCategories().isEmpty()) {
                        String format = sessionizeSession.getCategories().get(0).getCategoryItems().get(0).getName();
                        session.setFormat(format.replace(" (2h, Mercredi)", "")
                                .replace(" (55 min)", "")
                                .replace(" (25 min)", "")
                                .replace(" (15 min)", ""));
                        session.setEventType(sessionizeSession.getCategories().get(1).getCategoryItems().get(0).getName());
                    }
                    if (!sessionizeSession.getCategories().isEmpty()) {
                        webSiteSessions.add(session);
                    }
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(Paths.get(filename).toFile(), webSiteSessions);
        } catch (IOException e) {
            return 1;
        }
        return 0;
    }
}
