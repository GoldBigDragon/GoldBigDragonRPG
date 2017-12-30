package dungeon;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import effect.SoundEffect;
import main.MainServerOption;
import util.YamlLoader;



public class DungeonMain
{

	public void ironDoorOpening(Location loc)
	{
		servertick.ServerTickObject serverTickObject = new servertick.ServerTickObject(0, "Sound");
		serverTickObject.setType("Sound");
		serverTickObject.setString((byte)1, loc.getWorld().getName());
		serverTickObject.setInt((byte)0, (int)loc.getX());
		serverTickObject.setInt((byte)1, (int)loc.getY());
		serverTickObject.setInt((byte)2, (int)loc.getZ());
		serverTickObject.setString((byte)0, "0000001");//소리 구성
		serverTickObject.setInt((byte)3, 20);//소리 크기
		serverTickObject.setInt((byte)4, 5);//소리 속도
		
		serverTickObject.setInt((byte)5, 1);//틱 설정
		serverTickObject.setMaxCount(serverTickObject.getString((byte)0).length());
		serverTickObject.setTick(servertick.ServerTickMain.nowUTC);
		servertick.ServerTickMain.Schedule.put(servertick.ServerTickMain.nowUTC, serverTickObject);
	}

	public void dungeonClear(Player player, Location bossLoc)
	{
		YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/Dungeon/"+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() +"/Option.yml");
		
		int moneyReward = dungeonYaml.getInt("Reward.Money");
		int expReward = dungeonYaml.getInt("Reward.EXP");
		
		ItemStack item = new ItemStack(Material.IRON_HOE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§a§0§a§f§l[보상 상자 열쇠]");
		im.setLore(Arrays.asList("","§f보상 상자를 열 수 있는","§f낡은 열쇠이다."));
		im.addEnchant(Enchantment.DURABILITY, 6000, true);
		item.setItemMeta(im);

		if(MainServerOption.partyJoiner.containsKey(player))
		{
			Player[] partyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
			for(int count = 0; count < partyMember.length; count++)
			{
				Long target = MainServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).getDungeon_UTC();
				if(target.equals(MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC()))
				{
					{
						SoundEffect.playSound(partyMember[count], Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
						new util.UtilPlayer().giveItemDrop(partyMember[count], item, partyMember[count].getLocation());
						new util.UtilPlayer().DungeonClear(partyMember[count], moneyReward, expReward);
					}
				}
			}
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
			new util.UtilPlayer().giveItemDrop(player, item, player.getLocation());
			new util.UtilPlayer().DungeonClear(player, moneyReward, expReward);
		}
		
		bossLoc.add(3, -1, -30);
		ironDoorOpening(bossLoc);
		Block block = null;
    	for(int count = 0; count < 7; count++)
    	{
    		for(int count2 = 0; count2 < 5; count2++)
    		{
    			block = bossLoc.add(-1, 0, 0).getBlock();
				block.setType(Material.AIR,true);
    		}
			block = bossLoc.add(5, 1, 0).getBlock();
    	}
		bossLoc.add(0, -7, -1);
    	for(int count = 0; count < 7; count++)
    	{
    		for(int count2 = 0; count2 < 5; count2++)
    		{
    			block = bossLoc.add(-1, 0, 0).getBlock();
				block.setType(Material.AIR,true);
    		}
			block = bossLoc.add(5, 1, 0).getBlock();
    	}
	}
	
	public void bossRoomOpen(Player player, Location bossLoc, String dungeonName)
	{
		YamlLoader dungeonOptionYaml = new YamlLoader();
		dungeonOptionYaml.getConfig("Dungeon/Dungeon/"+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() +"/Option.yml");
		int soundTrack = dungeonOptionYaml.getInt("BGM.BOSS");
		
		otherplugins.NoteBlockApiMain noteBlockApi = new otherplugins.NoteBlockApiMain();
		if(MainServerOption.partyJoiner.containsKey(player))
		{
			Player[] partyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
			for(int count = 0; count < partyMember.length; count++)
			{
				if(MainServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).isBgmOn())
				{
					Long target = MainServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).getDungeon_UTC();
					if(target.equals(MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC()))
					{
						noteBlockApi.Stop(partyMember[count]);
						noteBlockApi.Play(partyMember[count], soundTrack);	
					}
				}
			}
		}
		else
		{
			noteBlockApi.Stop(player);
			noteBlockApi.Play(player, soundTrack);	
		}

		YamlLoader dungeonMonsterYaml = new YamlLoader();
		dungeonMonsterYaml.getConfig("Dungeon/Dungeon/"+dungeonName +"/Monster.yml");
    	String[] mobList = null;
		if(dungeonMonsterYaml.getConfigurationSection("Boss").getKeys(false).size()!=0)
			mobList = dungeonMonsterYaml.getConfigurationSection("Boss").getKeys(false).toArray(new String[0]);

		int XYZloc[] = new int[3];
		XYZloc[0] = (int) bossLoc.getX();
		XYZloc[1] = (int) bossLoc.getY();
		XYZloc[2] = (int) bossLoc.getZ();
		byte groupNumber = (byte) new util.UtilNumber().RandomNum(0, 15);
		char group = '0';
		switch(groupNumber)
		{
			case 0 : group = '0'; break;
			case 1 : group = '1'; break;
			case 2 : group = '2'; break;
			case 3 : group = '3'; break;
			case 4 : group = '4'; break;
			case 5 : group = '5'; break;
			case 6 : group = '6'; break;
			case 7 : group = '7'; break;
			case 8 : group = '8'; break;
			case 9 : group = '9'; break;
			case 10 : group = 'a'; break;
			case 11 : group = 'b'; break;
			case 12 : group = 'c'; break;
			case 13 : group = 'd'; break;
			case 14 : group = 'e'; break;
			case 15 : group = 'f'; break;
		}
		
