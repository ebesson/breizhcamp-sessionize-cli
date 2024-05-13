package org.breizhcamp.sessionize.model.sessionize.grid;


import lombok.Data;
import lombok.experimental.Accessors;
import org.breizhcamp.sessionize.model.sessionize.session.Session;

import java.util.List;

@Data
@Accessors(chain = true)
public class Room {

    Integer id;
    String name;
    List<Session> sessions;
}
