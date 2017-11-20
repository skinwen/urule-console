package com.bstek.urule;

public class RuleException extends RuntimeException {
    private static final long serialVersionUID = -8624533394127244753L;

    public RuleException() {
    }

    public RuleException(String msg) {
        super(msg);
    }

    public RuleException(Exception ex) {
        super(ex);
        ex.printStackTrace();
    }
}
