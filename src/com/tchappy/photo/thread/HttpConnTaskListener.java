package com.tchappy.photo.thread;

public interface HttpConnTaskListener {

	void onPreExecute(HttpConnTask task);

	void onPostExecute(HttpConnTask task, HttpResultStatus result);

	void onProgressUpdate(HttpConnTask task, Object param);

	void onCancelled(HttpConnTask task);

	void onHandler(HttpConnTask task, HttpResultStatus result);
}
