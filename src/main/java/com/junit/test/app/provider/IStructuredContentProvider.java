package com.junit.test.app.provider;

import java.util.List;

import com.junit.test.app.model.TreadCycleCount;
import com.junit.test.app.model.Viewer;

public interface IStructuredContentProvider {

	void inputChanged(Viewer viewer, Object oldInput, Object newInput);
	
	List<TreadCycleCount> getThreadsAndCycles(int intValue);
}
