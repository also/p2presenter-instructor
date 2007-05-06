/* $Id$ */

package org.p2presenter.instructor.ui.wizard;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.p2presenter.instructor.model.Course;
import org.p2presenter.instructor.model.Person;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.model.BaseLabelProvider;


class SelectCourseWizardPage extends WizardPage {
	private ListViewer courseListViewer;
	
	public SelectCourseWizardPage() {
		super("selectCourse");
		setTitle("Select Course");
		setDescription("Select the course to open.");
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		
		container.setLayout(layout);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(gridData);
		label.setText("Your courses:");
		
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		
		courseListViewer = new ListViewer(container, SWT.BORDER);
		courseListViewer.getControl().setLayoutData(gridData);
		courseListViewer.setContentProvider(new ArrayContentProvider());
		courseListViewer.setLabelProvider(new BaseLabelProvider());
		courseListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updatePageComplete();
			}
		});
		
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		Link link = new Link(container, SWT.NONE);
		link.setLayoutData(gridData);
		// FIXME make link active
		link.setText("To create a new course, use the " + Activator.getDefault().getActiveSession().getSiteName() + " <a>web site</a>.");
		
		try {
			Person person = Activator.getDefault().getActiveSession().getPerson();
			courseListViewer.setInput(person.getCoursesTaught());
			
		}
		catch (Exception ex) {
			// TODO
			ex.printStackTrace();
		}
		
		setPageComplete(false);
	}
	
	private void updatePageComplete() {
		if (getCourse() != null) {
			setPageComplete(true);
			setErrorMessage(null);
		}
		else {
			setErrorMessage("You must choose a course");
			setPageComplete(false);
		}
	}
	
	Course getCourse() {
		ISelection selection = courseListViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			return (Course) structuredSelection.getFirstElement();
		}
		
		return null;
	}
}