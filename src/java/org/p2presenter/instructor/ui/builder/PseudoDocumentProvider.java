package org.p2presenter.instructor.ui.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.AbstractDocumentProvider;

public class PseudoDocumentProvider extends AbstractDocumentProvider {

	@Override
	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		// TODO Auto-generated method stub
		Document document = new Document();
		return document;
	}

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isModifiable(Object element) {
		return true;
	}

}
