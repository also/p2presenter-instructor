/* $Id$  */

package org.p2presenter.instructor.ui.builder;

import java.util.ArrayList;

import org.eclipse.jface.text.Position;

class TextRangeNode {
	private TextRangeNode parent;
	private int startIndex;
	private int endIndex;
	
	private Position position;
	
	private ArrayList<TextRangeNode> children = new ArrayList<TextRangeNode>();
	
	public TextRangeNode() {
		this(null, -1, -1);
	}
	
	private TextRangeNode(TextRangeNode parent, int startIndex, int endIndex) {
		this.parent = parent;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public TextRangeNode addSnippet(int startIndex, int endIndex) throws TextRangeOverlapException {
		if (startIndex == this.startIndex && endIndex == this.endIndex) {
			return this;
		}
		
		int startsBeforeSnippetIndex = -1;
		for (int i = 0; i < children.size(); i++) {
			TextRangeNode child = children.get(i);
			if (startIndex >= child.endIndex) {
				/* selection is after child */
				continue;
			}
			else if (startIndex >= child.startIndex) {
				if (endIndex <= child.endIndex) {
					/* selection is inside child */
					return child.addSnippet(startIndex, endIndex);
				}
				else if (startIndex == child.startIndex) {
					/* selection spans child */
					startsBeforeSnippetIndex = i;
					break;
				}
				else {
					/* selection overlaps child */
					throw new TextRangeOverlapException();
				}
			}
			else {
				/* selection starts before child */
				startsBeforeSnippetIndex = i;
				break;
			}
		}
		
		if (startsBeforeSnippetIndex == -1) {
			/* selection is after all children */
			TextRangeNode result = new TextRangeNode(this, startIndex, endIndex);
			children.add(result);
			return result;
		}
		
		int endsBeforeSnippetIndex = -1;
		for (int i = startsBeforeSnippetIndex; i < children.size(); i++) {
			TextRangeNode child = children.get(i);
			if (endIndex <= child.startIndex) {
				/* selection ends before child */
				endsBeforeSnippetIndex = i;
				break;
			}
			else if (endIndex < child.endIndex) {
				/* selection overlaps child */
				throw new TextRangeOverlapException();
			}
		}
		if (endsBeforeSnippetIndex == -1) {
			endsBeforeSnippetIndex = children.size();
		}
		
		if (startsBeforeSnippetIndex == endsBeforeSnippetIndex) {
			/* selection does not span children */
			TextRangeNode result = new TextRangeNode(this, startIndex, endIndex);
			children.add(startsBeforeSnippetIndex, result);
			return result;
		}
		else if (startsBeforeSnippetIndex + 1 == endsBeforeSnippetIndex) {
			/* selection spans only one child */
			TextRangeNode result = children.get(startsBeforeSnippetIndex);
			TextRangeNode spanned = new TextRangeNode(result, result.startIndex, result.endIndex);
			result.startIndex = startIndex;
			result.endIndex = endIndex;
			ArrayList<TextRangeNode> resultChildren = spanned.children;
			spanned.children = result.children;
			result.children = resultChildren;
			resultChildren.add(spanned);
			return result;
		}
		else {
			/* selection spans multiple children */
			TextRangeNode result = new TextRangeNode(this, startIndex, endIndex);
			for (int i = startsBeforeSnippetIndex; i < endsBeforeSnippetIndex; i++) {
				/* FIXME should use linked list; this means multiple array copies */
				TextRangeNode child = children.remove(startsBeforeSnippetIndex);
				child.parent = result;
				result.children.add(child);
			}
			children.add(startsBeforeSnippetIndex, result);
			return result;
		}
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public ArrayList<TextRangeNode> getChildren() {
		return children;
	}
	
	public TextRangeNode getParent() {
		return parent;
	}
	
	public static class TextRangeOverlapException extends Exception {}
}