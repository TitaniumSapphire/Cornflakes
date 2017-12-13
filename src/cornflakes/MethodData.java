package cornflakes;

import java.util.LinkedHashMap;
import java.util.Map;

public class MethodData {
	private String name;
	private String returnType;
	private Map<String, String> parameters = new LinkedHashMap<>();
	private Map<String, String> locals = new LinkedHashMap<>();
	private int stackSize;
	private int localVariables;
	private int modifiers;

	public MethodData(String name, String ret, int mods) {
		this.name = name;
		this.returnType = ret;
		this.modifiers = mods;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnTypeSignature() {
		return returnType;
	}

	public void setReturnTypeSignature(String returnType) {
		this.returnType = returnType;
	}

	public Class<?> getReturnType() {
		return Types.getTypeFromSignature(Types.unpadSignature(returnType));
	}

	public int getStackSize() {
		return stackSize;
	}

	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}

	public int getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(int localVariables) {
		this.localVariables = localVariables;
	}

	public void addLocalVariable() {
		this.localVariables++;
		this.stackSize++;
	}

	public void increaseStackSize() {
		this.stackSize++;
	}

	public boolean hasLocal(String name) {
		return locals.containsKey(name);
	}

	public String getLocalType(String name) {
		return locals.get(name);
	}

	public void addLocal(String name, String val) {
		locals.put(name, val);
	}
	
	public Map<String, String> getLocals() {
		return locals;
	}

	public void setLocals(Map<String, String> locals) {
		this.locals = new LinkedHashMap<>(locals);
	}

	public void setParameters(Map<String, String> params) {
		this.parameters = new LinkedHashMap<>(params);
	}

	public void addParameter(String name, String type) {
		this.parameters.put(name, type);
	}

	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public boolean hasModifier(int mod) {
		return (this.modifiers & mod) == mod;
	}

	public String getSignature() {
		String desc = "(";
		for (String par : parameters.values()) {
			desc += par;
		}
		desc += ")" + returnType;

		return desc;
	}
}