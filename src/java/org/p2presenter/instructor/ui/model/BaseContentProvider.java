package org.p2presenter.instructor.ui.model;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class BaseContentProvider extends BaseWorkbenchContentProvider {
	@Override
	protected IWorkbenchAdapter getAdapter(Object element) {
		IWorkbenchAdapter adapter = super.getAdapter(element);
		
		// see eclipse bug 97780
		if (adapter == null && element != null) {
			adapter = (IWorkbenchAdapter) Platform.getAdapterManager().loadAdapter(element, IWorkbenchAdapter.class.getName());
		}
		
		return adapter;
	}
}
