package com.tchappy.photo.thread;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * 适用于Http初始化
 * 
 * @author MrLovelyCbb
 * 
 */
public class HttpConnectionClient {
	private static final String TAG = "HttpClient";
	private static final int DEFAULT_MAX_CONNECTIONS = 10;
	private static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;
	private static final int DEFAULT_MAX_RETRIES = 5;
	private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
	private static int maxConnections = DEFAULT_MAX_CONNECTIONS;
	private static int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";
	
	private static String SERVER_HOST = "http://app.youmsj.cn";

	private DefaultHttpClient mClient;
	private BasicHttpContext localcontext;
	private AuthScope mAuthScope;
	private String mUserId;
	private String mPassword;
	private static boolean isAuthenticationEnabled = false;

	public void setProxy(String host, int port, String scheme) {
		HttpHost proxy = new HttpHost(host, port, scheme);
		mClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	public void removeProxy() {
		mClient.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
	}

	public void setServerHost(String hostname) {
		SERVER_HOST = hostname;
		mAuthScope = new AuthScope(SERVER_HOST, AuthScope.ANY_PORT);
	}

	public void setUserAgent(String userAgent) {
		HttpProtocolParams.setUserAgent(this.mClient.getParams(), userAgent);
	}

//	public void setCookieStore(CookieStore cookieStore) {
//		localcontext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
//	}
	
	public HttpClient getHttpClient() {
        return this.mClient;
    }

	public void setTimeout(int timeout) {
		final HttpParams httpParams = this.mClient.getParams();
		ConnManagerParams.setTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
	}

	public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		this.mClient.getConnectionManager().getSchemeRegistry()
				.register(new Scheme("https", sslSocketFactory, 443));
	}
	
	public void reset() {
		setCredentials("", "");
	}

	public String getUserId() {
		return mUserId;
	}

	public String getPassword() {
		return mPassword;
	}
	
	
	//
	public HttpConnResponse post(String url, ArrayList<BasicNameValuePair> postParams,
			boolean authenticated) throws HttpException {
		if (null == postParams) {
			postParams = new ArrayList<BasicNameValuePair>();
		}
		return httpRequest(url, postParams, authenticated, HttpPost.METHOD_NAME);
	}

	public HttpConnResponse post(String url, ArrayList<BasicNameValuePair> params)
			throws HttpException {
		return httpRequest(url, params, false, HttpPost.METHOD_NAME);
	}

	public HttpConnResponse post(String url, boolean authenticated)
			throws HttpException {
		return httpRequest(url, null, authenticated, HttpPost.METHOD_NAME);
	}

	public HttpConnResponse post(String url) throws HttpException {
		return httpRequest(url, null, false, HttpPost.METHOD_NAME);
	}

	public HttpConnResponse post(String url, File file) throws HttpException {
		return httpRequest(url, null, file, false, HttpPost.METHOD_NAME);
	}

	/**
	 * POST一个文件
	 * 
	 * @param url
	 * @param file
	 * @param authenticate
	 * @return
	 * @throws HttpException
	 */
	public HttpConnResponse post(String url, File file, boolean authenticate)
			throws HttpException {
		return httpRequest(url, null, file, authenticate, HttpPost.METHOD_NAME);
	}

	public HttpConnResponse get(String url, ArrayList<BasicNameValuePair> params,
			boolean authenticated) throws HttpException {
		return httpRequest(url, params, authenticated, HttpGet.METHOD_NAME);
	}

	public HttpConnResponse get(String url, ArrayList<BasicNameValuePair> params)
			throws HttpException {
		return httpRequest(url, params, false, HttpGet.METHOD_NAME);
	}

	public HttpConnResponse get(String url) throws HttpException {
		return httpRequest(url, null, false, HttpGet.METHOD_NAME);
	}

	public HttpConnResponse get(String url, boolean authenticated) throws HttpException {
		return httpRequest(url, null, authenticated, HttpGet.METHOD_NAME);
	}

	public HttpConnResponse httpRequest(String url,
			ArrayList<BasicNameValuePair> postParams, boolean authenticated,
			String httpMethod) throws HttpException {
		return httpRequest(url, postParams, null, authenticated, httpMethod);
	}
	
	private void SetupHTTPConnectionParams(HttpUriRequest method) {
		HttpConnectionParams.setConnectionTimeout(method.getParams(),
				socketTimeout);
		HttpConnectionParams
				.setSoTimeout(method.getParams(), socketTimeout);
		mClient.setHttpRequestRetryHandler(new HttpRetryHandler(DEFAULT_MAX_RETRIES));
//		mClient.setRedirectHandler(defaultRedirectHandler);   //重定向
		method.addHeader("Accept-Encoding", "gzip, deflate");
		method.addHeader("Accept-Charset", "UTF-8,*;q=0.5");
		// china
		method.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
	}
	
	public HttpConnResponse httpRequest(String url,
			ArrayList<BasicNameValuePair> postParams, File file,
			boolean authenticated, String httpMethod) throws HttpException {
		Log.d(TAG, "Sending " + httpMethod + " request to " + url);

		URI uri = createURI(url);

		HttpResponse response = null;
		HttpConnResponse res = null;
		HttpUriRequest method = null;

		// Create POST, GET or DELETE METHOD
		method = createMethod(httpMethod, uri, file, postParams);
		// Setup ConnectionParams, Request Headers
		SetupHTTPConnectionParams(method);

		// Execute Request
		try {
//			response = mClient.execute(method, localcontext);
			response = mClient.execute(method);
			res = new HttpConnResponse(response);
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new HttpException(e.getMessage(), e);
		} catch (IOException ioe) {
			throw new HttpException(ioe.getMessage(), ioe);
		}

		if (response != null) {
			int statusCode = response.getStatusLine().getStatusCode();
			// It will throw a weiboException while status code is not 200
//			HandleResponseStatusCode(statusCode, res);
		} else {
			Log.e(TAG, "response is null");
		}

		return res;
	}
	
	
	

