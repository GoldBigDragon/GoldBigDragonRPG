package OtherPlugins;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;
import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class CitizensMain implements Listener
{
	public CitizensMain(JavaPlugin plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	public CitizensMain()
	{}
	
	public void NPCquest(NPCRightClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

	    YamlManager YM;
	    
	    Player player = event.getClicker();
	    net.citizensnpcs.api.npc.NPC target = event.getNPC();
	    YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
		if(PlayerQuestList.contains("Started"))
		{
			Object[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
				
			for(short count = 0; count < a.length; count++)
			{
				String QuestName = a[count].toString();
				short QuestFlow = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
				GBD_RPG.Quest.Quest_GUI QGUI = new GBD_RPG.Quest.Quest_GUI();
				boolean isThatTarget = false;
				if(QuestList.contains(QuestName+".FlowChart."+QuestFlow+".Type"))
					switch(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".Type"))
					{
					case "Script":
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".NPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
							isThatTarget = true;
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".NPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
							isThatTarget = true;
						if(isThatTarget == true)
						{
							event.setCancelled(true);
							QGUI.QuestTypeRouter(player, QuestName);
							return;
						}
						break;
					case "Choice":
						QGUI.Quest_UserChoice(player, QuestName, (short) PlayerQuestList.getInt("Started."+QuestName+".Flow"));
						return;
					case "PScript":
						event.setCancelled(true);
						QGUI.QuestTypeRouter(player, QuestName);
						return;
					case "Talk":
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".TargetNPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
							isThatTarget = true;
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".TargetNPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
							isThatTarget = true;
						if(isThatTarget == true)
						{
							event.setCancelled(true);
							PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
							PlayerQuestList.saveConfig();
							QGUI.QuestTypeRouter(player, QuestName);
							return;
						}
						break;
					case "Give":
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".TargetNPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
							isThatTarget = true;
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".TargetNPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
							isThatTarget = true;
						if(isThatTarget == true)
						{
							if(QuestList.contains(QuestName+".FlowChart."+QuestFlow+".Item") == false)
							{
								if(PlayerQuestList.getInt("Started."+QuestName+".Flow") == 0)
								{
									QGUI.QuestTypeRouter(player, QuestName);
								}
								else
								{
									PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
									PlayerQuestList.saveConfig();
									QGUI.QuestTypeRouter(player, QuestName);
								}
								return;
							}
							Object[] p = QuestList.getConfigurationSection(QuestName+".FlowChart."+QuestFlow+".Item").getKeys(false).toArray();
							ItemStack item[] = new ItemStack[p.length];
							
							for(byte counter = 0; counter < p.length; counter++)
								item[counter] = QuestList.getItemStack(QuestName+".FlowChart."+QuestFlow+".Item."+counter);
							
							int getfinished = 0;
							for(byte eight = 0; eight < 8; eight++)
							{
								for(byte counter = 0; counter <player.getInventory().getSize(); counter++)
								{
									if(player.getInventory().getItem(counter) != null)
										if(player.getInventory().getItem(counter).isSimilar(item[getfinished]))
										{
											if(player.getInventory().getItem(counter).getAmount() >= item[getfinished].getAmount())
											{
												getfinished = getfinished+1;
													break;
											}
										}
								}
								if(getfinished == item.length)
									break;
							}
							if(getfinished == item.length)
							{
								getfinished = 0;
								ItemStack Pitem = null;
	
								for(byte eight = 0; eight < 8; eight++)
								{
									for(byte counter = 0; counter <player.getInventory().getSize(); counter++)
									{
										if(player.getInventory().getItem(counter) != null)
										if(player.getInventory().getItem(counter).isSimilar(item[getfinished]))
										{
											if(player.getInventory().getItem(counter).getAmount() >= item[getfinished].getAmount())
											{
												Pitem = player.getInventory().getItem(counter);
												Pitem.setAmount(Pitem.getAmount()-item[getfinished].getAmount());
												if(Pitem.getAmount() - item[getfinished].getAmount() == 0)
													player.getInventory().remove(counter);
												else
													player.getInventory().setItem(counter, Pitem);
												
												getfinished = getfinished+1;
												if(getfinished == item.length)
												{
													event.setCancelled(true);
													PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
													PlayerQuestList.saveConfig();
													QGUI.QuestTypeRouter(player, QuestName);
													return;
												}
											}
										}
									}
								}
							}
						}
						break;
						
					case "Present":
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".TargetNPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
							isThatTarget = true;
						if(ChatColor.stripColor(QuestList.getString(QuestName+".FlowChart."+QuestFlow+".TargetNPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
							isThatTarget = true;
						if(isThatTarget == true)
						{
							event.setCancelled(true);
							if(QuestList.contains(QuestName+".FlowChart."+QuestFlow+".Item") == true)
							{
								Object[] p = QuestList.getConfigurationSection(QuestName+".FlowChart."+QuestFlow+".Item").getKeys(false).toArray();
								byte emptySlot = 0;
								ItemStack item[] = new ItemStack[p.length];
								
								for(byte counter = 0; counter < p.length; counter++)
									item[counter] = QuestList.getItemStack(QuestName+".FlowChart."+QuestFlow+".Item."+counter);
								
								for(byte counter = 0; counter < player.getInventory().getSize(); counter++)
								{
									if(player.getInventory().getItem(counter) == null)
										emptySlot++;
								}
								
								if(emptySlot >= item.length)
								{
									for(byte counter = 0;counter < p.length; counter++)
										player.getInventory().addItem(item[counter]);
	
									PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
									PlayerQuestList.saveConfig();

									GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".Money"), 0, false);
	
							    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
							    	{
							    		YM=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
							    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
							    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", 0);
							    		YM.saveConfig();
							    	}
							    	else
							    	{
							    		YM=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
							    		int ownlove = YM.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love");
							    		int owncareer = YM.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career");
							    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", ownlove +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
							    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", owncareer +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Career"));
							    		YM.saveConfig();
							    	}
						    		if(QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".EXP") != 0)
						    			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, 0, QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".EXP"), null, false, false);
									
									event.setCancelled(true);
									QGUI.QuestTypeRouter(player, QuestName);
									return;
								}
								else
								{
									s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
									player.sendMessage(ChatColor.YELLOW + "[퀘스트] : 현재 플레이어의 인벤토리 공간이 충분하지 않아 보상을 받을 수 없습니다!");
									return;
								}
							}
						}
						break;
					}//switch 끝
			}
		}
	}
	
	@EventHandler
	public void NPCRightClick(NPCRightClickEvent event)
	{
		Player player = event.getClicker();
		UserData_Object u = new UserData_Object();
		u.setNPCuuid(player, event.getNPC().getUniqueId().toString());
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager DNPC = YC.getNewConfig("NPC/DistrictNPC.yml");
		if(player.isOp()==true)
		{
			if(new UserData_Object().getInt(player, (byte)4)==114)
			{
				DNPC.removeKey(event.getNPC().getUniqueId().toString());
				DNPC.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[NPC] : 해당 NPC의 GUI창이 활성화 되었습니다!");
				new GBD_RPG.Effect.Effect_Sound().SP(player, Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
				new UserData_Object().setInt(player, (byte)4, -1);
			}
		}
		
		if(DNPC.contains(event.getNPC().getUniqueId().toString())==false)
		{
			GBD_RPG.NPC.NPC_GUI NPGUI = new GBD_RPG.NPC.NPC_GUI();
			NPGUI.MainGUI(event.getClicker(), event.getNPC().getName(), event.getClicker().isOp());
		}

		NPCquest(event);
		return;
	}
	@EventHandler
	public void NPCCreating(NPCCreateEvent event)
	{
		GBD_RPG.NPC.NPC_Config NPCC = new GBD_RPG.NPC.NPC_Config();
		NPCC.NPCNPCconfig(event.getNPC().getUniqueId().toString());
	}
	@EventHandler
	public void NPCRemove(NPCRemoveEvent event)
	{
		File file = new File("plugins/GoldBigDragonRPG/NPC/NPCData/" + event.getNPC().getUniqueId().toString()+".yml");
		file.delete();
		return;
	}

}
