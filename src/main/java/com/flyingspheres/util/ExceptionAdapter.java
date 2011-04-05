package com.flyingspheres.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ExceptionAdapter {
	public static String convertThrowable(Exception e) {
        OutputStream os = new ByteArrayOutputStream();
        PrintWriter writer= new PrintWriter(os);
        e.printStackTrace(writer);
        writer.flush();
        String myMessage = new String(
                        ((ByteArrayOutputStream)os).toByteArray());
        return myMessage;
	}
}
