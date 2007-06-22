package org.p2presenter.instructor.ui.builder;

import org.p2presenter.instructor.ui.event.AbstractEvent;

public class TextRangeEvent extends AbstractEvent {
	private TextRangeNode textRangeNode;
	
	public TextRangeEvent(Object source, TextRangeNode textRangeNode) {
		super(source);
		this.textRangeNode = textRangeNode;
	}
	
	public TextRangeNode getTextRangeNode() {
		return textRangeNode;
	}

}
