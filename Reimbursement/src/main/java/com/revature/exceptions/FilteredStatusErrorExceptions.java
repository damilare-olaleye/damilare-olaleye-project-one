package com.revature.exceptions;

public class FilteredStatusErrorExceptions extends Exception {

	public FilteredStatusErrorExceptions() {
		super();
	}

	public FilteredStatusErrorExceptions(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FilteredStatusErrorExceptions(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FilteredStatusErrorExceptions(String arg0) {
		super(arg0);
	}

	public FilteredStatusErrorExceptions(Throwable arg0) {
		super(arg0);
	}

}
