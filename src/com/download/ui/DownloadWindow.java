package com.download.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.download.action.DownloadYTVideo;

/**
 * This class will generate the view using Swing.
 * This also has the action listeners for every button that is used.
 * Does some minor level of validation.
 * @author kensen
 *
 */
public class DownloadWindow extends JPanel {

	 JTextField urlTextField = new JTextField();
	 JTextArea urlListArea = new JTextArea(18, 50);
	 
	 JTextField browseTextField = new JTextField();
	 
	 JLabel statusLabel = new JLabel("", JLabel.LEFT);
	 
	 JFrame mainFrame = new JFrame("Youtube Downloader");
	 String sNewUrl = "";
	 
	 /**
	  * Method will create the UI for the downloader.
	  * 1) This will have a textbox where you can enter the URL
	  * 2) Download location
	  * 3) List of files to be deleted
	  * 4) Download button.
	  */
	public void display()
	{
	    JPanel urlLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    urlLabelPanel.setPreferredSize(new Dimension(100, 35));
	    JPanel urlFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    urlFieldPanel.setPreferredSize(new Dimension(300, 35));
	    JPanel urlAddButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    urlAddButtonPanel.setPreferredSize(new Dimension(100, 35));
	    
	    
	    JPanel browseLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    urlLabelPanel.setPreferredSize(new Dimension(100, 35));
	    JPanel browseFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    urlFieldPanel.setPreferredSize(new Dimension(300, 35));
	    JPanel browseButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    urlAddButtonPanel.setPreferredSize(new Dimension(100, 35));
	    

	    JPanel statusLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    statusLabelPanel.setPreferredSize(new Dimension(300, 35));
	    
	    //add(urlLabelPanel, BorderLayout.WEST);
	    //add(urlFieldPanel, BorderLayout.CENTER);
	    //add(urlAddButtonPanel, BorderLayout.EAST);
	    
	    JLabel urlLabel = new JLabel("URL", JLabel.RIGHT);
	    JLabel downloadLocationLabel = new JLabel("Download Folder", JLabel.RIGHT);
	   
	    urlTextField.setColumns(300);
	    browseTextField.setColumns(20);
	    
	    
	    urlListArea.setBorder(BorderFactory.createLoweredBevelBorder());
	    urlListArea.setEditable(false);
	    
	    JButton urlAddButton = new JButton("Add");
	    urlAddButton.addActionListener(addButtonListener);
	    
	    
	    JButton downloadButton = new JButton("Start Download");
	    downloadButton.addActionListener(downloadButtonListener);
	    
	    JButton clearButton = new JButton("Clear");
	    clearButton.addActionListener(clearButtonListener);
	    
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(clearButtonListener);
	    
	    JButton browseButton = new JButton("Browse");
	    browseButton.addActionListener(browseButtonListener);

	    
	    urlLabel.setLabelFor(urlTextField);
	    urlLabelPanel.add(urlLabel);
	    urlFieldPanel.add(urlTextField);
	    
	    urlAddButtonPanel.add(urlAddButton);
	    
	    JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    urlPanel.add(urlLabelPanel);
	    urlPanel.add(urlFieldPanel);
	    urlPanel.add(urlAddButtonPanel);
	    
	    downloadLocationLabel.setLabelFor(browseTextField);
	    browseLabelPanel.add(downloadLocationLabel);
	    browseFieldPanel.add(browseTextField);
	    browseButtonPanel.add(browseButton);
	    
	    
	    JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    browsePanel.add(browseLabelPanel);
	    browsePanel.add(browseFieldPanel);
	    browsePanel.add(browseButtonPanel);
	    
	    JPanel urlListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    urlListPanel.add(urlListArea);
	    
	    statusLabelPanel.add(statusLabel);
	    
	    JPanel urlDownloadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    urlDownloadPanel.add(statusLabelPanel);
	    urlDownloadPanel.add(downloadButton);
	    urlDownloadPanel.add(clearButton);
	   // urlDownloadPanel.add(cancelButton);
	    
	    
	    JPanel innerPannel = new JPanel();
	    innerPannel.setLayout(new BoxLayout(innerPannel, BoxLayout.PAGE_AXIS));
	    innerPannel.add(urlPanel);
	    innerPannel.add(browsePanel);
	    innerPannel.add(urlListPanel);
	    innerPannel.add(urlDownloadPanel);
		
		
		 mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		 mainFrame.getContentPane().add(innerPannel, BorderLayout.NORTH);
		 mainFrame.setPreferredSize(new Dimension(600, 450));
		 mainFrame.pack();
		 mainFrame.setVisible(true);
	}
	
