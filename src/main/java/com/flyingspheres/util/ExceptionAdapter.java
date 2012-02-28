package com.flyingspheres.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ExceptionAdapter {
	/**
	 * convert an exception into a string for easier logging
	 */
	public static String convertThrowable(Throwable e) {
        OutputStream os = new ByteArrayOutputStream();
        PrintWriter writer= new PrintWriter(os);
        e.printStackTrace(writer);
        writer.flush();
        String myMessage = new String( ((ByteArrayOutputStream)os).toByteArray());
        return myMessage;
	}
}
