package eg.edu.alexu.csd.oop.calculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Engine implements Calculator {

	/** The memory. */
	private DataStructure memory = new DataStructure();

	/** The manager. */
	private ScriptEngineManager manager = new ScriptEngineManager();

	/** The engine. */
	private ScriptEngine engine = manager.getEngineByName("js");

	@Override
	public void input(String s) {
		// TODO Auto-generated method stub
		memory.addResult(s);

	}

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		String result;
		try {
			result = engine.eval(memory.getTop()).toString();
			if (result == "Infinity")
				throw new RuntimeException();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public String current() {
		// TODO Auto-generated method stub
		return memory.getTop();
	}

	@Override
	public String prev() {
		// TODO Auto-generated method stub
		return memory.getPrev();
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return memory.getNext();
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		/*
		 * Create file
		 */
		try {
			FileWriter fstream = new FileWriter("Memory.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			Iterator<String> iterator = memory.getIterator();
			while (iterator.hasNext()) {
				String line = iterator.next();
				out.write(line);
				out.newLine();
			}
			out.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		File input = new File("Memory.txt");
		BufferedReader br = null;
		String line;
		memory.clear();
		try {
			FileReader fr = new FileReader(input);
			br = new BufferedReader(fr);
			line = br.readLine();
			while (line != null) {
				memory.addResult(line);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
