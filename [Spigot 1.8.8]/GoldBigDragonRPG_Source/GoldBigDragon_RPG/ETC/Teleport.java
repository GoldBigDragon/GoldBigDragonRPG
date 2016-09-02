package GoldBigDragon_RPG.ETC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Teleport
{
	public void CreateNewTeleportSpot(Player player, String TeleportName)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		if(player.isOp() == false)
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		else
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

			if(TeleportList.contains(TeleportName))
			{
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 워프 지점은 이미 등록되어 있습니다!");
				return;
			}
			
			TeleportList.set(TeleportName+".World", player.getLocation().getWorld().getName());
			TeleportList.set(TeleportName+".X", player.getLocation().getX());
			TeleportList.set(TeleportName+".Y", player.getLocation().getY());
			TeleportList.set(TeleportName+".Z", player.getLocation().getZ());
			TeleportList.set(TeleportName+".Pitch", player.getLocation().getPitch());
			TeleportList.set(TeleportName+".Yaw", player.getLocation().getYaw());
			TeleportList.set(TeleportName+".OnlyOpUse", true);
			TeleportList.saveConfig();
			
			s.SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 현재 위치로 워프 지점이 등록되었습니다!");
			
			return;
	      }
	}
	
	public void setTeleportPermission(Player player, String TeleportSpotName)
	{
		if(player.isOp() == true)
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");
			if(TeleportList.contains(TeleportSpotName))
			{
				if(TeleportList.getBoolean(TeleportSpotName+".OnlyOpUse") == true)
				{
					TeleportList.set(TeleportSpotName+".OnlyOpUse", false);
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 해당 지역은 일반 유저도 워프할 수 있게 되었습니다!");
				}
				else
				{
					TeleportList.set(TeleportSpotName+".OnlyOpUse", true);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 지역은 일반 유저가 워프할 수 없게 되었습니다!");
					
				}
				TeleportList.saveConfig();
				new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
			}
			else
			{
				new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  		player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름으로 등록된 워프 지점이 없습니다!");
			}
	  		return;
		}
		else
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;	
		}
	}
	
	
	public void RemoveTeleportList(Player player, String TeleportName)
	{
		if(player.isOp() == false)
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			return;
		}
		else
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

			if(TeleportList.contains(TeleportName))
			{
				TeleportList.removeKey(TeleportName+".World");
				TeleportList.removeKey(TeleportName+".X");
				TeleportList.removeKey(TeleportName+".Y");
				TeleportList.removeKey(TeleportName+".Z");
				TeleportList.removeKey(TeleportName+".Pitch");
				TeleportList.removeKey(TeleportName+".Yaw");
				TeleportList.removeKey(TeleportName+".OnlyOpUse");
				TeleportList.removeKey(TeleportName+"");
				TeleportList.saveConfig();

				new GoldBigDragon_RPG.Effect.Sound().SP(player,org.bukkit.Sound.ITEM_BREAK,0.7F,1.0F);
	    		player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+TeleportName+ChatColor.GREEN+" 워프 지점을 성공적으로 삭제하였습니다!");
			}
			else
			{
				new GoldBigDragon_RPG.Effect.Sound().SP(player,org.bukkit.Sound.ITEM_BREAK,0.7F,1.0F);
		  		player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름으로 등록된 워프 지점이 없습니다!");
			}
		}
		return;
	}
	
	public void TeleportUser(Player player, String TeleportSpotName)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		GoldBigDragon_RPG.Effect.Potion p = new GoldBigDragon_RPG.Effect.Potion();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

		if(TeleportList.contains(TeleportSpotName))
		{
			if(TeleportList.getBoolean(TeleportSpotName+".OnlyOpUse")== true  && player.isOp() == false)
			{
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 구역은 제한된 지역입니다!");
				return;
			}
			else
			{
				s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
				Location loc = new Location(Bukkit.getWorld(TeleportList.getString(TeleportSpotName+".World")), TeleportList.getInt(TeleportSpotName+".X")+0.5,  TeleportList.getInt(TeleportSpotName+".Y")+0.5,  TeleportList.getInt(TeleportSpotName+".Z")+0.5,  TeleportList.getInt(TeleportSpotName+".Yaw"),  TeleportList.getInt(TeleportSpotName+".Pitch"));
				player.teleport(loc);
				s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
				p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
				return;
			}
		}
		for(short counter=0;counter<Bukkit.getServer().getWorlds().size();counter++)
		{
			if(Bukkit.getServer().getWorlds().get(counter).getName().equalsIgnoreCase(TeleportSpotName))
			{
				if(player.isOp()==true)
				{
					s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
					player.teleport(Bukkit.getServer().getWorld(TeleportSpotName).getSpawnLocation());
					p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
					s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
				}
				else
				{
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : 월드간 이동은 관리자만 허용됩니다!");
				}
				return;
			}
		}
		s.SP(player,org.bukkit.Sound.ITEM_BREAK,0.7F,1.0F);
  		player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름으로 등록된 워프 지점이 없습니다!");
		return;
	}
}
