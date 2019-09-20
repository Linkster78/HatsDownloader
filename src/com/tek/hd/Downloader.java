package com.tek.hd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Downloader {
	
	public static final String XML_TABULA = "https://www.creeperrepo.net/ichun/static/hats.xml";
	
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
		
		File hatsFolder = new File("hats");
		hatsFolder.mkdir();
		new File(hatsFolder, "Favourites").mkdir();
		
		InputSource is = new InputSource(new StringReader(buffer.toString()));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(is);
		document.getDocumentElement().normalize();
		
		NodeList fileList = document.getElementsByTagName("File");
		
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		List<CompletableFuture<Void>> futures = new ArrayList<CompletableFuture<Void>>(fileList.getLength() / 2);
		
		for(int i = 0; i < fileList.getLength(); i++) {
			Element fileNode = (Element) fileList.item(i);
			String path = fileNode.getElementsByTagName("Path").item(0).getTextContent();
			String cdnUrl = fileNode.getElementsByTagName("URL").item(0).getTextContent().replace("http", "https");
			int size = Integer.parseInt(fileNode.getElementsByTagName("Size").item(0).getTextContent());
			
			if(!cdnUrl.endsWith(".md5")) {
				CompletableFuture<Void> vFuture = new CompletableFuture<Void>();
				futures.add(vFuture);
				
				threadPool.execute(() -> {
					HttpURLConnection downloadConn;
					try {
						downloadConn = (HttpURLConnection) new URL(cdnUrl.replaceAll(" ", "%20")).openConnection();
						downloadConn.setConnectTimeout(15000);
						downloadConn.setReadTimeout(15000);
						File hatFile = new File(hatsFolder, path);
						hatFile.mkdirs();
						Files.copy(downloadConn.getInputStream(), Paths.get(hatFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
						
						if(hatFile.length() != size) {
							System.out.println("Error while downloading " + path);
						} else {
							System.out.println("Downloaded " + path);
						}
						
						vFuture.complete(null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
		
		while(true) {
			boolean done = true;
			for(CompletableFuture<Void> future : futures) {
				if(!future.isDone()) done = false;
			}
			if(done) break;
		}
		
		threadPool.shutdown();
		
		System.out.println("\n-----------------\nDone downloading!\n-----------------");
	}
	
}
