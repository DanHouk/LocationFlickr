package com.houkcorp.locationflickr.model.handler;

import com.houkcorp.locationflickr.model.FlickrImage;
import com.houkcorp.locationflickr.model.FlickrImageHolder;
import com.houkcorp.locationflickr.model.PhotosData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FlickrImageHandler {
    public FlickrImageHolder parse(InputStream inputStream) throws XmlPullParserException,
            IOException, ParserConfigurationException, SAXException {
        FlickrImageHolder flickrImageHolder = new FlickrImageHolder();
        PhotosData photosData = new PhotosData();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList rspNodeList = document.getElementsByTagName("rsp");
            Element rspElement = (Element)rspNodeList.item(0);
            String stat = rspElement.getAttribute("stat");
            if(stat.equals("ok")) {
                NodeList photosNodeList = rspElement.getElementsByTagName("photos");
                Element photosElement = (Element)photosNodeList.item(0);
                photosData.setPage(Integer.parseInt(photosElement.getAttribute("page")));
                photosData.setPages(Integer.parseInt(photosElement.getAttribute("pages")));
                photosData.setPerPage(Integer.parseInt(photosElement.getAttribute("perpage")));
                photosData.setTotal(Integer.parseInt(photosElement.getAttribute("total")));
                flickrImageHolder.setPhotosData(photosData);
                NodeList photoNodeList = photosElement.getElementsByTagName("photo");
                for(int i = 0; i < photoNodeList.getLength(); i++) {
                    FlickrImage flickrImage = new FlickrImage();
                    Element photoElement = (Element)photoNodeList.item(i);
                    flickrImage.setFarm(Integer.parseInt(photoElement.getAttribute("farm")));
                    flickrImage.setId(photoElement.getAttribute("id"));
                    flickrImage.setOwner(photoElement.getAttribute("owner"));
                    flickrImage.setSecret(photoElement.getAttribute("secret"));
                    flickrImage.setServer(Integer.parseInt(photoElement.getAttribute("server")));

                    flickrImageHolder.addPhoto(flickrImage);
                }
            }

            return flickrImageHolder;
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
    }
}