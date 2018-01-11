package cornflakes.compiler;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class GenericBlockCompiler implements GenericCompiler {
	private MethodData data;
	private List<String[]> lines = new ArrayList<>();

	public GenericBlockCompiler(MethodData data, List<String[]> lines) {
		this.data = data;
		this.lines = lines;
	}

	@Override
	public void compile(ClassData data, MethodVisitor m, Block block, String rawbody, String[] rawlines) {
		Label start = new Label();

		this.data.addBlock();

		m.visitLabel(start);
		m.visitLineNumber(this.data.getBlocks(), start);

		String firstLine = Strings.normalizeSpaces(lines.get(0)[0]);
		String condition = firstLine.substring(0, firstLine.lastIndexOf('{')).trim();

		if (condition.isEmpty()) {
			Block thisBlock = new Block(block.getStart() + 1, start, null);
			block.addBlock(thisBlock);
			String[] newLines = Strings.before(Strings.after(lines.get(0), 1), 1);
			String newBlock = Strings.accumulate(newLines).trim();

			thisBlock.setEndLabel(block.getEndLabel());
			new GenericBodyCompiler(this.data).compile(data, m, thisBlock, newBlock, Strings.accumulate(newBlock));
		} else {
			if (condition.startsWith("if ")) {
				boolean hasElse = false;
				Label finalEnd = new Label();

				int last = block.getStart() + 1;
				for (int i = 0; i < lines.size(); i++) {
					Block currentBlock = new Block(last++, start, null);
					block.addBlock(currentBlock);
					Label theEnd = new Label();
					currentBlock.setEndLabel(theEnd);

					firstLine = Strings.normalizeSpaces(lines.get(i)[0]);
					condition = firstLine.substring(0, firstLine.lastIndexOf('{')).trim();
					String[] newLines = Strings.before(Strings.after(lines.get(i), 1), 1);
					String newBlock = Strings.accumulate(newLines).trim();

					int val = 2;
					if (condition.startsWith("else")) {
						val = 4;
					}
					String parse = condition.substring(val).trim();
					if (!parse.isEmpty()) {
						if (parse.startsWith("if ")) {
							parse = parse.substring(2).trim();
						}
						new BooleanExpressionCompiler(this.data, theEnd, true).compile(data, m, currentBlock, parse,
								new String[] { parse });
					} else {
						if (hasElse) {
							throw new CompileError("Cannot have multiple else blocks attached to one if chain");
						}
						hasElse = true;
					}
					new GenericBodyCompiler(this.data).compile(data, m, block, newBlock, Strings.accumulate(newBlock));
					m.visitFrame(F_SAME, this.data.getLocalVariables(), null, this.data.getCurrentStack(), null);
					m.visitJumpInsn(GOTO, finalEnd);

					m.visitLabel(theEnd);
				}

				m.visitLabel(finalEnd);
			} else if (condition.startsWith("while ")) {
				Block currentBlock = new Block(block.getStart() + 1, start, null);
				block.addBlock(currentBlock);

				Label outOfLoop = new Label();
				currentBlock.setEndLabel(outOfLoop);

				String parse = condition.substring(5).trim();
				String[] newLines = Strings.before(Strings.after(lines.get(0), 1), 1);
				String newBlock = Strings.accumulate(newLines).trim();

				Label afterGoto = new Label();
				Label after = new Label();

				m.visitFrame(F_SAME, this.data.getLocalVariables(), null, this.data.getCurrentStack(), null);
				m.visitJumpInsn(GOTO, after);

				m.visitLabel(afterGoto);
				new GenericBodyCompiler(this.data).compile(data, m, currentBlock, newBlock,
						Strings.accumulate(newBlock));
				m.visitLabel(after);
				new BooleanExpressionCompiler(this.data, outOfLoop, true).compile(data, m, currentBlock, parse,
						new String[] { parse });

				m.visitFrame(F_SAME, this.data.getLocalVariables(), null, this.data.getCurrentStack(), null);
				m.visitJumpInsn(GOTO, afterGoto);
				m.visitLabel(outOfLoop);
			} else if (condition.startsWith("for ")) {
				Block currentBlock = new Block(block.getStart() + 1, start, null);
				block.addBlock(currentBlock);

				Label outOfLoop = new Label();
				currentBlock.setEndLabel(outOfLoop);

				String parse = condition.substring(4).trim();
				String[] newLines = Strings.before(Strings.after(lines.get(0), 1), 1);
				String newBlock = Strings.accumulate(newLines).trim();

				Label afterGoto = new Label();
				Label after = new Label();

				String[] spl = parse.split(";");
				if (spl.length != 3) {
					throw new CompileError("For-loop format should be 'declaration; condition; modification;'");
				}
				String declaration = spl[0].trim();
				String conditionBool = spl[1].trim();
				String increment = spl[2].trim();

				new GenericStatementCompiler(this.data).compile(data, m, currentBlock, declaration,
						new String[] { declaration });

				m.visitFrame(F_SAME, this.data.getLocalVariables(), null, this.data.getCurrentStack(), null);
				m.visitJumpInsn(GOTO, after);

				m.visitLabel(afterGoto);

				new GenericBodyCompiler(this.data).compile(data, m, currentBlock, newBlock,
						Strings.accumulate(newBlock));
				new MathExpressionCompiler(this.data, false, true).compile(data, m, currentBlock, increment,
						new String[] { increment });

				m.visitLabel(after);
				new BooleanExpressionCompiler(this.data, outOfLoop, true).compile(data, m, currentBlock, conditionBool,
						new String[] { conditionBool });

				m.visitFrame(F_SAME, this.data.getLocalVariables(), null, this.data.getCurrentStack(), null);
				m.visitJumpInsn(GOTO, afterGoto);
				m.visitLabel(outOfLoop);
			} else {
				throw new CompileError("Unresolved block condition: " + condition);
			}
		}
	}
}
