package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * Copyright 2017 GoldBigDragon
 * 
 * GoldBigDragonRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License.

 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class BackUpAllFile {
	public static void copyDir(File dir, File toDir) {
		if (!toDir.exists() && !toDir.getName().equals("BACKUP"))
			toDir.mkdir();
		File[] fList = dir.listFiles();
		for (File result : fList) {
			File oldDir = new File(result.getAbsolutePath());
			File newDir = new File(toDir.getAbsolutePath() + "\\" + result.getName());
			if (!oldDir.getAbsolutePath().contains("\\BACKUP\\")) {
				if (result.isDirectory())
					copyDir(oldDir, newDir);
				else if (result.isFile() && !result.getName().equals("PlayerHashMapSaveFile.yml"))
					copyFile(result, newDir);
			}
		}
	}

	public static boolean copyFile(File oldFile, File newDir) {
		boolean result = false;
		if (oldFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(oldFile.getAbsolutePath());
				FileOutputStream fos = new FileOutputStream(newDir.getAbsolutePath());
				int input = 0;
				while ((input = fis.read()) != -1) {
					fos.write(input);
					result = true;
				}
				fis.close();
				fos.close();
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}

	public static void removeDir(File dir) {
		File[] fList = dir.listFiles();
		for (File result : fList) {
			File newDir = new File(dir.getAbsolutePath() + "\\" + result.getName());
			if (result.isDirectory())
				removeDir(newDir);
			else if (result.isFile())
				newDir.delete();
		}
		dir.delete();
	}
}