package org.p2presenter.instructor.ui.model;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.p2presenter.instructor.model.Course;
import org.p2presenter.instructor.model.Lecture;
import org.p2presenter.instructor.model.Slide;

import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivityEvent;
import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivityMonitor;

public class AdapterFactory implements IAdapterFactory {
	private static final Object[] NO_CHILDREN = {};
	
	private static final IWorkbenchAdapter INTERACTIVITY_EVENT_ADAPTER = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}
	
		public String getLabel(Object o) {
			return o.toString();
		}
	
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}
	
		public Object[] getChildren(Object o) {
			return NO_CHILDREN;
		}
	};
	
	private static final IWorkbenchAdapter INTERACTIVITY_MONITOR_ADAPTER = new IWorkbenchAdapter(){
		public Object getParent(Object o) {
			return null;
		}
	
		public String getLabel(Object o) {
			return null;
		}
	
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}
	
		public Object[] getChildren(Object o) {
			return ((InteractivityMonitor) o).getEvents().toArray();
		}
	};
	
	private static final IWorkbenchAdapter COURSE_ADAPTER = new IWorkbenchAdapter() {
	public Object getParent(Object o) {
			return null;
		}
	
		public String getLabel(Object o) {
			Course course = (Course) o;
			
			String title = course.getTitle();
			String subject = course.getSubject();
			String number = course.getNumber();
			if (subject != null && number != null) {
				title += " (" + subject + ' ' + number + ')';
			}
			
			return title;
		}
	
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}
	
		public Object[] getChildren(Object o) {
			return null;
		}
	};
	
	private static final IWorkbenchAdapter LECTURE_ADAPTER = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}
	
		public String getLabel(Object o) {
			return ((Lecture) o).getTitle();
		}
	
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}
	
		public Object[] getChildren(Object o) {
			return null;
		}
	};
	
	private static final IWorkbenchAdapter SLIDE_ADAPTER = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}
	
		public String getLabel(Object o) {
			String title = ((Slide) o).getTitle();
			return title != null ? title : "Untitled Slide";
		}
	
		public ImageDescriptor getImageDescriptor(Object object) {
			/*final Slide slide = (Slide) object;
			// XXX not ideal
			return new ImageDescriptor() {
				@Override
				public ImageData getImageData() {
					return slide.getDefaultImage(PlatformUI.getWorkbench().getDisplay()).getImageData().scaledTo(160, 120);
				}
				
				
			};*/
			return null;
		}
	
		public Object[] getChildren(Object o) {
			return null;
		}
	};
	
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWorkbenchAdapter.class) {
			if (adaptableObject instanceof InteractivityEvent) {
				return INTERACTIVITY_EVENT_ADAPTER;
			}
			else if (adaptableObject instanceof InteractivityMonitor) {
				return INTERACTIVITY_MONITOR_ADAPTER;
			}
			else if (adaptableObject instanceof Course) {
				return COURSE_ADAPTER;
			}
			else if (adaptableObject instanceof Lecture) {
				return LECTURE_ADAPTER;
			}
			else if (adaptableObject instanceof Slide) {
				return SLIDE_ADAPTER;
			}
		}
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] {IWorkbenchAdapter.class};
	}

}