    	if(mobList ==null || mobList.length == 0)
    		dungeonClear(player, bossLoc);
    	else
    	{
    		monster.MonsterSpawn monsterSpawn = new monster.MonsterSpawn();
    		
    		for(int count = 0; count < mobList.length; count++)
    		{
    			bossLoc.add(0, 0.2, 0);
    			SoundEffect.playSoundLocation(bossLoc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
    			monsterSpawn.SpawnMob(bossLoc, dungeonMonsterYaml.getString("Boss."+mobList[count]),(byte) 4, XYZloc, group, true);
    		}
		}
	}
	
	public void eraseAllDungeonKey(Player player, boolean isDrop)
	{
		ItemStack item = new ItemStack(Material.IRON_HOE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§a§0§a§f§l[던전 룸 열쇠]");
		im.setLore(Arrays.asList("","§f던전 룸을 열 수 있는","§f낡은 열쇠이다."));
		im.addEnchant(Enchantment.DURABILITY, 6000, true);
		item.setItemMeta(im);
		if(isDrop)
			new util.UtilPlayer().dropItem(player, item, true);
		else
			new util.UtilPlayer().deleteItem(player, item, -1);
		
		item = new ItemStack(Material.IRON_HOE);
		im = item.getItemMeta();
		im.setDisplayName("§a§0§a§f§l[보상 상자 열쇠]");
		im.setLore(Arrays.asList("","§f보상 상자를 열 수 있는","§f낡은 열쇠이다."));
		im.addEnchant(Enchantment.DURABILITY, 6000, true);
		item.setItemMeta(im);
		new util.UtilPlayer().dropItem(player, item, true);
		
		if(isDrop)
			new util.UtilPlayer().dropItem(player, item, true);
		else
			new util.UtilPlayer().deleteItem(player, item, -1);
	}

	public void monsterSpawn(Location loc)
	{
		Block signBlock = new Location(loc.getWorld(),loc.getX(),loc.getY()+12,loc.getZ()).getBlock();
        Sign sign = (Sign) signBlock.getState();
        String dungeonName = sign.getLine(2);

	  	YamlLoader dungeonMonsterYaml = new YamlLoader();
		dungeonMonsterYaml.getConfig("Dungeon/Dungeon/"+dungeonName+"/Monster.yml");
    	String[] mobList = null;
    	String listName = "Middle";
    	byte randomLevel = (byte) new util.UtilNumber().RandomNum(0, 3);
    	if(randomLevel<=2)
    	{
    		if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
    			if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
	        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
	        			mobList = null;
		        	else
		        	{
		        		listName = "Normal";
		        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
		        	}
    			else
    			{
	        		listName = "High";
    				mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
    			}
    		else
    		{
        		listName = "Middle";
    			mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
    		}
    	}
    	else
    	{
    		if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
    			if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
	        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
	        			mobList = null;
		        	else
		        	{
		        		listName = "Normal";
		        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
		        	}
    			else
    			{
	        		listName = "Middle";
    				mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
    			}
    		else
    		{
        		listName = "High";
    			mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
    		}
    	}
    	if(mobList!=null)
    	{
        	String mob = mobList[new util.UtilNumber().RandomNum(0, mobList.length-1)];
    		if(mob != null)
    		{
        		randomLevel = (byte) new util.UtilNumber().RandomNum(0, 5);
        		switch(randomLevel)
        		{
        		case 0://2회 스폰
        			break;
        		default://1회 스폰
        			break;
        		}
        		
    			int[] xyzLoc = new int[3];
    			xyzLoc[0] = (int) loc.getX();
    			xyzLoc[1] = (int) loc.getY();
    			xyzLoc[2] = (int) loc.getZ();
    			byte groupNumber = (byte) new util.UtilNumber().RandomNum(0, 15);
    			char group = '0';
    			switch(groupNumber)
    			{
    				case 0 : group = '0'; break;
    				case 1 : group = '1'; break;
    				case 2 : group = '2'; break;
    				case 3 : group = '3'; break;
    				case 4 : group = '4'; break;
    				case 5 : group = '5'; break;
    				case 6 : group = '6'; break;
    				case 7 : group = '7'; break;
    				case 8 : group = '8'; break;
    				case 9 : group = '9'; break;
    				case 10 : group = 'a'; break;
    				case 11 : group = 'b'; break;
    				case 12 : group = 'c'; break;
    				case 13 : group = 'd'; break;
    				case 14 : group = 'e'; break;
    				case 15 : group = 'f'; break;
    			}
    			monster.MonsterSpawn monsterSpawn = new monster.MonsterSpawn();
    			
    			loc.add(0,1,0);
        		for(int count = 0; count < 7; count++)
        		{
        			SoundEffect.playSoundLocation(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
        			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mob),(byte) 1, xyzLoc, group, true);
        			loc.add(0, 0.2, 0);
        		}
    			SoundEffect.playSoundLocation(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
    			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mob),(byte) 3, xyzLoc, group, true);	
    		}
    		else
    		{
    			Location blockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ());
				ItemStack item = new ItemStack(Material.IRON_HOE);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName("§a§0§a§f§l[던전 룸 열쇠]");
				im.setLore(Arrays.asList("","§f던전 룸을 열 수 있는","§f낡은 열쇠이다."));
				im.addEnchant(Enchantment.DURABILITY, 6000, true);
				item.setItemMeta(im);
				new event.EventItemDrop().CustomItemDrop(blockLoc, item);
				new dungeon.DungeonMain().dungeonTrapDoorWorker(loc, false);
    		}
    	}
        return;
	}
	
	public void dungeonInteract(PlayerInteractEvent event)
	{
		//양동이 사용 못하게 하기
		if(event.getPlayer().getInventory().getItemInMainHand().getTypeId()>=325&&
			event.getPlayer().getInventory().getItemInMainHand().getTypeId()<=327)
		{
			event.setCancelled(true);
			SoundEffect.playSound(event.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
			new effect.SendPacket().sendActionBar(event.getPlayer(), "§c§l[던전에서는 양동이 사용이 불가능합니다!]", false);
			return;
		}
		else if(event.getPlayer().getInventory().getItemInMainHand().getTypeId()==432)
		{
			event.setCancelled(true);
			SoundEffect.playSound(event.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
			new effect.SendPacket().sendActionBar(event.getPlayer(), "§c§l[던전에서는 후렴과 사용이 불가능합니다!]", false);
			return;
		}
		Block block = event.getClickedBlock();
		if(block==null)
			return;
		if(block.getType()==Material.AIR)
			return;
		
		
		if(block.getType().getId()==146)//덫 상자
		{
			if(trapChestOpen(block))
			{
				event.setCancelled(true);
				byte howMuch = (byte) new util.UtilNumber().RandomNum(0, 8);
				for(int count = 0; count < howMuch; count++)
					new util.UtilPlayer().addMoneyAndEXP(event.getPlayer() , new util.UtilNumber().RandomNum(0, 500), 0, block.getLocation(), true, false);
			}
		}
		
		else if(block.getType().getId()==95) //구슬방 구슬
			TrapGlassTouch(block, event.getPlayer());
		else if(block.getType().getId()==138)//던전 탈출용 신호기
		{
			event.setCancelled(true);
			new dungeon.DungeonGui().DungeonEXIT(event.getPlayer());
		}
		
		else if(block.getType().getId()==23) //던전 문 열쇠구멍
		{
			event.setCancelled(true);
			Block signBlock = new Location(block.getWorld(),block.getX(),block.getY()+10,block.getZ()).getBlock();
			if(signBlock.getType()!=Material.SIGN_POST)
				return;
			if(event.getClickedBlock().getLocation().add(0, 10, 0).getBlock() !=null)
			{
				if(event.getClickedBlock().getLocation().add(0,10,0).getBlock().getType() == Material.SIGN_POST)
				{
			        Sign sign = (Sign) event.getClickedBlock().getLocation().add(0, 10, 0).getBlock().getState();
			        String gridImage = sign.getLine(1);
					if(gridImage.equals("▲")||gridImage.equals("▼")||gridImage.equals("▶")||gridImage.equals("◀")||gridImage.equals("♠"))
					{
						ItemStack item = new ItemStack(Material.IRON_HOE);
						ItemMeta im = item.getItemMeta();
						im.setDisplayName("§a§0§a§f§l[던전 룸 열쇠]");
						im.setLore(Arrays.asList("","§f던전 룸을 열 수 있는","§f낡은 열쇠이다."));
						im.addEnchant(Enchantment.DURABILITY, 6000, true);
						item.setItemMeta(im);
						if(new util.UtilPlayer().deleteItem(event.getPlayer(), item, 1))
						{
				        	Location loc = event.getClickedBlock().getLocation();
							String title = "§9";
							String subTitle  = "§f던전 룸 열쇠를 사용하여 문을 열었다.";
				        	new effect.SendPacket().sendTitle(event.getPlayer(), title, subTitle, 1, 2, 1);
							ironDoorOpening(loc);
					        switch(gridImage)
					        {
					        case "▲":
					        case "▼":
					        	loc.add(-2, -2, 0);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(1, 0, 0).getBlock();
						    		}
									block = loc.add(-5, 1, 0).getBlock();
						    	}
					        	break;
					        case "♠":
					        	loc.add(-2, -2, 0);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(1, 0, 0).getBlock();
						    		}
									block = loc.add(-5, 1, 0).getBlock();
						    	}
						    	loc = event.getClickedBlock().getLocation().add(-2, -2, -1);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(1, 0, 0).getBlock();
						    		}
									block = loc.add(-5, 1, 0).getBlock();
						    	}
						    	loc = event.getClickedBlock().getLocation().add(0, -1 ,-33);
								bossRoomOpen(event.getPlayer(), loc, sign.getLine(2));
					        	break;
					        case "▶":
					        case "◀":
					        	loc.add(0, -2, -2);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(0, 0, 1).getBlock();
						    		}
									block = loc.add(0, 1, -5).getBlock();
						    	}
					        	break;
					        }
					        return;
						}
						else
						{
							String title = "§9";
							String subTitle  = "§f문을 열기 위해서는 열쇠가 필요할 것 같다...";
				        	new effect.SendPacket().sendTitle(event.getPlayer(), title, subTitle, 1, 2, 1);
						}
					}
					else
					{
						String title = "§9";
						String subTitle  = "§f열쇠로 열 수 있는 문이 아닌 것 같다...";
			        	new effect.SendPacket().sendTitle(event.getPlayer(), title, subTitle, 1, 2, 1);
			        	return;
					}
				}
			}
		}
		else if(block.getType().getId() == 54) //미믹 방 일반 상자
		{
			SoundEffect.playSoundLocation(block.getLocation().add(0,2,0), Sound.BLOCK_CHEST_OPEN, 0.5F, 1.8F);
			event.setCancelled(true);
			block.setType(Material.AIR);
			ItemStack item = new ItemStack(Material.IRON_HOE);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName("§a§0§a§f§l[던전 룸 열쇠]");
			im.setLore(Arrays.asList("","§f던전 룸을 열 수 있는","§f낡은 열쇠이다."));
			im.addEnchant(Enchantment.DURABILITY, 6000, true);
			item.setItemMeta(im);
			SoundEffect.playSoundLocation(block.getLocation().add(0,2,0), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5F, 1.8F);
			new event.EventItemDrop().CustomItemDrop(block.getLocation().add(0.5,1,0.5), item);
			byte howMuch = (byte) new util.UtilNumber().RandomNum(0, 4);
			for(int count = 0; count < howMuch; count++)
				new util.UtilPlayer().addMoneyAndEXP(event.getPlayer() , new util.UtilNumber().RandomNum(0, 500), 0, block.getLocation(), true, false);
		}
		else if(block.getType().getId() == 130) //보상 상자
		{
			Block SB = new Location(block.getWorld(),block.getX(),block.getY()+12,block.getZ()).getBlock();
			if(SB.getType()!=Material.SIGN_POST)
				return;
			event.setCancelled(true);
	        Player player = event.getPlayer();
			ItemStack item = new ItemStack(Material.IRON_HOE);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName("§a§0§a§f§l[보상 상자 열쇠]");
			im.setLore(Arrays.asList("","§f보상 상자를 열 수 있는","§f낡은 열쇠이다."));
			im.addEnchant(Enchantment.DURABILITY, 6000, true);
			item.setItemMeta(im);
			if(new util.UtilPlayer().deleteItem(player, item, 1))
			{
				event.getClickedBlock().setType(Material.AIR, true);
				SoundEffect.playSoundLocation(event.getClickedBlock().getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
		        Sign signBlock = (Sign) SB.getState();
		        String dungeonName = signBlock.getLine(2);
			  	YamlLoader dungeonRewardYaml = new YamlLoader();
				dungeonRewardYaml.getConfig("Dungeon/Dungeon/"+dungeonName+"/Reward.yml");
				
				boolean treasureGet = false;
				for(int count = 0; count < 8; count++)
				{
					item = dungeonRewardYaml.getItemStack("100."+count);
					if(item!=null)
					{
						treasureGet = true;
						new util.UtilPlayer().giveItemDrop(player, item, event.getClickedBlock().getLocation());
						break;
					}
				}
				int luck = new util.UtilNumber().RandomNum(1, 10);
				if(luck != 10)
				{
					int count = new util.UtilNumber().RandomNum(0, 7);
					item = dungeonRewardYaml.getItemStack("90."+count);
					if(item!=null)
					{
						treasureGet = true;
						new util.UtilPlayer().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new util.UtilNumber().RandomNum(1, 10);
				if(luck <= 5)
				{
					int count = new util.UtilNumber().RandomNum(0, 7);
					item = dungeonRewardYaml.getItemStack("50."+count);
					if(item!=null)
					{
						treasureGet = true;
						new util.UtilPlayer().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new util.UtilNumber().RandomNum(1, 10);
				if(luck == 1)
				{
					int count = new util.UtilNumber().RandomNum(0, 7);
					item = dungeonRewardYaml.getItemStack("10."+count);
					if(item!=null)
					{
						treasureGet = true;
						new util.UtilPlayer().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new util.UtilNumber().RandomNum(1, 100);
				if(luck == 1)
				{
					int count = new util.UtilNumber().RandomNum(0, 7);
					item = dungeonRewardYaml.getItemStack("1."+count);
					if(item!=null)
					{
						treasureGet = true;
						new util.UtilPlayer().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new util.UtilNumber().RandomNum(1, 1000);
				if(luck == 1)
				{
					int count = new util.UtilNumber().RandomNum(0, 7);
					item = dungeonRewardYaml.getItemStack("0."+count);
					if(item!=null)
					{
						treasureGet = true;
						new util.UtilPlayer().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				
				if( ! treasureGet)
					new effect.SendPacket().sendActionBar(player, "§c§l[꽝! 다음 기회에...]", false);
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.5F);
				new effect.SendPacket().sendActionBar(player, "§f§l[상자에 맞는 열쇠가 없습니다!]", false);
			}
		}
		return;
	}
	
	public boolean trapChestOpen(Block block)
	{
		Block signBlock = new Location(block.getWorld(),block.getX(),block.getY()+12,block.getZ()).getBlock();
		if(signBlock.getType()!=Material.SIGN_POST)
			return false;
		monster.MonsterSpawn monsterSpawn = new monster.MonsterSpawn();
		
		
        Sign sign = (Sign) signBlock.getState();
        String gridImage = sign.getLine(1);
        String dungeonName = sign.getLine(2);
        Location loc = new Location(block.getWorld(),block.getX(),block.getY(),block.getZ());

	  	YamlLoader dungeonMonsterYaml = new YamlLoader();
		dungeonMonsterYaml.getConfig("Dungeon/Dungeon/"+dungeonName+"/Monster.yml");
    	String[] mobList = null;
    	String listName = "Normal";
    	byte randomLevel = (byte) new util.UtilNumber().RandomNum(0, 3);
        if(! dungeonMonsterYaml.contains("Normal"))
        	dungeonMonsterYaml.createSection("Normal");
        if(! dungeonMonsterYaml.contains("Middle"))
        	dungeonMonsterYaml.createSection("Middle");
        if(! dungeonMonsterYaml.contains("High"))
        	dungeonMonsterYaml.createSection("High");
    	if(randomLevel <= 1)
    	{
    		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
        		if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
        			mobList = null;
	        	else
	        	{
	        		listName = "Middle";
	        		mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
	        	}
        	else
        	{
        		listName = "Normal";
        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
        	}
    	}
    	else if(randomLevel==2)
    	{
    		if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
    			if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
	        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
	        			mobList = null;
		        	else
		        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
    			else
    			{
	        		listName = "High";
    				mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
    			}
    		else
    		{
        		listName = "Middle";
    			mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
    		}
    	}
    	else
    	{
    		if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
    			if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
	        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
	        			mobList = null;
		        	else
		        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
    			else
    			{
	        		listName = "Middle";
    				mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
    			}
    		else
    		{
        		listName = "High";
    			mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
    		}
    	}
    	if(mobList!=null && mobList.length != 0)
    	{
    		ArrayList<String> mob = new ArrayList<>();
    		for(int count = 0; count < 8; count++)
        		mob.add(mobList[new util.UtilNumber().RandomNum(0, mobList.length-1)]);
    		if(mobList.length > 0)
    		{
            	if(gridImage.equals("◇"))
            	{
        			SoundEffect.playSoundLocation(loc, Sound.BLOCK_CHEST_OPEN, 1.3F, 1.8F);
            		monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mob.get(0)),(byte)-1,null, (char) -1, false);
            	}
            	else
            	{
            		randomLevel = (byte) new util.UtilNumber().RandomNum(0, 5);
            		switch(randomLevel)
            		{
            		case 0://2회 스폰
            			break;
            		default://1회 스폰
            			break;
            		}
            		
    				dungeonTrapDoorWorker(loc, true);
    				int[] xyzLoc = new int[3];
    				xyzLoc[0] = block.getX();
    				xyzLoc[1] = block.getY();
    				xyzLoc[2] = block.getZ();
    				byte roomChallenge = (byte) new util.UtilNumber().RandomNum(1, 5);
    				byte groupNumber = (byte) new util.UtilNumber().RandomNum(0, 15);
    				char group = '0';
    				switch(groupNumber)
    				{
        				case 0 : group = '0'; break;
        				case 1 : group = '1'; break;
        				case 2 : group = '2'; break;
        				case 3 : group = '3'; break;
        				case 4 : group = '4'; break;
        				case 5 : group = '5'; break;
        				case 6 : group = '6'; break;
        				case 7 : group = '7'; break;
        				case 8 : group = '8'; break;
        				case 9 : group = '9'; break;
        				case 10 : group = 'a'; break;
        				case 11 : group = 'b'; break;
        				case 12 : group = 'c'; break;
        				case 13 : group = 'd'; break;
        				case 14 : group = 'e'; break;
        				case 15 : group = 'f'; break;
    				}
        			loc.add(0, 1, 0);
    				if(roomChallenge <= 2)
    				{
                		for(int count = 0; count < 8; count++)
                		{
                			SoundEffect.playSoundLocation(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                			loc.add(0, 0.2, 0);
                			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mob.get(count)),(byte) 2, xyzLoc, group, true);
                		}
    				}
    				else
    				{
                		for(int count = 0; count < 7; count++)
                		{
                			loc.add(0, 0.2, 0);
                			SoundEffect.playSoundLocation(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mob.get(count)),(byte) 1, xyzLoc, group, true);
                		}
            			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mob.get(7)),(byte) 3, xyzLoc, group, true);
    				}
            		/*
            		일반 방 열면 몹 스폰되고 문 닫히게 하기
            		열쇠 가지고 있는 몬스터에게는 이름 앞에 칼라 코드를 붙여 표시한다.
            		칼라코드 순서 배열은 녹-검-녹 으로 통일한다.
            		녹-검-녹-빨 = 열쇠 가진 녀석
            		녹-검-녹-노 = 일반 녀석
            		녹-검-녹-녹 = 보스를 뜻하는 것으로, 반경 25블록 이내에 녹검녹녹이 없을 경우, 보스방 문이 열린다.
            		보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.
            		녹-검-녹-파 = 다음 웨이브 존재
            		이후 오는 & 코드는 각각의 몬스터 그룹을 설정하기 위함으로,
            		방 내에 그룹 코드가 붙은 몬스터가 반경 20 이내에 없을 경우, 다음 웨이브가 나오거나
            		문이 열리게 된다. 몬스터 그룹 코드는 0 ~ f 까지 존재한다.
            		
            		문이 열리기 위해서는 애초에 열었던 상자 위치를 알고 있어야 하는데,
            		X, Y, Z 좌표를 저장 해 두어야 한다.
            		저장하기 좋은 곳은 몬스터가 장착 중인 아이템인데
            		그게 까다롭다.
            		손에 아이템이 없다면 장착 시켜 주고, 있다면 그 아래에 좌표를 저장한 뒤,
            		죽을 때 계산하고 다시 좌표를 없애버리면 되지만, 블레이즈, 골렘, 위더 등의 몬스터들은 아이템 장착란이 없다.
            		
            		
            		EntityDeath 이벤트에서, 엔티티가 죽은 월드가 Dungeon일 경우만
            		위와 같은 연산을 처리하며, 커스텀 몬스터 관련 데이터를 찾기 위해
            		앞의 8캐릭터 바이트 커스텀 네임은 삭제하고 넘기도록 한다.
            		고로 Entity Damage By Entity 이벤트에서도 던전에서 때릴 경우를 잡아 주어야 한다.
            		*/
            	}
    		}
    		else if(!gridImage.equals("◇"))
    		{
    			Location blockLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY()+1, block.getLocation().getZ());
				ItemStack item = new ItemStack(Material.IRON_HOE);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName("§a§0§a§f§l[던전 룸 열쇠]");
				im.setLore(Arrays.asList("","§f던전 룸을 열 수 있는","§f낡은 열쇠이다."));
				im.addEnchant(Enchantment.DURABILITY, 6000, true);
				item.setItemMeta(im);
				new event.EventItemDrop().CustomItemDrop(blockLoc, item);
    		}
    	}
		else if(!gridImage.equals("◇"))
		{
			Location blockLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY()+1, block.getLocation().getZ());
			ItemStack item = new ItemStack(Material.IRON_HOE);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName("§a§0§a§f§l[던전 룸 열쇠]");
			im.setLore(Arrays.asList("","§f던전 룸을 열 수 있는","§f낡은 열쇠이다."));
			im.addEnchant(Enchantment.DURABILITY, 6000, true);
			item.setItemMeta(im);
			new event.EventItemDrop().CustomItemDrop(blockLoc, item);
		}
		SoundEffect.playSoundLocation(block.getLocation(), org.bukkit.Sound.BLOCK_CHEST_OPEN, 1.0F, 0.5F);
        block.setTypeIdAndData(0, (byte)0, true);
        return true;
	}
	
	public void TrapGlassTouch(Block trapTypeBlock, Player player)
	{
		Block signBlock = new Location(trapTypeBlock.getWorld(),trapTypeBlock.getX(),trapTypeBlock.getY()+11,trapTypeBlock.getZ()).getBlock();
		if(signBlock.getType()!=Material.SIGN_POST)
			return;
		monster.MonsterSpawn monsterSpawn = new monster.MonsterSpawn();
		
		effect.ParticleEffect p = new effect.ParticleEffect();
		if(trapTypeBlock.getData()==15||trapTypeBlock.getData()==14||trapTypeBlock.getData()==13||
			trapTypeBlock.getData()==11||trapTypeBlock.getData()==8)
		{
			switch(trapTypeBlock.getData())
			{
    			case 15: trapTypeBlock.setTypeIdAndData(95, (byte)7, true);break;
    			case 14: trapTypeBlock.setTypeIdAndData(95, (byte)6, true);break;
    			case 13: trapTypeBlock.setTypeIdAndData(95, (byte) 5, true);break;
    			case 11: trapTypeBlock.setTypeIdAndData(95, (byte)3, true);break;
    			case 8: trapTypeBlock.setTypeIdAndData(95, (byte)0, true);break;
			}
			for(int counter=0;counter<50;counter++)
				p.PL(trapTypeBlock.getLocation(), org.bukkit.Effect.MAGIC_CRIT, 0);
			SoundEffect.playSoundLocation(trapTypeBlock.getLocation(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
		}
		else if(trapTypeBlock.getData()==0||trapTypeBlock.getData()==3||trapTypeBlock.getData()==5||trapTypeBlock.getData()==6||trapTypeBlock.getData()==7)
		{
			for(int counter=0;counter<31;counter++)
				p.PL(trapTypeBlock.getLocation(), org.bukkit.Effect.CRIT, 0);
			SoundEffect.playSoundLocation(trapTypeBlock.getLocation(), org.bukkit.Sound.ENTITY_GENERIC_HURT, 0.5F, 0.5F);
			return;
		}
		trapTypeBlock = new Location(trapTypeBlock.getWorld(),trapTypeBlock.getX(),trapTypeBlock.getY()-2,trapTypeBlock.getZ()).getBlock();
		if(trapTypeBlock.getType()==Material.STONE)
		{
			trapTypeBlock = new Location(trapTypeBlock.getWorld(),trapTypeBlock.getX(),trapTypeBlock.getY()+13,trapTypeBlock.getZ()).getBlock();
	        Sign sign = (Sign) trapTypeBlock.getState();
	        String gridImage = sign.getLine(1);
	        Location loc = new Location(trapTypeBlock.getWorld(),trapTypeBlock.getX(),trapTypeBlock.getY(),trapTypeBlock.getZ());
	        int direction = Integer.parseInt(sign.getLine(3));
			dungeonDoorRemover(player, gridImage.charAt(0), direction, loc);
		}
		else
		{
			trapTypeBlock = new Location(trapTypeBlock.getWorld(),trapTypeBlock.getX(),trapTypeBlock.getY()+13,trapTypeBlock.getZ()).getBlock();
	        Sign sign = (Sign) trapTypeBlock.getState();
	        String gridImage = sign.getLine(1);
	        String dungeonName = sign.getLine(2);
	        /*
	        Direction : 0 = 북
	        Direction : 1 = 북동
	        Direction : 2 = 동
	        Direction : 3 = 남동
	        Direction : 4 = 남
	        Direction : 5 = 남서
	        Direction : 6 = 서
	        Direction : 7 = 북서
	        Direction : 8 = 중앙
	         */
	        Location loc = new Location(trapTypeBlock.getWorld(),trapTypeBlock.getX(),trapTypeBlock.getY(),trapTypeBlock.getZ());
	        int direction = Integer.parseInt(sign.getLine(3));
	        int nowLevel = Integer.parseInt(sign.getLine(0).split("/")[0]);
        	if(nowLevel<=0)
        		nowLevel = 1;
	        int maxLevel = Integer.parseInt(sign.getLine(0).split("/")[1]);
	        switch(direction)
	        {
	        case 1:
	        	loc.add(-6, -12, 6);
	        	break;
	        case 3:
	        	loc.add(-6, -12, -6);
	        	break;
	        case 5:
	        	loc.add(6, -12, -6);
	        	break;
	        case 7:
	        	loc.add(6, -12, 6);
	        	break;
	        }
		  	YamlLoader dungeonMonsterYaml = new YamlLoader();
			dungeonMonsterYaml.getConfig("Dungeon/Dungeon/"+dungeonName+"/Monster.yml");
        	String[] mobList = null;
        	String listName = "Normal";
	        if(gridImage.equals("◎"))
	        {
	        	if(dungeonMonsterYaml.getConfigurationSection("SubBoss").getKeys(false).isEmpty())
	        		if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
		        		if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
			        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
			        			mobList = null;
			        		else
			        			mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
		        		else
		        		{
		        			listName = "Middle";
		        			mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
		        		}
	        		else
	        		{
	        			listName = "High";
	        			mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
	        		}
	        	else
        		{
        			listName = "SubBoss";
	        		mobList = dungeonMonsterYaml.getConfigurationSection("SubBoss").getKeys(false).toArray(new String[0]);
        		}
	        	if(mobList!=null)
	        	{
	        		loc.add(0,1,0);
	        		for(int count = 0; count < 4; count++)
	        		{
	        			loc.add(new util.UtilNumber().RandomNum(-2, 2), 0.1*count, new util.UtilNumber().RandomNum(-2, 2));
	        			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mobList[new util.UtilNumber().RandomNum(0, mobList.length-1)]), (byte)-1, null, (char) -1, false);
	        		}
	        	}
	        }
	        else
	        {
	        	if(maxLevel < 5)
	        		maxLevel = maxLevel*20;
	        	else if(maxLevel < 10)
	        		maxLevel = maxLevel*10;
	        	else if(maxLevel < 20)
	        		maxLevel = maxLevel*5;
	        	else if(maxLevel >= 100)
	        		maxLevel = 100;
	        	if(maxLevel/nowLevel>20)
	        	{
	        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
		        		if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
		        			mobList = null;
			        	else
			        	{
			        		listName = "Middle";
			        		mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
			        	}
		        	else
		        	{
		        		listName = "Normal";
		        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
		        	}
	        	}  	
	        	else if(maxLevel/nowLevel>10)
	        	{
	        		if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
	        			if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
			        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
			        			mobList = null;
				        	else
				        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
	        			else
	        			{
			        		listName = "High";
	        				mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
	        			}
	        		else
	        		{
		        		listName = "Middle";
	        			mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
	        		}
	        	}
	        	else
	        	{
	        		if(dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).isEmpty())
	        			if(dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).isEmpty())
			        		if(dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).isEmpty())
			        			mobList = null;
				        	else
				        		mobList = dungeonMonsterYaml.getConfigurationSection("Normal").getKeys(false).toArray(new String[0]);
	        			else
	        			{
			        		listName = "Middle";
	        				mobList = dungeonMonsterYaml.getConfigurationSection("Middle").getKeys(false).toArray(new String[0]);
	        			}
	        		else
	        		{
		        		listName = "High";
	        			mobList = dungeonMonsterYaml.getConfigurationSection("High").getKeys(false).toArray(new String[0]);
	        		}
	        	}
	        }
	        
        	if(mobList!=null)
        	{
        		loc.add(0,1,0);
        		for(int count = 0; count < 4; count++)
        		{
        			loc.add(new util.UtilNumber().RandomNum(-2, 2),0.1*count, new util.UtilNumber().RandomNum(-2, 2));
        			SoundEffect.playSoundLocation(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
        			monsterSpawn.SpawnMob(loc, dungeonMonsterYaml.getString(listName+"."+mobList[new util.UtilNumber().RandomNum(0, mobList.length-1)]), (byte)-1, null, (char) -1, false);
        		}
        	}
		}
	}
	
	
	
	private void dungeonDoorRemover(Player player, char gridImage, int direction, Location loc)
	{
		
		Location original = loc.add(0,-12,0);
		original.setX(loc.getX());
		original.setY(loc.getY());
		original.setZ(loc.getZ());
		Block block = null;
		switch(gridImage)
        {
        case '△':
			switch(direction)
	        {
		        case 1:
		        	loc.add(-3, 0, -4).getBlock();
		        	break;
		        case 3:
		        	loc.add(-3, 0, -16).getBlock();
		        	break;
		        case 5:
		        	loc.add(9, 0, -16).getBlock();
		        	break;
		        case 7:
		        	loc.add(9, 0, -4).getBlock();
		        	break;
	        }
			ironDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(-1, 0, 0).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(5, 1, 0).getBlock();
        	}
        	break;
        case '♥':
        	Location bossLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        	Location signLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        	signLoc.add(0, 12, 0);
	        Sign signBlock = (Sign) signLoc.getBlock().getState();
			switch(direction)
	        {
		        case 1:
		        	loc = loc.add(-3, 0, -4);
		        	bossLoc = bossLoc.add(-6, 1, -37);
		        	break;
		        case 3:
		        	loc.add(-3, 0, -16).getBlock();
		        	bossLoc = bossLoc.add(-6, 1, -49);
		        	break;
		        case 5:
		        	loc.add(9, 0, -16).getBlock();
		        	bossLoc = bossLoc.add(6, 1, -49);
		        	break;
		        case 7:
		        	loc.add(9, 0, -4).getBlock();
		        	bossLoc = bossLoc.add(6, 1, -37);
		        	break;
	        }
			ironDoorOpening(loc);
			for(int c=0;c<2;c++)
			{
	        	for(int count = 0; count < 5; count++)
	        	{
	        		for(int count2 = 0; count2 < 5; count2++)
	        		{
	        			block = loc.add(-1, 0, 0).getBlock();
    					block.setType(Material.AIR,true);
	        		}
        			block = loc.add(5, 1, 0).getBlock();
	        	}
    			block = loc.add(0, -5, -1).getBlock();
			}
			bossRoomOpen(player, bossLoc, signBlock.getLine(2));
        	break;
        case '▽':
			switch(direction)
	        {
	        case 1:
	        	loc.add(-3, 0, 16).getBlock();
	        	break;
	        case 3:
	        	loc.add(-3, 0, 4).getBlock();
	        	break;
	        case 5:
	        	loc.add(9, 0, 4).getBlock();
	        	break;
	        case 7:
	        	loc.add(9, 0, 16).getBlock();
	        	break;
	        }
			ironDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(-1, 0, 0).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(5, 1, 0).getBlock();
        	}
        	break;
        case '◁':
			switch(direction)
	        {
		        case 1:
		        	loc.add(-16, 0, 9).getBlock();
		        	break;
		        case 3:
		        	loc.add(-16, 0, -3).getBlock();
		        	break;
		        case 5:
		        	loc.add(-4, 0, -3).getBlock();
		        	break;
		        case 7:
		        	loc.add(-4, 0, 9).getBlock();
		        	break;
	        }
			ironDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(0, 0, -1).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(0, 1, 5).getBlock();
        	}
        	break;
        case '▷':
			switch(direction)
	        {
		        case 1:
		        	loc.add(4, 0, 9).getBlock();
		        	break;
		        case 3:
		        	loc.add(4, 0, -3).getBlock();
		        	break;
		        case 5:
		        	loc.add(16, 0, -3).getBlock();
		        	break;
		        case 7:
		        	loc.add(16, 0, 9).getBlock();
		        	break;
	        }
			ironDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(0, 0, -1).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(0, 1, 5).getBlock();
        	}
        	break;
        }
	}
	
	public void dungeonTrapDoorWorker(Location loc, boolean isCreate)
	{
		Block block = null;
		Material M = Material.STAINED_GLASS_PANE;
		byte blockData = (byte)14;

		ironDoorOpening(loc);
		Location loc2;
		byte signX = 0;
		byte signZ = 0;
		byte addingX;
		byte addingZ;
		byte uppingX;
		byte uppingZ;
		for(int directionLoop = 0; directionLoop < 4; directionLoop++)
		{
			signX = 0;
			signZ = 0;
			addingX = 0;
			addingZ = 0;
			uppingX = 0;
			uppingZ = 0;
			loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			if(directionLoop == 0)
			{
				loc2.add(-10,0,2);
				addingZ = -1;
				uppingZ = 5;
				signX = 0;
				signZ = -2;
			}
			else if(directionLoop == 1)
			{
				loc2.add(2,0,-10);
				addingX = -1;
				uppingX = 5;
				signZ = 0;
				signX = -2;
			}
			else if(directionLoop ==2)
			{
				loc2.add(10,0,2);
				addingZ = -1;
				uppingZ = 5;
				signX = 0;
				signZ = -2;
			}
			else
			{
				loc2.add(2,0,10);
				addingX = -1;
				uppingX = 5;
				signZ = 0;
				signX = -2;
			}
			block = loc2.getBlock();
	    	for(int count = 0; count < 6; count++)
	    	{
	    		for(int count2 = 0; count2 < 5; count2++)
	    		{
	    			if(isCreate)
	    			{
		    			if(block.getType()==Material.AIR||block == null)
		    			{
		    				block.setType(M,true);
		    				block.setData(blockData);
		    			}
	    			}
	    			else if(block.getType()==M||block == null)
		    			block.setType(Material.AIR,true);
	    			block = loc2.add(addingX, 0, addingZ).getBlock();
	    		}
				block = loc2.add(uppingX, 1, uppingZ).getBlock();
	    	}
	    	block = loc2.add(signX,6,signZ).getBlock();
	    	
	    	block.setType(Material.SIGN_POST);

	    	if(block.getType()==Material.SIGN_POST)
	    	{
		        Sign s = (Sign) block.getState();
		        s.setLine(0, ""+(int)loc.getX());//들어갈 곳 X
		        s.setLine(1, ""+(int)loc.getY()+1);//들어갈 곳 Y
		        s.setLine(2, ""+(int)loc.getZ());//들어갈 곳 Z
		        s.setLine(3, "");//빈 공간
		        s.update();
	    	}
	    	else
	    	{
		    	block.setType(Material.SIGN_POST);
		    	if(block.getType()==Material.SIGN_POST)
		    	{
			        Sign s = (Sign) block.getState();
			        s.setLine(0, ""+(int)loc.getX());//들어갈 곳 X
			        s.setLine(1, ""+(int)loc.getY()+1);//들어갈 곳 Y
			        s.setLine(2, ""+(int)loc.getZ());//들어갈 곳 Z
			        s.setLine(3, "");//빈 공간
			        s.update();
		    	}
	    	}
		}
	}
	
}