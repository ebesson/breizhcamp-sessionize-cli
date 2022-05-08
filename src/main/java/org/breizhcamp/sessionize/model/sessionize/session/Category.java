package org.breizhcamp.sessionize.model.sessionize.session;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Category {

    Integer id;
    String name;
    Integer sort;
    List<CategoryItem> categoryItems;
}
