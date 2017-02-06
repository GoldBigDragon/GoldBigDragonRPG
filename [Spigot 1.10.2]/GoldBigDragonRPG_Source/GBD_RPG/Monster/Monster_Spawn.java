package GBD_RPG.Monster;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftZombie;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

import GBD_RPG.Monster.AI_Creature.CreatureBlaze;
import GBD_RPG.Monster.AI_Creature.CreatureCaveSpider;
import GBD_RPG.Monster.AI_Creature.CreatureCreeper;
import GBD_RPG.Monster.AI_Creature.CreatureEnderMite;
import GBD_RPG.Monster.AI_Creature.CreatureEnderman;
import GBD_RPG.Monster.AI_Creature.CreatureGiant;
import GBD_RPG.Monster.AI_Creature.CreatureGuardian;
import GBD_RPG.Monster.AI_Creature.CreatureMonster;
import GBD_RPG.Monster.AI_Creature.CreaturePigZombie;
import GBD_RPG.Monster.AI_Creature.CreaturePolarBear;
import GBD_RPG.Monster.AI_Creature.CreatureSilverFish;
import GBD_RPG.Monster.AI_Creature.CreatureSkeleton;
import GBD_RPG.Monster.AI_Creature.CreatureSpider;
import GBD_RPG.Monster.AI_Creature.CreatureWitch;
import GBD_RPG.Monster.AI_Creature.CreatureWither;
import GBD_RPG.Monster.AI_Creature.CreatureZombie;
import GBD_RPG.Monster.AI_Monster.NMSUtils;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Monster_Spawn
{
	public void EntitySpawn(CreatureSpawnEvent event)
	{
		if(event.getLocation().getWorld().getName().compareTo("Dungeon") == 0)
		{
			if(event.getSpawnReason()== SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.CHUNK_GEN
					|| event.getSpawnReason() == SpawnReason.MOUNT|| event.getSpawnReason() == SpawnReason.JOCKEY
					|| event.getSpawnReason() == SpawnReason.SLIME_SPLIT)
			{
				event.setCancelled(true);
				return;
			}
		}
		if(event.getEntity().getType()==EntityType.ARMOR_STAND)
			return;
		if(event.getSpawnReason() == SpawnReason.SLIME_SPLIT)
		{
			if(event.getEntity().getCustomName()!=null)
			{
				if(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.containsKey(event.getEntity().getCustomName()))
				{
					event.setCancelled(true);
				}
			}
		}
		GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
		String[] Area = A.getAreaName(event.getEntity());
		
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		if(Area != null)
		{
			if(A.getAreaOption(Area[0], (char) 3) == false)
			{
				event.setCancelled(true);
				return;
			}
			else if(A.getAreaOption(Area[0], (char) 8) == false)
			{
				YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
				String AreaName = A.getAreaName(event.getEntity())[0];
				Object[] MobNameList = AreaList.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
				boolean isExit = false;
				for(byte count = 0;count<10;count++)
				{
					if(isExit==true) break;
					if(MobNameList.length != 0)
					{
						short RandomMob = (short) new GBD_RPG.Util.Util_Number().RandomNum(0, MobNameList.length-1);
						if(GBD_RPG.Main_Main.Main_ServerOption.MonsterList.containsKey(MobNameList[RandomMob].toString()))
						{
							new GBD_RPG.Monster.Monster_Spawn().SpawnMob(event.getLocation(), MobNameList[RandomMob].toString(), (byte) -1, null,(char) -1, false);
							isExit = true;
						}
						else
						{
							AreaList.removeKey(AreaName+".Monster."+MobNameList[RandomMob]);
							AreaList.saveConfig();
						}
					}
					else
						break;
				}
			}
		}
	    YamlManager Config = YC.getNewConfig("config.yml");
		new GBD_RPG.Monster.Monster_Spawn().SpawnEffect(event.getEntity(),event.getLocation(), (byte) Config.getInt("Server.MonsterSpawnEffect"));
		return;
	}
	public void CreateMonster(String MonsterName)
	{
		ItemStack Item = new ItemStack(Material.AIR);
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Monster  = YC.getNewConfig("Monster/MonsterList.yml");
		
		Monster.set(MonsterName+".Name", MonsterName);
		Monster.set(MonsterName+".Type", "좀비");
		Monster.set(MonsterName+".AI", "일반 행동");
		Monster.set(MonsterName+".EXP", 15);
		Monster.set(MonsterName+".Biome", "NULL");
		Monster.set(MonsterName+".HP", 20);
		Monster.set(MonsterName+".MIN_Money", 1);
		Monster.set(MonsterName+".MAX_Money", 10);
		Monster.set(MonsterName+".STR", 10);
		Monster.set(MonsterName+".DEX", 10);
		Monster.set(MonsterName+".INT", 10);
		Monster.set(MonsterName+".WILL", 10);
		Monster.set(MonsterName+".LUK", 10);
		Monster.set(MonsterName+".DEF", 0);
		Monster.set(MonsterName+".Protect", 0);
		Monster.set(MonsterName+".Magic_DEF", 0);
		Monster.set(MonsterName+".Magic_Protect", 0);
		Monster.set(MonsterName+".Head.DropChance", 0);
		Monster.set(MonsterName+".Head.Item", Item);
		Monster.set(MonsterName+".Chest.DropChance", 0);
		Monster.set(MonsterName+".Chest.Item", Item);
		Monster.set(MonsterName+".Leggings.DropChance", 0);
		Monster.set(MonsterName+".Leggings.Item", Item);
		Monster.set(MonsterName+".Boots.DropChance", 0);
		Monster.set(MonsterName+".Boots.Item", Item);
		Monster.set(MonsterName+".Hand.DropChance", 0);
		Monster.set(MonsterName+".Hand.Item", Item);
		Monster.set(MonsterName+".OffHand.DropChance", 0);
		Monster.set(MonsterName+".OffHand.Item", Item);
		Monster.saveConfig();
		return;
	}

	public void StayLive(Entity e, boolean isStayLive)
	{
		if(e.getType()!=EntityType.ENDER_CRYSTAL)
			if(e.isDead()==false)
			{
				LivingEntity LE = (LivingEntity) e;
				LE.setRemoveWhenFarAway(isStayLive);
			}
	}
	
	public void SpawnMob(Location loc, String mob, byte DungeonSpawning, int[] XYZLoc, char Group, boolean isStayLive)
	{
		if(mob.charAt(0)=='놂')
		{
			String Type = mob.substring(1);
			mob = null;
			CreateCreature(Type, loc, mob, DungeonSpawning, XYZLoc, Group, isStayLive);
		}
		loc.add(0.5, 1, 0.5);
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Monster  = YC.getNewConfig("Monster/MonsterList.yml");
		String Type = Monster.getString(mob + ".Type");
		if(Monster.getString(mob+".Name")!=null)
		{
			String AI = Monster.getString(mob + ".AI");
			if(!(AI.compareTo("선공")==0||AI.compareTo("비선공")==0||AI.compareTo("무뇌아")==0||AI.compareTo("동물")==0||AI.compareTo("일반 행동")==0))
			{
				Monster.set(mob + ".AI", "일반 행동");
				Monster.saveConfig();
			}
			if(AI.compareTo("일반 행동")!=0)
			{
				String CustomMobName = "CUSTOM"+mob;
				int CustomMobID = getMonsterID(Monster.getString(mob + ".Type"));
				LivingEntity E = null;
				if(Type.compareTo("좀비")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureZombie.class);
		  			E = (CraftZombie) CreatureZombie.spawn(loc, AI);
				}
				else if(Type.compareTo("자이언트")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureGiant.class);
		  			E = (Giant) CreatureGiant.spawn(loc, AI);
				}
				else if(Type.compareTo("스켈레톤")==0||Type.compareTo("네더스켈레톤")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureSkeleton.class);
		  			E = (Skeleton) CreatureSkeleton.spawn(loc, AI);
				}
				else if(Type.compareTo("엔더맨")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureEnderman.class);
		  			E = (Enderman) CreatureEnderman.spawn(loc, AI);
				}
				else if(Type.compareTo("크리퍼")==0||Type.compareTo("번개크리퍼")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureCreeper.class);
		  			E = (Creeper) CreatureCreeper.spawn(loc, AI);
				}
				else if(Type.compareTo("거미")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureSpider.class);
		  			E = (Spider) CreatureSpider.spawn(loc, AI);
				}
				else if(Type.compareTo("동굴거미")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureCaveSpider.class);
		  			E = (CaveSpider) CreatureCaveSpider.spawn(loc, AI);
				}
				else if(Type.compareTo("좀벌레")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureSilverFish.class);
		  			E = (LivingEntity) CreatureSilverFish.spawn(loc, AI);
				}
				else if(Type.compareTo("엔더진드기")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureEnderMite.class);
		  			E = (LivingEntity) CreatureEnderMite.spawn(loc, AI);
				}
				else if(Type.compareTo("블레이즈")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureBlaze.class);
		  			E = (Blaze) CreatureBlaze.spawn(loc, AI);
				}
				else if(Type.compareTo("좀비피그맨")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreaturePigZombie.class);
		  			E = (PigZombie) CreaturePigZombie.spawn(loc, AI);
				}
				else if(Type.compareTo("마녀")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureWitch.class);
		  			E = (Witch) CreatureWitch.spawn(loc, AI);
				}
				else if(Type.compareTo("위더")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureWither.class);
		  			E = (Wither) CreatureWither.spawn(loc, AI);
				}
				else if(Type.compareTo("수호자")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureGuardian.class);
		  			E = (Guardian) CreatureGuardian.spawn(loc, AI);
				}
				else if(Type.compareTo("북극곰")==0)
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreaturePolarBear.class);
		  			E = (PolarBear) CreaturePolarBear.spawn(loc, AI);
				}
				else if(Type.compareTo("엔더크리스탈")==0)
					CreateCreature(Type, loc, mob, DungeonSpawning, XYZLoc, Group, isStayLive);
				else
				{
		  			new NMSUtils().registerEntity(CustomMobName, CustomMobID+ (Byte.MAX_VALUE + 1) * 2, CreatureMonster.class);
		  			E = (Monster) CreatureMonster.spawn(loc, AI);
				}
				if(E!=null)
					E = getEntity(E, mob, DungeonSpawning, XYZLoc, Group);
			}
			else
				CreateCreature(Type, loc, mob, DungeonSpawning, XYZLoc, Group, isStayLive);
		}
		return;
	}
	
	private void CreateCreature(String Type, Location loc, String mob, byte DungeonSpawning, int[] XYZLoc, char Group, boolean isStayLive)
	{
		switch(Type)
		{
			case "좀비":{Zombie zombie = (Zombie) loc.getWorld().spawn(loc, Zombie.class);zombie = (Zombie) getEntity(zombie,mob, DungeonSpawning, XYZLoc, Group);}break;
			case "자이언트":{Giant giant = (Giant) loc.getWorld().spawn(loc, Giant.class);giant = (Giant) getEntity(giant,mob, DungeonSpawning, XYZLoc, Group);StayLive(giant, isStayLive);}break;
			case "스켈레톤": case "네더스켈레톤":{Skeleton skeleton = (Skeleton) loc.getWorld().spawn(loc, Skeleton.class);skeleton = (Skeleton) getEntity(skeleton,mob, DungeonSpawning, XYZLoc, Group);StayLive(skeleton, isStayLive);}break;
			case "엔더맨":{Enderman enderman = (Enderman) loc.getWorld().spawn(loc, Enderman.class);enderman = (Enderman) getEntity(enderman,mob, DungeonSpawning, XYZLoc, Group);StayLive(enderman, isStayLive);}break;
			case "크리퍼":{Creeper creeper = (Creeper) loc.getWorld().spawn(loc, Creeper.class);creeper = (Creeper) getEntity(creeper,mob, DungeonSpawning, XYZLoc, Group);StayLive(creeper, isStayLive);}break;
			case "번개크리퍼":{Creeper Lcreeper = (Creeper) loc.getWorld().spawn(loc, Creeper.class);Lcreeper = (Creeper) getEntity(Lcreeper,mob, DungeonSpawning, XYZLoc, Group);StayLive(Lcreeper, isStayLive);}break;
			case "거미":{Spider spider = (Spider) loc.getWorld().spawn(loc, Spider.class);spider = (Spider) getEntity(spider,mob, DungeonSpawning, XYZLoc, Group);StayLive(spider, isStayLive);}break;
			case "동굴거미":{CaveSpider cavespider = (CaveSpider) loc.getWorld().spawn(loc, CaveSpider.class);cavespider = (CaveSpider) getEntity(cavespider,mob, DungeonSpawning, XYZLoc, Group);StayLive(cavespider, isStayLive);}break;
			case "좀벌레":{Silverfish silverfish = (Silverfish) loc.getWorld().spawn(loc, Silverfish.class);silverfish = (Silverfish) getEntity(silverfish,mob, DungeonSpawning, XYZLoc, Group);StayLive(silverfish, isStayLive);}break;
			case "엔더진드기":{Endermite endermite = (Endermite) loc.getWorld().spawn(loc, Endermite.class);endermite = (Endermite) getEntity(endermite,mob, DungeonSpawning, XYZLoc, Group);StayLive(endermite, isStayLive);}break;
			case "슬라임":case "초대형슬라임": case "특대슬라임": case "큰슬라임": case "보통슬라임": case "작은슬라임":{Slime Sslime = (Slime) loc.getWorld().spawn(loc, Slime.class);Sslime = (Slime) getEntity(Sslime,mob, DungeonSpawning, XYZLoc, Group);StayLive(Sslime, isStayLive);}break;
			case "마그마큐브": case "초대형마그마큐브":case "특대마그마큐브": case "큰마그마큐브": case "보통마그마큐브": case "작은마그마큐브":{MagmaCube Smagmacube = (MagmaCube) loc.getWorld().spawn(loc, MagmaCube.class);Smagmacube = (MagmaCube) getEntity(Smagmacube,mob, DungeonSpawning, XYZLoc, Group);StayLive(Smagmacube, isStayLive);}break;
			case "블레이즈":{Blaze blaze = (Blaze) loc.getWorld().spawn(loc, Blaze.class);blaze = (Blaze) getEntity(blaze,mob, DungeonSpawning, XYZLoc, Group);StayLive(blaze, isStayLive);}break;
			case "가스트":{Ghast ghast = (Ghast) loc.getWorld().spawn(loc, Ghast.class);ghast = (Ghast) getEntity(ghast,mob, DungeonSpawning, XYZLoc, Group);StayLive(ghast, isStayLive);}break;
			case "좀비피그맨":{PigZombie pigzombie = (PigZombie) loc.getWorld().spawn(loc, PigZombie.class);pigzombie = (PigZombie) getEntity(pigzombie,mob, DungeonSpawning, XYZLoc, Group);StayLive(pigzombie, isStayLive);}break;
			case "마녀":{Witch witch = (Witch) loc.getWorld().spawn(loc, Witch.class);witch = (Witch) getEntity(witch,mob, DungeonSpawning, XYZLoc, Group);StayLive(witch, isStayLive);}break;
			case "위더":{Wither wither = (Wither) loc.getWorld().spawn(loc, Wither.class);wither = (Wither) getEntity(wither,mob, DungeonSpawning, XYZLoc, Group);StayLive(wither, isStayLive);}break;
			case "엔더드래곤":{EnderDragon ED = (EnderDragon) loc.getWorld().spawn(loc, EnderDragon.class);ED = (EnderDragon) getEntity(ED,mob, DungeonSpawning, XYZLoc, Group);StayLive(ED, isStayLive);}break;
			case "엔더크리스탈":{EnderCrystal EC = (EnderCrystal) loc.getWorld().spawn(loc, EnderCrystal.class);EC = getEnderCrystal(EC,mob, DungeonSpawning, XYZLoc, Group);StayLive(EC, isStayLive);}break;
			case "수호자":{Guardian guardian = (Guardian) loc.getWorld().spawn(loc, Guardian.class);guardian = (Guardian) getEntity(guardian,mob, DungeonSpawning, XYZLoc, Group);StayLive(guardian, isStayLive);}break;
			case "양":{Sheep sheep = (Sheep) loc.getWorld().spawn(loc, Sheep.class);sheep = (Sheep) getEntity(sheep,mob, DungeonSpawning, XYZLoc, Group);StayLive(sheep, isStayLive);}break;
			case "소":{Cow cow = (Cow) loc.getWorld().spawn(loc, Cow.class);cow = (Cow) getEntity(cow,mob, DungeonSpawning, XYZLoc, Group);StayLive(cow, isStayLive);}break;
			case "돼지":{Pig pig = (Pig) loc.getWorld().spawn(loc, Pig.class);pig = (Pig) getEntity(pig,mob, DungeonSpawning, XYZLoc, Group);StayLive(pig, isStayLive);}break;
			case "말":{Horse horse = (Horse) loc.getWorld().spawn(loc, Horse.class);horse = (Horse) getEntity(horse,mob, DungeonSpawning, XYZLoc, Group);StayLive(horse, isStayLive);}break;
			case "토끼":{Rabbit rabbit = (Rabbit) loc.getWorld().spawn(loc, Rabbit.class);rabbit = (Rabbit) getEntity(rabbit,mob, DungeonSpawning, XYZLoc, Group);StayLive(rabbit, isStayLive);}break;
			case "오셀롯":{Ocelot oceleot = (Ocelot) loc.getWorld().spawn(loc, Ocelot.class);oceleot = (Ocelot) getEntity(oceleot,mob, DungeonSpawning, XYZLoc, Group);StayLive(oceleot, isStayLive);}break;
			case "늑대":{Wolf wolf = (Wolf) loc.getWorld().spawn(loc, Wolf.class);wolf = (Wolf) getEntity(wolf,mob, DungeonSpawning, XYZLoc, Group);StayLive(wolf, isStayLive);}break;
			case "닭":{Chicken chicken = (Chicken) loc.getWorld().spawn(loc, Chicken.class);chicken = (Chicken) getEntity(chicken,mob, DungeonSpawning, XYZLoc, Group);StayLive(chicken, isStayLive);}break;
			case "버섯소":{MushroomCow Mcow = (MushroomCow) loc.getWorld().spawn(loc, MushroomCow.class);Mcow = (MushroomCow) getEntity(Mcow,mob, DungeonSpawning, XYZLoc, Group);StayLive(Mcow, isStayLive);}break;
			case "박쥐":{Bat bat = (Bat) loc.getWorld().spawn(loc, Bat.class);bat = (Bat) getEntity(bat,mob, DungeonSpawning, XYZLoc, Group);StayLive(bat, isStayLive);}break;
			case "오징어":{Squid squid = (Squid) loc.getWorld().spawn(loc, Squid.class);squid = (Squid) getEntity(squid,mob, DungeonSpawning, XYZLoc, Group);StayLive(squid, isStayLive);}break;
			case "주민":{Villager villager = (Villager) loc.getWorld().spawn(loc, Villager.class);villager = (Villager) getEntity(villager,mob, DungeonSpawning, XYZLoc, Group);StayLive(villager, isStayLive);}break;
			case "눈사람":{Snowman snowman = (Snowman) loc.getWorld().spawn(loc, Snowman.class);snowman = (Snowman) getEntity(snowman,mob, DungeonSpawning, XYZLoc, Group);StayLive(snowman, isStayLive);}break;
			case "골렘":{IronGolem golem = (IronGolem) loc.getWorld().spawn(loc, IronGolem.class);golem = (IronGolem) getEntity(golem,mob, DungeonSpawning, XYZLoc, Group);StayLive(golem, isStayLive);}break;
			case "셜커":{Shulker shulker = (Shulker) loc.getWorld().spawn(loc, Shulker.class);shulker = (Shulker) getEntity(shulker,mob, DungeonSpawning, XYZLoc, Group);StayLive(shulker, isStayLive);}break;
			case "북극곰":{PolarBear polarBear = (PolarBear) loc.getWorld().spawn(loc, PolarBear.class);polarBear = (PolarBear) getEntity(polarBear,mob, DungeonSpawning, XYZLoc, Group);StayLive(polarBear, isStayLive);}break;
			//case "휴먼":{HumanEntity human = (HumanEntity) loc.getWorld().spawn(loc, Player.class);human = (HumanEntity) getEntity(human,mob, DungeonSpawning, XYZLoc, Group);StayLive(human, isStayLive);}break;
		}
	}

	private LivingEntity getEntity(LivingEntity Monster, String mob, byte DungeonSpawning, int[] XYZloc, char Group)
	{
		if(mob!=null)
		{
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager MobList  = YC.getNewConfig("Monster/MonsterList.yml");
			Monster.setCustomName(MobList.getString(mob + ".Name").replace("&", "§"));
			Monster.setCustomNameVisible(true);
			ItemStack Equip = MobList.getItemStack(mob+".Head.Item");
			if(Equip == null)
				Monster.getEquipment().setHelmet(null);
			else
			{
				Monster.getEquipment().setHelmet(Equip);
				if(Equip.hasItemMeta()==true)
					if(Equip.getItemMeta().hasLore()==true)
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).compareTo("이곳에 아이템을 넣어 주세요.")==0)
							Monster.getEquipment().setHelmet(null);
			}
			Equip = MobList.getItemStack(mob+".Chest.Item");
			if(Equip == null)
				Monster.getEquipment().setChestplate(null);
			else
			{
				Monster.getEquipment().setChestplate(Equip);
				if(Equip.hasItemMeta()==true)
					if(Equip.getItemMeta().hasLore()==true)
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).compareTo("이곳에 아이템을 넣어 주세요.")==0)
							Monster.getEquipment().setChestplate(null);
			}
			Equip = MobList.getItemStack(mob+".Leggings.Item");
			if(Equip == null)
				Monster.getEquipment().setLeggings(null);
			else
			{
				Monster.getEquipment().setLeggings(Equip);
				if(Equip.hasItemMeta()==true)
					if(Equip.getItemMeta().hasLore()==true)
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).compareTo("이곳에 아이템을 넣어 주세요.")==0)
							Monster.getEquipment().setLeggings(null);
			}
			Equip = MobList.getItemStack(mob+".Boots.Item");
			if(Equip == null)
				Monster.getEquipment().setBoots(null);
			else
			{
				Monster.getEquipment().setBoots(Equip);
				if(Equip.hasItemMeta()==true)
					if(Equip.getItemMeta().hasLore()==true)
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).compareTo("이곳에 아이템을 넣어 주세요.")==0)
							Monster.getEquipment().setBoots(null);
			}
			Equip = MobList.getItemStack(mob+".Hand.Item");
			if(Equip == null)
				Monster.getEquipment().setItemInMainHand(null);
			else
			{
				Monster.getEquipment().setItemInMainHand(Equip);
				if(Equip.hasItemMeta()==true)
					if(Equip.getItemMeta().hasLore()==true)
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).compareTo("이곳에 아이템을 넣어 주세요.")==0)
								Monster.getEquipment().setItemInMainHand(null);
			}
			Equip = MobList.getItemStack(mob+".OffHand.Item");
			if(Equip == null)
				Monster.getEquipment().setItemInOffHand(null);
			else
			{
				Monster.getEquipment().setItemInOffHand(Equip);
				if(Equip.hasItemMeta()==true)
					if(Equip.getItemMeta().hasLore()==true)
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).compareTo("이곳에 아이템을 넣어 주세요.")==0)
								Monster.getEquipment().setItemInOffHand(null);
			}

		    YamlManager Config =  YC.getNewConfig("config.yml");
			Monster.getEquipment().setHelmetDropChance((float)(MobList.getInt(mob+".Head.DropChance")*Config.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setChestplateDropChance((float)(MobList.getInt(mob+".Chest.DropChance")*Config.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setLeggingsDropChance((float)(MobList.getInt(mob+".Leggings.DropChance")*Config.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setBootsDropChance((float)(MobList.getInt(mob+".Boots.DropChance")*Config.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setItemInMainHandDropChance((float)(MobList.getInt(mob+".Hand.DropChance")*Config.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setItemInOffHandDropChance((float)(MobList.getInt(mob+".OffHand.DropChance")*Config.getInt("Event.DropChance")/1000.0));
			
			if(Monster.getType() == EntityType.SKELETON)
			{
				if(MobList.getString(mob+".Type").equalsIgnoreCase("네더스켈레톤") == true)
				{
					((Skeleton) Monster).setSkeletonType(SkeletonType.WITHER);
				}
			}
			else if(Monster.getType() == EntityType.CREEPER)
			{
				if(MobList.getString(mob+".Type").equalsIgnoreCase("번개크리퍼") == true)
				{
					  ((Creeper) Monster).setPowered(true);
				}
			}
			else if(Monster.getType() == EntityType.SLIME)
			{
				switch(MobList.getString(mob + ".Type"))
				{
					case "작은슬라임" : ((Slime) Monster).setSize(1);break;
					case "보통슬라임" : ((Slime) Monster).setSize(2);break;
					case "큰슬라임" : ((Slime) Monster).setSize(4);break;
					case "특대슬라임" : ((Slime) Monster).setSize(16);break;
					case "초대형슬라임" : ((Slime) Monster).setSize(64);break;
					default : ((Slime) Monster).setSize(new GBD_RPG.Util.Util_Number().RandomNum(1, 4));break;
				}
			}
			else if(Monster.getType() == EntityType.MAGMA_CUBE)
			{
				switch(MobList.getString(mob + ".Type"))
				{
					case "작은마그마큐브" : ((MagmaCube) Monster).setSize(1);break;
					case "보통마그마큐브" : ((MagmaCube) Monster).setSize(2);break;
					case "큰마그마큐브" : ((MagmaCube) Monster).setSize(4);break;
					case "특대마그마큐브" : ((MagmaCube) Monster).setSize(16);break;
					case "초대형마그마큐브" : ((MagmaCube) Monster).setSize(64);break;
					default : ((MagmaCube) Monster).setSize(new GBD_RPG.Util.Util_Number().RandomNum(1, 4));break;
				}
			}
			if(MobList.contains(mob+".Potion"))
			{
				GBD_RPG.Effect.Effect_Potion P = new GBD_RPG.Effect.Effect_Potion();
				if(MobList.getInt(mob+".Potion.Regenerate")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.REGENERATION, 50000, MobList.getInt(mob+".Potion.Regenerate")));
				if(MobList.getInt(mob+".Potion.Poison")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.POISON, 50000, MobList.getInt(mob+".Potion.Poison")));
				if(MobList.getInt(mob+".Potion.Speed")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.SPEED, 50000, MobList.getInt(mob+".Potion.Speed")));
				if(MobList.getInt(mob+".Potion.Slow")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.SLOW, 50000, MobList.getInt(mob+".Potion.Slow")));
				if(MobList.getInt(mob+".Potion.Strength")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.INCREASE_DAMAGE, 50000, MobList.getInt(mob+".Potion.Strength")));
				if(MobList.getInt(mob+".Potion.Weak")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.WEAKNESS, 50000, MobList.getInt(mob+".Potion.Weak")));
				if(MobList.getInt(mob+".Potion.JumpBoost")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.JUMP, 50000, MobList.getInt(mob+".Potion.JumpBoost")));
				if(MobList.getInt(mob+".Potion.FireRegistance")!=0)
					Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.FIRE_RESISTANCE, 50000, MobList.getInt(mob+".Potion.FireRegistance")));
				if(MobList.getInt(mob+".Potion.WaterBreath")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.WATER_BREATHING, 50000, MobList.getInt(mob+".Potion.WaterBreath")));
				if(MobList.getInt(mob+".Potion.Invisible")!=0)
				Monster.addPotionEffect(P.getPotionEffect(PotionEffectType.INVISIBILITY, 50000, MobList.getInt(mob+".Potion.Invisible")));
			}
			Damageable m = Monster;
			m.setMaxHealth(MobList.getInt(mob + ".HP")+0.5);
			m.setHealth(m.getMaxHealth());
		}
		
		
		
		if(DungeonSpawning != -1)
		{
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager MobList  = YC.getNewConfig("Monster/MonsterList.yml");
			/*
    		몬스터 식별 코드 이후 오는 GroupNumber 코드는 각각의 몬스터 그룹을 설정하기 위함으로,
    		방 내에 그룹 코드가 붙은 몬스터가 반경 20 이내에 없을 경우, 다음 웨이브가 나오거나
    		문이 열리게 된다. 몬스터 그룹 코드는 0 ~ f 까지 존재한다.
			 */

			if(mob==null)
			{
				switch(DungeonSpawning)
				{
				case 1://1번 == 일반
					Monster.setCustomName("§2§0§2§e§"+Group+"§f");
					break;
				case 2://2번 == 다음 웨이브 존재
					Monster.setCustomName("§2§0§2§1§"+Group+"§f");
					break;
				case 3://3번 == 열쇠 가진 놈
					Monster.setCustomName("§2§0§2§4§"+Group+"§f");
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					Monster.setCustomName("§2§0§2§2§"+Group+"§f");
					break;
				}
			}
			else
			{
				String a = MobList.getString(mob + ".Name").replace("&", "§");
				switch(DungeonSpawning)
				{
				case 1://1번 == 일반
					Monster.setCustomName("§2§0§2§e§"+Group+"§f"+a);
					break;
				case 2://2번 == 다음 웨이브 존재
					Monster.setCustomName("§2§0§2§1§"+Group+"§f"+a);
					break;
				case 3://3번 == 열쇠 가진 놈
					Monster.setCustomName("§2§0§2§4§"+Group+"§f"+a);
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					Monster.setCustomName("§2§0§2§2§"+Group+"§f"+a);
					break;
				}
			}
			ItemStack Equip = MobList.getItemStack(mob+".Head.Item");
			int itemnumber = 30;
			if(Equip!=null && Equip.getType() != Material.AIR)
			{
				if(Equip.hasItemMeta())
				{
					ItemMeta im = Equip.getItemMeta();
					im.setLore(Arrays.asList("xyz:"+XYZloc[0]+","+XYZloc[1]+","+XYZloc[2]));
					Equip.setItemMeta(im);
					Monster.getEquipment().setHelmet(Equip);
				}
				else
				{
					ItemStack Icon = new MaterialData(267, (byte) 0).toItemStack(1);
					ItemMeta Icon_Meta = Icon.getItemMeta();
					Icon_Meta.setLore(Arrays.asList("xyz:"+XYZloc[0]+","+XYZloc[1]+","+XYZloc[2]));
					Equip.setItemMeta(Icon_Meta);
					Monster.getEquipment().setHelmet(Equip);
				}
			}
			else
			{
				ItemStack i = new ItemStack(itemnumber);
				i.setAmount(1);
				ItemMeta im = i.getItemMeta();
				im.setLore(Arrays.asList("xyz:"+XYZloc[0]+","+XYZloc[1]+","+XYZloc[2]));
				i.setItemMeta(im);
				Monster.getEquipment().setHelmet(i);
			}
			Monster.getEquipment().setHelmetDropChance(0.00000000000000000F);
		}
		return Monster;
	}
	
	private EnderCrystal getEnderCrystal(EnderCrystal Monster, String mob, byte DungeonSpawning, int[] XYZLoc, char Group)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager MobList  = YC.getNewConfig("Monster/MonsterList.yml");
		if(DungeonSpawning != -1)
		{
			/*
    		몬스터 식별 코드 이후 오는 GroupNumber 코드는 각각의 몬스터 그룹을 설정하기 위함으로,
    		방 내에 그룹 코드가 붙은 몬스터가 반경 20 이내에 없을 경우, 다음 웨이브가 나오거나
    		문이 열리게 된다. 몬스터 그룹 코드는 0 ~ f 까지 존재한다.
			 */
			if(mob.charAt(0)=='놂')
			{
				switch(DungeonSpawning)
				{
				case 1://1번 == 일반
					Monster.setCustomName("§2§0§2§e§"+Group+"§f");
					break;
				case 2://2번 == 다음 웨이브 존재
					Monster.setCustomName("§2§0§2§1§"+Group+"§f");
					break;
				case 3://3번 == 열쇠 가진 놈
					Monster.setCustomName("§2§0§2§4§"+Group+"§f");
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					Monster.setCustomName("§2§0§2§2§"+Group+"§f");
					break;
				}
			}
			else
			{
				String a = MobList.getString(mob + ".Name").replace("&", "§");
				switch(DungeonSpawning)
				{
				case 1://1번 == 일반
					Monster.setCustomName("§2§0§2§e§"+Group+"§f"+a);
					break;
				case 2://2번 == 다음 웨이브 존재
					Monster.setCustomName("§2§0§2§1§"+Group+"§f"+a);
					break;
				case 3://3번 == 열쇠 가진 놈
					Monster.setCustomName("§2§0§2§4§"+Group+"§f"+a);
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					Monster.setCustomName("§2§0§2§2§"+Group+"§f"+a);
					break;
				}
			}
		}
		else
			Monster.setCustomName(MobList.getString(mob + ".Name").replace("&", "§"));
		Monster.setCustomNameVisible(true);
		return Monster;
	}

	
	private static void Stack(String Display, int ID, byte DATA, byte STACK, List<String> Lore, byte Loc, Inventory inventory)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
		return;
	}
	
	public void SpawnEggGive(Player player, String mob)
	{
		ItemStack Icon = new MaterialData(383, (byte) 0).toItemStack(1);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(ChatColor.RED+mob);
		Icon_Meta.addEnchant(Enchantment.DURABILITY, 1, true);
		Icon_Meta.setLore(Arrays.asList(ChatColor.GRAY+mob+"스폰 에그"));
		Icon.setItemMeta(Icon_Meta);
		player.getInventory().addItem(Icon);
		player.sendMessage(ChatColor.YELLOW+"[SYSTEM] : "+ChatColor.GREEN+mob +ChatColor.YELLOW+ "스폰 에그를 얻었습니다!");
		return;
	}
	
	public void SpawnEffect (Entity mob,Location loc, byte type)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		GBD_RPG.Effect.Effect_Particle p = new GBD_RPG.Effect.Effect_Particle();
		switch(type)
		{
		case 0: return;
		case 1:
			{
				s.SL(loc, org.bukkit.Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.5F);
				for(int counter=0;counter<400;counter++)
				p.PLC(loc, Effect.SPELL, 4);
				for(int counter=0;counter<300;counter++)
				p.PLC(loc, Effect.FLYING_GLYPH, 0);
				for(int counter=0;counter<200;counter++)
				p.PLC(loc, Effect.SMOKE, 4);
			}
			return;
		case 2:
			{
				loc.setY((double)(loc.getBlockY()+1));
				s.SL(loc, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 0.5F);
				p.PLC(loc, Effect.ENDER_SIGNAL, 0);
				for(int counter=0;counter<100;counter++)
				p.PLC(loc, Effect.ENDER_SIGNAL, 0);
				for(int counter=0;counter<50;counter++)
				p.PLC(loc, Effect.MAGIC_CRIT, 4);
			}
			return;
		case 3:
			{
				s.SL(loc, org.bukkit.Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 0.5F);
				p.PLC(loc, Effect.EXPLOSION_LARGE, 0);
				for(int counter=0;counter<3;counter++)
				p.PLC(loc, Effect.EXPLOSION_HUGE, 0);
				for(int counter=0;counter<10;counter++)
				p.PLC(loc, Effect.EXPLOSION, 4);
			}
			return;
		case 4:
			{
				s.SL(loc, org.bukkit.Sound.ENTITY_GHAST_SHOOT, 1.0F, 0.5F);
				p.PLC(loc, Effect.FLAME, 0);
				for(int counter=0;counter<200;counter++)
				p.PLC(loc, Effect.SMOKE, 9);
				for(int counter=0;counter<200;counter++)
				p.PLC(loc, Effect.FLAME, 0);
			}
			return;
		case 5:
			{
				s.SL(loc, org.bukkit.Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.6F);
				p.PLC(loc, Effect.CRIT, 0);
				for(int counter=0;counter<400;counter++)
				p.PLC(loc, Effect.SPELL, 4);
				loc.setY((double)(loc.getBlockY()+1.5));
				for(int counter=0;counter<1;counter++)
				p.PLC(loc, Effect.VILLAGER_THUNDERCLOUD, counter);
			}
			return;
		case 6:
			{
				s.SL(loc, org.bukkit.Sound.BLOCK_CHEST_OPEN, 1.0F, 0.5F);
				loc.setY((double)(loc.getBlockY()+1.8));
				p.PLC(loc, Effect.HEART, 0);
			}
			return;
		case 7:
			{
				switch(mob.getType())
				{
				case ZOMBIE :
				case GIANT :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1.8));
						p.PLC(loc, Effect.VILLAGER_THUNDERCLOUD, 0);
						for(int counter=0;counter<50;counter++)
							p.PLC(loc, Effect.MAGIC_CRIT, counter);
					}
					break;
				case SKELETON :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_SKELETON_DEATH, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1.8));
						p.PLC(loc, Effect.VILLAGER_THUNDERCLOUD, 0);
						for(int counter=0;counter<50;counter++)
							p.PLC(loc, Effect.MAGIC_CRIT, counter);
					}
					break;
				case ENDERMAN :
				case ENDERMITE :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1));
						for(int counter=0;counter<100;counter++)
							p.PLC(loc, Effect.ENDER_SIGNAL, 0);
					}
					break;
				case CREEPER :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1));
						p.PLC(loc, Effect.EXPLOSION_LARGE, 0);
						for(int counter=0;counter<3;counter++)
						p.PLC(loc, Effect.EXPLOSION_HUGE, counter);
						for(int counter=0;counter<5;counter++)
						p.PLC(loc, Effect.EXPLOSION, counter);
					}
					break;
				case SPIDER :
				case CAVE_SPIDER :
				case SILVERFISH:
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_SPIDER_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<10;counter++)
						p.PLC(loc, Effect.LARGE_SMOKE, counter);
						loc.setY((double)(loc.getBlockY()+1));
						p.PLC(loc, Effect.SMOKE, 0);
					}
					break;
				case SLIME:
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_SLIME_JUMP, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1));
						for(int counter=0;counter<20;counter++)
						p.PLC(loc, Effect.SLIME, counter);
					}
					break;
				case MAGMA_CUBE:
					{
						loc.setY((double)(loc.getBlockY()+1));
						s.SL(loc, org.bukkit.Sound.ENTITY_SLIME_JUMP, 1.0F, 0.5F);
						for(int counter=0;counter<40;counter++)
						p.PLC(loc, Effect.MOBSPAWNER_FLAMES, counter);
					}
					break;
				case BLAZE :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_BLAZE_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.SMOKE, 9);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.FLAME, 0);
					}
					break;
				case GHAST :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_GHAST_AMBIENT, 1.0F, 0.5F);
						p.PLC(loc, Effect.FLAME, 0);
						for(int counter=0;counter<100;counter++)
							p.PLC(loc, Effect.SMOKE, 4);
						for(int counter=0;counter<40;counter++)
						p.PLC(loc, Effect.MOBSPAWNER_FLAMES, counter);
					}
					break;
				case PIG_ZOMBIE :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<100;counter++)
							p.PLC(loc, Effect.SMOKE, 4);
						for(int counter=0;counter<40;counter++)
						p.PLC(loc, Effect.MOBSPAWNER_FLAMES, counter);
					}
					break;
				case WITCH:
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_WITCH_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<400;counter++)
						p.PLC(loc, Effect.SPELL, 4);
						for(int counter=0;counter<300;counter++)
						p.PLC(loc, Effect.FLYING_GLYPH, 0);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.SMOKE, 4);
					}
					break;
				case GUARDIAN :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_GUARDIAN_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<400;counter++)
						p.PLC(loc, Effect.WATERDRIP, counter);
					}
					break;
				case SNOWMAN :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_SNOWMAN_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.SNOWBALL_BREAK, counter);
					}
					break;
					case SHULKER :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_SHULKER_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<400;counter++)
						p.PLC(loc, Effect.ENDER_SIGNAL, counter);
					}
					break;
					case POLAR_BEAR :
					{
						s.SL(loc, org.bukkit.Sound.ENTITY_POLAR_BEAR_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.VILLAGER_THUNDERCLOUD, counter);
					}
					break;
				}
			}
			return;
		}
		return;
	}
	
	public short getMonsterID(String MonsterType)
	{
		switch(MonsterType)
		{
			case "좀비":
				return 54;
			case "자이언트":
				return 53;
			case "네더스켈레톤":
			case "스켈레톤":
				return 51;
			case "엔더맨":
				return 58;
			case "번개크리퍼":
			case "크리퍼":
				return 50;
			case "거미":
				return 52;
			case "동굴거미":
				return 59;
			case "좀벌레":
				return 60;
			case "엔더진드기":
				return 67;
			case "초대형슬라임": case "특대슬라임": case "큰슬라임": case "보통슬라임": case "작은슬라임": 
				return 55;
			case "특대마그마큐브": case "큰마그마큐브": case "보통마그마큐브": case "작은마그마큐브": case "마그마큐브":
				return 62;
			case "블레이즈":
				return 61;
			case "가스트":
				return 56;
			case "좀비피그맨":
				return 57;
			case "마녀":
				return 66;
			case "위더":
				return 64;
			case "엔더드래곤":
				return 63;
			case "엔더크리스탈":
				return -50;
			case "수호자":
				return 68;
			case "양":
				return 91;
			case "소":
				return 92;
			case "돼지":
				return 90;
			case "말":
				return 100;
			case "토끼":
				return 101;
			case "오셀롯":
				return 98;
			case "늑대":
				return 95;
			case "닭":
				return 93;
			case "버섯소":
				return 96;
			case "오징어":
				return 94;
			case "주민":
				return 120;
			case "눈사람":
				return 97;
			case "골렘":
				return 99;
			case "박쥐":
				return 65;
			case "셜커":
				return 69;
			case "북극곰":
				return 102;
			case "휴먼":
				return 49;
			default : return -60;
		}
	}

	public String getMonsterCustomName(String MonsterType)
	{
		switch(MonsterType)
		{
			case "좀비":
				return "CUSTOMZOMBIE";
			case "자이언트":
				return "CUSTOMGIANT";
			case "네더스켈레톤":
			case "스켈레톤":
				return "CUSTOMSKELETON";
			case "엔더맨":
				return "CUSTOMENDERMAN";
			case "번개크리퍼":
			case "크리퍼":
				return "CUSTOMCREEPER";
			case "거미":
				return "CUSTOMSPIDER";
			case "동굴거미":
				return "CUSTOMCAVESPIDER";
			case "좀벌레":
				return "CUSTOMSLIVERFISH";
			case "엔더진드기":
				return "CUSTOMENDERMITE";
			case "초대형슬라임": case "특대슬라임": case "큰슬라임": case "보통슬라임": case "작은슬라임":
				return "CUSTOMSLIME"; 
			case "특대마그마큐브": case "큰마그마큐브": case "보통마그마큐브": case "작은마그마큐브": case "마그마큐브":
				return "CUSTOMMAGMACUBE"; 
			case "블레이즈":
				return "CUSTOMBLAZE";
			case "가스트":
				return "CUSTOMGHAST";
			case "좀비피그맨":
				return "CUSTOMPIGZOMBIE";
			case "마녀":
				return "CUSTOMWITCH";
			case "위더":
				return "CUSTOMWITHER";
			case "엔더드래곤":
				return "CUSTOMENDERDRAGON";
			case "수호자":
				return "CUSTOMGUARDIAN";
			case "양":
				return "CUSTOMSHEEP";
			case "소":
				return "CUSTOMCOW";
			case "돼지":
				return "CUSTOMPIG";
			case "말":
				return "CUSTOMHORSE";
			case "토끼":
				return "CUSTOMRABBIT";
			case "오셀롯":
				return "CUSTOMOCELOT";
			case "늑대":
				return "CUSTOMWOLF";
			case "닭":
				return "CUSTOMCHICKEN";
			case "버섯소":
				return "CUSTOMMOOSHROOM";
			case "오징어":
				return "CUSTOMSQUID";
			case "주민":
				return "CUSTOMVILLAGER";
			case "눈사람":
				return "CUSTOMSNOWGOLEM";
			case "골렘":
				return "CUSTOMIRONGOLEM";
			case "박쥐":
				return "CUSTOMBAT";
			case "셜커":
				return "CUSTOMSHULKER";
			case "북극곰":
				return "CUSTOMPOLARBEAR";
			case "휴먼":
				return "CUSTOMHUMAN";
			default:
				return "CUSTOMZOMBIE";
		}
	}
}