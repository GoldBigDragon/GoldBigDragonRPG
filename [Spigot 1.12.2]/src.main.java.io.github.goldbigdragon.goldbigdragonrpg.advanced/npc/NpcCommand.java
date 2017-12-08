package npc;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import util.YamlLoader;

public class NpcCommand
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			SoundEffect.playSound((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length < 1)
		{
			HelpMessage(player);
			return;
		}
		if(player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getTypeId() == 0 || player.getInventory().getItemInMainHand().getAmount() == 0)
		{
			talker.sendMessage("§c[SYSTEM] : 상점에 등록할 아이템을 들고 있어야 합니다!");
			SoundEffect.playSound((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		List<Entity> NearbyEntity = player.getNearbyEntities(3.0, 3.0, 3.0);
		
		for(int count = 0; count <NearbyEntity.size(); count++)
		{
			if(CitizensAPI.getNPCRegistry().isNPC(NearbyEntity.get(count))==true)
			{
			  	YamlLoader npcScriptYaml = new YamlLoader();
				NPC npc = CitizensAPI.getNPCRegistry().getNPC(NearbyEntity.get(count));
				if(npcScriptYaml.isExit("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml") == true)
				{
					npcScriptYaml.getConfig("NPC/NPCData/"+ npc.getUniqueId().toString() +".yml");
					int directory = 0;
					switch(ChatColor.stripColor(args[0]))
					{
					case "판매":
						{
							if(isIntMinMax(args[1], player, 0, Integer.MAX_VALUE))
							{
								directory = npcScriptYaml.getConfigurationSection("Shop.Sell").getKeys(false).toArray().length;
								npcScriptYaml.set("Shop.Sell."+directory+".item", player.getInventory().getItemInMainHand());
								npcScriptYaml.set("Shop.Sell."+directory+".price", Integer.parseInt(args[1]));
								npcScriptYaml.saveConfig();
								talker.sendMessage("§a["+ NearbyEntity.get(count).getCustomName()+"] : 상점에 물품을 등록하였습니다.");
								SoundEffect.playSound((Player)talker, org.bukkit.Sound.BLOCK_CHEST_OPEN, 2.0F, 0.8F);
							}
						}
						return;
					case "구매":
						{
							if(isIntMinMax(args[1], player, 0, Integer.MAX_VALUE))
							{
								directory = npcScriptYaml.getConfigurationSection("Shop.Buy").getKeys(false).toArray().length;
								npcScriptYaml.set("Shop.Buy."+directory+".item", player.getInventory().getItemInMainHand());
								npcScriptYaml.set("Shop.Buy."+directory+".price", Integer.parseInt(args[1]));
								npcScriptYaml.saveConfig();
								talker.sendMessage("§a["+ NearbyEntity.get(count).getCustomName()+"] : 보여주신 물품을 "+args[1]+main.MainServerOption.money+"§a에 사 들이겠습니다.");
								SoundEffect.playSound((Player)talker, org.bukkit.Sound.BLOCK_CHEST_OPEN, 2.0F, 0.8F);
							}
						}
						return;
					}
				}	
			}
		}
		player.sendMessage("§c[SYSTEM] : NPC를 찾을 수 없습니다!");
		SoundEffect.playSound((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		return;
	}
	
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage("§c[SYSTEM] : 최소 §e"+Min+"§c, 최대 §e"+Max+"§c 이하의 숫자를 입력하세요!");
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage("§c[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (§e"+Min+"§c ~ §e"+Max+"§c)");
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}
	
	private void HelpMessage(Player player)
	{
		player.sendMessage("§e────────────[상점 구성 명령어]────────────");
		player.sendMessage("§6/상점 판매 [가격]§e - 근처에 있는 NPC에게 당신이 손에든 물건을 해당 가격에 판매하도록 합니다.");
		player.sendMessage("§6/상점 구매 [가격]§e - 근처에 있는 NPC에게 당신이 손에든 물건을 해당 가격에 구매하도록 합니다.");
		player.sendMessage("§e────────────────────────────────");
	}
}