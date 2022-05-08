package org.breizhcamp.sessionize.model.sessionize.all;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class All {
    public List<Category> categories;
    public List<Speaker> speakers;
}
