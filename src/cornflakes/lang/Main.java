package cornflakes.lang;

import cornflakes.compiler.MainCompiler;
import game.server.HelloWorld;

/**
 * A temporary class for testing Cornflakes stuff that I often accidentally
 * check into the repo because I'm in a hurry.
 * 
 * @author Lucas Baizer
 */
public class Main {
	public static void main(String[] args) throws Exception {
		MainCompiler.main(new String[] { "src" });

		HelloWorld.main(new String[0]);
	}
}
