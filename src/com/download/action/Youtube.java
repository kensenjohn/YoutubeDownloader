package com.download.action;

public class Youtube {
	private String sUserUrl = "";
	private String sDownLoadURL = "";
	private String sFileName = "";
	
	public Youtube(String sUserUrl) {
		this.sUserUrl = sUserUrl;
	}
	public String getUserUrl() {
		return sUserUrl;
	}
	public void setUserUrl(String sUserUrl) {
		this.sUserUrl = sUserUrl;
	}
	public String getDownLoadURL() {
		return sDownLoadURL;
	}
	public void setDownLoadURL(String sDownLoadURL) {
		this.sDownLoadURL = sDownLoadURL;
	}
	public String getFileName() {
		return sFileName;
	}
	public void setFileName(String sFileName) {
		this.sFileName = sFileName;
	}
	@Override
	public String toString() {
		return "Youtube [sUserUrl=" + sUserUrl + ", sDownLoadURL="
				+ sDownLoadURL + ", sFileName=" + sFileName + "]";
	}
	
	
}
