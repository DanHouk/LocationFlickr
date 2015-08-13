package com.houkcorp.locationflickr.model.handler;

import com.houkcorp.locationflickr.model.ImageMetaData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FlickrMetaDataHandler {
    public ImageMetaData parse(InputStream inputStream) throws ParserConfigurationException,
            IOException, SAXException {
        ImageMetaData imageMetaData = new ImageMetaData();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList rspNodeList = document.getElementsByTagName("rsp");
            Element rspElement = (Element)rspNodeList.item(0);
            String stat = rspElement.getAttribute("stat");
            if(stat.equals("ok")) {
                NodeList photoNodeList = rspElement.getElementsByTagName("photo");
                Element photoElement = (Element)photoNodeList.item(0);
                imageMetaData.setSecret(photoElement.getAttribute("secret"));
                imageMetaData.setOriginalSecret(photoElement.getAttribute("originalsecret"));
                NodeList ownerNodeList = photoElement.getElementsByTagName("owner");
                Element ownerElement = (Element)ownerNodeList.item(0);
                imageMetaData.setUserName(ownerElement.getAttribute("username"));
                imageMetaData.setRealName(ownerElement.getAttribute("realname"));
                NodeList titleNodeList = photoElement.getElementsByTagName("title");
                Element titleElement = (Element)titleNodeList.item(0);
                imageMetaData.setTitle(titleElement.getTextContent());
                NodeList datesNodeList = photoElement.getElementsByTagName("dates");
                Element dateElement = (Element)datesNodeList.item(0);
                long tempLong = Long.parseLong(dateElement.getAttribute("posted")) * 1000;
                imageMetaData.setPostedDate(new Date(tempLong));
                imageMetaData.setTakenDate(photoElement.getAttribute("taken"));
                NodeList tagsNodeList = dateElement.getElementsByTagName("tags");
                for(int i = 0; i < tagsNodeList.getLength(); i++) {
                    Element tagElement = (Element)tagsNodeList.item(i);
                    imageMetaData.addTag(tagElement.getElementsByTagName("tags").item(0)
                            .getTextContent());
                }
            }

            return imageMetaData;
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
    }
}