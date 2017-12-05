package monster;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

import effect.PottionBuff;
import effect.SoundEffect;
import monster.ai.*;
import util.YamlLoader;



public class MonsterSpawn
{
	public void EntitySpawn(CreatureSpawnEvent event)
	{
		if(event.getLocation().getWorld().getName().equals("Dungeon"))
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
				if(main.MainServerOption.MonsterNameMatching.containsKey(event.getEntity().getCustomName()))
				{
					event.setCancelled(true);
				}
			}
		}
		area.AreaMain A = new area.AreaMain();
		String[] Area = A.getAreaName(event.getEntity());
		
		YamlLoader areaYaml = new YamlLoader();
		if(Area != null)
		{
			if(A.getAreaOption(Area[0], (char) 3) == false)
			{
				event.setCancelled(true);
				return;
			}
			else if(A.getAreaOption(Area[0], (char) 8) == false)
			{
				areaYaml.getConfig("Area/AreaList.yml");
				String AreaName = A.getAreaName(event.getEntity())[0];
				Object[] MobNameList = areaYaml.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
				boolean isExit = false;
				for(int count = 0;count<10;count++)
				{
					if(isExit) break;
					if(MobNameList.length != 0)
					{
						short RandomMob = (short) new util.UtilNumber().RandomNum(0, MobNameList.length-1);
						if(main.MainServerOption.MonsterList.containsKey(MobNameList[RandomMob].toString()))
						{
							new monster.MonsterSpawn().SpawnMob(event.getLocation(), MobNameList[RandomMob].toString(), (byte) -1, null,(char) -1, false);
							isExit = true;
						}
						else
						{
							areaYaml.removeKey(AreaName+".Monster."+MobNameList[RandomMob]);
							areaYaml.saveConfig();
						}
					}
					else
						break;
				}
			}
		}
		YamlLoader configYaml = new YamlLoader();
	    configYaml.getConfig("config.yml");
		new monster.MonsterSpawn().SpawnEffect(event.getEntity(),event.getLocation(), (byte) configYaml.getInt("Server.MonsterSpawnEffect"));
		return;
	}
	
	public void CreateMonster(String MonsterName)
	{
		ItemStack Item = new ItemStack(Material.AIR);
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		
		monsterYaml.set(MonsterName+".Name", MonsterName);
		monsterYaml.set(MonsterName+".Type", "좀비");
		monsterYaml.set(MonsterName+".AI", "일반 행동");
		monsterYaml.set(MonsterName+".EXP", 15);
		monsterYaml.set(MonsterName+".Biome", "NULL");
		monsterYaml.set(MonsterName+".HP", 20);
		monsterYaml.set(MonsterName+".MIN_Money", 1);
		monsterYaml.set(MonsterName+".MAX_Money", 10);
		monsterYaml.set(MonsterName+".STR", 10);
		monsterYaml.set(MonsterName+".DEX", 10);
		monsterYaml.set(MonsterName+".INT", 10);
		monsterYaml.set(MonsterName+".WILL", 10);
		monsterYaml.set(MonsterName+".LUK", 10);
		monsterYaml.set(MonsterName+".DEF", 0);
		monsterYaml.set(MonsterName+".Protect", 0);
		monsterYaml.set(MonsterName+".Magic_DEF", 0);
		monsterYaml.set(MonsterName+".Magic_Protect", 0);
		monsterYaml.set(MonsterName+".Head.DropChance", 0);
		monsterYaml.set(MonsterName+".Head.Item", Item);
		monsterYaml.set(MonsterName+".Chest.DropChance", 0);
		monsterYaml.set(MonsterName+".Chest.Item", Item);
		monsterYaml.set(MonsterName+".Leggings.DropChance", 0);
		monsterYaml.set(MonsterName+".Leggings.Item", Item);
		monsterYaml.set(MonsterName+".Boots.DropChance", 0);
		monsterYaml.set(MonsterName+".Boots.Item", Item);
		monsterYaml.set(MonsterName+".Hand.DropChance", 0);
		monsterYaml.set(MonsterName+".Hand.Item", Item);
		monsterYaml.set(MonsterName+".OffHand.DropChance", 0);
		monsterYaml.set(MonsterName+".OffHand.Item", Item);
		monsterYaml.saveConfig();
		return;
	}

	public void StayLive(Entity e, boolean isStayLive)
	{
		if(e.getType()!=EntityType.ENDER_CRYSTAL)
			if(!e.isDead())
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
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String Type = monsterYaml.getString(mob + ".Type");
		if(monsterYaml.getString(mob+".Name")!=null)
		{
			String aiString = monsterYaml.getString(mob + ".AI");
			int ai = 0;
			
			if(aiString.equals("무뇌아"))
				ai = 3;
			else if(aiString.equals("동물"))
				ai = 4;
			else if(aiString.equals("선공"))
				ai = 1;
			else if(aiString.equals("비선공"))
				ai = 2;
			
			if(!(aiString.equals("선공")||aiString.equals("비선공")||aiString.equals("무뇌아")||aiString.equals("동물")||aiString.equals("일반 행동")))
			{
				monsterYaml.set(mob + ".AI", "일반 행동");
				monsterYaml.saveConfig();
			}
			if(!aiString.equals("일반 행동"))
			{
				String CustomMobName = "CUSTOM"+mob;
				int CustomMobID = getMonsterID(monsterYaml.getString(mob + ".Type"));
				LivingEntity E = null;
				if(Type.equals("좀비"))
					E = (CraftZombie) CustomZombie.spawn(loc, ai);
				else if(Type.equals("자이언트"))
					E = (CraftGiant) CustomGiant.spawn(loc, ai);
				else if(Type.equals("스켈레톤"))
					E = (CraftSkeleton) CustomSkeleton.spawn(loc, ai);
				else if(Type.equals("네더스켈레톤"))
					E = (CraftWitherSkeleton) CustomWitherSkeleton.spawn(loc, ai);
				else if(Type.equals("엔더맨"))
					E = (CraftEnderman) CustomEnderman.spawn(loc, ai);
				else if(Type.equals("크리퍼")||Type.equals("번개크리퍼"))
					E = (CraftCreeper) CustomCreeper.spawn(loc, ai);
				else if(Type.equals("거미"))
					E = (CraftSpider) CustomSpider.spawn(loc, ai);
				else if(Type.equals("동굴거미"))
					E = (CraftCaveSpider) CustomCaveSpider.spawn(loc, ai);
				else if(Type.equals("좀벌레"))
					E = (CraftSilverfish) CustomSilverFish.spawn(loc, ai);
				else if(Type.equals("엔더진드기"))
					E = (CraftEndermite) CustomEnderMite.spawn(loc, ai);
				else if(Type.equals("블레이즈"))
					E = (CraftBlaze) CustomBlaze.spawn(loc, ai);
				else if(Type.equals("좀비피그맨"))
					E = (CraftPigZombie) CustomPigZombie.spawn(loc, ai);
				else if(Type.equals("마녀"))
					E = (CraftWitch) CustomWitch.spawn(loc, ai);
				else if(Type.equals("위더"))
					E = (CraftWither) CustomWither.spawn(loc, ai);
				else if(Type.equals("수호자"))
					E = (CraftGuardian) CustomGuardian.spawn(loc, ai);
				else if(Type.equals("북극곰"))
					E = (CraftPolarBear) CustomPolarBear.spawn(loc, ai);
				else if(Type.equals("엔더크리스탈"))
					CreateCreature(Type, loc, mob, DungeonSpawning, XYZLoc, Group, isStayLive);

				else if (Type.equals("엘더가디언"))
					E = (CraftElderGuardian) CustomElderGuardian.spawn(loc, ai);
				else if (Type.equals("스트레이"))
					E = (CraftStray) CustomStray.spawn(loc, ai);
				else if (Type.equals("허스크"))
					E = (CraftHusk) CustomHusk.spawn(loc, ai);
				else if (Type.equals("주민좀비"))
					E = (CraftVillagerZombie) CustomZombieVillager.spawn(loc, ai);
				else if (Type.equals("소환사"))
					E = (CraftEvoker) CustomEvoker.spawn(loc, ai);
				else if (Type.equals("벡스"))
					E = (CraftVex) CustomVex.spawn(loc, ai);
				else if (Type.equals("변명자"))
					E = (CraftVindicator) CustomVindicator.spawn(loc, ai);
				else if (Type.equals("셜커"))
					E = (Shulker) CustomShulker.spawn(loc, ai);
				// 몬스터 형태의 몹
				else if (Type.equals("스켈레톤말"))
					E = (LivingEntity) CustomSkeletonHorse.spawn(loc, ai);
				else if (Type.equals("좀비말"))
					E = (LivingEntity) CustomZombieHorse.spawn(loc, ai);
				else if (Type.equals("당나귀"))
					E = (LivingEntity) CustomDonkey.spawn(loc, ai);
				else if (Type.equals("노새"))
					E = (LivingEntity) CustomMule.spawn(loc, ai);
				else if (Type.equals("인간"))
					E = (LivingEntity) CustomHuman.spawn(loc, ai);
				else if (Type.equals("박쥐"))
					E = (LivingEntity) CustomBat.spawn(loc, ai);
				else if (Type.equals("돼지"))
					E = (LivingEntity) CustomPig.spawn(loc, ai);
				else if (Type.equals("양"))
					E = (LivingEntity) CustomSheep.spawn(loc, ai);
				else if (Type.equals("소"))
					E = (LivingEntity) CustomCow.spawn(loc, ai);
				else if (Type.equals("닭"))
					E = (LivingEntity) CustomChicken.spawn(loc, ai);
				else if (Type.equals("오징어"))
					E = (LivingEntity) CustomSquid.spawn(loc, ai);
				else if (Type.equals("늑대"))
					E = (LivingEntity) CustomWolf.spawn(loc, ai);
				else if (Type.equals("버섯소"))
					E = (LivingEntity) CustomMooshroom.spawn(loc, ai);
				else if (Type.equals("눈사람"))
					E = (LivingEntity) CustomSnowman.spawn(loc, ai);
				else if (Type.equals("오셀롯"))
					E = (LivingEntity) CustomOcelot.spawn(loc, ai);
				else if (Type.equals("철골렘"))
					E = (LivingEntity) CustomIronGolem.spawn(loc, ai);
				else if (Type.equals("말"))
					E = (LivingEntity) CustomHorse.spawn(loc, ai);
				else if (Type.equals("토끼"))
					E = (LivingEntity) CustomRabbit.spawn(loc, ai);
				else if (Type.equals("라마"))
					E = (LivingEntity) CustomLlama.spawn(loc, ai);
				else if (Type.equals("주민"))
					E = (LivingEntity) CustomVillager.spawn(loc, ai);

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
			case "스켈레톤":{Skeleton skeleton = (Skeleton) loc.getWorld().spawn(loc, Skeleton.class);skeleton = (Skeleton) getEntity(skeleton,mob, DungeonSpawning, XYZLoc, Group);StayLive(skeleton, isStayLive);}break;
			case "네더스켈레톤":{WitherSkeleton skeleton = (WitherSkeleton) loc.getWorld().spawn(loc, WitherSkeleton.class);skeleton = (WitherSkeleton) getEntity(skeleton,mob, DungeonSpawning, XYZLoc, Group);StayLive(skeleton, isStayLive);}break;
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
			case "철골렘":{IronGolem golem = (IronGolem) loc.getWorld().spawn(loc, IronGolem.class);golem = (IronGolem) getEntity(golem,mob, DungeonSpawning, XYZLoc, Group);StayLive(golem, isStayLive);}break;
			case "셜커":{Shulker shulker = (Shulker) loc.getWorld().spawn(loc, Shulker.class);shulker = (Shulker) getEntity(shulker,mob, DungeonSpawning, XYZLoc, Group);StayLive(shulker, isStayLive);}break;
			case "북극곰":{PolarBear polarBear = (PolarBear) loc.getWorld().spawn(loc, PolarBear.class);polarBear = (PolarBear) getEntity(polarBear,mob, DungeonSpawning, XYZLoc, Group);StayLive(polarBear, isStayLive);}break;
			
			case "엘더가디언":{ElderGuardian elderGuardian = (ElderGuardian) loc.getWorld().spawn(loc, ElderGuardian.class);elderGuardian = (ElderGuardian) getEntity(elderGuardian,mob, DungeonSpawning, XYZLoc, Group);StayLive(elderGuardian, isStayLive);}break;
			case "스트레이":{Stray stray = (Stray) loc.getWorld().spawn(loc, Stray.class);stray = (Stray) getEntity(stray,mob, DungeonSpawning, XYZLoc, Group);StayLive(stray, isStayLive);}break;
			case "허스크":{Husk husk = (Husk) loc.getWorld().spawn(loc, Husk.class);husk = (Husk) getEntity(husk,mob, DungeonSpawning, XYZLoc, Group);StayLive(husk, isStayLive);}break;
			case "주민좀비":{ZombieVillager zombiVillager = (ZombieVillager) loc.getWorld().spawn(loc, ZombieVillager.class);zombiVillager = (ZombieVillager) getEntity(zombiVillager,mob, DungeonSpawning, XYZLoc, Group);StayLive(zombiVillager, isStayLive);}break;
			case "소환사":{Evoker evoker = (Evoker) loc.getWorld().spawn(loc, Evoker.class);evoker = (Evoker) getEntity(evoker,mob, DungeonSpawning, XYZLoc, Group);StayLive(evoker, isStayLive);}break;
			case "벡스":{Vex vex = (Vex) loc.getWorld().spawn(loc, Vex.class);vex = (Vex) getEntity(vex,mob, DungeonSpawning, XYZLoc, Group);StayLive(vex, isStayLive);}break;
			case "변명자":{Vindicator polarBear = (Vindicator) loc.getWorld().spawn(loc, Vindicator.class);polarBear = (Vindicator) getEntity(polarBear,mob, DungeonSpawning, XYZLoc, Group);StayLive(polarBear, isStayLive);}break;
			case "스켈레톤말":{SkeletonHorse skeletonHorse = (SkeletonHorse) loc.getWorld().spawn(loc, SkeletonHorse.class);skeletonHorse = (SkeletonHorse) getEntity(skeletonHorse,mob, DungeonSpawning, XYZLoc, Group);StayLive(skeletonHorse, isStayLive);}break;
			case "좀비말":{ZombieHorse zombieHorse = (ZombieHorse) loc.getWorld().spawn(loc, ZombieHorse.class);zombieHorse = (ZombieHorse) getEntity(zombieHorse,mob, DungeonSpawning, XYZLoc, Group);StayLive(zombieHorse, isStayLive);}break;
			case "당나귀":{Donkey donkey = (Donkey) loc.getWorld().spawn(loc, Donkey.class);donkey = (Donkey) getEntity(donkey,mob, DungeonSpawning, XYZLoc, Group);StayLive(donkey, isStayLive);}break;
			case "노새":{Mule mule = (Mule) loc.getWorld().spawn(loc, Mule.class);mule = (Mule) getEntity(mule,mob, DungeonSpawning, XYZLoc, Group);StayLive(mule, isStayLive);}break;
			case "인간":{Player player = (Player) loc.getWorld().spawn(loc, Player.class);player = (Player) getEntity(player,mob, DungeonSpawning, XYZLoc, Group);StayLive(player, isStayLive);}break;
			case "라마":{Llama llama = (Llama) loc.getWorld().spawn(loc, Llama.class);llama = (Llama) getEntity(llama,mob, DungeonSpawning, XYZLoc, Group);StayLive(llama, isStayLive);}break;
			//case "휴먼":{HumanEntity human = (HumanEntity) loc.getWorld().spawn(loc, Player.class);human = (HumanEntity) getEntity(human,mob, DungeonSpawning, XYZLoc, Group);StayLive(human, isStayLive);}break;
		}
	}

	private LivingEntity getEntity(LivingEntity Monster, String mob, byte DungeonSpawning, int[] XYZloc, char Group)
	{
		if(mob!=null)
		{
			YamlLoader monsterYaml = new YamlLoader();
			monsterYaml.getConfig("Monster/MonsterList.yml");
			Monster.setCustomName(monsterYaml.getString(mob + ".Name").replace("&", "§"));
			Monster.setCustomNameVisible(true);
			ItemStack Equip = monsterYaml.getItemStack(mob+".Head.Item");
			if(Equip == null)
				Monster.getEquipment().setHelmet(null);
			else
			{
				Monster.getEquipment().setHelmet(Equip);
				if(Equip.hasItemMeta())
					if(Equip.getItemMeta().hasLore())
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요."))
							Monster.getEquipment().setHelmet(null);
			}
			Equip = monsterYaml.getItemStack(mob+".Chest.Item");
			if(Equip == null)
				Monster.getEquipment().setChestplate(null);
			else
			{
				Monster.getEquipment().setChestplate(Equip);
				if(Equip.hasItemMeta())
					if(Equip.getItemMeta().hasLore())
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요."))
							Monster.getEquipment().setChestplate(null);
			}
			Equip = monsterYaml.getItemStack(mob+".Leggings.Item");
			if(Equip == null)
				Monster.getEquipment().setLeggings(null);
			else
			{
				Monster.getEquipment().setLeggings(Equip);
				if(Equip.hasItemMeta())
					if(Equip.getItemMeta().hasLore())
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요."))
							Monster.getEquipment().setLeggings(null);
			}
			Equip = monsterYaml.getItemStack(mob+".Boots.Item");
			if(Equip == null)
				Monster.getEquipment().setBoots(null);
			else
			{
				Monster.getEquipment().setBoots(Equip);
				if(Equip.hasItemMeta())
					if(Equip.getItemMeta().hasLore())
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요."))
							Monster.getEquipment().setBoots(null);
			}
			Equip = monsterYaml.getItemStack(mob+".Hand.Item");
			if(Equip == null)
				Monster.getEquipment().setItemInMainHand(null);
			else
			{
				Monster.getEquipment().setItemInMainHand(Equip);
				if(Equip.hasItemMeta())
					if(Equip.getItemMeta().hasLore())
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요."))
								Monster.getEquipment().setItemInMainHand(null);
			}
			Equip = monsterYaml.getItemStack(mob+".OffHand.Item");
			if(Equip == null)
				Monster.getEquipment().setItemInOffHand(null);
			else
			{
				Monster.getEquipment().setItemInOffHand(Equip);
				if(Equip.hasItemMeta())
					if(Equip.getItemMeta().hasLore())
						if(ChatColor.stripColor(Equip.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요."))
								Monster.getEquipment().setItemInOffHand(null);
			}
			YamlLoader configYaml = new YamlLoader();
		    configYaml.getConfig("config.yml");
			Monster.getEquipment().setHelmetDropChance((float)(monsterYaml.getInt(mob+".Head.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setChestplateDropChance((float)(monsterYaml.getInt(mob+".Chest.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setLeggingsDropChance((float)(monsterYaml.getInt(mob+".Leggings.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setBootsDropChance((float)(monsterYaml.getInt(mob+".Boots.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setItemInMainHandDropChance((float)(monsterYaml.getInt(mob+".Hand.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			Monster.getEquipment().setItemInOffHandDropChance((float)(monsterYaml.getInt(mob+".OffHand.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			
			if(Monster.getType() == EntityType.CREEPER)
			{
				if(monsterYaml.getString(mob+".Type").equalsIgnoreCase("번개크리퍼") == true)
				{
					  ((Creeper) Monster).setPowered(true);
				}
			}
			else if(Monster.getType() == EntityType.SLIME)
			{
				switch(monsterYaml.getString(mob + ".Type"))
				{
					case "작은슬라임" : ((Slime) Monster).setSize(1);break;
					case "보통슬라임" : ((Slime) Monster).setSize(2);break;
					case "큰슬라임" : ((Slime) Monster).setSize(4);break;
					case "특대슬라임" : ((Slime) Monster).setSize(16);break;
					case "초대형슬라임" : ((Slime) Monster).setSize(64);break;
					default : ((Slime) Monster).setSize(new util.UtilNumber().RandomNum(1, 4));break;
				}
			}
			else if(Monster.getType() == EntityType.MAGMA_CUBE)
			{
				switch(monsterYaml.getString(mob + ".Type"))
				{
					case "작은마그마큐브" : ((MagmaCube) Monster).setSize(1);break;
					case "보통마그마큐브" : ((MagmaCube) Monster).setSize(2);break;
					case "큰마그마큐브" : ((MagmaCube) Monster).setSize(4);break;
					case "특대마그마큐브" : ((MagmaCube) Monster).setSize(16);break;
					case "초대형마그마큐브" : ((MagmaCube) Monster).setSize(64);break;
					default : ((MagmaCube) Monster).setSize(new util.UtilNumber().RandomNum(1, 4));break;
				}
			}
			if(monsterYaml.contains(mob+".Potion"))
			{
				if(monsterYaml.getInt(mob+".Potion.Regenerate")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.REGENERATION, 50000, monsterYaml.getInt(mob+".Potion.Regenerate")));
				if(monsterYaml.getInt(mob+".Potion.Poison")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.POISON, 50000, monsterYaml.getInt(mob+".Potion.Poison")));
				if(monsterYaml.getInt(mob+".Potion.Speed")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.SPEED, 50000, monsterYaml.getInt(mob+".Potion.Speed")));
				if(monsterYaml.getInt(mob+".Potion.Slow")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.SLOW, 50000, monsterYaml.getInt(mob+".Potion.Slow")));
				if(monsterYaml.getInt(mob+".Potion.Strength")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.INCREASE_DAMAGE, 50000, monsterYaml.getInt(mob+".Potion.Strength")));
				if(monsterYaml.getInt(mob+".Potion.Weak")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.WEAKNESS, 50000, monsterYaml.getInt(mob+".Potion.Weak")));
				if(monsterYaml.getInt(mob+".Potion.JumpBoost")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.JUMP, 50000, monsterYaml.getInt(mob+".Potion.JumpBoost")));
				if(monsterYaml.getInt(mob+".Potion.FireRegistance")!=0)
					Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.FIRE_RESISTANCE, 50000, monsterYaml.getInt(mob+".Potion.FireRegistance")));
				if(monsterYaml.getInt(mob+".Potion.WaterBreath")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.WATER_BREATHING, 50000, monsterYaml.getInt(mob+".Potion.WaterBreath")));
				if(monsterYaml.getInt(mob+".Potion.Invisible")!=0)
				Monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.INVISIBILITY, 50000, monsterYaml.getInt(mob+".Potion.Invisible")));
			}
			Damageable m = Monster;
			m.setMaxHealth(monsterYaml.getInt(mob + ".HP")+0.5);
			m.setHealth(m.getMaxHealth());
		}
		
		
		
		if(DungeonSpawning != -1)
		{
			YamlLoader monsterYaml = new YamlLoader();
			monsterYaml.getConfig("Monster/MonsterList.yml");
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
				String a = monsterYaml.getString(mob + ".Name").replace("&", "§");
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
			ItemStack Equip = monsterYaml.getItemStack(mob+".Head.Item");
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
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
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
				String a = monsterYaml.getString(mob + ".Name").replace("&", "§");
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
			Monster.setCustomName(monsterYaml.getString(mob + ".Name").replace("&", "§"));
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
		Icon_Meta.setDisplayName("§c"+mob);
		Icon_Meta.addEnchant(Enchantment.DURABILITY, 1, true);
		Icon_Meta.setLore(Arrays.asList("§8"+mob+"스폰 에그"));
		Icon.setItemMeta(Icon_Meta);
		player.getInventory().addItem(Icon);
		player.sendMessage("§e[SYSTEM] : "+ChatColor.GREEN+mob +"§e스폰 에그를 얻었습니다!");
		return;
	}
	
	public void SpawnEffect (Entity mob,Location loc, byte type)
	{
		
		effect.ParticleEffect p = new effect.ParticleEffect();
		switch(type)
		{
		case 0: return;
		case 1:
			{
				SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.5F);
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
				SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 0.5F);
				p.PLC(loc, Effect.ENDER_SIGNAL, 0);
				for(int counter=0;counter<100;counter++)
				p.PLC(loc, Effect.ENDER_SIGNAL, 0);
				for(int counter=0;counter<50;counter++)
				p.PLC(loc, Effect.MAGIC_CRIT, 4);
			}
			return;
		case 3:
			{
				SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 0.5F);
				p.PLC(loc, Effect.EXPLOSION_LARGE, 0);
				for(int counter=0;counter<3;counter++)
				p.PLC(loc, Effect.EXPLOSION_HUGE, 0);
				for(int counter=0;counter<10;counter++)
				p.PLC(loc, Effect.EXPLOSION, 4);
			}
			return;
		case 4:
			{
				SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_GHAST_SHOOT, 1.0F, 0.5F);
				p.PLC(loc, Effect.FLAME, 0);
				for(int counter=0;counter<200;counter++)
				p.PLC(loc, Effect.SMOKE, 9);
				for(int counter=0;counter<200;counter++)
				p.PLC(loc, Effect.FLAME, 0);
			}
			return;
		case 5:
			{
				SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.6F);
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
				SoundEffect.playSoundLocation(loc, org.bukkit.Sound.BLOCK_CHEST_OPEN, 1.0F, 0.5F);
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
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1.8));
						p.PLC(loc, Effect.VILLAGER_THUNDERCLOUD, 0);
						for(int counter=0;counter<50;counter++)
							p.PLC(loc, Effect.MAGIC_CRIT, counter);
					}
					break;
				case SKELETON :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_SKELETON_DEATH, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1.8));
						p.PLC(loc, Effect.VILLAGER_THUNDERCLOUD, 0);
						for(int counter=0;counter<50;counter++)
							p.PLC(loc, Effect.MAGIC_CRIT, counter);
					}
					break;
				case ENDERMAN :
				case ENDERMITE :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1));
						for(int counter=0;counter<100;counter++)
							p.PLC(loc, Effect.ENDER_SIGNAL, 0);
					}
					break;
				case CREEPER :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 0.5F);
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
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_SPIDER_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<10;counter++)
						p.PLC(loc, Effect.LARGE_SMOKE, counter);
						loc.setY((double)(loc.getBlockY()+1));
						p.PLC(loc, Effect.SMOKE, 0);
					}
					break;
				case SLIME:
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_SLIME_JUMP, 1.0F, 0.5F);
						loc.setY((double)(loc.getBlockY()+1));
						for(int counter=0;counter<20;counter++)
						p.PLC(loc, Effect.SLIME, counter);
					}
					break;
				case MAGMA_CUBE:
					{
						loc.setY((double)(loc.getBlockY()+1));
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_SLIME_JUMP, 1.0F, 0.5F);
						for(int counter=0;counter<40;counter++)
						p.PLC(loc, Effect.MOBSPAWNER_FLAMES, counter);
					}
					break;
				case BLAZE :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_BLAZE_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.SMOKE, 9);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.FLAME, 0);
					}
					break;
				case GHAST :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_GHAST_AMBIENT, 1.0F, 0.5F);
						p.PLC(loc, Effect.FLAME, 0);
						for(int counter=0;counter<100;counter++)
							p.PLC(loc, Effect.SMOKE, 4);
						for(int counter=0;counter<40;counter++)
						p.PLC(loc, Effect.MOBSPAWNER_FLAMES, counter);
					}
					break;
				case PIG_ZOMBIE :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<100;counter++)
							p.PLC(loc, Effect.SMOKE, 4);
						for(int counter=0;counter<40;counter++)
						p.PLC(loc, Effect.MOBSPAWNER_FLAMES, counter);
					}
					break;
				case WITCH:
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_WITCH_AMBIENT, 1.0F, 0.5F);
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
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_GUARDIAN_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<400;counter++)
						p.PLC(loc, Effect.WATERDRIP, counter);
					}
					break;
				case SNOWMAN :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_SNOWMAN_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<200;counter++)
						p.PLC(loc, Effect.SNOWBALL_BREAK, counter);
					}
					break;
					case SHULKER :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_SHULKER_AMBIENT, 1.0F, 0.5F);
						for(int counter=0;counter<400;counter++)
						p.PLC(loc, Effect.ENDER_SIGNAL, counter);
					}
					break;
					case POLAR_BEAR :
					{
						SoundEffect.playSoundLocation(loc, org.bukkit.Sound.ENTITY_POLAR_BEAR_AMBIENT, 1.0F, 0.5F);
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