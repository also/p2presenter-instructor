package org.p2presenter.instructor.ui;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class InteractivityFormView extends ViewPart {
	public static final String ID = "org.uoregon.cs.presenter.instructor.ui.views.interactivityformview";
	private FormToolkit toolkit;
	private ScrolledForm form;
	
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);

		toolkit.paintBordersFor(form.getBody());
		form.setText("Interactivity Definition");
		
		GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		layout.numColumns = 2;
		
		Section studentSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR|
				  Section.TWISTIE|Section.EXPANDED);
		
		Label instructorJarFileLabel = toolkit.createLabel(form.getBody(), "Instructor JAR:");
		Text instructorJarFileText = toolkit.createText(form.getBody(), "");
		instructorJarFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label studentJarFileLabel = toolkit.createLabel(form.getBody(), "Student JAR:");
		Text studentJarFileText = toolkit.createText(form.getBody(), "");
		studentJarFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label classNameLabel = toolkit.createLabel(form.getBody(), "Class:");
		Text classNameText = toolkit.createText(form.getBody(), "");
		classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	public void setFocus() {
		form.setFocus();
	}
	
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

}
