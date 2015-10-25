package at.buc.spring_webapplication_wrapper.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class ApplicationExecutor {
	public static Map<String, String> exec(String cmd, String input) throws IOException {
		File temp=File.createTempFile("application_wrapper", System.nanoTime()+".tmp");
		PrintWriter out = new PrintWriter(new FileWriter(temp));
		StringBuilder sb=new StringBuilder();
		
		out.write(input);
		out.flush();
		out.close();
		
		Runtime runtime = Runtime.getRuntime();

		Process proc = runtime.exec(cmd);

		out.close();
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TreeMap<String, String> ret=new TreeMap<String, String>();
		String line;
		BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		while ( (line=br.readLine()) != null ) {
			sb.append(line);
		}
		ret.put("stdout", sb.toString());
		sb=new StringBuilder();
		br=new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		while ( (line=br.readLine()) != null ) {
			sb.append(line);
		}
		ret.put("stderr", sb.toString());
		
		temp.deleteOnExit();
		return ret;
	}
	public static Map<String, String> exec(String cmd) throws IOException {
		StringBuilder sb=new StringBuilder();
		
		Runtime runtime = Runtime.getRuntime();

		Process proc = runtime.exec(cmd);

		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TreeMap<String, String> ret=new TreeMap<String, String>();
		String line;
		BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		while ( (line=br.readLine()) != null ) {
			sb.append(line);
		}
		ret.put("stdout", sb.toString());
		sb=new StringBuilder();
		br=new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		while ( (line=br.readLine()) != null ) {
			sb.append(line);
		}
		ret.put("stderr", sb.toString());
		
		return ret;
	}
}
