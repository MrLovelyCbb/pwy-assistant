package com.tchappy.photo.thread;

import java.util.Observable;
import java.util.Observer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class HttpConnTask extends
		AsyncTask<HttpConnParams, Object, HttpResultStatus> implements Observer{

	private Handler handler = null;
	private HttpConnTaskListener hTaskListener;

	public void SetHttpConnTaskListener(
			HttpConnTaskListener httpConnTaskListener) {
		this.hTaskListener = httpConnTaskListener;
	}

	public HttpConnTaskListener GetHttpConnTaskListener() {
		return hTaskListener;
	}
	
	public void SetHttpConnHandler(Handler handler){
		this.handler = handler;
	}

	public Handler getHttpConnHandler(){
		return this.handler;
	}
	
	abstract protected HttpResultStatus _doInBackground(
			HttpConnParams... params);

	abstract protected HttpResultStatus _HandlerdoInBackground();

	public void doPublishProgress(Object... values) {
		super.publishProgress(values);
	}

	@Override
	protected HttpResultStatus doInBackground(HttpConnParams... params) {
		HttpResultStatus httpStatus = _doInBackground(params);
		if (this.handler != null) {
			_HandlerdoInBackground();
		}
		return httpStatus;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (hTaskListener != null) {
			hTaskListener.onCancelled(this);
		}
	}

	@Override
	protected void onPostExecute(HttpResultStatus result) {
		super.onPostExecute(result);
		if (hTaskListener != null) {
			hTaskListener.onPostExecute(this, result);
		}
		if (this.handler != null) {
			hTaskListener.onHandler(this, result);
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (hTaskListener != null) {
			hTaskListener.onPreExecute(this);
		}
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		if (hTaskListener != null) {
			if (values != null && values.length > 0) {
				hTaskListener.onProgressUpdate(this, values);
			}
		}
	}
	private boolean isCancelable = true;
	
	public void update(Observable observable, Object data) {
		if (HttpTaskManager.CANCEL_ALL == (Integer) data && isCancelable) {
			if (getStatus() == HttpConnTask.Status.RUNNING) {
				cancel(true);
			}
		}
	}
	
	public void setCancelable(boolean flag){
		isCancelable =flag;
	}

}
