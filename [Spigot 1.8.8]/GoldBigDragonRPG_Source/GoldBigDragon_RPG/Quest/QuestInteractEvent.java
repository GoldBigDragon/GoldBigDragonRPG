package GoldBigDragon_RPG.Quest;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class QuestInteractEvent
{
	public void EntityInteract(PlayerInteractEntityEvent event)
	{
		Entity target = event.getRightClicked();
		Player player = event.getPlayer();
    	event.setCancelled(true);
    	Object_UserData u = new Object_UserData();
    	if(u.getType(player).compareTo("Quest")==0)
    	{
        	GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
        	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
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
	    			short b = (short) QuestConfig.getConfigurationSection(u.getString(player,(byte)2)+".FlowChart").getKeys(false).size();
	        		if(u.getString(player,(byte)3)!=null)
	        		{
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".Type", "Script");
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".Script", u.getString(player,(byte)3));
	    				
	    				if(target.getType() == EntityType.PLAYER)
	    				{
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".NPCname", target.getName());
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".NPCuuid", ((Player)target).getUniqueId().toString());
	    				}
	    				else
	    				{
	    					if(target.isCustomNameVisible() == true)
	    						QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".NPCname", target.getCustomName());
	    					else
	    						QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".NPCname", target.getName());
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+b+".NPCuuid", target.getUniqueId().toString());
	    				}
	    				new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
	    		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
	    				QuestConfig.saveConfig();
	    		    	QGUI.FixQuestGUI(player, (short) 0, u.getString(player,(byte)2));
	    		    	u.clearAll(player);
	        		}
	        	}
	        	break;
        	case "Talk" :
	        	{
	    			short c = (short) QuestConfig.getConfigurationSection(u.getString(player,(byte)2)+".FlowChart").getKeys(false).size();
	    			QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c+".Type", "Talk");
	    			if(target.getType() == EntityType.PLAYER)
	    			{
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c+".TargetNPCuuid", ((Player)target).getUniqueId().toString());
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c+".TargetNPCname", target.getName());
	    			}
	    			else
	    			{
	    				if(target.isCustomNameVisible() == true)
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c+".TargetNPCname", target.getCustomName());
	    				else
	    					QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c+".TargetNPCname", target.getName());
	    				QuestConfig.set(u.getString(player,(byte)2)+".FlowChart."+c+".TargetNPCuuid", target.getUniqueId().toString());
	    			}
	    			new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
	    	    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
	    			QuestConfig.saveConfig();
	    	    	QGUI.FixQuestGUI(player, (short) 0, u.getString(player,(byte)2));
	    	    	u.clearAll(player);
	        	}
	        	break;
        	}
        	return;
    	}
    	return;
	}

	public void BlockInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		Object_UserData u = new Object_UserData();
		
		event.setCancelled(true);
		if(event.getAction()==Action.LEFT_CLICK_BLOCK)
		{
			if(u.getString(player, (byte)1).compareTo("TelePort")==0)
			{
				String QuestName = u.getString(player, (byte)3);
		    	GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();

		    	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
				short	size = (short) QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length;
				
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "TelePort");
				QuestConfig.set(QuestName+".FlowChart."+size+".World", block.getLocation().getWorld().getName());
				QuestConfig.set(QuestName+".FlowChart."+size+".X", block.getLocation().getX());
				QuestConfig.set(QuestName+".FlowChart."+size+".Y", block.getLocation().getY());
				QuestConfig.set(QuestName+".FlowChart."+size+".Z", block.getLocation().getZ());
				QuestConfig.set(QuestName+".FlowChart."+size+".NPCname", " ");
				QuestConfig.saveConfig();
		    	QGUI.FixQuestGUI(player, (short) 0, QuestName);
		    	u.clearAll(player);
		    	new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
		    	return;
			}
		}
		if(event.getAction() ==Action.RIGHT_CLICK_BLOCK)
		{
			if(u.getString(player, (byte)1)!=null&&
				u.getString(player, (byte)3)!=null)
			{
				if(u.getString(player, (byte)1).compareTo("Harvest")==0)
				{
					u.setInt(player, (byte)1, block.getTypeId());
					u.setInt(player, (byte)2,(int)block.getData());
			    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.GREEN + "블록의 데이터까지 완벽히 같아야 하나요? ("+ChatColor.RED + "X"+ChatColor.GREEN +" 혹은 "+ChatColor.DARK_AQUA +"O"+ChatColor.GREEN +")");
			    	player.closeInventory();
			    	return;
				}
				else if(u.getString(player, (byte)1).compareTo("BlockPlace")==0)
				{
					String QuestName = u.getString(player, (byte)2);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
					short	size = (short) QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length;
					
					QuestConfig.set(QuestName+".FlowChart."+size+".Type", "BlockPlace");
					QuestConfig.set(QuestName+".FlowChart."+size+".World", block.getLocation().getWorld().getName());
					QuestConfig.set(QuestName+".FlowChart."+size+".X", block.getLocation().getX());
					QuestConfig.set(QuestName+".FlowChart."+size+".Y", block.getLocation().getY());
					QuestConfig.set(QuestName+".FlowChart."+size+".Z", block.getLocation().getZ());
					QuestConfig.set(QuestName+".FlowChart."+size+".ID", 1);
					QuestConfig.set(QuestName+".FlowChart."+size+".DATA", 0);
					QuestConfig.saveConfig();
			    	u.setString(player, (byte)3, null);
			    	u.setString(player, (byte)1, "BPID");
			    	u.setInt(player, (byte)1, size);
			    	new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
			    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : 설치 될 블록 ID를 입력 해 주세요!");
					return;
				}
			}
		}
		return;
	}
}
