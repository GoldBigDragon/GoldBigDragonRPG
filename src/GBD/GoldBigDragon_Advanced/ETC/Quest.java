package GBD.GoldBigDragon_Advanced.ETC;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class Quest
{
	public void EntityInteract(PlayerInteractEntityEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		
		Entity target = event.getRightClicked();
		Player player = event.getPlayer();
    	event.setCancelled(true);

    	if(Main.UserData.get(player).getType()=="Quest")
    	{
        	GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
    		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
    		YamlManager QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");
    		if((Main.UserData.get(player).getString((byte)1).equalsIgnoreCase("Give")||Main.UserData.get(player).getString((byte)1).equalsIgnoreCase("Present")
    				||Main.UserData.get(player).getString((byte)1).equalsIgnoreCase("Hunt"))
    				&&Main.UserData.get(player).getString((byte)3)!=null)
    		{
				Main.UserData.get(player).setType("Quest");
    			if(Main.UserData.get(player).getString((byte)1)=="Hunt")
    			{
    				if(target.getType() == EntityType.PLAYER)
    					Main.UserData.get(player).setString((byte)3, target.getName());
    				else
    				{
    					if(target.isCustomNameVisible() == false)
        					Main.UserData.get(player).setString((byte)3, target.getName());
    					else
        					Main.UserData.get(player).setString((byte)3, target.getCustomName());
    				}
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW +QGUI.SkullType(Main.UserData.get(player).getString((byte)3))+ChatColor.GREEN + " 몬스터를 얼마나 사냥할지 설정하세요! ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"마리)");
    		    	player.closeInventory();
    		    	return;
    			}
    			
    			if(target.getType() == EntityType.PLAYER)
					Main.UserData.get(player).setString((byte)2, target.getName());
    			else
    			{
    				if(target.isCustomNameVisible() == false)
    					Main.UserData.get(player).setString((byte)2, target.getName());
    				else
    					Main.UserData.get(player).setString((byte)2, target.getCustomName());
    			}
    			s.SP(event.getPlayer(), org.bukkit.Sound.HORSE_ARMOR, 1.0F,1.2F);
    			if(Main.UserData.get(player).getString((byte)1)=="Give")
    			{
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : NPC가 유저에게 받을 물건을 설정하세요!");
    		    	player.closeInventory();
    		    	Main.UserData.get(player).setBoolean((byte)1, true);
    	    		QGUI.GetItemGUI(player, Main.UserData.get(player).getString((byte)3));
    			}
    			else if(Main.UserData.get(player).getString((byte)1)=="Present")
    			{
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 보상으로 줄 물건을 설정하세요!");
    		    	player.closeInventory();
    		    	Main.UserData.get(player).setBoolean((byte)1, true);
    	    		QGUI.GetPresentGUI(player, Main.UserData.get(player).getString((byte)3));
    			}
				Main.UserData.get(player).setString((byte)1, target.getUniqueId().toString());
        		return;
    		}
        	switch(Main.UserData.get(player).getString((byte)1))
        	{
        	case "Script" :
    			Set<String> b = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false);
        		if(Main.UserData.get(player).getString((byte)3)!=null)
        		{
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".Type", "Script");
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".Script", Main.UserData.get(player).getString((byte)3));
    				
    				if(target.getType() == EntityType.PLAYER)
    				{
    					QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".NPCname", target.getName());
    					QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".NPCuuid", ((Player)target).getUniqueId().toString());
    				}
    				else
    				{
    					if(target.isCustomNameVisible() == true)
    						QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".NPCname", target.getCustomName());
    					else
    						QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".NPCname", target.getName());
    					QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b.size()+".NPCuuid", target.getUniqueId().toString());
    				}
    				s.SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
    				QuestConfig.saveConfig();
    		    	QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
    		    	Main.UserData.get(player).clearAll();
        		}
        		break;
        		
        	case "Talk" :
    			Set<String> c = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false);
    			QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+c.size()+".Type", "Talk");
    			if(target.getType() == EntityType.PLAYER)
    			{
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+c.size()+".TargetNPCuuid", ((Player)target).getUniqueId().toString());
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+c.size()+".TargetNPCname", target.getName());
    			}
    			else
    			{
    				if(target.isCustomNameVisible() == true)
    					QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+c.size()+".TargetNPCname", target.getCustomName());
    				else
    					QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+c.size()+".TargetNPCname", target.getName());
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+c.size()+".TargetNPCuuid", target.getUniqueId().toString());
    			}
    			s.SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
    	    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
    			QuestConfig.saveConfig();
    	    	QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
		    	Main.UserData.get(player).clearAll();
        		break;
        	}
        	return;
    	}
	}
}
