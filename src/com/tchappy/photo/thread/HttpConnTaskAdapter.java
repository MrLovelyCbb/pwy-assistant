package com.tchappy.photo.thread;

public abstract class HttpConnTaskAdapter implements HttpConnTaskListener {

	public void onPreExecute(HttpConnTask task) {
	};

	public void onPostExecute(HttpConnTask task, HttpResultStatus result) {
	};

	public void onProgressUpdate(HttpConnTask task, Object param) {
	};

	public void onCancelled(HttpConnTask task) {
	}

	public void onHandler(HttpConnTask task, HttpResultStatus result) {
	}

}
