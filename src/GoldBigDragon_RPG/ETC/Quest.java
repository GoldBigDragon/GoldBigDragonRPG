package GoldBigDragon_RPG.ETC;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import GoldBigDragon_RPG.Main.Main;
import GoldBigDragon_RPG.Main.UserDataObject;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Quest
{
	public void EntityInteract(PlayerInteractEntityEvent event)
	{
		Entity target = event.getRightClicked();
		Player player = event.getPlayer();
    	event.setCancelled(true);
    	UserDataObject u = new UserDataObject();
    	if(u.getType(player).compareTo("Quest")==0)
    	{
        	GoldBigDragon_RPG.GUI.QuestGUI QGUI = new GoldBigDragon_RPG.GUI.QuestGUI();
    		YamlController Config_YC = GoldBigDragon_RPG.Main.Main.YC_1;
    		YamlManager QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");
    		if((u.getString(player,(byte)1).compareTo("Give")==0||u.getString(player,(byte)1).compareTo("Present")==0)
    				||u.getString(player,(byte)1).compareTo("Hunt")==0
    				&&u.getString(player,(byte)3)!=null)
    		{
    			u.setType(player,"Quest");
    			if(u.getString(player,(byte)1).compareTo("Hunt")==0)
    			{
    				if(target.getType() == EntityType.PLAYER)
    					u.setString(player, (byte)3, target.getName());
    				else
    				{
    					if(target.isCustomNameVisible() == false)
        					u.setString(player, (byte)3, target.getName());
    					else
        					u.setString(player, (byte)3, target.getCustomName());
    				}
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW +QGUI.SkullType(u.getString(player,(byte)3))+ChatColor.GREEN + " 몬스터를 얼마나 사냥할지 설정하세요! ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"마리)");
    		    	player.closeInventory();
    		    	return;
    			}
    			
    			if(target.getType() == EntityType.PLAYER)
					u.setString(player, (byte)2, target.getName());
    			else
    			{
    				if(target.isCustomNameVisible() == false)
    					u.setString(player, (byte)2, target.getName());
    				else
    					u.setString(player, (byte)2, target.getCustomName());
    			}
    			new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.HORSE_ARMOR, 1.0F,1.2F);
    			if(u.getString(player,(byte)1).compareTo("Give")==0)
    			{
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : NPC가 유저에게 받을 물건을 설정하세요!");
    		    	player.closeInventory();
    		    	u.setBoolean(player, (byte)1, true);
    	    		QGUI.GetItemGUI(player, u.getString(player,(byte)3));
    			}
    			else if(u.getString(player,(byte)1).compareTo("Present")==0)
    			{
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 보상으로 줄 물건을 설정하세요!");
    		    	player.closeInventory();
    		    	u.setBoolean(player, (byte)1, true);
    	    		QGUI.GetPresentGUI(player, u.getString(player,(byte)3));
    			}
				u.setString(player, (byte)1, target.getUniqueId().toString());
        		return;
    		}
        	switch(u.getString(player,(byte)1))
        	{
	        	case "Script" :
	        	{
	    			Set<String> b = QuestConfig.getConfigurationSection(u.getString(player,(byte)2)+".FlowChart").getKeys(false);
	        		if(u.getString(player,(byte)3)!=null)
	        		{
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".Type", "Script");
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".Script", u.getString(player,(byte)3));
	    				
	    				if(target.getType() == EntityType.PLAYER)
	    				{
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".NPCname", target.getName());
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".NPCuuid", ((Player)target).getUniqueId().toString());
	    				}
	    				else
	    				{
	    					if(target.isCustomNameVisible() == true)
	    						QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".NPCname", target.getCustomName());
	    					else
	    						QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".NPCname", target.getName());
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b.size()+".NPCuuid", target.getUniqueId().toString());
	    				}
	    				new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
	    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
	    				QuestConfig.saveConfig();
	    		    	QGUI.FixQuestGUI(player, 0, u.getString(player,(byte)2));
	    		    	u.clearAll(player);
	        		}
	        	}
	        	break;
        	case "Talk" :
	        	{
	    			Set<String> c = QuestConfig.getConfigurationSection(u.getString(player,(byte)2)+".FlowChart").getKeys(false);
	    			QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c.size()+".Type", "Talk");
	    			if(target.getType() == EntityType.PLAYER)
	    			{
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c.size()+".TargetNPCuuid", ((Player)target).getUniqueId().toString());
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c.size()+".TargetNPCname", target.getName());
	    			}
	    			else
	    			{
	    				if(target.isCustomNameVisible() == true)
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c.size()+".TargetNPCname", target.getCustomName());
	    				else
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c.size()+".TargetNPCname", target.getName());
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c.size()+".TargetNPCuuid", target.getUniqueId().toString());
	    			}
	    			new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
	    	    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
	    			QuestConfig.saveConfig();
	    	    	QGUI.FixQuestGUI(player, 0, u.getString(player,(byte)2));
	    	    	u.clearAll(player);
	        	}
	        	break;
        	}
        	return;
    	}
    	return;
	}
}
