package org.breizhcamp.sessionize.model.sessionize.grid;

import lombok.Data;
import lombok.experimental.Accessors;
import org.breizhcamp.sessionize.model.sessionize.grid.Room;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class Grid {

    String date;
    List<Room> rooms;
}
