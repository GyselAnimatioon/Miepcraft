package ch.gyselanimatioon.miepcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	private File file;

	public boolean createFile(String folder, String filename) {
		this.file = new File(PluginMain.plugin.getDataFolder() + "/" + folder, filename + ".txt");
		if (!this.file.exists()) {
			this.file.getParentFile().mkdirs();
			try {
				this.file.createNewFile();
				return true;
			} catch (IOException localIOException) {

			}
		}
		return false;
	}

	public List<String> read(String folder, String filename) {
		this.file = new File(PluginMain.plugin.getDataFolder() + "/" + folder, filename + ".txt");
		if (!this.file.exists()) {
			this.file.getParentFile().mkdirs();
			try {
				this.file.createNewFile();
			} catch (IOException localIOException1) {

			}
		}

		ArrayList<String> list = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.file));

			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			reader.close();
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void write(String folder, String filename, List<String> list) {
		this.file = new File(PluginMain.plugin.getDataFolder() + "/" + folder, filename + ".txt");
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