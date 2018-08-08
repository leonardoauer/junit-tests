package com.junit.test.app.model;

public class TableViewer extends Viewer {

	private boolean refreshed;
	
	public void refresh() {
		this.refreshed = true;
	}

	public boolean isRefreshed() {
		return refreshed;
	}

}