	/**
	 *
	 * Invoke code to retrieve the folder location where the videos have to to be downloaded to.
	 * If no folder location is returned, nothing happens.
	 */
	ActionListener browseButtonListener = new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 	FileChooser fileChooser = new FileChooser();
			 	String sFolder = fileChooser.getFolderLocation();
			 	
			 	if("None".equalsIgnoreCase(sFolder))
			 	{
			 		
			 	}
			 	else
			 	{
			 		browseTextField.setText(sFolder);
			 	}
			 }
	};
	
	/**
	 * Listener for clear button, will clear the arraylist.
	 * Will clear the display.
	 */
	ActionListener clearButtonListener = new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 	arrUrlList.clear();
			 	urlListArea.setText("");
			 }
	};
	
	/**
	 * Do validation, check to see if the download location is specified.
	 * Check to see if there are any videos to download.
	 * Invoke the download video API if all validation is statisfied.
	 */
	ActionListener downloadButtonListener = new ActionListener() {
		 public void actionPerformed(ActionEvent e) {

     		
     		String sDownloadLocation = browseTextField.getText();
     		if(arrUrlList.size()==0)
     		{
     			showAlertMessage("Please add a video to be download.");
     		}
     		else if(sDownloadLocation!=null && !"".equalsIgnoreCase(sDownloadLocation))
     		{
     			downloadVideo(sDownloadLocation);
         		
     		}
     		else
     		{
     			showAlertMessage("Please select a folder to which the video has to be downloaded");
     		}
		 }
	};
	/**
	 * Add URLs to to the Download Queue
	 */
	ActionListener addButtonListener = new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  String sNewUrl = urlTextField.getText();
	    	  if(arrUrlList.size()<10)
	    	  {
		    	  if(addUrlToList(sNewUrl))
		    	  {
		    		  urlTextField.setText("");
		    		  resetUrlListDisplay(sNewUrl);
		    	  }
	    	  }
	    	  else
	    	  {
	    		  showAlertMessage("You can only download 10 videos at a time.");
	    	  }
	      }
	};
	
	/**
	 * Iterate through list of URLs.
	 * Invoke the downloader one at a time with each URL.
	 * This will also display error message in case the folder has no write permission.
	 * @param sDownloadLocation
	 */
	public void downloadVideo(String sDownloadLocation)
	{
		DownloadYTVideo downloader = new DownloadYTVideo();
		Integer urlNum = 1;
		boolean isError = false;
		for(String sUrls : arrUrlList)
		{
			String sMessage = "";
			statusLabel.setText("Downloading " + urlNum + " of " + arrUrlList.size());
			sMessage = downloader.invokeDownloader(sUrls,sDownloadLocation);
			
			if("NoPerm".equalsIgnoreCase(sMessage))
     		{
     			showAlertMessage("You do not have access to download the video to this folder.\nPlease select another folder.");
     			isError = true;
     		}
			
			if(isError)
			{
				break;
			}
			urlNum++;
		}
		if(isError)
		{
			statusLabel.setText("There was an error during download.");
		}
		else
		{
			statusLabel.setText("Download Complete.");
		}
		
	}
	ArrayList<String> arrUrlList = new ArrayList<String>();
	
	/**
	 * This will add list of URLS to the display list window.
	 * 
	 * @param sUrl
	 * @return
	 */
	public boolean addUrlToList(String sUrl)
	{
		boolean isURlExist = false;
		boolean isUrlAdded = false;
		for(String sIndivUrl:arrUrlList)
		{
			if(sIndivUrl.equalsIgnoreCase(sUrl))
			{
				isURlExist = true;
				break;
			}			
		}
		
		if(!isURlExist)
		{
			isUrlAdded = true;
			arrUrlList.add(sUrl);
		}
		else
		{
			showAlertMessage("This URL already has been added.");
		}
		return isUrlAdded;
	}
	
	public void showAlertMessage(String sMessage)
	{
		JOptionPane.showMessageDialog(mainFrame,
				sMessage,
			    "Youtube Downloader Error",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public void resetUrlListDisplay(String sUrl)
	{
		//urlListArea.repaint();
		urlListArea.append(sUrl+"\n");
	}
}
