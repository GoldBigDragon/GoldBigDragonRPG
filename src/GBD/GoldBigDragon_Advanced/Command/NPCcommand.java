package GBD.GoldBigDragon_Advanced.Command;

import java.util.List;

import net.citizensnpcs.api.*;
import net.citizensnpcs.api.npc.*;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class NPCcommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ���ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
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
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : ������ ����� �������� ��� �־�� �մϴ�!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		ItemStack item = player.getItemInHand();
		
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		
		List<Entity> NearbyEntity = player.getNearbyEntities(3.0, 3.0, 3.0);
		
		for(int count = 0; count <NearbyEntity.size(); count++)
		{
			if(CitizensAPI.getNPCRegistry().isNPC(NearbyEntity.get(count))==true)
			{
				NPC npc = CitizensAPI.getNPCRegistry().getNPC(NearbyEntity.get(count));
				if(GUI_YC.isExit("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml") == true)
				{
					YamlManager NPCscript = GUI_YC.getNewConfig("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml");
					int directory = 0;

					switch(ChatColor.stripColor(args[0]))
					{
					case "�Ǹ�":
						directory = NPCscript.getConfigurationSection("Shop.Sell").getKeys(false).toArray().length;
						NPCscript.set("Shop.Sell."+directory+".item", item);
						NPCscript.set("Shop.Sell."+directory+".price", Integer.parseInt(args[1]));
						NPCscript.saveConfig();
						talker.sendMessage(ChatColor.GREEN +"["+ NearbyEntity.get(count).getCustomName()+"] : ������ ��ǰ�� ����Ͽ����ϴ�.");
						s.SP((Player)talker, org.bukkit.Sound.CHEST_CLOSE, 2.0F, 0.8F);
						return;
					case "����":
						directory = NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).toArray().length;
						directory = NPCscript.getConfigurationSection("Shop.Buy").getKeys(false).size();
						NPCscript.set("Shop.Buy."+directory+".item", item);
						NPCscript.set("Shop.Buy."+directory+".price", Integer.parseInt(args[1]));
						NPCscript.saveConfig();
						talker.sendMessage(ChatColor.GREEN +"["+ NearbyEntity.get(count).getCustomName()+"] : �����ֽ� ��ǰ�� "+args[1]+"���� �� ���̰ڽ��ϴ�.");
						s.SP((Player)talker, org.bukkit.Sound.CHEST_OPEN, 2.0F, 0.8F);
						return;
					}
				}	
			}
		}
		
		player.sendMessage(ChatColor.RED + "[SYSTEM] : NPC�� ã�� �� �����ϴ�!");
		s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		return;
	}
}