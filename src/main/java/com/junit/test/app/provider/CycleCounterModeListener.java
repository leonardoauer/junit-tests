package com.junit.test.app.provider;

public interface CycleCounterModeListener {

	void dispose();
	void removeCycleCounterModeListener(CycleCounterModeListener listener);
	void addCycleCounterModeListener(CycleCounterModeListener listener);
	void dataUpdated();
}
