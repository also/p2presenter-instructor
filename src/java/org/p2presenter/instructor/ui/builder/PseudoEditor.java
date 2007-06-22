/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.builder.TextRangeNode.TextRangeOverlapException;
import org.p2presenter.instructor.ui.event.ClassListenerRegistry;

public class PseudoEditor extends AbstractDecoratedTextEditor {
	public static final String ID = "org.p2presenter.instructor.ui.builder.pseudoeditor";
	
	private TextRangeNode rootTextRange = new TextRangeNode();
	
	private ISelectionListener selectionListener;
	
	private ClassListenerRegistry<TextRangeEvent> textRangeListeners;
	
	public PseudoEditor() {
		Activator.getDefault().setPseudoEditor(this);
		textRangeListeners = Activator.getDefault().getListenerRegsitry().getListeners(TextRangeEvent.class);
		setDocumentProvider(new PseudoDocumentProvider());
		
		selectionListener = new ISelectionListener() {
			
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				if (selection instanceof IStructuredSelection) {
					Object selectedObject = ((IStructuredSelection) selection).getFirstElement();
					if (selectedObject instanceof TextRangeNode) {
						TextRangeNode node = (TextRangeNode) selectedObject;
						//getSourceViewer().setSelectedRange(node.getStartIndex(), node.getEndIndex() - node.getStartIndex());
						setHighlightRange(node.getStartIndex(), node.getEndIndex() - node.getStartIndex(), true);
						
						// TODO selection doesn't show up until focused
						//setFocus();
						// the following code causes recursion due to selection change
						//part.setFocus();
					}
				}
			}
		
		};
		
		showChangeInformation(true);
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);

		site.getPage().addPostSelectionListener(selectionListener);
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void defineTextRange() throws TextRangeOverlapException {
		Point selection = getSourceViewer().getSelectedRange();
		if (selection.x != selection.y) {
			TextRangeNode result = rootTextRange.addSnippet(selection.x, selection.x + selection.y);
			textRangeListeners.onEvent(new TextRangeEvent(this, result));
		}
		else {
			MessageDialog.openWarning(getSite().getShell(), "Create Snippet", "No text is selected");
		}
	}
	
	public String getText(TextRangeNode textRangeNode) {
		try {
			return getDocumentProvider().getDocument(getEditorInput()).get(textRangeNode.getStartIndex(), textRangeNode.getEndIndex() - textRangeNode.getStartIndex());
		}
		catch (BadLocationException ex) {
			return "";
		}
	}
	
	public TextRangeNode getRootTextRange() {
		return rootTextRange;
	}

}
