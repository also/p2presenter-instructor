/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.eclipse.swt.SWT;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class InkView extends ViewPart {
	public static final String ID = "org.p2presenter.instructor.ui.active.inkView";
	
	private OleFrame frame;
	
	public void createPartControl(final Composite parent) {
		frame = new OleFrame(parent, SWT.NONE);
		OleControlSite clientSite = new OleControlSite(frame, SWT.NONE, "msinkaut.InkPicture.1");
		clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
	}

	public void setFocus() {
		frame.setFocus();
	}

}
