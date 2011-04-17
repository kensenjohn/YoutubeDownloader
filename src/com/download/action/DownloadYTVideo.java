package com.download.action;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * This will download Youtube file from the given URL.
 * Usage : DownloadYTVideo Youtube_URL1 [ Youtube_URL2]
 * 
 * If you tube decides to change the format of the html, this will stop working.
 * 
 * The extension is hardcoded to ".mp4"
 * VLC Player can be used to run the video.
 * 
 * @version 1.0
 *
 */
public class DownloadYTVideo {

	public static void main(String[] args) 
	{
        try {

        		if(args.length == 1)
        		{
        			System.out.println("Usage: DownloadYTVideo URL1 [URL2]");
        		}
        		
        		DownloadYTVideo downloader = new DownloadYTVideo();
        		ArrayList<String> arrURLList = downloader.getListOfURLs(args);
        		
        		downloader.invokeDownloader(arrURLList,"/home/kjohn");
                
            }
            catch ( Exception e ) {

                System.err.println( e.getMessage() );
            }
        
    }
	
	/**
	 * Get list of URLs from the invocation parameters.
	 * @param args
	 * @return
	 */
	private ArrayList<String> getListOfURLs(String[] args)
	{
		ArrayList<String> arrURLList = new ArrayList<String>();
		
		for(String sUrl : args)
		{
			arrURLList.add(sUrl);
		}
		return arrURLList;
	}
	
	public String invokeDownloader(ArrayList<String> arrUrls, String sDownloadLocation)
	{
		String sMessage = "";
		Integer urlNum = 1;
		for(String sUrls : arrUrls)
		{
			System.out.println("Downloading " + sUrls + " (" + urlNum + " of " + arrUrls.size()+")");
			
			Youtube youtube = getYoutubeObj(sUrls);
			
			sMessage = downloadVideo(youtube,sDownloadLocation);
			
			urlNum++;
		}
		return sMessage;
	}
	
	public String invokeDownloader(String sUrl, String sDownloadLocation)
	{
		String sMessage = "";
		Youtube youtube = getYoutubeObj(sUrl);
		sMessage = downloadVideo(youtube,sDownloadLocation);
		return sMessage;
	}
	
	/**
	 * This method will do the actual download of the stream
	 * The location to where it has to be downloaded has been hard coded.
	 * This can be changed or passed as a parameter.
	 * @param youtube
	 */
	private String downloadVideo(Youtube youtube, String sDownloadLocation)
	{
		String sErrorMessg = "";
        try {
        	
        	URL urlVideoURL = new URL(youtube.getDownLoadURL());
			
            InputStream inStreamVideo = urlVideoURL.openStream();
            
            OutputStream outStreamVideo = new BufferedOutputStream(new FileOutputStream(sDownloadLocation+"/"+youtube.getFileName()+".mp4"));
            for (int b; (b = inStreamVideo.read()) != -1; ) {
            	outStreamVideo.write(b);
            }
            outStreamVideo.close();
            inStreamVideo.close();
        	
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        catch (FileNotFoundException e )
        {
        	sErrorMessg = "NoPerm";
        }
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sErrorMessg = "IOException";
		}
        return sErrorMessg;
	}
	
	/**
	 * This will open a normal Youtube video, and parse through the html to retrieve the the stream URL and title name.
	 * @param sUserYoutubeURl
	 * @return
	 */
	private Youtube getYoutubeObj(String sUserYoutubeURl)
	{
		Youtube youtube = new Youtube(sUserYoutubeURl);
		try {
			URL youtubeVideo = new URL(sUserYoutubeURl);
			BufferedReader in = new BufferedReader(new InputStreamReader(	youtubeVideo.openStream()));
			
			String inputLine = "";
			boolean isDownUrlFound = false;
			boolean isTitleFound = false;
			while ((inputLine = in.readLine()) != null)
			{
				if(inputLine.contains("generate_204") && !isDownUrlFound)
				{	// Retireve the Stream URL and clean up the file.
					System.out.println(inputLine);
					String sVideoURL = inputLine.substring( (inputLine.indexOf("http:")+9) , inputLine.indexOf("\";") );
					
					sVideoURL = "http://" + sVideoURL;
					
					sVideoURL = sVideoURL.replace("\\/", "/");
					sVideoURL = sVideoURL.replace("%2C", ",");
					sVideoURL = sVideoURL.replace("\\u0026", "&");
					sVideoURL = sVideoURL.replace("generate_204", "videoplayback"); //if we dont replace this, no download takes place.
					
					youtube.setDownLoadURL(sVideoURL);
					isDownUrlFound = true;
				}
				else if(inputLine.contains("<meta name=\"title\"") && !isTitleFound)
				{
					//retrieve the title name.
					
					inputLine = inputLine.trim();
					String sVideoTitle = inputLine.substring( (inputLine.indexOf("<meta name=\"title\" content=\"")+28) , inputLine.indexOf("\">") );
					
					sVideoTitle = sVideoTitle.replace(" ", "_");
					youtube.setFileName(sVideoTitle);
					isTitleFound = true;
				}
				
				if(isDownUrlFound && isTitleFound)
				{	//need not parse the whole file if the required information is found.
					break;
				}
			}
			
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return youtube;
	}

}
