/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.model.BaseContentProvider;
import org.p2presenter.instructor.ui.model.BaseLabelProvider;

import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivityMonitor;

public class InteractivityRecordingView extends ViewPart {
	public static final String ID = "org.p2presenter.instructor.ui.active.interactivityrecordingview";
	
	private ListViewer interactivityEventListViewer;
	
	private Activator plugin;
	
	private ISelectionListener interactivityEventSelectionListener;
	
	public InteractivityRecordingView() {
		plugin = Activator.getDefault();
		plugin.setInteractivityRecordingView(this);
		plugin.getListenerRegsitry().register(InteractivityMonitorEvent.class, new Listener<InteractivityMonitorEvent>() {
			public void onEvent(final InteractivityMonitorEvent event) {
				getSite().getWorkbenchWindow().getShell().getDisplay().syncExec(new Runnable() {
					public void run() {
						interactivityEventListViewer.refresh();
						interactivityEventListViewer.setSelection(new StructuredSelection(event.getInteractivityEvent()));
					}
				});
			}
		});
	}
	
	@Override
	public void createPartControl(Composite parent) {
		interactivityEventListViewer = new ListViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		interactivityEventListViewer.setLabelProvider(new BaseLabelProvider());
		interactivityEventListViewer.setContentProvider(new BaseContentProvider());
		
		interactivityEventSelectionListener = new ISelectionListener() {
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				partSelectionChanged(part, selection);
			}
		};
		
		getSite().setSelectionProvider(interactivityEventListViewer);
		getSite().getPage().addSelectionListener(interactivityEventSelectionListener);
	}

	@Override
	public void setFocus() {
		interactivityEventListViewer.getControl().setFocus();
	}
	
	public void setInteractivityMonitor(InteractivityMonitor<?> interactivityMonitor) {
		// TODO is this safe?
		interactivityEventListViewer.setInput(interactivityMonitor);
	}
	
	private void partSelectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == this || !(selection instanceof IStructuredSelection)) {
			return;
		}
		
		interactivityEventListViewer.setSelection(selection);
	}
}
