package at.buc.web.service.application_wrapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.*;

public class ApplicationExecutor {

	public static Map<String, Object> exec(String cmd) throws ExecuteException, IOException {
		int exitValue = -1;
		HashMap<String, Object> result = new HashMap<String, Object>();

		CommandLine cmdLine = CommandLine.parse(cmd);
		DefaultExecutor executor = new DefaultExecutor();
		
		MyLogOutputStream stdout = new MyLogOutputStream();
		MyLogOutputStream stderr = new MyLogOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(stdout, stderr));
		
		//executor.setStreamHandler(streamHandler);
		Date exec_start = new Date();
		exitValue = executor.execute(cmdLine);
		Date exec_stop = new Date();
		
		result.put("cmd", cmd);
		result.put("exitcode", Integer.valueOf(exitValue));
		result.put("stdout", stdout.toString());
		result.put("stderr", stderr.toString());
		result.put("exec_start", exec_start);
		result.put("exec_stop", exec_stop);
		return result;
	}

}
