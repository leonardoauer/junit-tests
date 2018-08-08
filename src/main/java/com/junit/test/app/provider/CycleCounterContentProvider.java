package com.junit.test.app.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.junit.test.app.model.TableViewer;
import com.junit.test.app.model.TreadCycleCount;
import com.junit.test.app.model.Viewer;
import com.junit.test.app.utils.GUIUtils;

public class CycleCounterContentProvider implements IStructuredContentProvider, CycleCounterModeListener {
	
	private TableViewer tableViewer;
	private String activeSessionId;
	public static final CycleCounterContentProvider INSTANCE = new CycleCounterContentProvider();
	private List<CycleCounterModeListener> listeners;
	
	public CycleCounterContentProvider() {
		this.activeSessionId = null;
	}
	
	@Override
	public void dispose() {
		this.tableViewer = null;
		this.activeSessionId = null;
		CycleCounterContentProvider.INSTANCE.removeCycleCounterModeListener(this); 
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		this.tableViewer = (TableViewer) viewer;
		
		if(oldInput != newInput) {
		
			if(oldInput != null) {
				CycleCounterContentProvider.INSTANCE.removeCycleCounterModeListener(this);	
			}
			
			if(newInput != null) {
				CycleCounterContentProvider.INSTANCE.addCycleCounterModeListener(this);
			}
		}
		
	}
	
	public Object[] getElements(Object inputElement) {
		
		if(activeSessionId == null) {
			return Collections.emptyList().toArray();
		}
		
		if(!(inputElement instanceof CycleCounterContentProvider)) {
			return Collections.emptyList().toArray();
		}
		
		List<TreadCycleCount> tcc = ((CycleCounterContentProvider) inputElement).getThreadsAndCycles(new Integer(activeSessionId).intValue());
		return tcc.toArray();
	}
	
	public void setActiveSessionId(String activeSessionId) {
		this.activeSessionId = activeSessionId;
		dataUpdated();
	}
	
	@Override
	public void dataUpdated() {
		GUIUtils.exec(new Runnable() {
			@Override
			public void run() {
				if(tableViewer != null) {
					tableViewer.refresh();
				}
			}
		});
	}
	
	@Override
	public void removeCycleCounterModeListener(CycleCounterModeListener listener) {
		
		if(listener == null) {
			return;
		}

		if(INSTANCE.listeners != null && INSTANCE.listeners.size() > 0) {
			INSTANCE.listeners.remove(listener);
		}
	}	

	@Override
	public void addCycleCounterModeListener(CycleCounterModeListener listener) {
		
		if(listener == null) {
			return;
		}

		if(INSTANCE.listeners == null) {
			INSTANCE.listeners = new ArrayList<>();
		}
		
		INSTANCE.listeners.add(listener);
	}

	@Override
	public List<TreadCycleCount> getThreadsAndCycles(int intValue) {
		
		if(intValue == 0 || intValue > 10) {
			return null;
		}

		List<TreadCycleCount> treadCycleCounts = new ArrayList<>();;
		
		for(int i=0; i<intValue; i++) {
			treadCycleCounts.add(new TreadCycleCount());
		}

		return treadCycleCounts;
	}

	public List<CycleCounterModeListener> getListeners() {
		return INSTANCE.listeners;
	}

	public void setListeners(List<CycleCounterModeListener> listeners) {
		INSTANCE.listeners = listeners;
	}
	
	public TableViewer getTableViewer() {
		return tableViewer;
	}

}
