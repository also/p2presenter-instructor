package org.p2presenter.instructor.ui.builder;

import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

public class PseudoDebuggerEditor extends AbstractDecoratedTextEditor {
	public static final String ID = "org.p2presenter.instructor.ui.builder.pseudoDebuggerEditor";
	
	public PseudoDebuggerEditor() {
		setDocumentProvider(new PseudoDocumentProvider());
	}
}
