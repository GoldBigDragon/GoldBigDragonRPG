package GoldBigDragon_RPG.Effect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Sound
{
    //해당 플레이어에게 소리를 내게하는 메소드//
	public void SP(Player player, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(player.isOnline() == true)
    		player.playSound(player.getLocation(), sound ,volume, pitch);
	}
	
    //해당 플레이어에게 해당 위치에서 소리를 내게하는 메소드//
	public void SPL(Player player, Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(player.isOnline() == true)
		player.playSound(loc, sound ,volume, pitch);
	}
	
	//해당 위치에 사운드를 내게하는 메소드//
	public void SL(Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
		World world = loc.getWorld();
		world.playSound(loc, sound ,volume, pitch);
	}

	public void IronDoorOpening(Location loc)
	{
		GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(0, "Sound");
		STSO.setType("Sound");
		STSO.setString((byte)1, loc.getWorld().getName());
		STSO.setInt((byte)0, (int)loc.getX());
		STSO.setInt((byte)1, (int)loc.getY());
		STSO.setInt((byte)2, (int)loc.getZ());
		STSO.setString((byte)0, "0000001");//소리 구성
		STSO.setInt((byte)3, 20);//소리 크기
		STSO.setInt((byte)4, 5);//소리 속도
		
		STSO.setInt((byte)5, 1);//틱 설정
		STSO.setMaxCount(STSO.getString((byte)0).length());
		STSO.setTick(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC);
		GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, STSO);
	}
}
