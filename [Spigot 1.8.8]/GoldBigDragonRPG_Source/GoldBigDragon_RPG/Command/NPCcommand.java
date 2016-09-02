package GoldBigDragon_RPG.Command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class NPCcommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length < 1)
		{
			HelpMessager(player, (byte) 7);
			return;
		}
		if(player.getItemInHand().getType() == Material.AIR || player.getItemInHand().getTypeId() == 0 || player.getItemInHand().getAmount() == 0)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 상점에 등록할 아이템을 들고 있어야 합니다!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		List<Entity> NearbyEntity = player.getNearbyEntities(3.0, 3.0, 3.0);
		
		for(short count = 0; count <NearbyEntity.size(); count++)
		{
			if(CitizensAPI.getNPCRegistry().isNPC(NearbyEntity.get(count))==true)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				NPC npc = CitizensAPI.getNPCRegistry().getNPC(NearbyEntity.get(count));
				if(YC.isExit("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml") == true)
				{
					YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml");
					int directory = 0;
					switch(ChatColor.stripColor(args[0]))
					{
					case "판매":
						{
							if(isIntMinMax(args[1], player, 0, Integer.MAX_VALUE))
							{
								directory = NPCscript.getConfigurationSection("Shop.Sell").getKeys(false).toArray().length;
								NPCscript.set("Shop.Sell."+directory+".item", player.getItemInHand());
								NPCscript.set("Shop.Sell."+directory+".price", Integer.parseInt(args[1]));
								NPCscript.saveConfig();
								talker.sendMessage(ChatColor.GREEN +"["+ NearbyEntity.get(count).getCustomName()+"] : 상점에 물품을 등록하였습니다.");
								s.SP((Player)talker, org.bukkit.Sound.CHEST_CLOSE, 2.0F, 0.8F);
							}
						}
						return;
					case "구매":
						{
							if(isIntMinMax(args[1], player, 0, Integer.MAX_VALUE))
							{
								directory = NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).toArray().length;
								NPCscript.set("Shop.Buy."+directory+".item", player.getItemInHand());
								NPCscript.set("Shop.Buy."+directory+".price", Integer.parseInt(args[1]));
								NPCscript.saveConfig();
								talker.sendMessage(ChatColor.GREEN +"["+ NearbyEntity.get(count).getCustomName()+"] : 보여주신 물품을 "+args[1]+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.GREEN+"에 사 들이겠습니다.");
								s.SP((Player)talker, org.bukkit.Sound.CHEST_OPEN, 2.0F, 0.8F);
							}
						}
						return;
					}
				}	
			}
		}
		player.sendMessage(ChatColor.RED + "[SYSTEM] : NPC를 찾을 수 없습니다!");
		s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		return;
	}
	
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+Min+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+Max+ChatColor.RED+" 이하의 숫자를 입력하세요!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}
}
