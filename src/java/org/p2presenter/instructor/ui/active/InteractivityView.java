/* $Id$ */

package org.p2presenter.instructor.ui.active;

import java.awt.Container;
import java.awt.Frame;

import javax.swing.SwingUtilities;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.p2presenter.instructor.model.InteractivityDefinition;
import org.p2presenter.instructor.model.Slide;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.SlideChangedEvent;
import org.p2presenter.instructor.ui.event.ClassListenerRegistry;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerAdaptor;
import org.p2presenter.messaging.LocalConnection;
import org.p2presenter.remoting.InvocationRequestHandler;

import edu.uoregon.cs.p2presenter.interactivity.InteractivityController;
import edu.uoregon.cs.p2presenter.interactivity.InteractivityModel;
import edu.uoregon.cs.p2presenter.interactivity.InteractivityStateListener;
import edu.uoregon.cs.p2presenter.interactivity.host.InteractivityHostClient;
import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivityEvent;
import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivityMonitor;
import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivtyMonitorEventListener;

public class InteractivityView extends ViewPart {
	public static final String ID = "org.p2presenter.instructor.ui.active.interactivityView";
	
	private Activator plugin = Activator.getDefault();
	
	private Label slideLabel;
	
	private Frame awtFrame;
	
	private Composite top;
	
	private Slide slide;
	
	private InteractivityRecordingView interactivityRecordingView;
	
	private Action launchInteractivityAction;
	
	private Listener<SlideChangedEvent> slideChangedListener;
	
	private ISelectionListener postSelectionListener;
	
	private InteractivityStateListener view;
	
	public void createPartControl(final Composite parent) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;
		parent.setLayout(layout);
		
		top = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		
		awtFrame = SWT_AWT.new_Frame(top);
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		slideLabel = new Label(parent, SWT.NONE);
		slideLabel.setText("Select a slide...");
		slideLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		createActions();
		
		slideChangedListener = new ListenerAdaptor<SlideChangedEvent>(this) {
			@Override
			public void onExternalEvent(SlideChangedEvent event) {
				slideChanged(event);
			}
		};
		
		plugin.getListenerRegsitry().register(SlideChangedEvent.class, slideChangedListener);
		
	}
	
	public void createActions() {
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();

		launchInteractivityAction = new Action("Launch Interactivity") {
			public void run() {
				if (slide == null) {
					MessageDialog.openError(getSite().getWorkbenchWindow().getShell(), "Launch Interactivity", "No slide is being displayed");
					return;
				}
				
				InteractivityDefinition interactivityDefinition = slide.getInteractivityDefinition();
				if (interactivityDefinition == null) {
					MessageDialog.openError(getSite().getWorkbenchWindow().getShell(), "Launch Interactivity", "No interactivity exists on the current slide");
					return;
				}
				LocalConnection connection = plugin.getActiveSession().getConnection();
				
				if (connection == null) {
					return;
				}
				
				try {
					Integer interactivityId = interactivityDefinition.getId();
			
					final InteractivityHostClient client = new InteractivityHostClient(connection, interactivityId);
					
					InteractivityController controller = client.getController();
					final Container viewContainer = controller.getView();
					view = (InteractivityStateListener) viewContainer;
					
					InteractivityModel model = controller.getModel();
					
					view.stateChanged(model);
					
					InteractivityMonitor interactivityMonitor = new InteractivityMonitor(model, view);
					
					SwingUtilities.invokeAndWait(new Runnable() {
						public void run() {
							awtFrame.add(viewContainer);
							viewContainer.repaint();
						}
					});
					
					awtFrame.validate();
					
					postSelectionListener = new ISelectionListener() {
						public void selectionChanged(IWorkbenchPart part, ISelection selection) {
							partSelectionChanged(part, selection);
						}
					};
					
					getSite().getPage().addPostSelectionListener(postSelectionListener);
			
					InvocationRequestHandler invoker = new InvocationRequestHandler();
					invoker.setInvocationListener(interactivityMonitor);
					connection.getRequestHandlerMapping().mapHandler("/interactivity/" + interactivityId + "/controller", invoker);
					connection.setAttribute("interactivity", client.getController());
					
					interactivityRecordingView = plugin.getInteractivityRecordingView();
					interactivityRecordingView.setInteractivityMonitor(interactivityMonitor);
					
					final ClassListenerRegistry<InteractivityMonitorEvent> monitorListeners = plugin.getListenerRegsitry().getListeners(InteractivityMonitorEvent.class);
					interactivityMonitor.setListener(new InteractivtyMonitorEventListener() {
						public void onInteractivityEvent(final InteractivityEvent event) {
							monitorListeners.onEvent(new InteractivityMonitorEvent(InteractivityView.this, event));
						}
					});
					
					client.begin();
					
					// record the intitial state
					interactivityMonitor.stateChanged(model);
				}
				catch (Exception ex) {
					// TODO
					ex.printStackTrace();
				}
			}
		};
		
		launchInteractivityAction.setEnabled(false);
		
		mgr.add(launchInteractivityAction);

	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	private void slideChanged(SlideChangedEvent event) {
		slide = event.getSlide();
		
		if (slide != null) {
			slideLabel.setText(slide.getTitle() != null ? slide.getTitle() : "Untitled Slide");
			InteractivityDefinition interactivity = slide.getInteractivityDefinition();
			if (interactivity != null) {
				launchInteractivityAction.setEnabled(true);
			}
			else {
				launchInteractivityAction.setEnabled(false);
			}
		}
		else {
			// TODO clear slide display
		}
	}
	
	private void partSelectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == this || !(selection instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		Object firstElement = structuredSelection.getFirstElement();
		if (firstElement instanceof InteractivityEvent) {
			view.stateChanged(((InteractivityEvent) firstElement).getCurrentStateEvent().getState());
		}
		
	}
	
	@Override
	public void dispose() {
		if (slideChangedListener != null) {
			plugin.getListenerRegsitry().unregister(SlideChangedEvent.class, slideChangedListener);
		}
		if (postSelectionListener != null) {
			getSite().getPage().removePostSelectionListener(postSelectionListener);
		}
		super.dispose();
	}

}
