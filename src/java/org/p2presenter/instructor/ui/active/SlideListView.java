package org.p2presenter.instructor.ui.active;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.p2presenter.instructor.model.Lecture;
import org.p2presenter.instructor.model.Slide;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.SlideChangedEvent;
import org.p2presenter.instructor.ui.event.ClassListenerRegistry;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerRegistry;
import org.p2presenter.instructor.ui.model.BaseLabelProvider;

public class SlideListView extends ViewPart {
	public static final String ID = "org.p2presenter.instructor.ui.active.slideListView";
	
	private CTabFolder tabs;
	private CTabItem slidesTab, whiteboardsTab;
	private TableViewer slidesViewer, whiteboardsListViewer;
	
	private Activator plugin;
	
	private ClassListenerRegistry<SlideChangedEvent> slideChangedListeners;
	private Lecture lecture;
	
	public SlideListView() {
		this.plugin = Activator.getDefault();
		this.slideChangedListeners = plugin.getListenerRegsitry().getListeners(SlideChangedEvent.class);
	}
	
	public void createPartControl(Composite parent) {
		tabs = new CTabFolder(parent, SWT.BOTTOM);
		
		slidesTab = new CTabItem(tabs, SWT.NONE);
		slidesTab.setText("Slides");
		
		Composite slidesComposite = new Composite(tabs, SWT.NONE);
		slidesComposite.setLayout(new FillLayout(SWT.VERTICAL));
		slidesViewer = new TableViewer(slidesComposite, SWT.V_SCROLL);
		slidesViewer.setContentProvider(new ArrayContentProvider());
		slidesViewer.setLabelProvider(new BaseLabelProvider());
		slidesTab.setControl(slidesComposite);
		
		// TODO should this be removed on dispose?
		slidesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				slideChangedListeners.onEvent(new SlideChangedEvent(this, (Slide) selection.getFirstElement()));
			}
		});

		whiteboardsTab = new CTabItem(tabs, SWT.NONE);
		whiteboardsTab.setText("Whiteboards");
		
		Composite whiteboardsComposite = new Composite(tabs, SWT.NONE);
		whiteboardsComposite.setLayout(new FillLayout(SWT.VERTICAL));
		whiteboardsListViewer = new TableViewer(whiteboardsComposite, SWT.NONE);
		whiteboardsTab.setControl(whiteboardsComposite);
		
		tabs.setSelection(slidesTab);
		
		whiteboardsComposite.setFocus();
		
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		
		Action openLectureAction = new OpenLectureAction("&Open", getViewSite().getWorkbenchWindow());		
		mgr.add(openLectureAction);
		
		Action beginLectureAction = new BeginLectureAction("&Begin");
		mgr.add(beginLectureAction);
		
		Action endLectureAction = new EndLectureAction("&End");
		mgr.add(endLectureAction);
		
		ListenerRegistry listenerRegistry = plugin.getListenerRegsitry();
		
		listenerRegistry.register(LectureOpenedEvent.class, new Listener<LectureOpenedEvent>() {
			public void onEvent(LectureOpenedEvent event) {
				lecture = event.getLecture();
				slidesViewer.setInput(lecture.getSlides());
			}
		});
	}

	public void setFocus() {
		tabs.getSelection().getControl().setFocus();
	}
	
	Lecture getLecture() {
		return lecture;
	}

}
