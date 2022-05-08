package org.breizhcamp.sessionize.service.mapper;

import org.breizhcamp.sessionize.model.io.cfp.Format;
import org.breizhcamp.sessionize.model.sessionize.Constants;
import org.breizhcamp.sessionize.model.sessionize.all.Category;
import org.breizhcamp.sessionize.model.sessionize.all.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormatMapperService {

    public List<Format> mapFromCategories(List<Category> categories){
        List<Format> formats = new ArrayList<>();
        Category categoryFormat = categories.stream().filter(category -> Constants.FORMAT.equals(category.getTitle())).findFirst().get();
        for (Item item : categoryFormat.getItems()){
            Format format = new Format();
            format.setName(item.getName());
            format.setId(item.getId());
            formats.add(format);
        }
        return formats;
    }
}
