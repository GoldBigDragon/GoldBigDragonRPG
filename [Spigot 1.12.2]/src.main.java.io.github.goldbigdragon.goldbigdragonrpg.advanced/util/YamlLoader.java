package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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

public class YamlLoader
{
	private FileConfiguration config;
	private File file;
	private String charset;
	private Charset defaultCharset = Charset.forName("UTF-8");
	
	public void getConfig(String path)
	{
		getConfig(path, "UTF-8");
	}

	public void getConfig(String path, char language)
	{
		if(language == 'J')
			getConfig(path, "EUCJP");//iso-2022-jp
		else if(language == 'C')
			getConfig(path, "EUCCN");//UTF-16BE
		else if(language == 'K')
			getConfig(path, "EUC-KR");
		else
			getConfig(path, "UTF-8");
	}

	public void getConfig(String path, String encoding)
	{
		charset = encoding;
		file = new File(main.Main.plugin.getDataFolder().getAbsolutePath() + File.separator + path);
		try
		{
			if (!file.exists())
			{
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		}
		catch (IOException e)
		{
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	
	
    public Object get(String path) {return this.config.get(path);}
    
    public Object get(String path, Object def) {return this.config.get(path, def);}
 
    public String getString(String path) {return this.config.getString(path);}
 
    public int getInt(String path) {return this.config.getInt(path);}
 
    public long getLong(String path) {return this.config.getLong(path);}
 
    public boolean getBoolean(String path) {return this.config.getBoolean(path);}
 
    public void createSection(String path) {this.config.createSection(path);}
 
    public ConfigurationSection getConfigurationSection(String path) {return this.config.getConfigurationSection(path);}
 
    public double getDouble(String path) {return this.config.getDouble(path);}
 
    public List<?> getList(String path) {return this.config.getList(path);}
 
    public ItemStack getItemStack(String path) {return this.config.getItemStack(path);}
    
    public List<Map<?, ?>> getMapList(String path) {return this.config.getMapList(path);}
    
    public boolean contains(String path) {return this.config.contains(path);}
 
    public void removeKey(String path) {this.config.set(path, null);}

    public void set(String path, Object value){this.config.set(path, value);}
    
    public void setString(String path, String value)
    {
    	value = new String(value.getBytes(defaultCharset), defaultCharset);
    	this.config.set(path, value);
    }
    
    public void saveConfig() 
    {
		try {
	    	Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), defaultCharset));
	    	fileWriter.write(config.saveToString());
	    	fileWriter.flush();
	    	fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    public Set<String> getKeys() {return this.config.getKeys(false);}

    public void delete()
    {
		if (file.exists())
	    	file.delete();
    }
    
    public void delete(String path)
    {
    	File willDelete = new File(main.Main.plugin.getDataFolder().getAbsolutePath() + File.separator + path);
		if (willDelete.exists())
			willDelete.delete();
    }
    
    public boolean isExit(String path)
    {
    	return new File(main.Main.plugin.getDataFolder().getAbsolutePath() + File.separator + path).exists();
    }
}