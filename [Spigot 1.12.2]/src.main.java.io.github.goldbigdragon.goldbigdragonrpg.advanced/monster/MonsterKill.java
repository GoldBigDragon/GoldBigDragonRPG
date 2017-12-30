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
	
	public void Boomb(Entity entity)
	{
		String EntityName = getRealName(entity);
		int MonsterINT = 10;
		int radius = 5;
		if(main.MainServerOption.MonsterList.containsKey(EntityName))
			MonsterINT = main.MainServerOption.MonsterList.get(EntityName).getINT();
		else
		{
			if(entity.getType()==EntityType.CREEPER)
			{
				Creeper c = (Creeper)entity;
				if(c.isPowered())
					MonsterINT = 40;
				else
					MonsterINT = 20;
			}
			else if(entity.getType() == EntityType.ENDER_CRYSTAL)
				MonsterINT = 200;
			else if(entity.getType() == EntityType.PRIMED_TNT || entity.getType() == EntityType.MINECART_TNT)
				MonsterINT = 90;
		}
		int MinPower = (int)(MonsterINT/4);
		int MaxPower = (int)(MonsterINT/2.5);
		
		int Power = new Random().nextInt((int) (MaxPower-MinPower+1))+MinPower;
		radius = (int)((Power/3)*2);
		if(radius < 3)
			radius = 3;
		else if(radius > 8)
			radius = 8;

		entity.getLocation().getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_LARGE, 0);
		entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE ,1.5F, 1.0F);
		Iterator<Entity> e = Bukkit.getWorld("Dungeon").getNearbyEntities(entity.getLocation(), radius, radius, radius).iterator();
	    while(e.hasNext())
	    {
			int Temp = Power;
	        Entity Choosedentity = e.next();
	        if(Choosedentity!=null)
	        {
				String name = Choosedentity.getCustomName();
				if(ChatColor.stripColor(name) == null)
					name = null;
				else if(ChatColor.stripColor(name).length() == 0)
					name = null;
		        if(Choosedentity.isDead()==false)
		        {
			        int DEF = 0;
			        int PRO = 0;
			        
			        if(Choosedentity.getType()==EntityType.PLAYER)
			        {
			        	Player p = (Player) Choosedentity;
			        	if(p.getGameMode()==GameMode.SURVIVAL||p.getGameMode()==GameMode.ADVENTURE)
			        	{
			        		if(p.isOnline())
			        		{
			        			DEF = main.MainServerOption.PlayerList.get(p.getUniqueId().toString()).getStat_DEF();
			        			PRO = main.MainServerOption.PlayerList.get(p.getUniqueId().toString()).getStat_Protect();
			        		}
			        		else if(name!=null)
			        		{
								name = getRealName(Choosedentity);
			        			DEF = main.MainServerOption.MonsterList.get(name).getDEF();
			        			PRO = main.MainServerOption.MonsterList.get(name).getPRO();
			        		}
			        	}
			        }
			        else if(name!=null)
			        {
						name = getRealName(Choosedentity);
						DEF = main.MainServerOption.MonsterList.get(name).getDEF();
	        			PRO = main.MainServerOption.MonsterList.get(name).getPRO();
	        		}

					if(Power >= 100)
						Temp =(int)(Power*(100-PRO)/100);
					else if(Power >= 10)
						Temp =(int)(Power*((100-PRO)/10)/10);
					else
						Temp =(int)(Power-PRO);
					Temp = Temp-DEF;
					if(Choosedentity.getType()!=EntityType.DROPPED_ITEM&&Choosedentity.getType()!=EntityType.ARMOR_STAND&&
						Choosedentity.getType()!=EntityType.ARROW&&Choosedentity.getType()!=EntityType.BOAT&&
						Choosedentity.getType()!=EntityType.EGG&&Choosedentity.getType()!=EntityType.ENDER_PEARL&&
						Choosedentity.getType()!=EntityType.ENDER_SIGNAL&&Choosedentity.getType()!=EntityType.EXPERIENCE_ORB&&
						Choosedentity.getType()!=EntityType.FALLING_BLOCK&&Choosedentity.getType()!=EntityType.FIREBALL&&
						Choosedentity.getType()!=EntityType.FIREWORK&&Choosedentity.getType()!=EntityType.FISHING_HOOK&&
						Choosedentity.getType()!=EntityType.ITEM_FRAME&&Choosedentity.getType()!=EntityType.LEASH_HITCH&&
						Choosedentity.getType()!=EntityType.LIGHTNING&&Choosedentity.getType()!=EntityType.PAINTING&&
						Choosedentity.getType()!=EntityType.PRIMED_TNT&&Choosedentity.getType()!=EntityType.SMALL_FIREBALL&&
						Choosedentity.getType()!=EntityType.SNOWBALL&&Choosedentity.getType()!=EntityType.SPLASH_POTION&&
						Choosedentity.getType()!=EntityType.THROWN_EXP_BOTTLE&&Choosedentity.getType()!=EntityType.UNKNOWN&&
						Choosedentity.getType()!=EntityType.WITHER_SKULL)
					{
						if(Choosedentity != entity)
							if(!Choosedentity.isDead())
							{
								LivingEntity LE = (LivingEntity) Choosedentity;
								LE.damage(Temp, entity);
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

	public void DungeonKilled(LivingEntity entity, boolean isBoomed)
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
						if(SearchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new dungeon.DungeonMain().dungeonTrapDoorWorker(loc, false);
						break;
					case '1' : //´ÙÀ½ ¿þÀÌºê Á¸Àç
						if(SearchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new dungeon.DungeonMain().monsterSpawn(loc);
						break;
					case '4' : //¿­¼è °¡Áø ³ð
						if(SearchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
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
									int BossCount = dungeonYaml.getConfigurationSection("Boss").getKeys(false).size();
									ArrayList<String> BossList = new ArrayList<String>();
									boolean isChecked = false;
									for(int count = 0; count < BossCount; count++)
									{
										if(!isChecked&&dungeonYaml.getString("Boss."+count).equals(name))
											isChecked = true;
										else
											BossList.add(dungeonYaml.getString("Boss."+count));
									}
									dungeonYaml.removeKey("Boss");
									dungeonYaml.saveConfig();
									if(BossList.isEmpty() == false)
									{
							    		for(int count = 0; count < BossList.size(); count++)
							    			dungeonYaml.set("Boss."+count, BossList.get(count));
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
	
	public void MonsterKilling(EntityDeathEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().equals("Dungeon"))
			DungeonKilled(event.getEntity(), false);
    	if(event.getEntity()!=null && event.getEntity().getKiller() != null)
    	{
    		if(event.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK  || event.getEntity().getLastDamageCause().getCause() == DamageCause.PROJECTILE
    				|| event.getEntity().getLastDamageCause().getCause() == DamageCause.MAGIC)
    		{
				if(Bukkit.getServer().getPlayer(event.getEntity().getKiller().getName()).isOnline())
				{
					Player player = (Player) Bukkit.getServer().getPlayer(event.getEntity().getKiller().getName());
					if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth())
					    new SendPacket().sendTitle(player, "¡×0¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á", "¡×4¡×l[DEAD]", 0, 0, 1);
    				Reward(event,player);
    				Quest(event, player);
					return;
				}
			}
		}
    	return;
	}

	public byte SearchRoomMonster(byte searchSize, char Group, Location loc)
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
					if(e.get(i).isDead() == false)
					{
						if(!name.equals("øïÞÝ"))
						{
							if(name.charAt(0)=='¡×'&&name.charAt(1)=='2'&&
								name.charAt(2)=='¡×'&&name.charAt(3)=='0'&&
								name.charAt(4)=='¡×'&&name.charAt(5)=='2')
							{
								if(name.charAt(9)==Group)
									mobs++;
							}
						}
					}
				}
			}
		}
		return mobs;
	}
	
	
	public void Reward(EntityDeathEvent event, Player player)
	{
		util.UtilNumber N = new util.UtilNumber();
		byte amount = 1;
		if(40 <= N.RandomNum(0, 100) * MainServerOption.eventDropChance)
		{
			int lucky = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
			if(lucky >= 10) lucky =10;
			if(lucky <= 0) lucky = 1;
			if(lucky >= N.RandomNum(0, 100))
			{
				int luckysize = N.RandomNum(0, 100);
				if(luckysize <= 80){player.sendMessage("¡×e¡×l[SYSTEM] : ·°Å° ÇÇ´Ï½Ã!");amount = 2;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);}
				else if(luckysize <= 95){player.sendMessage("¡×e¡×l[SYSTEM] : ºò ·°Å° ÇÇ´Ï½Ã!");amount = 5;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);}
				else{player.sendMessage("¡×e¡×l[SYSTEM] : ÈÞÁî ·°Å° ÇÇ´Ï½Ã!");amount = 20;	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);}
			}
		}
		else
			amount = 0;
		String name = getRealName(event.getEntity());
		if(main.MainServerOption.MonsterList.containsKey(name))
		{
			if(amount == 0)
				new util.UtilPlayer().addMoneyAndEXP(player, 0, main.MainServerOption.MonsterList.get(name).getEXP(), event.getEntity().getLocation(), true, false);
			else
				new util.UtilPlayer().addMoneyAndEXP(player, amount* N.RandomNum(main.MainServerOption.MonsterList.get(name).getMinMoney(), main.MainServerOption.MonsterList.get(name).getMaxMoney()), main.MainServerOption.MonsterList.get(name).getEXP(), event.getEntity().getLocation(), true, false);
			return;
		}
		else
		{
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			EntityType ET = event.getEntityType();
			if(ET == EntityType.SKELETON)
			{
				Skeleton s = (Skeleton)event.getEntity();
				new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.SKELETON.MIN_MONEY"), configYaml.getInt("Normal_Monster.SKELETON.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SKELETON.EXP"), event.getEntity().getLocation(), true, false);
			}
			else if(ET == EntityType.CREEPER)
			{
				Creeper c = (Creeper)event.getEntity();
				if(!c.isPowered())
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.CREEPER.MIN_MONEY"), configYaml.getInt("Normal_Monster.CREEPER.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.CREEPER.EXP"), event.getEntity().getLocation(), true, false);
				else
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.CHARGED_CREEPER.MIN_MONEY"), configYaml.getInt("Normal_Monster.CHARGED_CREEPER.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.CHARGED_CREEPER.EXP"), event.getEntity().getLocation(), true, false);
			}
			else if(ET == EntityType.SLIME)
			{
				Slime sl = (Slime)event.getEntity();
				if(sl.getSize() == 1)
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.SLIME_SMALL.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_SMALL.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_SMALL.EXP"), event.getEntity().getLocation(), true, false);
				else if(sl.getSize() <= 3)
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.SLIME_MIDDLE.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_MIDDLE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_MIDDLE.EXP"), event.getEntity().getLocation(), true, false);
				else if(sl.getSize() == 4)
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.SLIME_BIG.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_BIG.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_BIG.EXP"), event.getEntity().getLocation(), true, false);
				else
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.SLIME_HUGE.MIN_MONEY"), configYaml.getInt("Normal_Monster.SLIME_HUGE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.SLIME_HUGE.EXP"), event.getEntity().getLocation(), true, false);
			}
			else if(ET == EntityType.MAGMA_CUBE)
			{
				MagmaCube ma = (MagmaCube)event.getEntity();
				if(ma.getSize() == 1)
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_SMALL.EXP"), event.getEntity().getLocation(), true, false);
				else if(ma.getSize() <= 3)
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP"), event.getEntity().getLocation(), true, false);
				else if(ma.getSize() == 4)
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_BIG.EXP"), event.getEntity().getLocation(), true, false);
				else
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY"), configYaml.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster.MAGMA_CUBE_HUGE.EXP"), event.getEntity().getLocation(), true, false);
			}
			else
			{
				if(configYaml.contains("Normal_Monster."+ET.toString()))
					new util.UtilPlayer().addMoneyAndEXP(player, N.RandomNum(configYaml.getInt("Normal_Monster."+ET.toString()+".MIN_MONEY"), configYaml.getInt("Normal_Monster."+ET.toString()+".MAX_MONEY"))*amount, configYaml.getLong("Normal_Monster."+ET.toString()+".EXP"), event.getEntity().getLocation(), true, false);
			}
		}
		return;
	}

	public void Quest(EntityDeathEvent event, Player player)
	{
		YamlLoader questYaml = new YamlLoader();
		questYaml.getConfig("Quest/QuestList.yml");
		YamlLoader playerQuestListYaml = new YamlLoader();
		playerQuestListYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(MainServerOption.partyJoiner.containsKey(player)==false)
		{
			Object[] a = playerQuestListYaml.getConfigurationSection("Started.").getKeys(false).toArray();
			for(int count = 0; count < a.length; count++)
			{
				String QuestName = (String) a[count];
				short Flow = (short) playerQuestListYaml.getInt("Started."+QuestName+".Flow");
				if(playerQuestListYaml.getString("Started."+QuestName+".Type").equalsIgnoreCase("Hunt"))
				{
					if(questYaml.contains(QuestName)==false)
					{
						playerQuestListYaml.removeKey("Started."+QuestName);
						playerQuestListYaml.saveConfig();
						return;
					}
					Object[] MobList = questYaml.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
					int Finish = 0;
					for(int counter = 0; counter < MobList.length; counter++)
					{
						String QMobName = questYaml.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
						int MAX = questYaml.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
						String KilledName = "null";
						KilledName = event.getEntity().getName();
						if(event.getEntity().isCustomNameVisible() == true)
						{
							KilledName = event.getEntity().getCustomName();
							if(event.getEntity().getLocation().getWorld().getName().equals("Dungeon"))
							{
								if(KilledName.length() >= 6)
								{
									if(KilledName.charAt(0)=='¡×'&&KilledName.charAt(1)=='2'&&
										KilledName.charAt(2)=='¡×'&&KilledName.charAt(3)=='0'&&
										KilledName.charAt(4)=='¡×'&&KilledName.charAt(5)=='2')
									{
										KilledName = KilledName.substring(12, KilledName.length());
									}
								}
							}
						}
						if(QMobName.equalsIgnoreCase(KilledName) == true && MAX > playerQuestListYaml.getInt("Started."+QuestName+".Hunt."+counter))
						{
							//Äù½ºÆ® ÁøÇàµµ ¾Ë¸²//
							playerQuestListYaml.set("Started."+QuestName+".Hunt."+counter, playerQuestListYaml.getInt("Started."+QuestName+".Hunt."+counter)+1);
							playerQuestListYaml.saveConfig();
						}
						if(MAX == playerQuestListYaml.getInt("Started."+QuestName+".Hunt."+counter))
						{
							Finish++;
						}
						if(Finish == MobList.length)
						{
							playerQuestListYaml.set("Started."+QuestName+".Type",questYaml.getString(QuestName+".FlowChart."+(playerQuestListYaml.getInt("Started."+QuestName+".Flow")+1)+".Type"));
							playerQuestListYaml.set("Started."+QuestName+".Flow",playerQuestListYaml.getInt("Started."+QuestName+".Flow")+1);
							playerQuestListYaml.removeKey("Started."+QuestName+".Hunt");
							playerQuestListYaml.saveConfig();
							quest.QuestGui QGUI = new quest.QuestGui();
							QGUI.QuestRouter(player, QuestName);
							//Äù½ºÆ® ¿Ï·á ¸Þ½ÃÁö//
							break;
						}
					}
				}
			}
		}
		else
		{
			Player[] PartyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			int expShareDistance = configYaml.getInt("Party.EXPShareDistance");
			for(int counta = 0; counta < PartyMember.length; counta++)
			{
				player = PartyMember[counta];
				if(event.getEntity().getLocation().getWorld() == player.getLocation().getWorld())
				{
					if(event.getEntity().getLocation().distance(player.getLocation()) <= expShareDistance)
					{
						playerQuestListYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						
						Object[] a = playerQuestListYaml.getConfigurationSection("Started.").getKeys(false).toArray();
						for(int count = 0; count < a.length; count++)
						{
							String QuestName = (String) a[count];
							short Flow = (short) playerQuestListYaml.getInt("Started."+QuestName+".Flow");
							if(playerQuestListYaml.getString("Started."+QuestName+".Type").equalsIgnoreCase("Hunt"))
							{
								Object[] MobList = questYaml.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
								int Finish = 0;
								for(int counter = 0; counter < MobList.length; counter++)
								{
									String QMobName = questYaml.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
									int MAX = questYaml.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
									String KilledName = "null";
									KilledName = event.getEntity().getName();
									if(event.getEntity().isCustomNameVisible() == true)
									{
										KilledName = event.getEntity().getCustomName();
										if(event.getEntity().getLocation().getWorld().getName().equals("Dungeon"))
										{
											if(KilledName.length() >= 6)
											{
												if(KilledName.charAt(0)=='¡×'&&KilledName.charAt(1)=='2'&&
													KilledName.charAt(2)=='¡×'&&KilledName.charAt(3)=='0'&&
													KilledName.charAt(4)=='¡×'&&KilledName.charAt(5)=='2')
												{
													KilledName = KilledName.substring(12, KilledName.length());
												}
											}
										}
									}
									if(QMobName.equalsIgnoreCase(KilledName) == true && MAX > playerQuestListYaml.getInt("Started."+QuestName+".Hunt."+counter))
									{
										//Äù½ºÆ® ÁøÇàµµ ¾Ë¸²//
										playerQuestListYaml.set("Started."+QuestName+".Hunt."+counter, playerQuestListYaml.getInt("Started."+QuestName+".Hunt."+counter)+1);
										playerQuestListYaml.saveConfig();
									}
									if(MAX == playerQuestListYaml.getInt("Started."+QuestName+".Hunt."+counter))
									{
										Finish = Finish+1;
									}
									if(Finish == MobList.length)
									{
										playerQuestListYaml.set("Started."+QuestName+".Type",questYaml.getString(QuestName+".FlowChart."+(playerQuestListYaml.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										playerQuestListYaml.set("Started."+QuestName+".Flow",playerQuestListYaml.getInt("Started."+QuestName+".Flow")+1);
										playerQuestListYaml.removeKey("Started."+QuestName+".Hunt");
										playerQuestListYaml.saveConfig();
										quest.QuestGui QGUI = new quest.QuestGui();
										QGUI.QuestRouter(player, QuestName);
										//Äù½ºÆ® ¿Ï·á ¸Þ½ÃÁö//
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
}