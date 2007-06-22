/* $Id$ */

package org.p2presenter.instructor.ui.builder;


import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.event.Listener;

public class PseudoSnippetsView extends ViewPart {
	public static final String ID = "org.p2presenter.instructor.ui.builder.pseudosnippets";
	
	private TreeViewer snippetTreeViewer;
	
	private Listener<TextRangeEvent> textRangeEventListener = new Listener<TextRangeEvent>() {
	
		public void onEvent(TextRangeEvent event) {
			snippetTreeViewer.refresh(true);
		}
	
	};
	
	@Override
	public void createPartControl(Composite parent) {
		snippetTreeViewer = new TreeViewer(parent);
		getSite().setSelectionProvider(snippetTreeViewer);
		
		snippetTreeViewer.setContentProvider(new ITreeContentProvider() {
		
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// TODO Auto-generated method stub
		
			}
		
			public void dispose() {
				// TODO Auto-generated method stub
		
			}
		
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}
		
			public boolean hasChildren(Object element) {
				TextRangeNode node = (TextRangeNode) element;
				return node.getChildren().size() > 0;
			}
		
			public Object getParent(Object element) {
				return ((TextRangeNode) element).getParent();
			}
		
			public Object[] getChildren(Object parentElement) {
				return ((TextRangeNode) parentElement).getChildren().toArray();
			}
		
		});
		
		snippetTreeViewer.setLabelProvider(new ILabelProvider() {
		
			public void removeListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
		
			}
		
			public boolean isLabelProperty(Object element, String property) {
				// TODO Auto-generated method stub
				return false;
			}
		
			public void dispose() {
				// TODO Auto-generated method stub
		
			}
		
			public void addListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
		
			}
		
			public String getText(Object element) {
				return Activator.getDefault().getPseudoEditor().getText((TextRangeNode) element);
			}
		
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
		
		});
		snippetTreeViewer.setInput(Activator.getDefault().getPseudoEditor().getRootTextRange());
		
		Activator.getDefault().getListenerRegsitry().register(TextRangeEvent.class, textRangeEventListener);
	}

	@Override
	public void setFocus() {
		snippetTreeViewer.getControl().setFocus();
	}
	
	@Override
	public void dispose() {
		Activator.getDefault().getListenerRegsitry().unregister(TextRangeEvent.class, textRangeEventListener);
		super.dispose();
	}

}
