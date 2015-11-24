package imageDownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Grabber {

	/**
	 * @param args
	 */

	private void writeFile(InputStream is, OutputStream os) throws IOException {
	    byte[] buf = new byte[512]; // optimize the size of buffer to your need
	    int num;
	    while ((num = is.read(buf)) != -1) {
	      os.write(buf, 0, num);
	    }
	}
	
	public static ArrayList<String> filenameArray = new ArrayList<String>();
	Document doc;
	
	public void updateFileNames()
	{
		try 
		{
			String webaddress = GUI.url;
			
			//get all images
			doc = Jsoup.connect(webaddress).get();
			// selector uses CSS selector with regular expression
			
			Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			for (Element image : images) 
			{
				String urlstr = image.attr("src");
				//System.out.println(urlstr);
				if(urlstr.indexOf(webaddress)<=0)
					urlstr = webaddress + urlstr;
				//System.out.println(urlstr);

				String fileName = urlstr.substring( urlstr.lastIndexOf('/')+1, urlstr.length() );
				//System.out.println(fileName);
				
				filenameArray.add(fileName);
				
				System.out.println("filenameArray is " + filenameArray.size() + " elements big!");
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		
		try 
		{
			String webaddress = GUI.url;
			String folderPath = GUI.path + "/";
			
			System.out.println("URL is: " + webaddress + "\n" + "Folder path is: " + folderPath + "\n");
			
			//get all images
			doc = Jsoup.connect(webaddress).get();
			// selector uses CSS selector with regular expression
			Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			for (Element image : images) 
			{
				String urlstr = image.attr("src");
				System.out.println(urlstr);
				if(urlstr.indexOf(webaddress)<=0)
					urlstr = webaddress + urlstr;
				System.out.println(urlstr);

				String fileName = urlstr.substring( urlstr.lastIndexOf('/')+1, urlstr.length() );
				System.out.println(fileName);
				
				filenameArray.add(fileName);
				
				 //Open a URL Stream
				URL url = new URL(urlstr);
				InputStream in = url.openStream();
				OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath + fileName));
				for (int b; (b = in.read()) != -1;) 
				{
					out.write(b);
				}
				
				out.close();
				in.close();
			}
			
			System.out.println("in the array list is the following");
			for (int i = 0; i < filenameArray.size(); i++) {
				System.out.println(i + " image: " + filenameArray.get(i));
			}
	 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
       
	}

}

