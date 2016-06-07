package ch.gyselanimatioon.miepcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	private File file;

	public FileManager() {
	}

	public boolean createFile(String folder, String filename) {
		file = new File(Main.plugin.getDataFolder() + "/" + folder, filename + ".txt");

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
				return true;
			} catch (IOException localIOException) {
			}
		}
		return false;
	}

	public List<String> read(String folder, String filename) {
		file = new File(Main.plugin.getDataFolder() + "/" + folder, filename + ".txt");
		if (!file.exists()) {
			this.file.getParentFile().mkdirs();
			try {
				this.file.createNewFile();
			} catch (IOException localIOException1) {
			}
		}
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.file));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					// String line;
					list.add(line);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void write(String folder, String filename, List<String> list) {
		file = new File(Main.plugin.getDataFolder() + "/" + folder, filename + ".txt");
		if (!file.exists()) {
			this.file.getParentFile().mkdirs();
			try {
				this.file.createNewFile();
			} catch (IOException localIOException1) {
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
			for (int i = 0; i < list.size(); i++) {
				writer.write((String) list.get(i));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
