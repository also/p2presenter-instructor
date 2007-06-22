/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SelectSlideImageDirectoryWizardPage extends WizardPage {
	private File directory;
	private String lectureName;
	
	public SelectSlideImageDirectoryWizardPage() {
		super("selectSlideImage");
	}

	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		
		container.setLayout(layout);
		Label label = new Label(container, SWT.NONE);
		label.setText("Slide image directory:");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		label.setLayoutData(gridData);
		
		final Label directoryLabel = new Label(container, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		directoryLabel.setLayoutData(gridData);
		
		Button selectButton = new Button(container, SWT.PUSH);
	    selectButton.setText("Choose...");
	    
	    label = new Label(container, SWT.NONE);
	    label.setText("Lecture name:");
	    gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
	    label.setLayoutData(gridData);
	    
	    final Text lectureNameText = new Text(container, SWT.BORDER);
	    gridData = new GridData();
	    gridData.horizontalAlignment = GridData.FILL;
	    gridData.horizontalSpan = 2;
	    lectureNameText.setLayoutData(gridData);
	    
	    selectButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				DirectoryDialog directoryDialog = new DirectoryDialog(getShell());
				directoryDialog.setMessage("Select the directory containing the slide images");
				String directoryString = directoryDialog.open();
				if (directoryString != null) {
					File directory = new File (directoryString);
					if (directory.isDirectory()) {
						SelectSlideImageDirectoryWizardPage.this.directory = directory;
						directoryLabel.setText(directoryString);
						container.layout();

						updatePageComplete();
					}
					else {
						directory = null;
					}
					updatePageComplete();
				}
			}
		});
	    
	    lectureNameText.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				if (lectureNameText.getText().length() > 1) {
					lectureName = lectureNameText.getText();
				}
				else {
					lectureName = null;
				}

				updatePageComplete();
			}
		});
	}
	
	public File getDirectory() {
		return directory;
	}
	
	public String getLectureName() {
		return lectureName;
	}
	
	private void updatePageComplete() {
		if (directory == null) {
			setPageComplete(false);
			setErrorMessage("You must choose a directory");
		}
		else if (lectureName == null) {
			setPageComplete(false);
			setErrorMessage("You must enter a lecture name");
		}
		else {
			setPageComplete(true);
			setErrorMessage(null);
		}
	}

}
