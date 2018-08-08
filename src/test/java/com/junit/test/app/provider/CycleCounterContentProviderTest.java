package com.junit.test.app.provider;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.junit.test.app.model.TableViewer;

@RunWith(SpringRunner.class)
public class CycleCounterContentProviderTest {

	private CycleCounterContentProvider provider = new CycleCounterContentProvider();
	private static final String SESSION_ID = "4";

	@Test
	public void testListener_removal_and_addition() {

		// Listeners must be null before running this test
		provider.setListeners(null);

		// Listener must be null
		provider.dispose();
		assertNull(provider.getListeners());

		// Listener must remain null
		provider.inputChanged(null, null, null);
		assertNull(provider.getListeners());

		// Listener not null and listener size equal 1
		TableViewer viewer = new TableViewer();
		;
		Object oldInput = new Object();
		Object newInput = new Object();
		provider.inputChanged(viewer, oldInput, newInput);
		assertNotNull(provider.getListeners());
		assertEquals(provider.getListeners().size(), 1);

		// Listener not null but zero capacity when newInput equal oldInput
		oldInput = new Object();
		newInput = oldInput;
		provider.inputChanged(viewer, oldInput, newInput);
		assertNotNull(provider.getListeners());
		assertEquals(provider.getListeners().size(), 1);

		// Listener not null and listener size 1
		oldInput = new Object();
		newInput = null;
		provider.inputChanged(viewer, oldInput, newInput);
		assertNotNull(provider.getListeners());
		assertEquals(provider.getListeners().size(), 0);

		// Listener not null and listener size 1
		newInput = new Object();
		oldInput = null;
		provider.inputChanged(viewer, oldInput, newInput);
		assertNotNull(provider.getListeners());
		assertEquals(provider.getListeners().size(), 1);
	}

	@Test
	public void test_getElements_method() {

		// Empty array
		Object[] emptyArray = new Object[] {};

		// test result is empty array
		Object[] result = provider.getElements(null);
		assertArrayEquals(emptyArray, result);

		// test result is empty array
		Object inputElement = "an object";
		result = provider.getElements(inputElement);
		assertArrayEquals(emptyArray, result);

		// Test there not Cast Exception when inputElement is String
		provider.setActiveSessionId(SESSION_ID);
		result = provider.getElements(inputElement);
		assertArrayEquals(emptyArray, result);

		inputElement = new CycleCounterContentProvider();
		result = provider.getElements(inputElement);
		assertEquals(4, result.length);
	}

	@Test
	public void testRemoveCycleCounterModeListener() {

		// Listeners must be null before running this test
		provider.setListeners(null);

		// Test exception is not thrown
		provider.removeCycleCounterModeListener(null);
		assertNull(provider.getListeners());
		
		// Add a listener
		provider.addCycleCounterModeListener(new CycleCounterContentProvider());

		// listeners size should remain same
		int listenerSize = provider.getListeners().size();
		provider.removeCycleCounterModeListener(null);
		assertEquals(listenerSize, provider.getListeners().size());

		// listeners size should remain same
		CycleCounterModeListener cycleCounterModeListener = provider.getListeners().get(0);
		provider.removeCycleCounterModeListener(cycleCounterModeListener);
		assertNotEquals(listenerSize, provider.getListeners().size());
	}
	
	@Test
	public void testAddCycleCounterModeListener() {
		
		// Listeners must be null before running this test
		provider.setListeners(null);
		
		// Test exception is not thrown
		provider.addCycleCounterModeListener(null);
		assertNull(provider.getListeners());
		
		// Add a listener
		provider.addCycleCounterModeListener(new CycleCounterContentProvider());
		
		// listeners size should have increased
		int listenerSize = provider.getListeners().size();
		assertEquals(1, listenerSize);
	}
}
