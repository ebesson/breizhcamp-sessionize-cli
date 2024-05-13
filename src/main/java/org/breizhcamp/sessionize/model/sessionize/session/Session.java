package org.breizhcamp.sessionize.model.sessionize.session;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class Session {

    public String id;
    public String title;
    public String description;
    public String startsAt;
    public String endsAt;
    public List<Speaker> speakers;
    public List<Category> categories = new ArrayList<>();
    public String status;

    public Date getStartAt(){
        return Date.from(Instant.parse(startsAt));
    }

    public Date getEndtAt(){
        return Date.from(Instant.parse(endsAt));
    }
}
