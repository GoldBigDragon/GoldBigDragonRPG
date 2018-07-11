package monster;

import java.util.Arrays;
import java.util.List;

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
import util.NumericUtil;
import util.YamlLoader;

public class MonsterSpawn
{
	public void entitySpawn(CreatureSpawnEvent event)
	{
		if(event.getLocation().getWorld().getName().equals("Dungeon"))
		{
			if(event.getSpawnReason() == SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.CHUNK_GEN
					|| event.getSpawnReason() == SpawnReason.MOUNT|| event.getSpawnReason() == SpawnReason.JOCKEY
					|| event.getSpawnReason() == SpawnReason.SLIME_SPLIT)
			{
				event.setCancelled(true);
				return;
			}
		}
		
		if(event.getEntity().getType()==EntityType.ARMOR_STAND)
			return;
		
		if(event.getSpawnReason() == SpawnReason.SLIME_SPLIT &&
				event.getEntity().getCustomName() != null &&
				main.MainServerOption.MonsterNameMatching.containsKey(event.getEntity().getCustomName()))
			event.setCancelled(true);
		
		area.AreaAPI areaApi = new area.AreaAPI();
		String[] areaNameArrays = areaApi.getAreaName(event.getEntity());
		
		YamlLoader areaYaml = new YamlLoader();
		if(areaNameArrays != null)
		{
			if( ! areaApi.getAreaOption(areaNameArrays[0], (char) 3))
			{
				event.setCancelled(true);
				return;
			}
			else if( ! areaApi.getAreaOption(areaNameArrays[0], (char) 8))
			{
				areaYaml.getConfig("Area/AreaList.yml");
				String areaName = areaApi.getAreaName(event.getEntity())[0];
				String[] mobNameArrays = areaYaml.getConfigurationSection(areaName+".Monster").getKeys(false).toArray(new String[0]);
				int randomMob = 0;
				NumericUtil nu = new NumericUtil();
				for(int count = 0; count < 10; count++)
				{
					if(mobNameArrays.length != 0)
					{
						randomMob = nu.RandomNum(0, mobNameArrays.length-1);
						if(main.MainServerOption.MonsterList.containsKey(mobNameArrays[randomMob]))
						{
							new monster.MonsterSpawn().spawnMonster(event.getLocation(), mobNameArrays[randomMob], (byte) -1, null,(char) -1, false);
							break;
						}
						else
						{
							areaYaml.removeKey(areaName+".Monster."+mobNameArrays[randomMob]);
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
	
	public void createMonster(String monsterName)
	{
		ItemStack item = new ItemStack(Material.AIR);
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		
		monsterYaml.set(monsterName+".Name", monsterName);
		monsterYaml.set(monsterName+".Type", "좀비");
		monsterYaml.set(monsterName+".AI", "일반 행동");
		monsterYaml.set(monsterName+".EXP", 15);
		monsterYaml.set(monsterName+".Biome", "NULL");
		monsterYaml.set(monsterName+".HP", 20);
		monsterYaml.set(monsterName+".MIN_Money", 1);
		monsterYaml.set(monsterName+".MAX_Money", 10);
		monsterYaml.set(monsterName+".STR", 10);
		monsterYaml.set(monsterName+".DEX", 10);
		monsterYaml.set(monsterName+".INT", 10);
		monsterYaml.set(monsterName+".WILL", 10);
		monsterYaml.set(monsterName+".LUK", 10);
		monsterYaml.set(monsterName+".DEF", 0);
		monsterYaml.set(monsterName+".Protect", 0);
		monsterYaml.set(monsterName+".Magic_DEF", 0);
		monsterYaml.set(monsterName+".Magic_Protect", 0);
		monsterYaml.set(monsterName+".Head.DropChance", 0);
		monsterYaml.set(monsterName+".Head.Item", item);
		monsterYaml.set(monsterName+".Chest.DropChance", 0);
		monsterYaml.set(monsterName+".Chest.Item", item);
		monsterYaml.set(monsterName+".Leggings.DropChance", 0);
		monsterYaml.set(monsterName+".Leggings.Item", item);
		monsterYaml.set(monsterName+".Boots.DropChance", 0);
		monsterYaml.set(monsterName+".Boots.Item", item);
		monsterYaml.set(monsterName+".Hand.DropChance", 0);
		monsterYaml.set(monsterName+".Hand.Item", item);
		monsterYaml.set(monsterName+".OffHand.DropChance", 0);
		monsterYaml.set(monsterName+".OffHand.Item", item);
		monsterYaml.saveConfig();
		return;
	}

	public void stayLive(Entity e, boolean isStayLive)
	{
		if(e.getType()!=EntityType.ENDER_CRYSTAL && !e.isDead())
		{
			LivingEntity le = (LivingEntity) e;
			le.setRemoveWhenFarAway(isStayLive);
		}
	}
	
	public void spawnMonster(Location loc, String monsterName, byte dungeonSpawning, int[] xyzLoc, char group, boolean isStayLive)
	{
		if(monsterName.charAt(0)=='놂')
		{
			String type = monsterName.substring(1);
			monsterName = null;
			createCreature(type, loc, monsterName, dungeonSpawning, xyzLoc, group, isStayLive);
		}
		loc.add(0.5, 1, 0.5);
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String type = monsterYaml.getString(monsterName + ".Type");
		if(monsterYaml.getString(monsterName+".Name")!=null)
		{
			String aiString = monsterYaml.getString(monsterName + ".AI");
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
				monsterYaml.set(monsterName + ".AI", "일반 행동");
				monsterYaml.saveConfig();
			}
			if(!aiString.equals("일반 행동"))
			{
				LivingEntity entity = null;
				if(type.equals("좀비"))
					entity = (CraftZombie) CustomZombie.spawn(loc, ai);
				else if(type.equals("자이언트"))
					entity = (CraftGiant) CustomGiant.spawn(loc, ai);
				else if(type.equals("스켈레톤"))
					entity = (CraftSkeleton) CustomSkeleton.spawn(loc, ai);
				else if(type.equals("네더스켈레톤"))
					entity = (CraftWitherSkeleton) CustomWitherSkeleton.spawn(loc, ai);
				else if(type.equals("엔더맨"))
					entity = (CraftEnderman) CustomEnderman.spawn(loc, ai);
				else if(type.equals("크리퍼")||type.equals("번개크리퍼"))
					entity = (CraftCreeper) CustomCreeper.spawn(loc, ai);
				else if(type.equals("거미"))
					entity = (CraftSpider) CustomSpider.spawn(loc, ai);
				else if(type.equals("동굴거미"))
					entity = (CraftCaveSpider) CustomCaveSpider.spawn(loc, ai);
				else if(type.equals("좀벌레"))
					entity = (CraftSilverfish) CustomSilverFish.spawn(loc, ai);
				else if(type.equals("엔더진드기"))
					entity = (CraftEndermite) CustomEnderMite.spawn(loc, ai);
				else if(type.equals("블레이즈"))
					entity = (CraftBlaze) CustomBlaze.spawn(loc, ai);
				else if(type.equals("좀비피그맨"))
					entity = (CraftPigZombie) CustomPigZombie.spawn(loc, ai);
				else if(type.equals("마녀"))
					entity = (CraftWitch) CustomWitch.spawn(loc, ai);
				else if(type.equals("위더"))
					entity = (CraftWither) CustomWither.spawn(loc, ai);
				else if(type.equals("수호자"))
					entity = (CraftGuardian) CustomGuardian.spawn(loc, ai);
				else if(type.equals("북극곰"))
					entity = (CraftPolarBear) CustomPolarBear.spawn(loc, ai);
				else if(type.equals("엔더크리스탈"))
					createCreature(type, loc, monsterName, dungeonSpawning, xyzLoc, group, isStayLive);

				else if (type.equals("엘더가디언"))
					entity = (CraftElderGuardian) CustomElderGuardian.spawn(loc, ai);
				else if (type.equals("스트레이"))
					entity = (CraftStray) CustomStray.spawn(loc, ai);
				else if (type.equals("허스크"))
					entity = (CraftHusk) CustomHusk.spawn(loc, ai);
				else if (type.equals("주민좀비"))
					entity = (CraftVillagerZombie) CustomZombieVillager.spawn(loc, ai);
				else if (type.equals("소환사"))
					entity = (CraftEvoker) CustomEvoker.spawn(loc, ai);
				else if (type.equals("벡스"))
					entity = (CraftVex) CustomVex.spawn(loc, ai);
				else if (type.equals("변명자"))
					entity = (CraftVindicator) CustomVindicator.spawn(loc, ai);
				else if (type.equals("셜커"))
					entity = (Shulker) CustomShulker.spawn(loc, ai);
				// 몬스터 형태의 몹
				else if (type.equals("스켈레톤말"))
					entity = (LivingEntity) CustomSkeletonHorse.spawn(loc, ai);
				else if (type.equals("좀비말"))
					entity = (LivingEntity) CustomZombieHorse.spawn(loc, ai);
				else if (type.equals("당나귀"))
					entity = (LivingEntity) CustomDonkey.spawn(loc, ai);
				else if (type.equals("노새"))
					entity = (LivingEntity) CustomMule.spawn(loc, ai);
				else if (type.equals("인간"))
					entity = (LivingEntity) CustomHuman.spawn(loc, ai);
				else if (type.equals("박쥐"))
					entity = (LivingEntity) CustomBat.spawn(loc, ai);
				else if (type.equals("돼지"))
					entity = (LivingEntity) CustomPig.spawn(loc, ai);
				else if (type.equals("양"))
					entity = (LivingEntity) CustomSheep.spawn(loc, ai);
				else if (type.equals("소"))
					entity = (LivingEntity) CustomCow.spawn(loc, ai);
				else if (type.equals("닭"))
					entity = (LivingEntity) CustomChicken.spawn(loc, ai);
				else if (type.equals("오징어"))
					entity = (LivingEntity) CustomSquid.spawn(loc, ai);
				else if (type.equals("늑대"))
					entity = (LivingEntity) CustomWolf.spawn(loc, ai);
				else if (type.equals("버섯소"))
					entity = (LivingEntity) CustomMooshroom.spawn(loc, ai);
				else if (type.equals("눈사람"))
					entity = (LivingEntity) CustomSnowman.spawn(loc, ai);
				else if (type.equals("오셀롯"))
					entity = (LivingEntity) CustomOcelot.spawn(loc, ai);
				else if (type.equals("철골렘"))
					entity = (LivingEntity) CustomIronGolem.spawn(loc, ai);
				else if (type.equals("말"))
					entity = (LivingEntity) CustomHorse.spawn(loc, ai);
				else if (type.equals("토끼"))
					entity = (LivingEntity) CustomRabbit.spawn(loc, ai);
				else if (type.equals("라마"))
					entity = (LivingEntity) CustomLlama.spawn(loc, ai);
				else if (type.equals("주민"))
					entity = (LivingEntity) CustomVillager.spawn(loc, ai);

				if(entity!=null)
					entity = getEntity(entity, monsterName, dungeonSpawning, xyzLoc, group);
			}
			else
				createCreature(type, loc, monsterName, dungeonSpawning, xyzLoc, group, isStayLive);
		}
		return;
	}
	
	private void createCreature(String type, Location loc, String mob, byte dungeonSpawning, int[] xyzLoc, char group, boolean isStayLive)
	{
		switch(type)
		{
			case "좀비":{Zombie zombie = (Zombie) loc.getWorld().spawn(loc, Zombie.class);zombie = (Zombie) getEntity(zombie,mob, dungeonSpawning, xyzLoc, group);}break;
			case "자이언트":{Giant giant = (Giant) loc.getWorld().spawn(loc, Giant.class);giant = (Giant) getEntity(giant,mob, dungeonSpawning, xyzLoc, group);stayLive(giant, isStayLive);}break;
			case "스켈레톤":{Skeleton skeleton = (Skeleton) loc.getWorld().spawn(loc, Skeleton.class);skeleton = (Skeleton) getEntity(skeleton,mob, dungeonSpawning, xyzLoc, group);stayLive(skeleton, isStayLive);}break;
			case "네더스켈레톤":{WitherSkeleton skeleton = (WitherSkeleton) loc.getWorld().spawn(loc, WitherSkeleton.class);skeleton = (WitherSkeleton) getEntity(skeleton,mob, dungeonSpawning, xyzLoc, group);stayLive(skeleton, isStayLive);}break;
			case "엔더맨":{Enderman enderman = (Enderman) loc.getWorld().spawn(loc, Enderman.class);enderman = (Enderman) getEntity(enderman,mob, dungeonSpawning, xyzLoc, group);stayLive(enderman, isStayLive);}break;
			case "크리퍼":{Creeper creeper = (Creeper) loc.getWorld().spawn(loc, Creeper.class);creeper = (Creeper) getEntity(creeper,mob, dungeonSpawning, xyzLoc, group);stayLive(creeper, isStayLive);}break;
			case "번개크리퍼":{Creeper Lcreeper = (Creeper) loc.getWorld().spawn(loc, Creeper.class);Lcreeper = (Creeper) getEntity(Lcreeper,mob, dungeonSpawning, xyzLoc, group);stayLive(Lcreeper, isStayLive);}break;
			case "거미":{Spider spider = (Spider) loc.getWorld().spawn(loc, Spider.class);spider = (Spider) getEntity(spider,mob, dungeonSpawning, xyzLoc, group);stayLive(spider, isStayLive);}break;
			case "동굴거미":{CaveSpider cavespider = (CaveSpider) loc.getWorld().spawn(loc, CaveSpider.class);cavespider = (CaveSpider) getEntity(cavespider,mob, dungeonSpawning, xyzLoc, group);stayLive(cavespider, isStayLive);}break;
			case "좀벌레":{Silverfish silverfish = (Silverfish) loc.getWorld().spawn(loc, Silverfish.class);silverfish = (Silverfish) getEntity(silverfish,mob, dungeonSpawning, xyzLoc, group);stayLive(silverfish, isStayLive);}break;
			case "엔더진드기":{Endermite endermite = (Endermite) loc.getWorld().spawn(loc, Endermite.class);endermite = (Endermite) getEntity(endermite,mob, dungeonSpawning, xyzLoc, group);stayLive(endermite, isStayLive);}break;
			case "슬라임":case "초대형슬라임": case "특대슬라임": case "큰슬라임": case "보통슬라임": case "작은슬라임":{Slime Sslime = (Slime) loc.getWorld().spawn(loc, Slime.class);Sslime = (Slime) getEntity(Sslime,mob, dungeonSpawning, xyzLoc, group);stayLive(Sslime, isStayLive);}break;
			case "마그마큐브": case "초대형마그마큐브":case "특대마그마큐브": case "큰마그마큐브": case "보통마그마큐브": case "작은마그마큐브":{MagmaCube Smagmacube = (MagmaCube) loc.getWorld().spawn(loc, MagmaCube.class);Smagmacube = (MagmaCube) getEntity(Smagmacube,mob, dungeonSpawning, xyzLoc, group);stayLive(Smagmacube, isStayLive);}break;
			case "블레이즈":{Blaze blaze = (Blaze) loc.getWorld().spawn(loc, Blaze.class);blaze = (Blaze) getEntity(blaze,mob, dungeonSpawning, xyzLoc, group);stayLive(blaze, isStayLive);}break;
			case "가스트":{Ghast ghast = (Ghast) loc.getWorld().spawn(loc, Ghast.class);ghast = (Ghast) getEntity(ghast,mob, dungeonSpawning, xyzLoc, group);stayLive(ghast, isStayLive);}break;
			case "좀비피그맨":{PigZombie pigzombie = (PigZombie) loc.getWorld().spawn(loc, PigZombie.class);pigzombie = (PigZombie) getEntity(pigzombie,mob, dungeonSpawning, xyzLoc, group);stayLive(pigzombie, isStayLive);}break;
			case "마녀":{Witch witch = (Witch) loc.getWorld().spawn(loc, Witch.class);witch = (Witch) getEntity(witch,mob, dungeonSpawning, xyzLoc, group);stayLive(witch, isStayLive);}break;
			case "위더":{Wither wither = (Wither) loc.getWorld().spawn(loc, Wither.class);wither = (Wither) getEntity(wither,mob, dungeonSpawning, xyzLoc, group);stayLive(wither, isStayLive);}break;
			case "엔더드래곤":{EnderDragon ED = (EnderDragon) loc.getWorld().spawn(loc, EnderDragon.class);ED = (EnderDragon) getEntity(ED,mob, dungeonSpawning, xyzLoc, group);stayLive(ED, isStayLive);}break;
			case "엔더크리스탈":{EnderCrystal EC = (EnderCrystal) loc.getWorld().spawn(loc, EnderCrystal.class);EC = getEnderCrystal(EC,mob, dungeonSpawning, xyzLoc, group);stayLive(EC, isStayLive);}break;
			case "수호자":{Guardian guardian = (Guardian) loc.getWorld().spawn(loc, Guardian.class);guardian = (Guardian) getEntity(guardian,mob, dungeonSpawning, xyzLoc, group);stayLive(guardian, isStayLive);}break;
			case "양":{Sheep sheep = (Sheep) loc.getWorld().spawn(loc, Sheep.class);sheep = (Sheep) getEntity(sheep,mob, dungeonSpawning, xyzLoc, group);stayLive(sheep, isStayLive);}break;
			case "소":{Cow cow = (Cow) loc.getWorld().spawn(loc, Cow.class);cow = (Cow) getEntity(cow,mob, dungeonSpawning, xyzLoc, group);stayLive(cow, isStayLive);}break;
			case "돼지":{Pig pig = (Pig) loc.getWorld().spawn(loc, Pig.class);pig = (Pig) getEntity(pig,mob, dungeonSpawning, xyzLoc, group);stayLive(pig, isStayLive);}break;
			case "말":{Horse horse = (Horse) loc.getWorld().spawn(loc, Horse.class);horse = (Horse) getEntity(horse,mob, dungeonSpawning, xyzLoc, group);stayLive(horse, isStayLive);}break;
			case "토끼":{Rabbit rabbit = (Rabbit) loc.getWorld().spawn(loc, Rabbit.class);rabbit = (Rabbit) getEntity(rabbit,mob, dungeonSpawning, xyzLoc, group);stayLive(rabbit, isStayLive);}break;
			case "오셀롯":{Ocelot oceleot = (Ocelot) loc.getWorld().spawn(loc, Ocelot.class);oceleot = (Ocelot) getEntity(oceleot,mob, dungeonSpawning, xyzLoc, group);stayLive(oceleot, isStayLive);}break;
			case "늑대":{Wolf wolf = (Wolf) loc.getWorld().spawn(loc, Wolf.class);wolf = (Wolf) getEntity(wolf,mob, dungeonSpawning, xyzLoc, group);stayLive(wolf, isStayLive);}break;
			case "닭":{Chicken chicken = (Chicken) loc.getWorld().spawn(loc, Chicken.class);chicken = (Chicken) getEntity(chicken,mob, dungeonSpawning, xyzLoc, group);stayLive(chicken, isStayLive);}break;
			case "버섯소":{MushroomCow Mcow = (MushroomCow) loc.getWorld().spawn(loc, MushroomCow.class);Mcow = (MushroomCow) getEntity(Mcow,mob, dungeonSpawning, xyzLoc, group);stayLive(Mcow, isStayLive);}break;
			case "박쥐":{Bat bat = (Bat) loc.getWorld().spawn(loc, Bat.class);bat = (Bat) getEntity(bat,mob, dungeonSpawning, xyzLoc, group);stayLive(bat, isStayLive);}break;
			case "오징어":{Squid squid = (Squid) loc.getWorld().spawn(loc, Squid.class);squid = (Squid) getEntity(squid,mob, dungeonSpawning, xyzLoc, group);stayLive(squid, isStayLive);}break;
			case "주민":{Villager villager = (Villager) loc.getWorld().spawn(loc, Villager.class);villager = (Villager) getEntity(villager,mob, dungeonSpawning, xyzLoc, group);stayLive(villager, isStayLive);}break;
			case "눈사람":{Snowman snowman = (Snowman) loc.getWorld().spawn(loc, Snowman.class);snowman = (Snowman) getEntity(snowman,mob, dungeonSpawning, xyzLoc, group);stayLive(snowman, isStayLive);}break;
			case "철골렘":{IronGolem golem = (IronGolem) loc.getWorld().spawn(loc, IronGolem.class);golem = (IronGolem) getEntity(golem,mob, dungeonSpawning, xyzLoc, group);stayLive(golem, isStayLive);}break;
			case "셜커":{Shulker shulker = (Shulker) loc.getWorld().spawn(loc, Shulker.class);shulker = (Shulker) getEntity(shulker,mob, dungeonSpawning, xyzLoc, group);stayLive(shulker, isStayLive);}break;
			case "북극곰":{PolarBear polarBear = (PolarBear) loc.getWorld().spawn(loc, PolarBear.class);polarBear = (PolarBear) getEntity(polarBear,mob, dungeonSpawning, xyzLoc, group);stayLive(polarBear, isStayLive);}break;
			
			case "엘더가디언":{ElderGuardian elderGuardian = (ElderGuardian) loc.getWorld().spawn(loc, ElderGuardian.class);elderGuardian = (ElderGuardian) getEntity(elderGuardian,mob, dungeonSpawning, xyzLoc, group);stayLive(elderGuardian, isStayLive);}break;
			case "스트레이":{Stray stray = (Stray) loc.getWorld().spawn(loc, Stray.class);stray = (Stray) getEntity(stray,mob, dungeonSpawning, xyzLoc, group);stayLive(stray, isStayLive);}break;
			case "허스크":{Husk husk = (Husk) loc.getWorld().spawn(loc, Husk.class);husk = (Husk) getEntity(husk,mob, dungeonSpawning, xyzLoc, group);stayLive(husk, isStayLive);}break;
			case "주민좀비":{ZombieVillager zombiVillager = (ZombieVillager) loc.getWorld().spawn(loc, ZombieVillager.class);zombiVillager = (ZombieVillager) getEntity(zombiVillager,mob, dungeonSpawning, xyzLoc, group);stayLive(zombiVillager, isStayLive);}break;
			case "소환사":{Evoker evoker = (Evoker) loc.getWorld().spawn(loc, Evoker.class);evoker = (Evoker) getEntity(evoker,mob, dungeonSpawning, xyzLoc, group);stayLive(evoker, isStayLive);}break;
			case "벡스":{Vex vex = (Vex) loc.getWorld().spawn(loc, Vex.class);vex = (Vex) getEntity(vex,mob, dungeonSpawning, xyzLoc, group);stayLive(vex, isStayLive);}break;
			case "변명자":{Vindicator polarBear = (Vindicator) loc.getWorld().spawn(loc, Vindicator.class);polarBear = (Vindicator) getEntity(polarBear,mob, dungeonSpawning, xyzLoc, group);stayLive(polarBear, isStayLive);}break;
			case "스켈레톤말":{SkeletonHorse skeletonHorse = (SkeletonHorse) loc.getWorld().spawn(loc, SkeletonHorse.class);skeletonHorse = (SkeletonHorse) getEntity(skeletonHorse,mob, dungeonSpawning, xyzLoc, group);stayLive(skeletonHorse, isStayLive);}break;
			case "좀비말":{ZombieHorse zombieHorse = (ZombieHorse) loc.getWorld().spawn(loc, ZombieHorse.class);zombieHorse = (ZombieHorse) getEntity(zombieHorse,mob, dungeonSpawning, xyzLoc, group);stayLive(zombieHorse, isStayLive);}break;
			case "당나귀":{Donkey donkey = (Donkey) loc.getWorld().spawn(loc, Donkey.class);donkey = (Donkey) getEntity(donkey,mob, dungeonSpawning, xyzLoc, group);stayLive(donkey, isStayLive);}break;
			case "노새":{Mule mule = (Mule) loc.getWorld().spawn(loc, Mule.class);mule = (Mule) getEntity(mule,mob, dungeonSpawning, xyzLoc, group);stayLive(mule, isStayLive);}break;
			case "인간":{Player player = (Player) loc.getWorld().spawn(loc, Player.class);player = (Player) getEntity(player,mob, dungeonSpawning, xyzLoc, group);stayLive(player, isStayLive);}break;
			case "라마":{Llama llama = (Llama) loc.getWorld().spawn(loc, Llama.class);llama = (Llama) getEntity(llama,mob, dungeonSpawning, xyzLoc, group);stayLive(llama, isStayLive);}break;
			//case "휴먼":{HumanEntity human = (HumanEntity) loc.getWorld().spawn(loc, Player.class);human = (HumanEntity) getEntity(human,mob, DungeonSpawning, XYZLoc, Group);StayLive(human, isStayLive);}break;
		}
	}

	private LivingEntity getEntity(LivingEntity monster, String monsterName, byte dungeonSpawning, int[] xyzLoc, char group)
	{
		if(monsterName!=null)
		{
			YamlLoader monsterYaml = new YamlLoader();
			monsterYaml.getConfig("Monster/MonsterList.yml");
			monster.setCustomName(monsterYaml.getString(monsterName + ".Name").replace("&", "§"));
			monster.setCustomNameVisible(true);
			String[] parts = {"Head", "Chest", "Leggings", "Boots", "Hand", "OffHand"};
			ItemStack item = null;
			ItemStack[] equipments = new ItemStack[parts.length];
			for(int count = 0; count < parts.length; count++)
			{
				item = monsterYaml.getItemStack(monsterName+"." + parts[count] + ".Item");
				if(item != null && ! (item.hasItemMeta() && item.getItemMeta().hasLore() &&
						ChatColor.stripColor(item.getItemMeta().getLore().get(0)).equals("이곳에 아이템을 넣어 주세요.")))
					equipments[count] = item;
				else
					equipments[count] = null;
			}
			monster.getEquipment().setHelmet(equipments[0]);
			monster.getEquipment().setChestplate(equipments[1]);
			monster.getEquipment().setLeggings(equipments[2]);
			monster.getEquipment().setBoots(equipments[3]);
			monster.getEquipment().setItemInMainHand(equipments[4]);
			monster.getEquipment().setItemInOffHand(equipments[5]);
			
			YamlLoader configYaml = new YamlLoader();
		    configYaml.getConfig("config.yml");
			monster.getEquipment().setHelmetDropChance((float)(monsterYaml.getInt(monsterName+".Head.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			monster.getEquipment().setChestplateDropChance((float)(monsterYaml.getInt(monsterName+".Chest.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			monster.getEquipment().setLeggingsDropChance((float)(monsterYaml.getInt(monsterName+".Leggings.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			monster.getEquipment().setBootsDropChance((float)(monsterYaml.getInt(monsterName+".Boots.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			monster.getEquipment().setItemInMainHandDropChance((float)(monsterYaml.getInt(monsterName+".Hand.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			monster.getEquipment().setItemInOffHandDropChance((float)(monsterYaml.getInt(monsterName+".OffHand.DropChance")*configYaml.getInt("Event.DropChance")/1000.0));
			
			if(monster.getType() == EntityType.CREEPER)
			{
				if(monsterYaml.getString(monsterName+".Type").equalsIgnoreCase("번개크리퍼"))
					  ((Creeper) monster).setPowered(true);
			}
			else if(monster.getType() == EntityType.SLIME)
			{
				switch(monsterYaml.getString(monsterName + ".Type"))
				{
					case "작은슬라임" : ((Slime) monster).setSize(1);break;
					case "보통슬라임" : ((Slime) monster).setSize(2);break;
					case "큰슬라임" : ((Slime) monster).setSize(4);break;
					case "특대슬라임" : ((Slime) monster).setSize(16);break;
					case "초대형슬라임" : ((Slime) monster).setSize(64);break;
					default : ((Slime) monster).setSize(new util.NumericUtil().RandomNum(1, 4));break;
				}
			}
			else if(monster.getType() == EntityType.MAGMA_CUBE)
			{
				switch(monsterYaml.getString(monsterName + ".Type"))
				{
					case "작은마그마큐브" : ((MagmaCube) monster).setSize(1);break;
					case "보통마그마큐브" : ((MagmaCube) monster).setSize(2);break;
					case "큰마그마큐브" : ((MagmaCube) monster).setSize(4);break;
					case "특대마그마큐브" : ((MagmaCube) monster).setSize(16);break;
					case "초대형마그마큐브" : ((MagmaCube) monster).setSize(64);break;
					default : ((MagmaCube) monster).setSize(new util.NumericUtil().RandomNum(1, 4));break;
				}
			}
			if(monsterYaml.contains(monsterName+".Potion"))
			{
				if(monsterYaml.getInt(monsterName+".Potion.Regenerate")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.REGENERATION, 50000, monsterYaml.getInt(monsterName+".Potion.Regenerate")));
				if(monsterYaml.getInt(monsterName+".Potion.Poison")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.POISON, 50000, monsterYaml.getInt(monsterName+".Potion.Poison")));
				if(monsterYaml.getInt(monsterName+".Potion.Speed")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.SPEED, 50000, monsterYaml.getInt(monsterName+".Potion.Speed")));
				if(monsterYaml.getInt(monsterName+".Potion.Slow")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.SLOW, 50000, monsterYaml.getInt(monsterName+".Potion.Slow")));
				if(monsterYaml.getInt(monsterName+".Potion.Strength")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.INCREASE_DAMAGE, 50000, monsterYaml.getInt(monsterName+".Potion.Strength")));
				if(monsterYaml.getInt(monsterName+".Potion.Weak")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.WEAKNESS, 50000, monsterYaml.getInt(monsterName+".Potion.Weak")));
				if(monsterYaml.getInt(monsterName+".Potion.JumpBoost")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.JUMP, 50000, monsterYaml.getInt(monsterName+".Potion.JumpBoost")));
				if(monsterYaml.getInt(monsterName+".Potion.FireRegistance")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.FIRE_RESISTANCE, 50000, monsterYaml.getInt(monsterName+".Potion.FireRegistance")));
				if(monsterYaml.getInt(monsterName+".Potion.WaterBreath")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.WATER_BREATHING, 50000, monsterYaml.getInt(monsterName+".Potion.WaterBreath")));
				if(monsterYaml.getInt(monsterName+".Potion.Invisible")!=0)
					monster.addPotionEffect(PottionBuff.getPotionEffect(PotionEffectType.INVISIBILITY, 50000, monsterYaml.getInt(monsterName+".Potion.Invisible")));
			}
			Damageable m = monster;
			m.setMaxHealth(monsterYaml.getInt(monsterName + ".HP")+0.5);
			m.setHealth(m.getMaxHealth());
		}
		
		if(dungeonSpawning != -1)
		{
			YamlLoader monsterYaml = new YamlLoader();
			monsterYaml.getConfig("Monster/MonsterList.yml");
			/*
    		몬스터 식별 코드 이후 오는 GroupNumber 코드는 각각의 몬스터 그룹을 설정하기 위함으로,
    		방 내에 그룹 코드가 붙은 몬스터가 반경 20 이내에 없을 경우, 다음 웨이브가 나오거나
    		문이 열리게 된다. 몬스터 그룹 코드는 0 ~ f 까지 존재한다.
			 */
			if(monsterName==null)
			{
				switch(dungeonSpawning)
				{
				case 1://1번 == 일반
					monster.setCustomName("§2§0§2§e§"+group+"§f");
					break;
				case 2://2번 == 다음 웨이브 존재
					monster.setCustomName("§2§0§2§1§"+group+"§f");
					break;
				case 3://3번 == 열쇠 가진 놈
					monster.setCustomName("§2§0§2§4§"+group+"§f");
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					monster.setCustomName("§2§0§2§2§"+group+"§f");
					break;
				}
			}
			else
			{
				String a = monsterYaml.getString(monsterName + ".Name").replace("&", "§");
				switch(dungeonSpawning)
				{
				case 1://1번 == 일반
					monster.setCustomName("§2§0§2§e§"+group+"§f"+a);
					break;
				case 2://2번 == 다음 웨이브 존재
					monster.setCustomName("§2§0§2§1§"+group+"§f"+a);
					break;
				case 3://3번 == 열쇠 가진 놈
					monster.setCustomName("§2§0§2§4§"+group+"§f"+a);
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					monster.setCustomName("§2§0§2§2§"+group+"§f"+a);
					break;
				}
			}
			ItemStack helmet = monsterYaml.getItemStack(monsterName+".Head.Item");
			int itemnumber = 30;
			if(helmet!=null && helmet.getType() != Material.AIR)
			{
				if(helmet.hasItemMeta())
				{
					ItemMeta im = helmet.getItemMeta();
					im.setLore(Arrays.asList("xyz:"+xyzLoc[0]+","+xyzLoc[1]+","+xyzLoc[2]));
					helmet.setItemMeta(im);
					monster.getEquipment().setHelmet(helmet);
				}
				else
				{
					ItemStack item = new MaterialData(267, (byte) 0).toItemStack(1);
					ItemMeta im = item.getItemMeta();
					im.setLore(Arrays.asList("xyz:"+xyzLoc[0]+","+xyzLoc[1]+","+xyzLoc[2]));
					helmet.setItemMeta(im);
					monster.getEquipment().setHelmet(helmet);
				}
			}
			else
			{
				ItemStack item = new ItemStack(itemnumber);
				item.setAmount(1);
				ItemMeta im = item.getItemMeta();
				im.setLore(Arrays.asList("xyz:"+xyzLoc[0]+","+xyzLoc[1]+","+xyzLoc[2]));
				item.setItemMeta(im);
				monster.getEquipment().setHelmet(item);
			}
			monster.getEquipment().setHelmetDropChance(0.00000000000000000F);
		}
		return monster;
	}
	
	private EnderCrystal getEnderCrystal(EnderCrystal monster, String mob, byte dungeonSpawning, int[] xyzLoc, char group)
	{
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		if(dungeonSpawning != -1)
		{
			/*
    		몬스터 식별 코드 이후 오는 GroupNumber 코드는 각각의 몬스터 그룹을 설정하기 위함으로,
    		방 내에 그룹 코드가 붙은 몬스터가 반경 20 이내에 없을 경우, 다음 웨이브가 나오거나
    		문이 열리게 된다. 몬스터 그룹 코드는 0 ~ f 까지 존재한다.
			 */
			if(mob.charAt(0)=='놂')
			{
				switch(dungeonSpawning)
				{
				case 1://1번 == 일반
					monster.setCustomName("§2§0§2§e§"+group+"§f");
					break;
				case 2://2번 == 다음 웨이브 존재
					monster.setCustomName("§2§0§2§1§"+group+"§f");
					break;
				case 3://3번 == 열쇠 가진 놈
					monster.setCustomName("§2§0§2§4§"+group+"§f");
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					monster.setCustomName("§2§0§2§2§"+group+"§f");
					break;
				}
			}
			else
			{
				String a = monsterYaml.getString(mob + ".Name").replace("&", "§");
				switch(dungeonSpawning)
				{
				case 1://1번 == 일반
					monster.setCustomName("§2§0§2§e§"+group+"§f"+a);
					break;
				case 2://2번 == 다음 웨이브 존재
					monster.setCustomName("§2§0§2§1§"+group+"§f"+a);
					break;
				case 3://3번 == 열쇠 가진 놈
					monster.setCustomName("§2§0§2§4§"+group+"§f"+a);
					break;
				case 4://4번 == 보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
					monster.setCustomName("§2§0§2§2§"+group+"§f"+a);
					break;
				}
			}
		}
		else
			monster.setCustomName(monsterYaml.getString(mob + ".Name").replace("&", "§"));
		monster.setCustomNameVisible(true);
		return monster;
	}

	
	private static void stack(String display, int id, int data, int amount, List<String> lore, int loc, Inventory inventory)
	{
		ItemStack item = new ItemStack(id);
		item.setAmount(amount);
		item.setDurability((short) data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(display);
		im.setLore(lore);
		item.setItemMeta(im);
		inventory.setItem(loc, item);
		return;
	}
	
	public void spawnEggGive(Player player, String mob)
	{
		ItemStack item = new ItemStack(383);
		item.setAmount(1);
		item.setDurability((short)0);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§c"+mob);
		im.addEnchant(Enchantment.DURABILITY, 1, true);
		im.setLore(Arrays.asList("§8"+mob+"스폰 에그"));
		item.setItemMeta(im);
		player.getInventory().addItem(item);
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
	
	public short getMonsterID(String monsterType)
	{
		switch(monsterType)
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

	public String getMonsterCustomName(String monsterType)
	{
		switch(monsterType)
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