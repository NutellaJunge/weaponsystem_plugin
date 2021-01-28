package de.paul.weaponsystem.assets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Assets {
	
	public static InputStream getFile(String f) {
		return Assets.class.getResourceAsStream(f);
	}

	public static void copyFile(File f, String fileInJar) {
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			InputStream stream = getFile(fileInJar);
			if (stream != null) {
				BufferedWriter w = new BufferedWriter(new FileWriter(f));
				BufferedReader r = new BufferedReader(new InputStreamReader(stream));
				
				while (r.ready()) {
					w.write(r.readLine());
					w.newLine();
				}
				
				r.close();
				w.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadLanguages(File langFolder) {
		try {
			File jarFile = new File(Assets.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			JarFile jar = new JarFile(jarFile);
		    Enumeration<JarEntry> entries = jar.entries();
		    while(entries.hasMoreElements()) {
		        String name = entries.nextElement().getName();
		        String path = "assets/lang/";
		        Path p = Paths.get(name);
				if (name.startsWith(path)) {
					if (name.endsWith(".lang")) {
						copyFile(new File(langFolder, p.getFileName().toString()), name.replace("assets/", ""));
					}
		        }
		    }
		    jar.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
