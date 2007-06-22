/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.p2presenter.instructor.model.Course;
import org.p2presenter.instructor.model.Lecture;
import org.p2presenter.instructor.ui.wizard.SelectCourseWizardPage;

public class NewLectureWizard extends Wizard {

	private SelectCourseWizardPage selectCourseWizardPage;
	private SelectSlideImageDirectoryWizardPage selectSlideImageDirectoryWizardPage;
	
	@Override
	public void addPages() {
		setWindowTitle("New Lecture");
		addPage(selectCourseWizardPage = new SelectCourseWizardPage());
		addPage(selectSlideImageDirectoryWizardPage = new SelectSlideImageDirectoryWizardPage());
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public boolean performFinish() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
		
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				File pngFiles[] = selectSlideImageDirectoryWizardPage.getDirectory().listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(".png");
					}
				});
				monitor.beginTask("Uploading images", pngFiles.length);
				
				// TODO duplicate name error
				Course course = selectCourseWizardPage.getCourse();
				Lecture lecture = new Lecture();
				lecture.setTitle(selectSlideImageDirectoryWizardPage.getLectureName());
				course.addLecture(lecture);
				
				for (File slideFile : pngFiles) {
					if (monitor.isCanceled()) {
						return;
					}
					try {
						lecture.addSlideImage(slideFile);
					}
					catch (RuntimeException ex) {
						throw ex;
					}
					catch (Exception ex) {
						throw new InvocationTargetException(ex);
					}
					
					monitor.worked(1);
				}
				
				monitor.done();
			}
		
		};
		
		try {
			getContainer().run(true, true, runnable);
		}
		catch (InterruptedException ex) {
			//TODO
		}
		catch (InvocationTargetException ex) {
			// TODO
		}
		return true;
	}
	
	@Override
	public boolean canFinish() {
		return selectSlideImageDirectoryWizardPage.getDirectory() != null && selectSlideImageDirectoryWizardPage.getLectureName() != null;
	}
	
	public Course getCourse() {
		return selectCourseWizardPage.getCourse();
	}

}
