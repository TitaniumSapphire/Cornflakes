package cornflakes.compiler;

public class Operator {
	public static final int BITWISE_AND = 0;
	public static final int ADD = 1;
	public static final int SUBTRACT = 2;
	public static final int MULTIPLY = 3;
	public static final int DIVIDE = 4;
	public static final int XOR = 5;
	public static final int BITWISE_OR = 6;
	public static final int AND = 7;
	public static final int OR = 8;
	public static final int EQUAL = 9;
	public static final int NOT_EQUAL = 10;
	public static final int GREATER_THAN = 11;
	public static final int GREATER_THAN_OR_EQUAL = 12;
	public static final int LESS_THAN = 13;
	public static final int LESS_THAN_OR_EQUAL = 14;

	public static boolean isMathOperator(int op) {
		return op < AND;
	}

	public static boolean isBooleanOperator(int op) {
		return op > BITWISE_OR;
	}

	public static int toOp(String str) {
		switch (str) {
			case "&":
				return BITWISE_AND;
			case "+":
				return ADD;
			case "-":
				return SUBTRACT;
			case "*":
				return MULTIPLY;
			case "/":
				return DIVIDE;
			case "^":
				return XOR;
			case "|":
				return BITWISE_OR;
			case "&&":
				return AND;
			case "||":
				return OR;
			case "==":
				return EQUAL;
			case "!=":
				return NOT_EQUAL;
			case ">":
				return GREATER_THAN;
			case ">=":
				return GREATER_THAN_OR_EQUAL;
			case "<":
				return LESS_THAN;
			case "<=":
				return LESS_THAN_OR_EQUAL;
			default:
				throw new CompileError("Invalid op string");
		}
	}

	public static String toString(int op) {
		switch (op) {
			case BITWISE_AND:
				return "&";
			case ADD:
				return "+";
			case SUBTRACT:
				return "-";
			case MULTIPLY:
				return "*";
			case DIVIDE:
				return "/";
			case XOR:
				return "^";
			case BITWISE_OR:
				return "|";
			case AND:
				return "&&";
			case OR:
				return "||";
			case EQUAL:
				return "==";
			case NOT_EQUAL:
				return "!=";
			case GREATER_THAN:
				return ">";
			case GREATER_THAN_OR_EQUAL:
				return ">=";
			case LESS_THAN:
				return "<";
			case LESS_THAN_OR_EQUAL:
				return "<=";
			default:
				throw new CompileError("Invalid operator ID");
		}
	}

	public static String getOperatorOverloadFunction(int op) {
		String ch = "";
		switch (op) {
			case ADD:
				ch = "_op_add";
				break;
			case SUBTRACT:
				ch = "_op_sub";
				break;
			case MULTIPLY:
				ch = "_op_mul";
				break;
			case DIVIDE:
				ch = "_op_div";
				break;
			case BITWISE_OR:
				ch = "_op_bitor";
				break;
			case BITWISE_AND:
				ch = "_op_bitand";
				break;
			case XOR:
				ch = "_op_xor";
				break;
			case AND:
				ch = "_op_and";
				break;
			case OR:
				ch = "_op_or";
				break;
			case EQUAL:
				ch = "_op_eq";
				break;
			case NOT_EQUAL:
				ch = "_op_neq";
				break;
			case GREATER_THAN:
				ch = "_op_gt";
				break;
			case GREATER_THAN_OR_EQUAL:
				ch = "_op_gte";
				break;
			case LESS_THAN:
				ch = "_op_le";
				break;
			case LESS_THAN_OR_EQUAL:
				ch = "_op_lte";
				break;
			default:
				throw new CompileError("Invalid operator ID: " + op);
		}

		return ch;
	}

	public static String getOperatorOverloadName(int op) {
		String ch = "";
		switch (op) {
			case ADD:
				ch = "add";
				break;
			case SUBTRACT:
				ch = "subtract";
				break;
			case MULTIPLY:
				ch = "multiply";
				break;
			case DIVIDE:
				ch = "divide";
				break;
			case BITWISE_OR:
				ch = "or";
				break;
			case BITWISE_AND:
				ch = "and";
				break;
			case XOR:
				ch = "xor";
				break;
			case AND:
				ch = "and";
				break;
			case OR:
				ch = "or";
				break;
			case EQUAL:
				ch = "equals";
				break;
			case NOT_EQUAL:
				ch = "notEquals";
				break;
			case GREATER_THAN:
				ch = "greaterThan";
				break;
			case GREATER_THAN_OR_EQUAL:
				ch = "greaterThanOrEqualTo";
				break;
			case LESS_THAN:
				ch = "lessThan";
				break;
			case LESS_THAN_OR_EQUAL:
				ch = "lessThanOrEqualTo";
				break;
			default:
				throw new CompileError("Invalid operator ID: " + op);
		}

		return ch;
	}
}
