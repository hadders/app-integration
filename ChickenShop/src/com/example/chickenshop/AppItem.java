package com.example.chickenshop;

public class AppItem {

	private String packageName;
	private String activityName;
	private String appName;

	public AppItem(String packageName, String activityName, String appName) {
		this.packageName = packageName;
		this.activityName = activityName;
		this.appName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
