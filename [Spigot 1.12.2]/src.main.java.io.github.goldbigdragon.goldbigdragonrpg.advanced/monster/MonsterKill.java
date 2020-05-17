package monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import effect.SendPacket;
import effect.SoundEffect;
import main.MainServerOption;
import quest.QuestGui;
import util.YamlLoader;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class MonsterKill
{
	public String getNormalName(Entity entity)
	{
		switch(entity.getType())
		{
			case ZOMBIE : return "³ïÁ»ºñ";
			case GIANT : return "³ïÀÚÀÌ¾ðÆ®";
			case SKELETON :
				return "³ï½ºÄÌ·¹Åæ";
			case WITHER_SKELETON:
				return "³ï³×´õ½ºÄÌ·¹Åæ";
			case ENDERMAN : return "³ï¿£´õ¸Ç";
			case CREEPER :
			{
				Creeper c = (Creeper) entity;
				if(c.isPowered())
					return "³ï¹ø°³Å©¸®ÆÛ";
				else
					 return "³ïÅ©¸®ÆÛ";
			}
			case SPIDER : return "³ï°Å¹Ì";
			case CAVE_SPIDER : return "³ïµ¿±¼°Å¹Ì";
			case SILVERFISH : return "³ïÁ»¹ú·¹";
			case ENDERMITE : return "³ï¿£´õÁøµå±â";
			case SLIME : return "³ï½½¶óÀÓ";
			case MAGMA_CUBE : return "³ï¸¶±×¸¶Å¥ºê";
			case BLAZE : return "³ïºí·¹ÀÌÁî";
			case GHAST : return "³ï°¡½ºÆ®";
			case PIG_ZOMBIE : return "³ïÁ»ºñÇÇ±×¸Ç";
			case WITCH : return "³ï¸¶³à";
			case WITHER : return "³ïÀ§´õ";
			case ENDER_DRAGON : return "³ï¿£´õµå·¡°ï";
			case ENDER_CRYSTAL : return "³ï¿£´õÅ©¸®½ºÅ»";
			case GUARDIAN : return "³ï¼öÈ£ÀÚ";
			case SHEEP : return "³ï¾ç";
			case COW : return "³ï¼Ò";
			case PIG : return "³ïµÅÁö";
			case HORSE : return "³ï¸»";
			case RABBIT : return "³ïÅä³¢";
			case OCELOT : return "³ï¿À¼¿·Ô";
			case WOLF : return "³ï´Á´ë";
			case CHICKEN : return "³ï´ß";
			case MUSHROOM_COW : return "³ï¹ö¼¸¼Ò";
			case BAT : return "³ï¹ÚÁã";
			case SQUID : return "³ï¿ÀÂ¡¾î";
			case VILLAGER : return "³ïÁÖ¹Î";
			case SNOWMAN : return "³ï´«»ç¶÷";
			case IRON_GOLEM : return "³ï°ñ·½";
			case SHULKER : return "³ï¼ÈÄ¿";
			case POLAR_BEAR : return "³ïºÏ±Ø°õ";

			case ELDER_GUARDIAN : return "³ï¿¤´õ°¡µð¾ð";
			case STRAY : return "³ï½ºÆ®·¹ÀÌ";
			case HUSK : return "³ïÇã½ºÅ©";
			case ZOMBIE_VILLAGER : return "³ïÁÖ¹ÎÁ»ºñ";
			case EVOKER : return "³ï¿¡º¸Ä¿";
			case VEX : return "³ïº¤½º";
			case VINDICATOR : return "³ïºóµðÄÉÀÌÅÍ";
			case SKELETON_HORSE : return "³ï½ºÄÌ·¹Åæ¸»";
			case ZOMBIE_HORSE : return "³ïÁ»ºñ¸»";
			case DONKEY : return "³ï´ç³ª±Í";
			case MULE : return "³ï³ë»õ";
			case LLAMA : return "³ï¶ó¸¶";
			default : return null;
		}
	}
	
	public String getRealName(Entity entity)
	{
		String name=entity.getCustomName();
		if(name == null || ChatColor.stripColor(name).length() <= 0)
			return getNormalName(entity);
		if(entity.getLocation().getWorld().getName().equals("Dungeon"))
		{
			if(name.length() >= 6)
			{
				if(name.charAt(0)=='¡×'&&name.charAt(1)=='2'&&
					name.charAt(2)=='¡×'&&name.charAt(3)=='0'&&
					name.charAt(4)=='¡×'&&name.charAt(5)=='2')
				{
					name = name.substring(12, name.length());
				}
			}
		}
		if(name.length()<=0)
			return getNormalName(entity);
		else
			return main.MainServerOption.MonsterNameMatching.get(name);
	}
	
	public void boomb(Entity entity)
	{
		String entityName = getRealName(entity);
		int monsterINT = 10;
		int radius = 5;
		if(main.MainServerOption.MonsterList.containsKey(entityName))
			monsterINT = main.MainServerOption.MonsterList.get(entityName).getINT();
		else
		{
			if(entity.getType()==EntityType.CREEPER)
			{
				Creeper c = (Creeper)entity;
				if(c.isPowered())
					monsterINT = 40;
				else
					monsterINT = 20;
			}
			else if(entity.getType() == EntityType.ENDER_CRYSTAL)
				monsterINT = 200;
			else if(entity.getType() == EntityType.PRIMED_TNT || entity.getType() == EntityType.MINECART_TNT)
				monsterINT = 90;
		}
		int minPower = monsterINT/4;
		int maxPower = (int)(monsterINT/2.5);
		
		int power = new Random().nextInt(maxPower-minPower+1)+minPower;
		radius = (power/3)*2;
		if(radius < 3)
			radius = 3;
		else if(radius > 8)
			radius = 8;

		entity.getLocation().getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_LARGE, 0);
		entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE ,1.5F, 1.0F);
		Iterator<Entity> e = Bukkit.getWorld("Dungeon").getNearbyEntities(entity.getLocation(), radius, radius, radius).iterator();
	    while(e.hasNext())
	    {
	        Entity choosedEntity = e.next();
	        if(choosedEntity!=null)
	        {
				String name = choosedEntity.getCustomName();
				if(ChatColor.stripColor(name) == null)
					name = null;
				else if(ChatColor.stripColor(name).length() == 0)
					name = null;
		        if(! choosedEntity.isDead())
		        {
			        int def = 0;
			        int pro = 0;
			        
			        if(choosedEntity.getType()==EntityType.PLAYER)
			        {
			        	Player p = (Player) choosedEntity;
			        	if(p.getGameMode()==GameMode.SURVIVAL||p.getGameMode()==GameMode.ADVENTURE)
			        	{
			        		if(p.isOnline())
			        		{
			        			def = main.MainServerOption.PlayerList.get(p.getUniqueId().toString()).getStat_DEF();
			        			pro = main.MainServerOption.PlayerList.get(p.getUniqueId().toString()).getStat_Protect();
			        		}
			        		else if(name!=null)
			        		{
								name = getRealName(choosedEntity);
			        			def = main.MainServerOption.MonsterList.get(name).getDEF();
			        			pro = main.MainServerOption.MonsterList.get(name).getPRO();
			        		}
			        	}
			        }
			        else if(name!=null)
			        {
						name = getRealName(choosedEntity);
						def = main.MainServerOption.MonsterList.get(name).getDEF();
	        			pro = main.MainServerOption.MonsterList.get(name).getPRO();
	        		}

					if(power >= 100)
						power = power*(100-pro)/100;
					else if(power >= 10)
						power = power*((100-pro)/10)/10;
					else
						power = power-pro;
					power = power-def;
					if(choosedEntity.getType()!=EntityType.DROPPED_ITEM&&choosedEntity.getType()!=EntityType.ARMOR_STAND&&
						choosedEntity.getType()!=EntityType.ARROW&&choosedEntity.getType()!=EntityType.BOAT&&
						choosedEntity.getType()!=EntityType.EGG&&choosedEntity.getType()!=EntityType.ENDER_PEARL&&
						choosedEntity.getType()!=EntityType.ENDER_SIGNAL&&choosedEntity.getType()!=EntityType.EXPERIENCE_ORB&&
						choosedEntity.getType()!=EntityType.FALLING_BLOCK&&choosedEntity.getType()!=EntityType.FIREBALL&&
						choosedEntity.getType()!=EntityType.FIREWORK&&choosedEntity.getType()!=EntityType.FISHING_HOOK&&
						choosedEntity.getType()!=EntityType.ITEM_FRAME&&choosedEntity.getType()!=EntityType.LEASH_HITCH&&
						choosedEntity.getType()!=EntityType.LIGHTNING&&choosedEntity.getType()!=EntityType.PAINTING&&
						choosedEntity.getType()!=EntityType.PRIMED_TNT&&choosedEntity.getType()!=EntityType.SMALL_FIREBALL&&
						choosedEntity.getType()!=EntityType.SNOWBALL&&choosedEntity.getType()!=EntityType.SPLASH_POTION&&
						choosedEntity.getType()!=EntityType.THROWN_EXP_BOTTLE&&choosedEntity.getType()!=EntityType.UNKNOWN&&
						choosedEntity.getType()!=EntityType.WITHER_SKULL)
					{
						if(choosedEntity != entity && !choosedEntity.isDead())
						{
							LivingEntity livingEntity = (LivingEntity) choosedEntity;
							livingEntity.damage(power, entity);
						}
					}
		        }
	        }
	    }
		
		//¸¸ÀÏ Ä¿½ºÅÒ ÀÌ¸§ÀÌ ¾ø´Â ÀÏ¹Ý ¸÷ÀÏ °æ¿ì,
		//ÀÏ¹Ý Å©¸®ÆÛ´Â 4 ~ 6
		//Â÷Áö Å©¸®ÆÛ´Â 10 ~ 16
		//¿£´õ Å©¸®½ºÅ»Àº 48 ~ 105
		//TNT´Â 16 ~ 35·Î ÇÏ±â.
		//¹Ý°æÀÌ ¸Ö¾îÁú¼ö·Ï µ¥¹ÌÁö ³·°Ô ÁÖ¸ç, ÀÌ´Â for¹® ÀÌ¿ëÇÏ±â
		//¸¶Áö¸·¿¡ Æø¹ß »ç¿îµå¿Í ÀÌÆåÆ® ³Ö±â.
	}

	public void dungeonKilled(LivingEntity entity, boolean isBoomed)
	{
		if(entity.getCustomName()!=null)
		{
			if(entity.getCustomName().length() >= 6)
			{
				String name = entity.getCustomName().toString();
				if(name.charAt(0)=='¡×'&&name.charAt(1)=='2'&&
					name.charAt(2)=='¡×'&&name.charAt(3)=='0'&&
					name.charAt(4)=='¡×'&&name.charAt(5)=='2')
				{
					Location loc = new Location(entity.getLocation().getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
					ItemStack item = entity.getEquipment().getHelmet();
					List<String> lore = item.getItemMeta().getLore();

					if(lore!=null)
					{
						String locString = lore.get(lore.size()-1).split(":")[1];
						ItemMeta im = item.getItemMeta();
						lore.remove(lore.size()-1);
						im.setLore(lore);
						item.setItemMeta(im);
						entity.getEquipment().setHelmet(item);
						loc = new Location(entity.getLocation().getWorld(), Integer.parseInt(locString.split(",")[0]), Integer.parseInt(locString.split(",")[1]), Integer.parseInt(locString.split(",")[2]));
					}
					if(name.charAt(7)!='2'&&isBoomed)
						entity.setCustomName("øïÞÝ");
					switch(name.charAt(7))
					{
					case 'e' : //ÀÏ¹Ý
						if(searchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new dungeon.DungeonMain().dungeonTrapDoorWorker(loc, false);
						break;
					case '1' : //´ÙÀ½ ¿þÀÌºê Á¸Àç
						if(searchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new dungeon.DungeonMain().monsterSpawn(loc);
						break;
					case '4' : //¿­¼è °¡Áø ³ð
						if(searchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new dungeon.DungeonMain().dungeonTrapDoorWorker(loc, false);
						loc.setY(loc.getY()+1);
						item = new ItemStack(292);
						ItemMeta im = item.getItemMeta();
						im.setDisplayName("¡×a¡×0¡×a¡×f¡×l[´øÀü ·ë ¿­¼è]");
						im.setLore(Arrays.asList("","¡×f´øÀü ·ëÀ» ¿­ ¼ö ÀÖ´Â","¡×f³°Àº ¿­¼èÀÌ´Ù."));
						im.addEnchant(Enchantment.DURABILITY, 6000, true);
						item.setItemMeta(im);
						new event.EventItemDrop().CustomItemDrop(loc, item);
						break;
					case '2' : //º¸½º [º¸½º¹æ ¹®À» Å½ÁöÇÏ±â À§ÇØ¼­ º¸»ó¹æ Ã¶Ã¢ Áß¾ÓÀÇ À§Ä¡¸¦ ´øÀü ÄÜÇÇ±×¿¡ ÀúÀå ½ÃÅ²´Ù.]
						Player player = entity.getKiller();
						if(entity.getKiller() == null||entity.getKiller().isOnline()==false)
						{
							List<Entity> e = (List<Entity>) loc.getWorld().getNearbyEntities(loc, 35D, 20D, 35D);
							for(int count = 0; count < e.size(); count++)
							{
								if(e.get(count).getType() == EntityType.PLAYER)
								{
									Player p = (Player) e.get(count);
									if(p.isOnline())
									{
										player = (Player) e.get(count);
										break;
									}
								}
							}
						}
						name = getRealName(entity);
						if(name != null)
						{
							YamlLoader dungeonYaml = new YamlLoader();
							if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() != null && main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC() > 1)
							{
								dungeonYaml.getConfig("Dungeon/Dungeon/"+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter()+"/Entered/"+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC()+".yml");
								if(dungeonYaml.contains("Boss"))
								{
									int bossCount = dungeonYaml.getConfigurationSection("Boss").getKeys(false).size();
									ArrayList<String> bossList = new ArrayList<>();
									boolean isChecked = false;
									for(int count = 0; count < bossCount; count++)
									{
										if(!isChecked&&dungeonYaml.getString("Boss."+count).equals(name))
											isChecked = true;
										else
											bossList.add(dungeonYaml.getString("Boss."+count));
									}
									dungeonYaml.removeKey("Boss");
									dungeonYaml.saveConfig();
									if(! bossList.isEmpty())
									{
							    		for(int count = 0; count < bossList.size(); count++)
							    			dungeonYaml.set("Boss."+count, bossList.get(count));
							    		dungeonYaml.saveConfig();
									}
									else
										new dungeon.DungeonMain().dungeonClear(player, loc);
								}
							}
						}
						break;
					}
				}
			}
		}
	}
	
	
	
	public void monsterKilling(EntityDeathEvent event)
	{
		LivingEntity entity = event.getEntity();
		if(entity.getLocation().getWorld().getName().equals("Dungeon"))
			dungeonKilled(entity, false);
    	if(entity!=null && entity.getKiller() != null)
    	{
    		if((entity.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK  || entity.getLastDamageCause().getCause() == DamageCause.PROJECTILE
    				|| entity.getLastDamageCause().getCause() == DamageCause.MAGIC)
    			&& entity.getKiller().getType() == EntityType.PLAYER&&entity.getKiller().isOnline())
    		{
				Player player = entity.getKiller();
				if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth())
				    new SendPacket().sendTitle(player, "¡×0¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á", "¡×4¡×l[DEAD]", 0, 0, 1);
				reward(entity, player);
				questProgress(entity, player);
			}
		}
    	
    	

		if(main.MainServerOption.removeMonsterDefaultDrops)
		{
			List<ItemStack> finalDrops = new ArrayList<>();
			List<ItemStack> drops = event.getDrops();
			if(!drops.isEmpty())
			{
				List<String> customDropsString = new ArrayList<>();
				List<ItemStack> items = new ArrayList<>();
				items.add(entity.getEquipment().getItemInMainHand());
				items.add(entity.getEquipment().getItemInOffHand());
				items.add(entity.getEquipment().getHelmet());
				items.add(entity.getEquipment().getChestplate());
				items.add(entity.getEquipment().getLeggings());
				items.add(entity.getEquipment().getBoots());
				for(int count = 0; count < items.size(); count++)
				{
					if(items.get(count)!=null)
					{
						if(items.get(count).hasItemMeta() && items.get(count).getItemMeta().hasDisplayName())
							customDropsString.add(items.get(count).getItemMeta().getDisplayName());
						else
							customDropsString.add(items.get(count).getTypeId() + ":" + items.get(count).getDurability());
					}
				}
				String name = null;
				for(int count = 0; count < drops.size(); count++)
				{
					if(drops.get(count).hasItemMeta() && drops.get(count).getItemMeta().hasDisplayName())
						name = drops.get(count).getItemMeta().getDisplayName();
					else
						name = drops.get(count).getTypeId() + ":" + drops.get(count).getDurability();
					if(customDropsString.contains(name))
						finalDrops.add(drops.get(count));
				}
			}
			event.getDrops().clear();
			event.getDrops().addAll(finalDrops);
		}
    	return;
	}

	public byte searchRoomMonster(byte searchSize, char group, Location loc)
	{
		byte mobs = 0;
		List<Entity> e = (List<Entity>) loc.getWorld().getNearbyEntities(loc, searchSize, searchSize, searchSize);
		
		for(int i = 0; i < e.size(); i++)
		{
			String name = e.get(i).getCustomName();
			if(name != null)
			{
				if(name.length() >= 6)
				{
					if(! e.get(i).isDead())
					{
						if(!name.equals("øïÞÝ"))
						{
							if(name.charAt(0)=='¡×'&&name.charAt(1)=='2'&&
								name.charAt(2)=='¡×'&&name.charAt(3)=='0'&&
								name.charAt(4)=='¡×'&&name.charAt(5)=='2')
							{
								if(name.charAt(9)==group)
									mobs++;
							}
						}
					}
				}
			}
		}
		return mobs;
	}
	
	public void reward(String realName, String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		util.NumericUtil numericUtil = new util.NumericUtil();
		int amount = 1;
		if(40 <= numericUtil.RandomNum(0, 100) * MainServerOption.eventDropChance)
		{
			int lucky = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
			if(lucky >= 10) lucky =10;
			if(lucky <= 0) lucky = 1;
			if(lucky >= numericUtil.RandomNum(0, 100))
			{
				int luckysize = numericUtil.RandomNum(0, 100);
				if(luckysize <= 80){player.sendMessage("¡×e¡×l[SYSTEM] : ·°Å° ÇÇ´Ï½Ã!");amount = 2;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);}
				else if(luckysize <= 95){player.sendMessage("¡×e¡×l[SYSTEM] : ºò ·°Å° ÇÇ´Ï½Ã!");amount = 5;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);}
				else{player.sendMessage("¡×e¡×l[SYSTEM] : ÈÞÁî ·°Å° ÇÇ´Ï½Ã!");amount = 20;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);}
			}
		}
		else
			amount = 0;
		if(main.MainServerOption.MonsterList.containsKey(realName))
		{
			if(amount == 0)
				new util.PlayerUtil().addMoneyAndEXP(player, 0, main.MainServerOption.MonsterList.get(realName).getEXP(), player.getLocation(), true, false);
			else
				new util.PlayerUtil().addMoneyAndEXP(player, amount* numericUtil.RandomNum(main.MainServerOption.MonsterList.get(realName).getMinMoney(), main.MainServerOption.MonsterList.get(realName).getMaxMoney()), main.MainServerOption.MonsterList.get(realName).getEXP(), player.getLocation(), true, false);
			return;
		}
		
	}
	
	public void reward(Entity entity, Player player)
	{
		util.NumericUtil numericUtil = new util.NumericUtil();
		int amount = 1;
		if(40 <= numericUtil.RandomNum(0, 100) * MainServerOption.eventDropChance)
		{
			int lucky = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
			if(lucky >= 10) lucky =10;
			if(lucky <= 0) lucky = 1;
			if(lucky >= numericUtil.RandomNum(0, 100))
			{
				int luckysize = numericUtil.RandomNum(0, 100);
				if(luckysize <= 80){player.sendMessage("¡×e¡×l[SYSTEM] : ·°Å° ÇÇ´Ï½Ã!");amount = 2;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);}
				else if(luckysize <= 95){player.sendMessage("¡×e¡×l[SYSTEM] : ºò ·°Å° ÇÇ´Ï½Ã!");amount = 5;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);}
				else{player.sendMessage("¡×e¡×l[SYSTEM] : ÈÞÁî ·°Å° ÇÇ´Ï½Ã!");amount = 20;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);}
			}
		}
		else
			amount = 0;
		String name = getRealName(entity);
		if(main.MainServerOption.MonsterList.containsKey(name))
		{
			if(amount == 0)
				new util.PlayerUtil().addMoneyAndEXP(player, 0, main.MainServerOption.MonsterList.get(name).getEXP(), entity.getLocation(), true, false);
			else
				new util.PlayerUtil().addMoneyAndEXP(player, amount* numericUtil.RandomNum(main.MainServerOption.MonsterList.get(name).getMinMoney(), main.MainServerOption.MonsterList.get(name).getMaxMoney()), main.MainServerOption.MonsterList.get(name).getEXP(), entity.getLocation(), true, false);
			return;
		}
		else
		{
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			EntityType entityType = entity.getType();
			if(entityType == EntityType.SKELETON)
				new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.SKELETON.MIN_MONEY"), configYaml.getInt("Normal_Monster.SKELETON.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SKELETON.EXP"), entity.getLocation(), true, false);
			else if(entityType == EntityType.CREEPER)
			{
				Creeper c = (Creeper)entity;
				if(!c.isPowered())
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.CREEPER.MIN_MONEY"), configYaml.getInt("Normal_Monster.CREEPER.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.CREEPER.EXP"), entity.getLocation(), true, false);
				else
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.CHARGED_CREEPER.MIN_MONEY"), configYaml.getInt("Normal_Monster.CHARGED_CREEPER.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.CHARGED_CREEPER.EXP"), entity.getLocation(), true, false);
			}
			else if(entityType == EntityType.SLIME)
			{
				Slime sl = (Slime)entity;
				if(sl.getSize() == 1)
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.SLIME_SMALL.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_SMALL.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_SMALL.EXP"), entity.getLocation(), true, false);
				else if(sl.getSize() <= 3)
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.SLIME_MIDDLE.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_MIDDLE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_MIDDLE.EXP"), entity.getLocation(), true, false);
				else if(sl.getSize() == 4)
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.SLIME_BIG.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_BIG.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_BIG.EXP"), entity.getLocation(), true, false);
				else
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.SLIME_HUGE.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_HUGE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_HUGE.EXP"), entity.getLocation(), true, false);
			}
			else if(entityType == EntityType.MAGMA_CUBE)
			{
				MagmaCube ma = (MagmaCube)entity;
				if(ma.getSize() == 1)
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_SMALL.EXP"), entity.getLocation(), true, false);
				else if(ma.getSize() <= 3)
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP"), entity.getLocation(), true, false);
				else if(ma.getSize() == 4)
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_BIG.EXP"), entity.getLocation(), true, false);
				else
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_HUGE.EXP"), entity.getLocation(), true, false);
			}
			else
			{
				if(configYaml.contains("Normal_Monster."+entityType.toString()))
					new util.PlayerUtil().addMoneyAndEXP(player, numericUtil.RandomNum(configYaml.getInt("Normal_Monster."+entityType.toString()+".MIN_MONEY"), configYaml.getInt("Normal_Monster."+entityType.toString()+".MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster."+entityType.toString()+".EXP"), entity.getLocation(), true, false);
			}
		}
		return;
	}

	public void questProgress(Entity entity, Player player)
	{
		List<Player> targetPlayers = new ArrayList<>();
		if( ! MainServerOption.partyJoiner.containsKey(player))
			targetPlayers.add(player);
		else
		{
			Player[] partyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			int expShareDistance = configYaml.getInt("Party.EXPShareDistance");
			Player member = null;
			Location entityLoc = entity.getLocation();
			for(int counta = 0; counta < partyMember.length; counta++)
			{
				member = partyMember[counta];
				if(entityLoc.getWorld() == member.getLocation().getWorld() &&
					entityLoc.distance(member.getLocation()) <= expShareDistance)
					targetPlayers.add(member);
			}
		}

		YamlLoader questYaml = new YamlLoader();
		questYaml.getConfig("Quest/QuestList.yml");
		YamlLoader playerQuestYaml = new YamlLoader();
		String[] startedQuestArrays = null;
		String questName = null;
		int flow = 0;
		int monsterListSize = 0;
		int finish = 0;

		String questMonsterName = null;
		int huntAmount = 0;
		String killedName = null;
		QuestGui qGui = new quest.QuestGui();
		for(int playerCount = 0; playerCount < targetPlayers.size(); playerCount++)
		{
			playerQuestYaml.getConfig("Quest/PlayerData/"+targetPlayers.get(playerCount).getUniqueId()+".yml");
			startedQuestArrays = playerQuestYaml.getConfigurationSection("Started.").getKeys(false).toArray(new String[0]);
			for(int count = 0; count < startedQuestArrays.length; count++)
			{
				questName = startedQuestArrays[count];
				flow = playerQuestYaml.getInt("Started."+questName+".Flow");
				if(playerQuestYaml.getString("Started."+questName+".Type").equalsIgnoreCase("Hunt"))
				{
					if( ! questYaml.contains(questName))
					{
						playerQuestYaml.removeKey("Started."+questName);
						playerQuestYaml.saveConfig();
						return;
					}
					monsterListSize = questYaml.getConfigurationSection(questName+".FlowChart."+flow+".Monster").getKeys(false).size();
					finish = 0;
					for(int counter = 0; counter < monsterListSize; counter++)
					{
						questMonsterName = questYaml.getString(questName+".FlowChart."+flow+".Monster."+counter+".MonsterName");
						huntAmount = questYaml.getInt(questName+".FlowChart."+flow+".Monster."+counter+".Amount");
						killedName = entity.getName();
						if(entity.isCustomNameVisible())
						{
							killedName = entity.getCustomName();
							if(entity.getLocation().getWorld().getName().equals("Dungeon"))
							{
								if(killedName.length() >= 6)
								{
									if(killedName.charAt(0)=='¡×'&&killedName.charAt(1)=='2'&&
										killedName.charAt(2)=='¡×'&&killedName.charAt(3)=='0'&&
										killedName.charAt(4)=='¡×'&&killedName.charAt(5)=='2')
									{
										killedName = killedName.substring(12, killedName.length());
									}
								}
							}
						}
						if(questMonsterName.equalsIgnoreCase(killedName) && huntAmount > playerQuestYaml.getInt("Started."+questName+".Hunt."+counter))
						{
							//Äù½ºÆ® ÁøÇàµµ ¾Ë¸²//
							playerQuestYaml.set("Started."+questName+".Hunt."+counter, playerQuestYaml.getInt("Started."+questName+".Hunt."+counter)+1);
							playerQuestYaml.saveConfig();
						}
						if(huntAmount == playerQuestYaml.getInt("Started."+questName+".Hunt."+counter))
						{
							finish++;
						}
						if(finish == monsterListSize)
						{
							playerQuestYaml.set("Started."+questName+".Type",questYaml.getString(questName+".FlowChart."+(playerQuestYaml.getInt("Started."+questName+".Flow")+1)+".Type"));
							playerQuestYaml.set("Started."+questName+".Flow",playerQuestYaml.getInt("Started."+questName+".Flow")+1);
							playerQuestYaml.removeKey("Started."+questName+".Hunt");
							playerQuestYaml.saveConfig();
							qGui.QuestRouter(targetPlayers.get(playerCount), questName);
							//Äù½ºÆ® ¿Ï·á ¸Þ½ÃÁö//
							break;
						}
					}
				}
			}
		}
		return;
	}
}