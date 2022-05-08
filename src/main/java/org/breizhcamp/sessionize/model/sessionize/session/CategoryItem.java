package org.breizhcamp.sessionize.model.sessionize.session;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryItem {
    Integer id;
    String name;
}
