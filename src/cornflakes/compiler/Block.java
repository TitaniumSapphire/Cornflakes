package cornflakes.compiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Label;

public class Block implements Comparable<Block> {
	private int start;
	private Label startLabel;
	private Label endLabel;
	private List<Block> subBlocks = new ArrayList<>();
	private Set<DefinitiveType> thrownExceptions = new HashSet<>();

	public Block(int start, Label slabel, Label elabel) {
		this.start = start;
		this.startLabel = slabel;
		this.endLabel = elabel;
	}

	public Label getStartLabel() {
		return startLabel;
	}

	public void setStartLabel(Label startLabel) {
		this.startLabel = startLabel;
	}

	public Label getEndLabel() {
		return endLabel;
	}

	public void setEndLabel(Label endLabel) {
		this.endLabel = endLabel;
	}

	public int getStart() {
		return this.start;
	}

	public int getEnd() {
		if (subBlocks.size() == 0) {
			return start + 1;
		}

		return subBlocks.get(subBlocks.size() - 1).start + 1;
	}

	public List<Block> getSubBlocks() {
		return subBlocks;
	}

	public void addBlock(Block sub) {
		subBlocks.add(sub);

		Collections.sort(subBlocks);
	}

	@Override
	public int compareTo(Block o2) {
		return this.start - o2.start;
	}

	public Set<DefinitiveType> getThrownExceptions() {
		return thrownExceptions;
	}

	public void addThrownException(DefinitiveType exception) {
		this.thrownExceptions.add(exception);
	}
}
