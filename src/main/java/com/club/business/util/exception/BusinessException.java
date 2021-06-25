package com.club.business.util.exception;

import com.club.business.contants.ExceptionIconEnum;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @Description: 异常处理器
 * @author Tony
 * @date 2018.06.02
 */
@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class BusinessException extends Exception {

	private ExceptionIconEnum iconEnum;

	private Throwable nested = null;

	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, ExceptionIconEnum iconEnum){
		super(msg);
		this.iconEnum = iconEnum;
	}

	public BusinessException(Throwable nested) {
		super();
		this.nested = nested;
	}

	public BusinessException(String msg, Throwable nested) {
		super(msg);
		this.nested = nested;
	}

	public ExceptionIconEnum getIconEnum() {
		return iconEnum;
	}

	@Override
	public String getMessage() {
		StringBuffer msg = new StringBuffer();
		String ourMsg = super.getMessage();
		if (ourMsg != null) {
			msg.append(ourMsg);
		}
		if (nested != null) {
			String nestedMsg = nested.getMessage();
			if (nestedMsg != null) {
				if (ourMsg != null) {
					msg.append(": ");
				}
				msg.append(nestedMsg);
			}

		}
		return (msg.length() > 0 ? msg.toString() : null);
	}

	@Override
	public void printStackTrace() {
		synchronized (System.err) {
			printStackTrace(System.err);
		}
	}

	@Override
	public void printStackTrace(PrintStream out) {
		synchronized (out) {
			PrintWriter pw = new PrintWriter(out, false);
			printStackTrace(pw);
			pw.flush();
		}
	}

	@Override
	public void printStackTrace(PrintWriter out) {
		synchronized (out) {
			printStackTrace(out, 0);
		}
	}

	public void printStackTrace(PrintWriter out, int skip) {
		String[] st = captureStackTrace();
		if (nested != null) {
			if (nested instanceof BusinessException) {
				((BusinessException) nested).printStackTrace(out, st.length - 2);
			} else {
				String[] nst = captureStackTrace(nested);
				for (int i = 0; i < nst.length - st.length + 2; i++) {
					out.println(nst[i]);
				}
			}
			out.print("rethrown as ");
		}
		for (int i = 0; i < st.length - skip; i++) {
			out.println(st[i]);
		}
	}

	private String[] captureStackTrace() {
		StringWriter sw = new StringWriter();
		super.printStackTrace(new PrintWriter(sw, true));
		return splitStackTrace(sw.getBuffer().toString());
	}

	private String[] captureStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw, true));
		return splitStackTrace(sw.getBuffer().toString());
	}

	private String[] splitStackTrace(String stackTrace) {
		String linebreak = System.getProperty("line.separator");
		StringTokenizer st = new StringTokenizer(stackTrace, linebreak);
		LinkedList list = new LinkedList();
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return (String[]) list.toArray(new String[] {});
	}
}
