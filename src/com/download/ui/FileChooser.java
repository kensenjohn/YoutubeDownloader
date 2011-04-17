package com.download.ui;

import java.io.FilePermission;
import java.security.AccessController;

import javax.swing.JFileChooser;

public class FileChooser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
	/**
	 * This will provide an interface to select the folder to download.
	 * @return
	 */
	public String getFolderLocation()
	{
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    String sFolder = "";
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	sFolder = chooser.getSelectedFile().getAbsolutePath();
	    	//System.out.println("sFolder = " + sFolder);
	    	//System.out.println("chooser = " + chooser);
	    	
	    } else {
	    	sFolder = "None";
	    }
	    
	    return sFolder;
	}
	
	public boolean filePermission(String sPath)
	{
		
		boolean isAccessAvailable = false;
		try {
			AccessController.checkPermission(new FilePermission(sPath, "read,write"));
			isAccessAvailable = true;
		}
		catch(SecurityException e) {
			isAccessAvailable = false;
			//e.printStackTrace();
		}
		
		return isAccessAvailable;
	}

}
