package at.buc.framework.web.service.application_wrapper;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.*;

public class ApplicationExecutor extends DefaultExecuteResultHandler{

	private final String cmd;
	private long exec_start;
	private long exec_end;
	
	private String stdout;
	private String stderr;
	
	private String cmd_base64;
	private String stdout_base64;
	private String stderr_base64;
	
	
	private MyLogOutputStream stdout_stream;
	private MyLogOutputStream stderr_stream;
	
	private DefaultExecutor executor;
	private final CommandLine command;
	
	public ApplicationExecutor(String cmd) {
		this.cmd=cmd;
		this.command=CommandLine.parse(cmd);
		setup();
	}
	public ApplicationExecutor(String cmd, Map<String, ?> params) {
		this.cmd=cmd;
		this.command=CommandLine.parse(cmd, params);
		setup();
	}
	
	private void setup() {
		executor = new DefaultExecutor();
		
		stdout_stream = new MyLogOutputStream();
		stderr_stream= new MyLogOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(stdout_stream, stderr_stream));
		
		cmd_base64=Base64.getEncoder().encodeToString(cmd.getBytes());
	}
	
	public ApplicationExecutor startExecute() throws ExecuteException, IOException{
		exec_start=new Date().getTime();
		executor.execute(command, this);
		return this;
	}
	
	@Override
	public void onProcessComplete(int exitValue) {
		super.onProcessComplete(exitValue);
		exec_end=new Date().getTime();
	}
	
	@Override
	public void waitFor() throws InterruptedException {
		super.waitFor();
		exec_end=new Date().getTime();
		stdout=stdout_stream.toString();
		stderr=stderr_stream.toString();

		stdout_base64=Base64.getEncoder().encodeToString(stdout_stream.toString().getBytes());
		stderr_base64=Base64.getEncoder().encodeToString(stderr_stream.toString().getBytes());
	}
	
	public Map<String, Object> asMap(){
		HashMap<String, Object> ret=new HashMap<>();
		ret.put("cmd", getCmd());
		ret.put("cmd_base64", getCmd_base64());
		ret.put("exitValue", getExitValue());
		ret.put("exec_start", String.valueOf(getExec_start()));
		ret.put("exec_end", String.valueOf(getExec_end()));
		ret.put("stdout", getStdout());
		ret.put("stdout_base64", getStdout_base64());
		ret.put("stderr", getStderr());
		ret.put("stderr_base64", getStderr_base64());
		return ret;
	}
	
	public String getCmd() {
		return cmd;
	}
	public long getExec_start() {
		return exec_start;
	}
	public long getExec_end() {
		return exec_end;
	}
	public String getStdout() {
		return stdout;
	}
	public String getStderr() {
		return stderr;
	}
	public String getCmd_base64() {
		return cmd_base64;
	}
	public String getStdout_base64() {
		return stdout_base64;
	}
	public String getStderr_base64() {
		return stderr_base64;
	}
}
