package com.example.cococ.utils;

import com.example.cococ.data.Description;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class DescriptionConverter implements Converter<Description> {
    @Override
    public Description read(InputNode node) throws Exception {
        Description description = new Description();

        String value = node.getValue();
        if(value != null){
            description.setLink(value);
        }

        InputNode node1 = node.getNext();
        if(node1 != null){
            value = node1.getValue();
            if(value != null){
                description.setImage(value);
            }
        }

        InputNode node2 = node.getNext();
        if(node1 != null){
            value = node1.getValue();
            if(value != null){
                description.setContent(value);
            }
        }
        return description;


//        Description description = new Description();
//
//        final String IMG_SRC_REG_EX = "<img src=([^>]+)>";
//        final String HTML_TAG_REG_EX = "</?[^>]+>";
//
//        String nodeText = node.getValue();
//
//        Pattern imageLinkPattern = Pattern.compile(IMG_SRC_REG_EX);
//        Matcher matcher = imageLinkPattern.matcher(nodeText);
//
//        String link = null;
//        while (matcher.find()) {
//            description.setLink(matcher.group(1));
//        }
//
//        String text = nodeText.replaceFirst(IMG_SRC_REG_EX, "")
//                .replaceAll(HTML_TAG_REG_EX, "");
//        description.setContent(text);
//        return description;
    }

    @Override
    public void write(OutputNode node, Description value) throws Exception {
        Description description = new Description();
    }
}
