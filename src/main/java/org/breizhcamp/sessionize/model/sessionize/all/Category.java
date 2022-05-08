package org.breizhcamp.sessionize.model.sessionize.all;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Category {

    Integer id;
    String title;
    List<Item> items;
}
