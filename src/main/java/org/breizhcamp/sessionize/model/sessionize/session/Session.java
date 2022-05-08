package org.breizhcamp.sessionize.model.sessionize.session;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Session {

    public String id;
    public String title;
    public String description;
    public List<Speaker> speakers;
    public List<Category> categories;
}
