package org.breizhcamp.sessionize.service.sessionize;


import org.breizhcamp.sessionize.model.sessionize.all.All;

import org.breizhcamp.sessionize.model.sessionize.all.Speaker;
import org.breizhcamp.sessionize.model.sessionize.grid.Grid;
import org.breizhcamp.sessionize.model.sessionize.session.Sessions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SessionizeService {

    private static final String SESSIONIZE_BASE_URL = "https://sessionize.com/api/v2/";

    @Value( "${sessionize_id}" )
    public String sessionizeId;

    private String getSessionsListApiEndpoint(){
        return SESSIONIZE_BASE_URL + this.sessionizeId +"/view/Sessions";
    }
    private String getAllApiEndpoint(){
        return  SESSIONIZE_BASE_URL + this.sessionizeId +"/view/All";
    }
    private String getSpeakerEndpoint(){ return  SESSIONIZE_BASE_URL + this.sessionizeId +"/view/Speakers";}
    private String getGridApiEndpoint(){ return  SESSIONIZE_BASE_URL + this.sessionizeId + "/view/GridSmart";}

    public List<Sessions> getSessions(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Sessions>> response =
                restTemplate.exchange(
                        getSessionsListApiEndpoint() ,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Sessions>>() {
                        });
        List<Sessions> sessions = response.getBody();
        return sessions;
    }

    public All getAll(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<All> response =
                restTemplate.exchange(
                        getAllApiEndpoint(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<All>() {
                        });
        All all = response.getBody();
        return all;
    }

    public List<Speaker> getSpeakers(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Speaker>> response =
                restTemplate.exchange(
                        getSpeakerEndpoint() ,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Speaker>>() {
                        });
        List<Speaker> speakers = response.getBody();
        return speakers;
    }

    public List<Grid> getGrids(){
        RestTemplate restTemplate = new RestTemplate();
        String a = getGridApiEndpoint();
        ResponseEntity<Grid[]> response =
             restTemplate.exchange(
                     getGridApiEndpoint(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Grid[]>() {
                        });
        return Arrays.asList(response.getBody());
    }
}