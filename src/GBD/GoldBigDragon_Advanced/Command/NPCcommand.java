package GBD.GoldBigDragon_Advanced.Command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class NPCcommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length < 1)
		{
			HelpMessager(player, 7);
			return;
		}
		if(player.getItemInHand().getType() == Material.AIR || player.getItemInHand().getTypeId() == 0 || player.getItemInHand().getAmount() == 0)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 상점에 등록할 아이템을 들고 있어야 합니다!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		List<Entity> NearbyEntity = player.getNearbyEntities(3.0, 3.0, 3.0);
		
		for(int count = 0; count <NearbyEntity.size(); count++)
		{
			if(CitizensAPI.getNPCRegistry().isNPC(NearbyEntity.get(count))==true)
			{
				YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
				NPC npc = CitizensAPI.getNPCRegistry().getNPC(NearbyEntity.get(count));
				if(GUI_YC.isExit("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml") == true)
				{
					YamlManager NPCscript = GUI_YC.getNewConfig("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml");
					int directory = 0;
					switch(ChatColor.stripColor(args[0]))
					{
					case "판매":
						{
							directory = NPCscript.getConfigurationSection("Shop.Sell").getKeys(false).toArray().length;
							NPCscript.set("Shop.Sell."+directory+".item", player.getItemInHand());
							NPCscript.set("Shop.Sell."+directory+".price", Integer.parseInt(args[1]));
							NPCscript.saveConfig();
							talker.sendMessage(ChatColor.GREEN +"["+ NearbyEntity.get(count).getCustomName()+"] : 상점에 물품을 등록하였습니다.");
							s.SP((Player)talker, org.bukkit.Sound.CHEST_CLOSE, 2.0F, 0.8F);
						}
						return;
					case "구매":
						{
							directory = NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).toArray().length;
							directory = NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).size();
							NPCscript.set("Shop.Buy."+directory+".item", player.getItemInHand());
							NPCscript.set("Shop.Buy."+directory+".price", Integer.parseInt(args[1]));
							NPCscript.saveConfig();
							talker.sendMessage(ChatColor.GREEN +"["+ NearbyEntity.get(count).getCustomName()+"] : 보여주신 물품을 "+args[1]+GBD.GoldBigDragon_Advanced.Main.ServerOption.Money+ChatColor.GREEN+"에 사 들이겠습니다.");
							s.SP((Player)talker, org.bukkit.Sound.CHEST_OPEN, 2.0F, 0.8F);
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
}
