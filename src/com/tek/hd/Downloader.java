package com.tek.hd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Downloader {
	
	public static final String XML_TABULA = "https://dist.creeper.host/ichun/static/hatstabula.xml";
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		URL url = new URL(XML_TABULA);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStreamReader reader = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(reader);
		
		StringBuffer buffer = new StringBuffer();
		String line;
		
		while((line = br.readLine()) != null) {
			buffer.append(line);
		}
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(buffer.toString());
		document.getDocumentElement().normalize();
	}
	
}
