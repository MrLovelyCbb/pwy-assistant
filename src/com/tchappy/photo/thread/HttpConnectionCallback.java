package com.tchappy.photo.thread;
 public interface HttpConnectionCallback
    {
        /**
         * Call back method will be execute after the http request return.
         *
         * @param response
         *            the response of http request. The value will be null if
         *            any error occur.
         */
        void execute(String response);
    }