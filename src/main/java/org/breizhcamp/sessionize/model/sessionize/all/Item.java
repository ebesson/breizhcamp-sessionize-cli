package org.breizhcamp.sessionize.model.sessionize.all;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Item {
    Integer id;
    String name;
    Integer sort;
}
