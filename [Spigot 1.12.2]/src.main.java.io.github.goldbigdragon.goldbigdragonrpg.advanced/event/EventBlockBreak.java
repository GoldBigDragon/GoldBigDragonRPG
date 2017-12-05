package event;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import battle.BattleCalculator;
import effect.SoundEffect;
import main.MainServerOption;
import util.YamlLoader;



public class EventBlockBreak implements Listener
{
	@EventHandler
	public void blockBreaking(BlockBreakEvent event)
	{
		if(event.isCancelled())
			return;
		if(event.getBlock().getLocation().getWorld().getName().equals("Dungeon"))
		{
			if(event.getBlock().getTypeId() != 50)
				event.setCancelled(true);
			return;
		}
		Player player = event.getPlayer();
		BattleCalculator.decreaseDurabilityWeapon(player);
		area.AreaMain area = new area.AreaMain();
		String[] areaName = area.getAreaName(event.getBlock());
		if(areaName != null)
		{
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");

			if(!area.getAreaOption(areaName[0], (char) 1) && !event.getPlayer().isOp())
			{
				event.setCancelled(true);
				SoundEffect.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				if(areaName[1].equals("%player%"))
					areaName[1] = player.getName();
				event.getPlayer().sendMessage("§c[SYSTEM] : §e"+ areaName[1] + "§c 지역 에서는 블록 채집이 불가능합니다!");
				return;
			}
			if(areaYaml.getInt(areaName[0]+".RegenBlock")!=0)
			{
				Long utc = (areaYaml.getInt(areaName[0]+".RegenBlock")*1000)+servertick.ServerTickMain.nowUTC+new util.UtilNumber().RandomNum(1, 1000);
				servertick.ServerTickObject serverTickObject = new servertick.ServerTickObject(utc, "A_RB");
				serverTickObject.setMaxCount(-1);
				Block block =event.getBlock();
				serverTickObject.setString((byte)1, block.getWorld().getName());//목적지 월드 이름 저장
				serverTickObject.setInt((byte)0, block.getX());//블록X 위치저장
				serverTickObject.setInt((byte)1, block.getY());//블록Y 위치저장
				serverTickObject.setInt((byte)2, block.getZ());//블록Z 위치저장
				serverTickObject.setInt((byte)3, block.getTypeId());//블록 ID저장
				serverTickObject.setInt((byte)4, block.getData());//블록 DATA 저장
				
				servertick.ServerTickMain.Schedule.put(utc, serverTickObject);
			}
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				String blockData = event.getBlock().getTypeId()+":"+event.getBlock().getData();
				if(areaYaml.contains(areaName[0]+".Mining."+blockData))
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.EventItemDrop itemDrop = new event.EventItemDrop();
					Location loc = event.getBlock().getLocation();
					loc.setY(loc.getY()+0.4);
					loc.setX(loc.getX()+0.5);
					loc.setZ(loc.getZ()+0.5);
					if(areaYaml.contains(areaName[0]+".Mining."+blockData+".100"))
					{
						if(!areaYaml.getString(areaName[0]+".Mining."+blockData+".100").equals("0:0"))
							itemDrop.CustomItemDrop(loc, areaYaml.getItemStack(areaName[0]+".Mining."+blockData+".100"));
						int random = new util.UtilNumber().RandomNum(1, 1000);
						if(random<=1)
						{
							if(!areaYaml.getString(areaName[0]+".Mining."+blockData+".0").equals("0:0"))
								itemDrop.CustomItemDrop(loc, areaYaml.getItemStack(areaName[0]+".Mining."+blockData+".0"));
						}
						else if(random<=10)
						{
							if(!areaYaml.getString(areaName[0]+".Mining."+blockData+".1").equals("0:0"))
								itemDrop.CustomItemDrop(loc, areaYaml.getItemStack(areaName[0]+".Mining."+blockData+".1"));
						}
						else if(random<=100)
						{
							if(!areaYaml.getString(areaName[0]+".Mining."+blockData+".10").equals("0:0"))
								itemDrop.CustomItemDrop(loc, areaYaml.getItemStack(areaName[0]+".Mining."+blockData+".10"));
						}
						else if(random<=500)
						{
							if(!areaYaml.getString(areaName[0]+".Mining."+blockData+".50").equals("0:0"))
								itemDrop.CustomItemDrop(loc, areaYaml.getItemStack(areaName[0]+".Mining."+blockData+".50"));
						}
						else if(random<=900)
						{
							if(!areaYaml.getString(areaName[0]+".Mining."+blockData+".90").equals("0:0"))
								itemDrop.CustomItemDrop(loc, areaYaml.getItemStack(areaName[0]+".Mining."+blockData+".90"));
						}
					}
				}
			}
		}
		quest(event, player);
		if(event.getBlock().getLocation().getWorld().getName().equals("Dungeon")
				&&!player.isOp())
		{
			event.setCancelled(true);
			return;
		}
		
		short id = (short) event.getBlock().getTypeId();
		if((id >= 14&&id <= 17)||id==21||id==56||id==129||id==73||id==153)
		{
		  	YamlLoader exceptionBlockYaml = new YamlLoader();
			exceptionBlockYaml.getConfig("EXPexceptionBlock.yml");
			Location loc = event.getBlock().getLocation();
			String location = ((int)loc.getX()+"_"+(int)loc.getY()+"_"+(int)loc.getZ());
			if(exceptionBlockYaml.contains(loc.getWorld().getName()+"."+id+"."+location))
			{
				exceptionBlockYaml.removeKey(loc.getWorld().getName()+"."+id+"."+location);
				exceptionBlockYaml.saveConfig();
			}
			else
			{
				if(player.getGameMode()!=GameMode.CREATIVE)
				{
					expAdd(event);
					if((id>=14&&id<=16)||id==56||id==129||id==73||id==21||id==17||id==162||id==153||id==89||id==169)
						luckyBonus(player, event.getBlock());
				}
			}
		}
		return;
	}

	private void quest(BlockBreakEvent event, Player player)
	{
	  	YamlLoader questYaml = new YamlLoader();
		questYaml.getConfig("Quest/QuestList.yml");
	  	if(!questYaml.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml"))
	  		new quest.QuestConfig().CreateNewPlayerConfig(player);
	  	YamlLoader playerQuestYaml = new YamlLoader();
		playerQuestYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(!MainServerOption.partyJoiner.containsKey(player))
		{
			if(playerQuestYaml.contains("Started") && playerQuestYaml.getConfigurationSection("Started").getKeys(false).toArray().length >= 1)
			{
				String[] startedQuestList = playerQuestYaml.getConfigurationSection("Started").getKeys(false).toArray(new String[0]);
				for(int count = 0; count < startedQuestList.length; count++)
				{
					short flow = (short) playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow");
					if(questYaml.contains(startedQuestList[count]+".FlowChart."+flow+".Block")&&playerQuestYaml.getString("Started."+startedQuestList[count]+".Type").equalsIgnoreCase("Harvest"))
					{
						int blockSize = questYaml.getConfigurationSection(startedQuestList[count]+".FlowChart."+flow+".Block").getKeys(false).size();
						short finish = 0;
						for(int counter = 0; counter < blockSize; counter++)
						{
							short blockID = (short) questYaml.getInt(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".BlockID");
							byte blockData = (byte) questYaml.getInt(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".BlockData");
							int max = questYaml.getInt(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".Amount");
							boolean dataEquals = questYaml.getBoolean(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".DataEquals");
							if(blockID == event.getBlock().getTypeId() && max > playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
							{
								if(!dataEquals)
								{
									playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
									playerQuestYaml.saveConfig();
								}
								else
								{
									if(blockData == event.getBlock().getData())
									{
										playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
										playerQuestYaml.saveConfig();
									}
								}
							}
							if(max == playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
								finish++;
							if(finish == blockSize)
							{
								playerQuestYaml.set("Started."+startedQuestList[count]+".Type",questYaml.getString(startedQuestList[count]+".FlowChart."+(playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1)+".Type"));
								playerQuestYaml.set("Started."+startedQuestList[count]+".Flow",playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1);
								playerQuestYaml.removeKey("Started."+startedQuestList[count]+".Harvest");
								playerQuestYaml.saveConfig();
								quest.QuestGui questGui = new quest.QuestGui();
								questGui.QuestRouter(player, startedQuestList[count]);
								//퀘스트 완료 메시지//
								break;
							}
						}
					}
				}
			}
		}
		else
		{
			Player[] partyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			int partyEXPShareDistance = configYaml.getInt("Party.EXPShareDistance");
			for(int counta = 0; counta < partyMember.length; counta++)
			{
				player = partyMember[counta];
				if(event.getBlock().getLocation().getWorld() == player.getLocation().getWorld())
				{
					if(event.getBlock().getLocation().distance(player.getLocation()) <= partyEXPShareDistance)
					{
						playerQuestYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						String[] startedQuestList = playerQuestYaml.getConfigurationSection("Started.").getKeys(false).toArray(new String[0]);
						for(int count = 0; count < startedQuestList.length; count++)
						{
							short flow = (short) playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow");
							if(playerQuestYaml.getString("Started."+startedQuestList[count]+".Type").equalsIgnoreCase("Harvest"))
							{
								int blockSize = questYaml.getConfigurationSection(startedQuestList[count]+".FlowChart."+flow+".Block").getKeys(false).size();
								short finish = 0;
								for(int counter = 0; counter < blockSize; counter++)
								{
									short blockID = (short) questYaml.getInt(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".BlockID");
									byte blockData = (byte) questYaml.getInt(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".BlockData");
									int max = questYaml.getInt(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".Amount");
									boolean dataEquals = questYaml.getBoolean(startedQuestList[count]+".FlowChart."+flow+".Block."+counter+".DataEquals");
									if(blockID == event.getBlock().getTypeId() && max > playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
									{
										if(!dataEquals)
										{
											playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
											playerQuestYaml.saveConfig();
										}
										else
										{
											if(blockData == event.getBlock().getData())
											{
												playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
												playerQuestYaml.saveConfig();
											}
										}
									}
									if(max == playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
									{
										finish++;
									}
									if(finish == blockSize)
									{
										playerQuestYaml.set("Started."+startedQuestList[count]+".Type",questYaml.getString(startedQuestList[count]+".FlowChart."+(playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1)+".Type"));
										playerQuestYaml.set("Started."+startedQuestList[count]+".Flow",playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1);
										playerQuestYaml.removeKey("Started."+startedQuestList[count]+".Harvest");
										playerQuestYaml.saveConfig();
										quest.QuestGui questGui = new quest.QuestGui();
										questGui.QuestRouter(player, startedQuestList[count]);
										//퀘스트 완료 메시지//
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return;
	}

	private void luckyBonus(Player player, Block block)
	{
		int lucky = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
		if(lucky >= 150) lucky =150;
		if(lucky <= 0) lucky = 1;
		if(lucky >= new util.UtilNumber().RandomNum(0, 1000))
		{
			effect.SendPacket t = new effect.SendPacket();
			byte amount = 0;
			byte luckysize = (byte) new util.UtilNumber().RandomNum(0, 100);
			if(luckysize <= 80)
			{
				t.sendActionBar(player, "§e§l럭키 보너스!", false);
				amount = 1;
				SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);
			}
			else if(luckysize <= 95)
			{
				t.sendActionBar(player, "§e§l빅 럭키 보너스!", false);amount = 5;
				SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);
			}
			else
			{
				t.sendActionBar(player, "§e§l휴즈 럭키 보너스!", false);amount = 20;
				SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);
			}

			int id = block.getTypeId();
			if(id==16)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)263, (byte)0,amount);
			else if(id == 15)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)15, (byte)0,amount);
			else if(id == 14)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)14, (byte)0,amount);
			else if(id == 56)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)264, (byte)0,amount);
			else if(id == 129)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)388, (byte)0,amount);
			else if(id == 73)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)331, (byte)0,amount);
			else if(id == 21)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)351, (byte)4,amount);
			else if(id == 17 || id == 162)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)block.getTypeId(), (byte)block.getData(),amount);
			else if(id == 153)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)406, (byte)0,amount);
			else if(id == 89)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)348, (byte)0, amount);
			else if(id == 169)
				new EventItemDrop().PureItemNaturalDrop(block.getLocation(), (short)410, (byte)0, amount);
		}
		return;
	}

	private void expAdd(BlockBreakEvent event)
	{
		Player player = event.getPlayer();

	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		int id = event.getBlock().getTypeId();
		if(id==16)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Coal.Money"), configYaml.getLong("Getting.Coal.EXP"), player.getLocation(), true, false);
		else if(id == 15)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Iron.Money"), configYaml.getLong("Getting.Iron.EXP"), player.getLocation(), true, false);
		else if(id == 14)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Gold.Money"), configYaml.getLong("Getting.Gold.EXP"), player.getLocation(), true, false);
		else if(id == 56)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Diamond.Money"), configYaml.getLong("Getting.Diamond.EXP"), player.getLocation(), true, false);
		else if(id == 129)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Emerald.Money"), configYaml.getLong("Getting.Emerald.EXP"), player.getLocation(), true, false);
		else if(id == 73)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.RedStone.Money"), configYaml.getLong("Getting.RedStone.EXP"), player.getLocation(), true, false);
		else if(id == 21)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Lapis.Money"), configYaml.getLong("Getting.Lapis.EXP"), player.getLocation(), true, false);
		else if(id == 17 || id == 162)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.Wood.Money"), configYaml.getLong("Getting.Wood.EXP"), player.getLocation(), true, false);
		else if(id == 153)
			new util.UtilPlayer().addMoneyAndEXP(player, configYaml.getInt("Getting.NetherQuartz.Money"), configYaml.getLong("Getting.NetherQuartz.EXP"), player.getLocation(), true, false);

		return;
	}
}