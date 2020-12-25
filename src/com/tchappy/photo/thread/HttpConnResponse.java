package com.tchappy.photo.thread;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class HttpConnResponse {
	private final HttpResponse mResponse;
	private final byte[] buffer;
	private final HttpEntity entity;
	
	public HttpConnResponse(HttpResponse response) throws IOException {
		mResponse = response;
		entity = mResponse.getEntity();
		
		if (!entity.isRepeatable() || entity.getContentLength() < 0) {
			this.buffer = EntityUtils.toByteArray(entity);
		} else {
			this.buffer = null;
		}
	}
	
	public byte[] asByteArray(){
		return this.buffer;
	}

	public InputStream asStream() throws Exception {
		
		if (this.buffer != null) {
			return new ByteArrayInputStream(this.buffer);
		} else {
			return entity.getContent();
		}
	}

	public String asString() throws Exception {
		try {
			return entityToString(entity);
		} catch (IOException e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	public InputStream asByteToStream()throws Exception{
		try {
			if (this.buffer != null) {
				return new ByteArrayInputStream(this.buffer);
			} else {
				return entity.getContent();
			}
		} catch (IllegalStateException e) {
			throw new Exception(e.getMessage(), e);
		} catch (IOException e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	private String entityToString(final HttpEntity entity) throws IOException,
			Exception {
		if (null == entity) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		// InputStream instream = asStream(entity);
		if (instream == null) {
			return "";
		}
		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					"HTTP entity too large to be buffered in memory");
		}

		int i = (int) entity.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		Log.i("LDS", i + " content length");

		Reader reader = new BufferedReader(new InputStreamReader(instream,
				"UTF-8"));
		CharArrayBuffer buffer = new CharArrayBuffer(i);
		try {
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}
	
	public JSONObject asJSONObject() throws Exception {
		try {
			return new JSONObject(asString());
		} catch (JSONException jsone) {
			throw new Exception(jsone.getMessage() + ":" + asString(),
					jsone);
		}
	}

	public JSONArray asJSONArray() throws Exception {
		try {
			return new JSONArray(asString());
		} catch (Exception jsone) {
			throw new Exception(jsone.getMessage(), jsone);
		}
	}
	
}
