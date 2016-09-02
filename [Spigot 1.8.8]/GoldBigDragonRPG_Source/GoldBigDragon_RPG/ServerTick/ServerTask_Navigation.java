package GoldBigDragon_RPG.ServerTick;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ServerTask_Navigation
{
	public void Navigation(Long UTC)
	{
		if(ServerTickMain.Schedule.get(UTC).getMaxCount() != -1)
		{
			if(ServerTickMain.Schedule.get(UTC).getCount() < ServerTickMain.Schedule.get(UTC).getMaxCount())
			{
				ServerTickMain.Schedule.get(UTC).setCount(ServerTickMain.Schedule.get(UTC).getCount()+1);
				Routing(ServerTickMain.Schedule.get(UTC));
			}
			else
			{
				for(short count = 0; count < ServerTickMain.NaviUsingList.size(); count++)
				{
					if(ServerTickMain.NaviUsingList.get(count)==ServerTickMain.Schedule.get(UTC).getString((byte)2))
						ServerTickMain.NaviUsingList.remove(count);
				}
			}
		}
		else
			Routing(ServerTickMain.Schedule.get(UTC));
	}
	
	public void Routing(ServerTickScheduleObject STSO)
	{
		if(Bukkit.getServer().getPlayer(STSO.getString((byte)2))!=null)
		{
			if(Bukkit.getServer().getPlayer(STSO.getString((byte)2)).isOnline())
			{
				int Tick = 2500;
				if(STSO.getInt((byte)4)==0)//일반 화살표
					Tick = 2500;
				Player player = Bukkit.getServer().getPlayer(STSO.getString((byte)2));

				Location DestinationLoc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte)1)), STSO.getInt((byte)0), STSO.getInt((byte)1), STSO.getInt((byte)2));
				Location SourceLoc = player.getLocation();
				if(DestinationLoc.getWorld()!=SourceLoc.getWorld())
				{
					GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
					if(DestinationLoc.getWorld().getName().equals("world_nether"))
						PS.sendTitleSubTitle(player, "\'"+ChatColor.YELLOW+""+"\'", "\'"+ChatColor.YELLOW+"["+ChatColor.RED+"네더(지옥) 월드"+ChatColor.YELLOW+"로 이동하세요.]"+"\'", (byte)1, (byte)1, (byte)1);
					else if(DestinationLoc.getWorld().getName().equals("world_the_end"))
						PS.sendTitleSubTitle(player, "\'"+ChatColor.YELLOW+""+"\'", "\'"+ChatColor.YELLOW+"["+ChatColor.GRAY+"엔더 월드"+ChatColor.YELLOW+"로 이동하세요.]"+"\'", (byte)1, (byte)1, (byte)1);
					else
						PS.sendTitleSubTitle(player, "\'"+ChatColor.YELLOW+""+"\'", "\'"+ChatColor.YELLOW+"["+ChatColor.WHITE+DestinationLoc.getWorld().getName()+ChatColor.YELLOW+" 월드로 이동하세요.]"+"\'", (byte)1, (byte)1, (byte)1);
					STSO.setTick(STSO.getTick()+Tick);
					ServerTickMain.Schedule.put(STSO.getTick()+Tick, STSO);
					return;
				}
				int sensitive = STSO.getInt((byte)3);
				int x = (int) (DestinationLoc.getX() - SourceLoc.getX());
				short y = (short) (DestinationLoc.getY() - SourceLoc.getY());
				int z = (int) (DestinationLoc.getZ() - SourceLoc.getZ());
				
				byte nearx = 0;
				byte neary = 0;
				byte nearz = 0;
				
				if(x > sensitive)//동쪽
					nearx = 1;
				else if(x < -1*sensitive)
					nearx = -1;
				else
					nearx = 0;
				if(z > sensitive)//남쪽
					nearz = 1;
				else if(z < -1*sensitive)
					nearz = -1;
				else
					nearz = 0;
				if(y > sensitive)//위쪽
					neary = 1;
				else if(y < -1*sensitive)
					neary = -1;
				else
					neary = 0;
				
				String Way = "F";
				if(nearx == 1)
				{
					if(nearz==0)Way="E";
					if(nearz==1)Way="EN";
					if(nearz==-1)Way="ES";
				}
				else if(nearx == -1)
				{
					if(nearz==0)Way="W";
					if(nearz==1)Way="WN";
					if(nearz==-1)Way="WS";
				}
				else
				{
					if(nearz==1)Way="N";
					if(nearz==-1)Way="S";
				}
				if(nearx==0&&neary==0&&nearz==0)
					FindedWay(player,DestinationLoc);
				else
				{
					ShowWay(player,Way,neary,(byte) STSO.getInt((byte)4));
					STSO.setTick(STSO.getTick()+Tick);
					ServerTickMain.Schedule.put(STSO.getTick()+Tick, STSO);
				}
			}
			else
			{
				for(short count = 0; count < ServerTickMain.NaviUsingList.size(); count++)
				{
					if(ServerTickMain.NaviUsingList.get(count)==STSO.getString((byte)2))
						ServerTickMain.NaviUsingList.remove(count);
				}
			}
		}
		else
		{
			for(short count = 0; count < ServerTickMain.NaviUsingList.size(); count++)
			{
				if(ServerTickMain.NaviUsingList.get(count)==STSO.getString((byte)2))
					ServerTickMain.NaviUsingList.remove(count);
			}
		}
		return;
	}
	
	public void ShowWay(Player player,String Way, short y,byte arrowtype)
	{
		switch(arrowtype)
		{
		default:
			if(Way=="N")
				ArrowParticle_0(player, (byte) 0, y);
			else if(Way=="E")
				ArrowParticle_0(player, (byte) 1, y);
			else if(Way=="S")
				ArrowParticle_0(player, (byte) 2, y);
			else if(Way=="W")
				ArrowParticle_0(player, (byte) 3, y);
			
			if(Way=="EN")
				ArrowParticle_45(player, (byte) 1, y);
			else if(Way=="ES")
				ArrowParticle_45(player, (byte) 2, y);
			else if(Way=="WN")
				ArrowParticle_45(player, (byte) 3, y);
			else if(Way=="WS")
				ArrowParticle_45(player, (byte) 4, y);
			break;
		}
		return;
	}
	
	private void ArrowParticle_0(Player player, byte rotation, short y)
	{
		GoldBigDragon_RPG.Effect.Particle P = new GoldBigDragon_RPG.Effect.Particle();

		P.RLPLR(player, 0, 0.4, 0.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 0.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 0.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.5, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -0.5, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.25, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -0.25, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.75, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -0.75, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -1, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		
		if(y > 0)
			UPSign_0(player, rotation);
		else if(y < 0)
			DNSign_0(player, rotation);
	}

	private void ArrowParticle_45(Player player, byte rotation, short y)
	{
		GoldBigDragon_RPG.Effect.Particle P = new GoldBigDragon_RPG.Effect.Particle();
		P.RLPLR(player, 0.5, 0.4, 0.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.75, 0.4, 0.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1, 0.4, 1, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1.25, 0.4, 1.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1.5, 0.4, 1.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1.75, 0.4, 1.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.25, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.5, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.75, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.25, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.5, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.75, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		if(y>0)
			UPSign_45(player, rotation);
		else if(y<0)
			DNSign_45(player, rotation);
	}
	
	private void UPSign_0(Player player, byte rotation)
	{
		GoldBigDragon_RPG.Effect.Particle P = new GoldBigDragon_RPG.Effect.Particle();
		//U
		P.RLPLRR(player, -0.1, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.4, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.3, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.2, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		//P
		P.RLPLRR(player, 0.1, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.2, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.4, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.2, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.4, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
	}
	
	private void UPSign_45(Player player, byte rotation)
	{
		GoldBigDragon_RPG.Effect.Particle P = new GoldBigDragon_RPG.Effect.Particle();
		//U
		P.RLPLRR(player, 2.4, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.3, 1.4, 2.6, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.2, 1.4, 2.7, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.1, 1.4, 2.8, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 2, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.9, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.8, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.7, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.6, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.5, 2.9, Effect.WATERDRIP, 0, rotation);
		//P
		P.RLPLRR(player, 2.5, 2, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.9, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.8, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.7, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.6, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.5, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.4, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.6, 2, 2.2, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 2, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.8, 2, 2.0, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.6, 1.7, 2.2, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.7, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.8, 1.7, 2.0, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.9, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.8, 1.9, Effect.WATERDRIP, 0, rotation);
	}

	private void DNSign_0(Player player, byte rotation)
	{
		GoldBigDragon_RPG.Effect.Particle P = new GoldBigDragon_RPG.Effect.Particle();
		//D
		P.RLPLRR(player, -0.2, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.3, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.4, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.4, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.3, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.2, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		//W
		P.RLPLRR(player, 0.1, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.2, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.4, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
	}
	
	private void DNSign_45(Player player, byte rotation)
	{
		GoldBigDragon_RPG.Effect.Particle P = new GoldBigDragon_RPG.Effect.Particle();
		//D
		P.RLPLRR(player, 2, 1.4, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.5, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.6, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.7, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.8, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.9, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 2, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.1, 2, 2.8, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.2, 2, 2.7, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.3, 2, 2.6, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.3, 1.4, 2.6, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.2, 1.4, 2.7, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.1, 1.4, 2.8, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.4, 2.9, Effect.WATERDRIP, 0, rotation);
		//W
		P.RLPLRR(player, 2.5, 2, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.9, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.8, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.7, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.6, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.5, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.6, 1.4, 2.2, Effect.WATERDRIP, 0, rotation);
		
		P.RLPLRR(player, 2.7, 2, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.9, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.8, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.7, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.6, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.5, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.8, 1.4, 2, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 2, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.9, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.8, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.7, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.6, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.5, 1.9, Effect.WATERDRIP, 0, rotation);
	}
	
	public void FindedWay(Player player, Location DestinationLoc)
	{
		GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
		new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.NOTE_PLING, 1.0F, 1.0F);
		PS.sendTitleSubTitle(player, "\'"+ChatColor.YELLOW+"도착하였습니다!"+"\'", "\'"+ChatColor.WHITE+"[네비게이션을 초기화 합니다.]"+"\'", (byte)1, (byte)1, (byte)1);

		for(short count = 0; count < ServerTickMain.NaviUsingList.size();count++)
		{
			if(ServerTickMain.NaviUsingList.get(count).equals(player.getName()))
			{
				ServerTickMain.NaviUsingList.remove(count);
				return;
			}
		}
		return;
	}
}
