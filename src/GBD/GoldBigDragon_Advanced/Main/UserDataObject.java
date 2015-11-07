package GBD.GoldBigDragon_Advanced.Main;

import org.bukkit.entity.Player;

public class UserDataObject
{
	private Player player = null;
	private String Type = null;
	private String Stringa[] = new String[9];
	private int Int[] = {-1,-1,-1,-1,-1,-1};
	private boolean Boolean[] = new boolean[2];
	
	public UserDataObject(Player p)
	{
		this.player = p;
		this.Type = null;
		for(byte count = 0; count < this.Stringa.length;count++)
			this.Stringa[count] = null;
		for(byte count = 0; count < this.Int.length;count++)
			this.Int[count] = 0;
		for(byte count = 0; count < this.Boolean.length;count++)
			this.Boolean[count] = false;
	}
	
	public void setType(String TypeName)
	{
		this.Type = TypeName;
	}

	public String getType()
	{
		return this.Type;
	}
	
	public String getString(byte StringNumber)
	{
		return this.Stringa[StringNumber];
	}
	
	public int getInt(byte IntNumber)
	{
		return this.Int[IntNumber];
	}

	public Boolean getBoolean(byte BooleanNumber)
	{
		return this.Boolean[BooleanNumber];
	}

	public void setString(byte StringNumber,String Value)
	{
		this.Stringa[StringNumber] = Value;
	}
	
	public void setInt(byte IntNumber,int Value)
	{
		this.Int[IntNumber] = Value;
	}
	
	public void setBoolean(byte BooleanNumber,boolean Value)
	{
		this.Boolean[BooleanNumber] = Value;
	}

	public void clearType()
	{
		this.Type = null;
	}

	public void clearAll()
	{
		this.Type = null;
		for(byte count = 0; count < this.Stringa.length;count++)
			this.Stringa[count] = null;
		for(byte count = 0; count < this.Int.length;count++)
			this.Int[count] = -1;
		for(byte count = 0; count < this.Boolean.length;count++)
			this.Boolean[count] = false;
	}

	public byte getStringSize()
	{
		return (byte) this.Stringa.length;
	}
	
	public byte getIntSize()
	{
		return (byte) this.Int.length;
	}
	
	public byte getBooleanSize()
	{
		return (byte) this.Boolean.length;
	}
}