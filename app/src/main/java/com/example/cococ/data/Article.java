package com.example.cococ.data;

import com.example.cococ.utils.DescriptionConverter;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name = "item", strict = false)
public class Article {
    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "description")
    private String description;

//    @Element(name="description", required = false)
//    @Convert(DescriptionConverter.class)

//    @Element(name="description", required = false)
//    private Description description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public Description getDescription() {
//        return description;
//    }
//
//    public void setDescription(Description description) {
//        this.description = description;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