	public HttpConnectionClient() {
		InitHttpClient();
	}
	
	public HttpConnectionClient(String username,String password){
		InitHttpClient();
		setCredentials(username,password);
	}
	
	public void setCredentials(String username, String password) {
		mUserId = username;
		mPassword = password;
		
		mClient.getCredentialsProvider().setCredentials(mAuthScope,
				new UsernamePasswordCredentials(username, password));
		isAuthenticationEnabled = true;
	}
	
	public boolean isAuthenticationEnabled() {
		return isAuthenticationEnabled;
	}

	public void InitHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();

		ConnManagerParams.setTimeout(httpParams, socketTimeout);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
				new ConnPerRouteBean(maxConnections));
		ConnManagerParams.setMaxTotalConnections(httpParams,
				DEFAULT_MAX_CONNECTIONS);

		HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);
		HttpConnectionParams.setConnectionTimeout(httpParams, socketTimeout);
		HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpConnectionParams.setSocketBufferSize(httpParams,
				DEFAULT_SOCKET_BUFFER_SIZE);

		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				httpParams, schemeRegistry);
		
		mClient = new DefaultHttpClient(cm, httpParams);
		// Setup BasicAuth
		BasicScheme basicScheme = new BasicScheme();
		
		// Setup CookieStore
//		CookieStore cookieStore = new BasicCookieStore();
//		mAuthScope = new AuthScope(SERVER_HOST, AuthScope.ANY_PORT);

		mClient.setCredentialsProvider(new BasicCredentialsProvider());

		// No save cookieStore
//		localcontext = new BasicHttpContext();
//
//		localcontext.setAttribute("preemptive-auth", basicScheme);
//		localcontext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		// 加入Request 请求
		
		mClient.addRequestInterceptor(preemptiveAuth);
		// 加入Response 响应
		mClient.addResponseInterceptor(gzipResponseIntercepter);

		mClient.setHttpRequestRetryHandler(new HttpRetryHandler(DEFAULT_MAX_RETRIES));

	}

	private static HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {
		public void process(final HttpRequest request, final HttpContext context) {
			if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
				request.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
			}
			AuthState authState = (AuthState) context
					.getAttribute(ClientContext.TARGET_AUTH_STATE);
			CredentialsProvider credsProvider = (CredentialsProvider) context
					.getAttribute(ClientContext.CREDS_PROVIDER);
			HttpHost targetHost = (HttpHost) context
					.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

			if (authState.getAuthScheme() == null) {
				AuthScope authScope = new AuthScope(targetHost.getHostName(),
						targetHost.getPort());
				Credentials creds = credsProvider.getCredentials(authScope);
				if (creds != null) {
					authState.setAuthScheme(new BasicScheme());
					authState.setCredentials(creds);
				}
			}
		}
	};

	private static HttpResponseInterceptor gzipResponseIntercepter = new HttpResponseInterceptor() {

		public void process(HttpResponse response, HttpContext context)
				throws HttpException, IOException {
			final HttpEntity entity = response.getEntity();
			final Header encoding = entity.getContentEncoding();
			if (encoding != null) {
				for (HeaderElement element : encoding.getElements()) {
					if (element.getName().equalsIgnoreCase(ENCODING_GZIP)) {
						response.setEntity(new GzipDecompressingEntity(response
								.getEntity()));
						break;
					}
				}
			}
		}

	};

	static class GzipDecompressingEntity extends HttpEntityWrapper {
		private final byte[] buffer;

		public GzipDecompressingEntity(final HttpEntity entity)
				throws IOException {
			super(entity);
			if (!entity.isRepeatable() || entity.getContentLength() < 0) {
				this.buffer = EntityUtils.toByteArray(entity);
			} else {
				this.buffer = null;
			}

		}

		@Override
		public InputStream getContent() throws IOException,
				IllegalStateException {
			final InputStream wrappedin;
			if (this.buffer != null) {
				wrappedin = new ByteArrayInputStream(this.buffer);
			} else {
				wrappedin = wrappedEntity.getContent();
			}

			return new GZIPInputStream(wrappedin);
		}

		@Override
		public long getContentLength() {
			return -1;
		}

	}
	
	private URI createURI(String url) throws HttpException {
		URI uri;

		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new HttpException("Invalid URL.");
		}

		return uri;
	}
	
	private HttpUriRequest createMethod(String httpMethod, URI uri, File file,
			ArrayList<BasicNameValuePair> postParams) throws HttpException {

		HttpUriRequest method;

		if (httpMethod.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
			// POST METHOD

			HttpPost post = new HttpPost(uri);
			// See this:
			// http://groups.google.com/group/twitter-development-talk/browse_thread/thread/e178b1d3d63d8e3b
			post.getParams().setBooleanParameter(
					"http.protocol.expect-continue", false);

			try {
				HttpEntity entity = null;
				if (null != file) {
//					entity = createMultipartEntity("photo", file, postParams);
//					post.setEntity(entity);
				} else if (null != postParams) {
					entity = new UrlEncodedFormEntity(postParams, HTTP.UTF_8);
				}
				post.setEntity(entity);
			} catch (IOException ioe) {
				throw new HttpException(ioe.getMessage(), ioe);
			}

			method = post;
		} else if (httpMethod.equalsIgnoreCase(HttpDelete.METHOD_NAME)) {
			method = new HttpDelete(uri);
		} else {
			method = new HttpGet(uri);
		}

		return method;
	}
	
}
