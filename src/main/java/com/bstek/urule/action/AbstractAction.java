package com.bstek.urule.action;

public abstract class AbstractAction implements Action {
	private int priority;
	
	@Override
	public int compareTo(Action o) {
		return o.getPriority()-priority;
	}
	@Override
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
