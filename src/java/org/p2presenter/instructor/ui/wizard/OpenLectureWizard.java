/* $Id$ */

package org.p2presenter.instructor.ui.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.p2presenter.instructor.model.Course;
import org.p2presenter.instructor.model.Lecture;



public class OpenLectureWizard extends Wizard {
	private SelectCourseWizardPage selectCourseWizardPage;
	private SelectLectureWizardPage selectLectureWizardPage;
	
	@Override
	public void addPages() {
		setWindowTitle("Open Lecture");
		addPage(selectCourseWizardPage = new SelectCourseWizardPage());
		addPage(selectLectureWizardPage = new SelectLectureWizardPage());
	}
	
	@Override
	public boolean canFinish() {
		return selectLectureWizardPage.getLecture() != null;
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	public Course getCourse() {
		return selectCourseWizardPage.getCourse();
	}

	public Lecture getLecture() {
		return selectLectureWizardPage.getLecture();
	}
}
