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
		this.file = new File(Main.plugin.getDataFolder(), "tickets.txt");
		if (!this.file.exists()) {
			this.file.getParentFile().mkdirs();
			try {
				this.file.createNewFile();
			} catch (IOException localIOException) {
			}
		}
	}
	
	public void createFile(String name) {
		
	}

	public List<String> read() {
		if (!this.file.exists()) {
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

	public void write(List<String> list) {
		if (!this.file.exists()) {
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
