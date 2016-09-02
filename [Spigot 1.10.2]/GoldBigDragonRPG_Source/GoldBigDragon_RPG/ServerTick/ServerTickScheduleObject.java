package GoldBigDragon_RPG.ServerTick;

public class ServerTickScheduleObject
{
	private long Tick = 0;
	private int count = 0;
	private int MaxCount = 0;
	private String Type = null;
	private String Stringa[] = new String[4];
	private int Int[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private boolean Boolean[] = new boolean[2];
	
	public ServerTickScheduleObject(long Tick, String Type)
	{
		this.Tick = Tick;
		this.Type = Type;
		for(byte count = 0; count < this.Stringa.length;count++)
			this.Stringa[count] = null;
		for(byte count = 0; count < this.Int.length;count++)
			this.Int[count] = 0;
		for(byte count = 0; count < this.Boolean.length;count++)
			this.Boolean[count] = false;
	}
	
	public void copyThisScheduleObject(long WillAddTick)
	{
		GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(WillAddTick, this);
	}

	public void setTick(long Tick)
	{
		this.Tick = Tick;
	}

	public long getTick()
	{
		return this.Tick;
	}
	public void setMaxCount(int Max)
	{
		this.MaxCount = Max;
	}

	public int getMaxCount()
	{
		return this.MaxCount;
	}
	public void setCount(int count)
	{
		this.count = count;
	}

	public int getCount()
	{
		return this.count;
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