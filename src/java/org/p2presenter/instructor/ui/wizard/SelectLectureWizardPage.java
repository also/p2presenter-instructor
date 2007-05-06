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
import org.p2presenter.instructor.model.Course;
import org.p2presenter.instructor.model.Lecture;
import org.p2presenter.instructor.ui.model.BaseLabelProvider;


class SelectLectureWizardPage extends WizardPage {
	private Label label;
	private ListViewer lectureListViewer;
	private Lecture lecture;
	
	public SelectLectureWizardPage() {
		super("selectLecture");
		setTitle("Select Lecture");
		setDescription("Select the lecture to open.");
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		
		container.setLayout(layout);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		label = new Label(container, SWT.NONE);
		label.setLayoutData(gridData);
		label.setText("Lectures:");
		
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		
		lectureListViewer = new ListViewer(container, SWT.BORDER);
		lectureListViewer.getControl().setLayoutData(gridData);
		lectureListViewer.setContentProvider(new ArrayContentProvider());
		lectureListViewer.setLabelProvider(new BaseLabelProvider());
		lectureListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = lectureListViewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					lecture = (Lecture) structuredSelection.getFirstElement();
				}
				else {
					lecture = null;
				}
				updatePageComplete();
			}
		});
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			OpenLectureWizard wizard = (OpenLectureWizard) getWizard();
			Course course = wizard.getCourse();
			label.setText(course.getTitle() + " lectures:");
			label.getParent().layout();
			lectureListViewer.setInput(course.getLectures());
		}
		super.setVisible(visible);
	}
	
	private void updatePageComplete() {
		if (getLecture() == null) {
			setPageComplete(false);
			setErrorMessage("You must choose a lecture");
		}
		else {
			setPageComplete(true);
			setErrorMessage(null);
		}
	}
	
	Lecture getLecture() {
		
		
		return lecture;
	}
}