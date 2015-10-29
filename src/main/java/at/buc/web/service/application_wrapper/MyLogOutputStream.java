package at.buc.web.service.application_wrapper;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.LogOutputStream;

public class MyLogOutputStream extends LogOutputStream {
	private final List<String> lines = new LinkedList<String>();
	
	@Override
	protected void processLine(String line, int level) {
		lines.add(line);
	}
	public List<String> getLines() {
		return lines;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (String string : lines) {
			sb.append(string+"\n");
		}
		return sb.toString();
	}
}
