package org.breizhcamp.sessionize.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.breizhcamp.sessionize.model.sessionize.all.All;
import org.breizhcamp.sessionize.model.sessionize.all.Link;
import org.breizhcamp.sessionize.model.sessionize.grid.Grid;
import org.breizhcamp.sessionize.model.sessionize.grid.Room;
import org.breizhcamp.sessionize.model.sessionize.session.Session;
import org.breizhcamp.sessionize.model.sessionize.session.Sessions;
import org.breizhcamp.sessionize.model.website.Speaker;
import org.breizhcamp.sessionize.service.sessionize.SessionizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SpeakerService {

    @Autowired
    private SessionizeService sessionizeService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Integer export(String filename) {

       Collection<Speaker> websiteSpeakers = new HashSet<Speaker>();

       List<Session> allSessions = sessionizeService.getSessions().get(0).getSessions();
       List<Session> acceptedSessions = allSessions.stream()
               .filter(session -> "Accepted".equals(session.getStatus()))
               .collect(Collectors.toList());

       Map<String, org.breizhcamp.sessionize.model.sessionize.all.Speaker> speakersMap = sessionizeService.getSpeakers()
               .stream()
               .collect(Collectors.toMap(org.breizhcamp.sessionize.model.sessionize.all.Speaker::getId, Function.identity(),
                       (o1, o2) -> o1, HashMap::new));

       List<org.breizhcamp.sessionize.model.sessionize.all.Speaker> speakers =  sessionizeService.getSpeakers();

       for(Session session : acceptedSessions){
           for( org.breizhcamp.sessionize.model.sessionize.session.Speaker s : session.getSpeakers()){
               org.breizhcamp.sessionize.model.sessionize.all.Speaker speaker = speakersMap.get(s.getId());
               Speaker websiteSpeaker = new Speaker();
               websiteSpeaker.setId(speaker.getId());
               websiteSpeaker.setFirstname(speaker.getFirstName());
               websiteSpeaker.setLastname(speaker.getLastName());
               websiteSpeaker.setBio(speaker.getBio());
               websiteSpeaker.setImageProfilURL(speaker.getProfilePicture());

               if(!speaker.getLinks().isEmpty()){
                   for(Link link : speaker.getLinks()){
                       if("Twitter".equals(link.getLinkType())) {
                           websiteSpeaker.setTwitter(link.getUrl().replace("https://twitter.com/", "@"));
                       }
                       if("LinkedIn".equals(link.getLinkType())) {
                           websiteSpeaker.setSocial(link.getUrl());
                       }
                   }
               }

               websiteSpeakers.add(websiteSpeaker);
           }
       }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(Paths.get(filename).toFile(),
                    websiteSpeakers.stream().sorted((speaker1, speaker2) -> speaker1.getLastname().compareToIgnoreCase(speaker2.getLastname())).collect(Collectors.toList()));
        } catch (IOException e) {
            return 1;
        }
        return 0;
    }
}
