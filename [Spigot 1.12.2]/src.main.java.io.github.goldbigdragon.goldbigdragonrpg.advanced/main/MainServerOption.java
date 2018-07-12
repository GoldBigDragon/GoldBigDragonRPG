package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import area.AreaObject;
import corpse.CorpseAPI;
import fr.tenebrae.EntityAppearence;
import fr.tenebrae.EntityRegistrer;
import monster.MonsterObject;
import monster.ai.*;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.EntityBat;
import net.minecraft.server.v1_12_R1.EntityBlaze;
import net.minecraft.server.v1_12_R1.EntityCaveSpider;
import net.minecraft.server.v1_12_R1.EntityChicken;
import net.minecraft.server.v1_12_R1.EntityCow;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.EntityEnderman;
import net.minecraft.server.v1_12_R1.EntityEndermite;
import net.minecraft.server.v1_12_R1.EntityEvoker;
import net.minecraft.server.v1_12_R1.EntityGiantZombie;
import net.minecraft.server.v1_12_R1.EntityGuardian;
import net.minecraft.server.v1_12_R1.EntityGuardianElder;
import net.minecraft.server.v1_12_R1.EntityHorse;
import net.minecraft.server.v1_12_R1.EntityHorseDonkey;
import net.minecraft.server.v1_12_R1.EntityHorseMule;
import net.minecraft.server.v1_12_R1.EntityHorseSkeleton;
import net.minecraft.server.v1_12_R1.EntityHorseZombie;
import net.minecraft.server.v1_12_R1.EntityIronGolem;
import net.minecraft.server.v1_12_R1.EntityLlama;
import net.minecraft.server.v1_12_R1.EntityMushroomCow;
import net.minecraft.server.v1_12_R1.EntityOcelot;
import net.minecraft.server.v1_12_R1.EntityPig;
import net.minecraft.server.v1_12_R1.EntityPigZombie;
import net.minecraft.server.v1_12_R1.EntityPolarBear;
import net.minecraft.server.v1_12_R1.EntityRabbit;
import net.minecraft.server.v1_12_R1.EntitySheep;
import net.minecraft.server.v1_12_R1.EntityShulker;
import net.minecraft.server.v1_12_R1.EntitySilverfish;
import net.minecraft.server.v1_12_R1.EntitySkeleton;
import net.minecraft.server.v1_12_R1.EntitySkeletonStray;
import net.minecraft.server.v1_12_R1.EntitySkeletonWither;
import net.minecraft.server.v1_12_R1.EntitySnowman;
import net.minecraft.server.v1_12_R1.EntitySpider;
import net.minecraft.server.v1_12_R1.EntitySquid;
import net.minecraft.server.v1_12_R1.EntityVex;
import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.EntityVindicator;
import net.minecraft.server.v1_12_R1.EntityWitch;
import net.minecraft.server.v1_12_R1.EntityWither;
import net.minecraft.server.v1_12_R1.EntityWolf;
import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.EntityZombieHusk;
import net.minecraft.server.v1_12_R1.EntityZombieVillager;
import party.PartyDataManager;
import party.PartyObject;
import user.UserObject;
import util.YamlLoader;

public class MainServerOption
{
	public static ArrayList<String> dungeonTheme = new ArrayList<>();
	
	public static Economy economy = null;
	
	public static String statSTR = "체력";
	public static String statDEX = "솜씨";
	public static String statINT = "지력";
	public static String statWILL = "의지";
	public static String statLUK = "행운";
	public static String money = "§e§lGold";
	public static String damage = "대미지";
	public static String magicDamage = "마법 대미지";
	
	public static byte eventSkillPoint = 1;
	public static byte eventStatPoint = 1;
	public static byte eventExp = 1;
	public static byte eventDropChance = 1;
	public static byte eventProficiency = 1;
	
	public static byte levelUpPerSkillPoint = 1;
	public static byte levelUpPerStatPoint = 10;
	
	
	public static int maxLevel = 100;
	public static int maxSTR = 1500;
	public static int maxDEX = 1500;
	public static int maxINT = 1500;
	public static int maxWILL = 1500;
	public static int maxLUK = 1500;
	public static int expShareDistance = 50;
	public static long maxDropMoney = 100000;
	
	public static String STR_Lore = "%enter%§7 "+MainServerOption.statSTR+"은 플레이어의%enter%§7 물리적 공격력을%enter%§7 상승시켜 줍니다.%enter%";
	public static String DEX_Lore = "%enter%§7 "+MainServerOption.statDEX+"는 플레이어의%enter%§7 원거리 공격력을%enter%§7 상승시켜 줍니다.%enter%";
	public static String INT_Lore = "%enter%§7 "+MainServerOption.statINT+"은 플레이어가%enter%§7 사용하는 스킬 중%enter%§7 "+MainServerOption.statINT+" 영향을 받는%enter%§7 스킬 공격력을%enter%§7 상승시켜 줍니다.%enter%";
	public static String WILL_Lore = "%enter%§7 "+MainServerOption.statWILL+"는 플레이어의%enter%§7 크리티컬 및 스킬 중%enter%§7 "+MainServerOption.statWILL+" 영향을 받는%enter%§7 스킬 공격력을%enter%§7 상승시켜 줍니다.%enter%";
	public static String LUK_Lore = "%enter%§7 "+MainServerOption.statLUK+"은 플레이어에게%enter%§7 뜻하지 않은 일이 일어날%enter%§7 확률을 증가시킵니다.%enter%";

	public static boolean MoneySystem = false;
	public static short Money1ID = 348;
	public static byte Money1DATA = 0;
	public static short Money2ID = 371;
	public static byte Money2DATA = 0;
	public static short Money3ID = 147;
	public static byte Money3DATA = 0;
	public static short Money4ID = 266;
	public static byte Money4DATA = 0;
	public static short Money5ID = 41;
	public static byte Money5DATA = 0;
	public static short Money6ID = 41;
	public static byte Money6DATA = 0;
	
	public static String serverUpdate = "2018-07-12-01:44";
	public static String serverVersion = "Advanced";
	private static String updateCheckURL = "https://goldbigdragon.github.io/1_12.html";
	public static String currentServerUpdate = "2018-07-12-01:44";
	public static String currentServerVersion = "Advanced";
	
	public static java.util.Map<Long, PartyObject> party = new LinkedHashMap<>();
	public static java.util.Map<Player, Long> partyJoiner = new LinkedHashMap<>();
	
	public static HashMap<Player, Location> catchedLocation1 = new HashMap<>();
	public static HashMap<Player, Location> catchedLocation2 = new HashMap<>();
	
	public static HashMap<Player, String> PlayerUseSpell = new HashMap<>();
	public static HashMap<Player, ItemStack> PlayerlastItem = new HashMap<>();

	public static HashMap<String, ArrayList<AreaObject>> AreaList = new HashMap<>();
	public static HashMap<Player, String> PlayerCurrentArea = new HashMap<>();
	public static HashMap<String, UserObject> PlayerList = new HashMap<>();
	public static HashMap<String, MonsterObject> MonsterList = new HashMap<>();
	public static HashMap<String, String> MonsterNameMatching = new HashMap<>();
		
	public static boolean MagicSpellsCatched = false;
	public static boolean MagicSpellsEnable = false;
	public static boolean CitizensCatched = false;
	
	public static boolean Mapping = false;
	public static boolean AntiExplode = true;
	public static boolean PVP = true;
	public static boolean removeMonsterDefaultDrops = false;
	
	public static boolean dualWeapon = true;
	
	public static ItemStack DeathRescue = null;
	public static ItemStack DeathRevive = null;
	
	
	
	public void initialize()
	{
	  	new area.AreaAPI().addAreaList();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 영역 정보 로드");
		Object[] players = Bukkit.getOnlinePlayers().toArray();
		for(int count = 0; count < players.length; count++)
		{
			Player p = ((Player)players[count]);
			new UserObject(p);
			if(new area.AreaAPI().SearchAreaName(p.getLocation()) != null)
				PlayerCurrentArea.put(p, new area.AreaAPI().SearchAreaName(p.getLocation())[0].toString());
			if(PlayerList.get(p.getUniqueId().toString()).isDeath())
				new CorpseAPI().createCorpse(p);
		}
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 플레이어 정보 로드");
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String[] keyList = monsterYaml.getKeys().toArray(new String[0]);
		for(int count = 0; count < keyList.length; count++)
		{
			MonsterObject monsterObject = new MonsterObject(keyList[count], monsterYaml.getString(keyList[count]+".Name"), monsterYaml.getLong(keyList[count]+".EXP"), monsterYaml.getInt(keyList[count]+".HP"), monsterYaml.getInt(keyList[count]+".MIN_Money"), monsterYaml.getInt(keyList[count]+".MAX_Money"), monsterYaml.getInt(keyList[count]+".STR"), monsterYaml.getInt(keyList[count]+".DEX"), monsterYaml.getInt(keyList[count]+".INT"), monsterYaml.getInt(keyList[count]+".WILL"), monsterYaml.getInt(keyList[count]+".LUK"), monsterYaml.getInt(keyList[count]+".DEF"), monsterYaml.getInt(keyList[count]+".Protect"), monsterYaml.getInt(keyList[count]+".Magic_DEF"), monsterYaml.getInt(keyList[count]+".Magic_Protect"));
			MonsterList.put(keyList[count], monsterObject);
			MonsterNameMatching.put(monsterYaml.getString(keyList[count]+".Name"), keyList[count]);
		}
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 몬스터 정보 로드");
		
	  	File musicFolder = new File(Main.plugin.getDataFolder().getAbsolutePath() + "/NoteBlockSound/");
		if(!musicFolder.exists())
			musicFolder.mkdirs();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 NBS 파일 로드");
	  	new main.MainConfig().checkConfig();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 콘피그 정보 로드");
	  	if(monsterYaml.isExit("Skill/SkillList.yml") == false)
	  	  new skill.SkillConfig().CreateNewSkillList();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 스킬 정보 로드");
	  	if(monsterYaml.isExit("Skill/JobList.yml") == false)
	  		new skill.SkillConfig().CreateNewJobList();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 직업 정보 로드");
	  	if(monsterYaml.isExit("ETC/NewBie.yml") == false)
	  		new admin.NewBieConfig().createNewConfig();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 초보자 정보 로드");
	  	
	  	new PartyDataManager().loadParty();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 파티 정보 로드");

	  	YamlLoader worldYaml = new YamlLoader();
		worldYaml.getConfig("WorldList.yml");
		String[] worldname = worldYaml.getKeys().toArray(new String[0]);
		for(int count = 0; count < worldYaml.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count]) == null)
				WorldCreator.name(worldname[count]).createWorld();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 월드 정보 로드");

		new servertick.ServerTickMain(Main.plugin);
		new servertick.ServerTickScheduleManager().loadCategoriFile();
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 틱 정보 로드");
		
		if(Bukkit.getServer().getOnlineMode()==false)
		  	Bukkit.getConsoleSender().sendMessage("§c비 정품 서버에서는 일부 기능이 정상적인 실행이 되지 않을수도 있습니다.");

		if(Bukkit.getWorld("Dungeon") == null)
		{
			WorldCreator.name("Dungeon").type(WorldType.FLAT).generateStructures(false).createWorld();
			Block block = null;
			for(int count = 0; count < 21; count++)
			{
				for(int count2 = 0; count2 < 21; count2++)
				{
					byte id = 1;
					if(count == 1||count2 == 1||count == 7||count2 == 7||count == 14||count2 == 14||count == 20||count2 == 20)
						id=89;
					block = new Location(Bukkit.getWorld("Dungeon"), -100+count, 30, -100+count2).getBlock();
					block.setTypeIdAndData(id, (byte) 0, true);
					block = new Location(Bukkit.getWorld("Dungeon"), -100+count, 42, -100+count2).getBlock();
					block.setTypeIdAndData(id, (byte) 0, true);
				}
			}
			for(int count = 0; count < 21; count++)
			{
				for(int count2 = 0; count2 < 12; count2++)
				{
					byte id = 1;
					if(count2 == 1||count2 == 11)
						id=89;
					block = new Location(Bukkit.getWorld("Dungeon"), -100+count, 30+count2, -100).getBlock();
					block.setTypeIdAndData(id, (byte) 0, true);
					block = new Location(Bukkit.getWorld("Dungeon"), -100, 30+count2, -100+count).getBlock();
					block.setTypeIdAndData(id, (byte) 0, true);
					block = new Location(Bukkit.getWorld("Dungeon"), -100+count, 30+count2, -79).getBlock();
					block.setTypeIdAndData(id, (byte) 0, true);
					block = new Location(Bukkit.getWorld("Dungeon"), -79, 30+count2, -100+count).getBlock();
					block.setTypeIdAndData(id, (byte) 0, true);
				}
			}
			block = new Location(Bukkit.getWorld("Dungeon"), -100+10, 31, -100+11).getBlock();
			block.setTypeIdAndData(138, (byte) 0, true);
			block = new Location(Bukkit.getWorld("Dungeon"), -100+11, 31, -100+11).getBlock();
			block.setTypeIdAndData(138, (byte) 0, true);
			block = new Location(Bukkit.getWorld("Dungeon"), -100+10, 31, -100+10).getBlock();
			block.setTypeIdAndData(138, (byte) 0, true);
			block = new Location(Bukkit.getWorld("Dungeon"), -100+11, 31, -100+10).getBlock();
			block.setTypeIdAndData(138, (byte) 0, true);
			
			YamlLoader dungeonYaml = new YamlLoader();
			dungeonYaml.getConfig("Dungeon/DungeonData.yml");
			dungeonYaml.set("StartPoint.X", 1000);
			dungeonYaml.set("StartPoint.Y", 30);
			dungeonYaml.set("StartPoint.Z", 1000);
			dungeonYaml.saveConfig();
		}
		else
		{
			Iterator<Entity> entityList = Bukkit.getWorld("Dungeon").getEntities().iterator();
			boolean isPlayerExist = false;
			while(entityList.hasNext())
			{
				Entity entity = (entityList.next());
				if(entity.getType()==EntityType.PLAYER)
				{
					Player p = (Player) entity;
					if(p.isOnline())
					{
						isPlayerExist = true;
						break;
					}
				}
			}
			if(isPlayerExist==false)
			{
				while(entityList.hasNext())
					entityList.next().remove();
				YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/DungeonData.yml");
				if(dungeonYaml.getLong("StartPoint.X")>=50000)
				{
					dungeonYaml.set("StartPoint.X", 1000);
					dungeonYaml.set("StartPoint.Y", 30);
					dungeonYaml.set("StartPoint.Z", 1000);
					dungeonYaml.saveConfig();
				}
			}
		}
		File directory = new File(Main.plugin.getDataFolder() + "\\Dungeon\\Schematic"); 
		if(!directory.exists())
			directory.mkdir();
		File[] fileList = directory.listFiles(); 
		try
		{
			for(int count = 0 ; count < fileList.length ; count++)
				if(!fileList[count].isFile())
				{
					File innerDirectory = new File(Main.plugin.getDataFolder() + "\\Dungeon\\Schematic\\"+fileList[count].getName()); 
					File[] schematicList = innerDirectory.listFiles();
					if(schematicList.length != 25)
					{
						ArrayList<String> dungeonFile = new ArrayList<>();
						for(int count2 = 0 ; count2 < schematicList.length ; count2++)
							dungeonFile.add(schematicList[count2].getName());
						Bukkit.getConsoleSender().sendMessage("§c[Fail] " + fileList[count].getName()+" 던전 테마를 사용하려면 아래 구성물들이 더 필요합니다!");
						if(!dungeonFile.contains("Boss.schematic"))
							Bukkit.getConsoleSender().sendMessage("§c　 Boss.schematic");
						for(int count2 = 0 ; count2 < 4 ; count2++)
							if(!dungeonFile.contains("Closed_Door"+count2+".schematic"))
								Bukkit.getConsoleSender().sendMessage("§c　 Closed_Door"+count2+".schematic");
						if(!dungeonFile.contains("CrossRoad.schematic"))
							Bukkit.getConsoleSender().sendMessage("§c　 CrossRoad.schematic");
						for(int count2 = 0 ; count2 < 4 ; count2++)
							if(!dungeonFile.contains("Door"+count2+".schematic"))
								Bukkit.getConsoleSender().sendMessage("§c　 Door"+count2+".schematic");
						for(int count2 = 0 ; count2 < 4 ; count2++)
							if(!dungeonFile.contains("LRoad"+count2+".schematic"))
								Bukkit.getConsoleSender().sendMessage("§c　 LRoad"+count2+".schematic");
						for(int count2 = 0 ; count2 < 2 ; count2++)
							if(!dungeonFile.contains("Road"+count2+".schematic"))
								Bukkit.getConsoleSender().sendMessage("§c　 Road"+count2+".schematic");
						if(!dungeonFile.contains("Room.schematic"))
							Bukkit.getConsoleSender().sendMessage("§c　 Room.schematic");
						for(int count2 = 0 ; count2 < 4 ; count2++)
							if(!dungeonFile.contains("TRoad"+count2+".schematic"))
								Bukkit.getConsoleSender().sendMessage("§c　 TRoad"+count2+".schematic");
						for(int count2 = 0 ; count2 < 4 ; count2++)
							if(!dungeonFile.contains("Wall"+count2+".schematic"))
								Bukkit.getConsoleSender().sendMessage("§c　 Wall"+count2+".schematic");
					}
					else
					{
						dungeonTheme.add(fileList[count].getName());
						Bukkit.getConsoleSender().sendMessage("§5[던전 테마 추가] " + fileList[count].getName());
					}
				}
		}
		catch(Exception e)
		{}
	  	Bukkit.getConsoleSender().sendMessage("§2§l[OK]§8 던전 정보 로드");

	  	Bukkit.getConsoleSender().sendMessage("§e──────────────────────");
	  	Bukkit.getConsoleSender().sendMessage("§eI changed My Symbol!");
	  	Bukkit.getConsoleSender().sendMessage("§eLike this Oriental Dragon...");
	  	Bukkit.getConsoleSender().sendMessage("§eBecause some peoples claimed");
	  	Bukkit.getConsoleSender().sendMessage("§emy original Dragon symbol...");
	  	Bukkit.getConsoleSender().sendMessage("§e(They said my original symbol");
	  	Bukkit.getConsoleSender().sendMessage("§elooks like the 'Nazi''s Hakenkreuz)");
	  	Bukkit.getConsoleSender().sendMessage("§6　");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　　◆"); 
	  	Bukkit.getConsoleSender().sendMessage("§e　　　◆　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　◆　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　　◆　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　◆　　　◆　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　◆　　　◆　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　◆　　　◆　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　◆　◆　　　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　◆　　　　　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage("§6　GoldBigDragon Advanced");
	  	Bukkit.getConsoleSender().sendMessage("§bhttp://cafe.naver.com/goldbigdragon");
	  	Bukkit.getConsoleSender().sendMessage("§6　　　　");
	  	Bukkit.getConsoleSender().sendMessage("§e──────────────────────");
	  	
	  	VersionCheck();

		fr.tenebrae.CustomRegistry.registerEntities();

		EntityRegistrer.register("CreatureBlaze", EntityAppearence.BLAZE, EntityType.BLAZE, EntityBlaze.class, CustomBlaze.class);
		EntityRegistrer.register("CreatureCaveSpider", EntityAppearence.CAVE_SPIDER, EntityType.CAVE_SPIDER, EntityCaveSpider.class, CustomCaveSpider.class);
		EntityRegistrer.register("CreatureCreeper", EntityAppearence.CREEPER, EntityType.CREEPER, EntityCreeper.class, CustomCreeper.class);
		EntityRegistrer.register("CreatureElderGuardian", EntityAppearence.ELDER_GUARDIAN, EntityType.ELDER_GUARDIAN, EntityGuardianElder.class, CustomElderGuardian.class);
		EntityRegistrer.register("CreatureEnderman", EntityAppearence.ENDERMAN, EntityType.ENDERMAN, EntityEnderman.class, CustomEnderman.class);
		EntityRegistrer.register("CreatureEnderMite", EntityAppearence.ENDERMITE, EntityType.ENDERMITE, EntityEndermite.class, CustomEnderMite.class);
		EntityRegistrer.register("CreatureEvoker", EntityAppearence.EVOKER, EntityType.EVOKER, EntityEvoker.class, CustomEvoker.class);
		EntityRegistrer.register("CreatureGiant", EntityAppearence.GIANT, EntityType.GIANT, EntityGiantZombie.class, CustomGiant.class);
		EntityRegistrer.register("CreatureGuardian", EntityAppearence.GUARDIAN, EntityType.GUARDIAN, EntityGuardian.class, CustomGuardian.class);
		EntityRegistrer.register("CreatureHusk", EntityAppearence.HUSK, EntityType.HUSK, EntityZombieHusk.class, CustomHusk.class);
		EntityRegistrer.register("CreaturePigZombie", EntityAppearence.ZOMBIE_PIGMAN, EntityType.PIG_ZOMBIE, EntityPigZombie.class, CustomPigZombie.class);
		EntityRegistrer.register("CreaturePolarBear", EntityAppearence.POLAR_BEAR, EntityType.POLAR_BEAR, EntityPolarBear.class, CustomPolarBear.class);
		EntityRegistrer.register("CreatureShulker", EntityAppearence.SHULKER, EntityType.SHULKER, EntityShulker.class, CustomShulker.class);
		EntityRegistrer.register("CreatureSilverFish", EntityAppearence.SILVERFISH, EntityType.SILVERFISH, EntitySilverfish.class, CustomSilverFish.class);
		EntityRegistrer.register("CreatureSkeleton", EntityAppearence.SKELETON, EntityType.SKELETON, EntitySkeleton.class, CustomSkeleton.class);
		EntityRegistrer.register("CreatureSpider", EntityAppearence.SPIDER, EntityType.SPIDER, EntitySpider.class, CustomSpider.class);
		EntityRegistrer.register("CreatureStray", EntityAppearence.STRAY, EntityType.STRAY, EntitySkeletonStray.class, CustomStray.class);
		EntityRegistrer.register("CreatureVex", EntityAppearence.VEX, EntityType.VEX, EntityVex.class, CustomVex.class);
		EntityRegistrer.register("CreatureVindicator", EntityAppearence.VINDICATOR, EntityType.VINDICATOR, EntityVindicator.class, CustomVindicator.class);
		EntityRegistrer.register("CreatureWitch", EntityAppearence.WITCH, EntityType.WITCH, EntityWitch.class, CustomWitch.class);
		EntityRegistrer.register("CreatureWither", EntityAppearence.WITHER, EntityType.WITHER, EntityWither.class, CustomWither.class);
		EntityRegistrer.register("CreatureWitherSkeleton", EntityAppearence.WITHER_SKELETON, EntityType.WITHER_SKELETON, EntitySkeletonWither.class, CustomWitherSkeleton.class);
		EntityRegistrer.register("CreatureZombie", EntityAppearence.ZOMBIE, EntityType.ZOMBIE, EntityZombie.class, CustomZombie.class);
		EntityRegistrer.register("CreatureZombieVillager", EntityAppearence.ZOMBIE_VILLAGER, EntityType.ZOMBIE_VILLAGER, EntityZombieVillager.class, CustomZombieVillager.class);

		EntityRegistrer.register("CreatureSkeletonHorse", EntityAppearence.HORSE_SKELETON, EntityType.SKELETON_HORSE, EntityHorseSkeleton.class, CustomSkeletonHorse.class);
		EntityRegistrer.register("CreatureZombieHorse", EntityAppearence.HORSE_ZOMBIE, EntityType.ZOMBIE_HORSE, EntityHorseZombie.class, CustomZombieHorse.class);
		EntityRegistrer.register("CreatureDonkey", EntityAppearence.HORSE_DONKEY, EntityType.DONKEY, EntityHorseDonkey.class, CustomDonkey.class);
		EntityRegistrer.register("CreatureMule", EntityAppearence.HORSE_MULE, EntityType.MULE, EntityHorseMule.class, CustomMule.class);
		EntityRegistrer.register("CreatureBat", EntityAppearence.BAT, EntityType.BAT, EntityBat.class, CustomBat.class);
		EntityRegistrer.register("CreaturePig", EntityAppearence.PIG, EntityType.PIG, EntityPig.class, CustomPig.class);
		EntityRegistrer.register("CreatureSheep", EntityAppearence.SHEEP, EntityType.SHEEP, EntitySheep.class, CustomSheep.class);
		EntityRegistrer.register("CreatureCow", EntityAppearence.COW, EntityType.COW, EntityCow.class, CustomCow.class);
		EntityRegistrer.register("CreatureChicken", EntityAppearence.CHICKEN, EntityType.CHICKEN, EntityChicken.class, CustomChicken.class);
		EntityRegistrer.register("CreatureSquid", EntityAppearence.SQUID, EntityType.SQUID, EntitySquid.class, CustomSquid.class);
		EntityRegistrer.register("CreatureWolf", EntityAppearence.WOLF, EntityType.WOLF, EntityWolf.class, CustomWolf.class);
		EntityRegistrer.register("CreatureMooshroom", EntityAppearence.MOOSHROOM, EntityType.MUSHROOM_COW, EntityMushroomCow.class, CustomMooshroom.class);
		EntityRegistrer.register("CreatureSnowman", EntityAppearence.SNOW_GOLEM, EntityType.SNOWMAN, EntitySnowman.class, CustomSnowman.class);
		EntityRegistrer.register("CreatureOcelot", EntityAppearence.OCELOT, EntityType.OCELOT, EntityOcelot.class, CustomOcelot.class);
		EntityRegistrer.register("CreatureIronGolem", EntityAppearence.IRON_GOLEM, EntityType.IRON_GOLEM, EntityIronGolem.class, CustomIronGolem.class);
		EntityRegistrer.register("CreatureHorse", EntityAppearence.HORSE, EntityType.HORSE, EntityHorse.class, CustomHorse.class);
		EntityRegistrer.register("CreatureRabbit", EntityAppearence.RABBIT, EntityType.RABBIT, EntityRabbit.class, CustomRabbit.class);
		EntityRegistrer.register("CreatureLlama", EntityAppearence.LLAMA, EntityType.LLAMA, EntityLlama.class, CustomLlama.class);
		EntityRegistrer.register("CreatureVillager", EntityAppearence.VILLAGER, EntityType.VILLAGER, EntityVillager.class, CustomVillager.class);
		return;
	}
	

	public void VersionCheck()
	{
		BufferedInputStream in = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			URL url = new URL(updateCheckURL);
			URLConnection urlConnection = url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			byte[] bufRead = new byte[10];
			int lenRead = 0;
			
			while ((lenRead = in.read(bufRead)) >0)
				sb.append(new String(bufRead, 0, lenRead));
			String[] parsed = sb.toString().split("<br>");
			
			String version = parsed[1].split(": ")[1];
			String update = parsed[2].split(": ")[1];
			
		  	Bukkit.getConsoleSender().sendMessage("§f최신 버전 : "+version);
		  	Bukkit.getConsoleSender().sendMessage("§7현재 버전 : "+serverVersion);
		  	Bukkit.getConsoleSender().sendMessage("§f최신 패치 : "+update);
		  	Bukkit.getConsoleSender().sendMessage("§7현재 패치 : "+serverUpdate);

			currentServerUpdate = update;
			currentServerVersion = version;
			if(serverVersion.equals(version)&&serverUpdate.equals(update))
				Bukkit.getConsoleSender().sendMessage("§3[ ! ] 현재 GoldBigDragonRPG는 최신 버전입니다!");
			else
			{
				Bukkit.getConsoleSender().sendMessage("§c[ ! ] GoldBigDragonRPG가 최신 버전이 아닙니다! 아래 주소에서 다운로드 받으세요!");
				Bukkit.getConsoleSender().sendMessage("§c[ ! ] http://cafe.naver.com/goldbigdragon/57885");
				Bukkit.getConsoleSender().sendMessage("§c[ ! ] 패치가 필요한 이유 : " + parsed[3].split(": ")[1]);
			}
			
		}
		catch (IOException ioe)
		{
		  	Bukkit.getConsoleSender().sendMessage("§c[버전 체크 오류] 인터넷 연결을 확인 해 주세요!");
		}
	}
	
	public void MagicSpellCatch()
	{
		if(!MagicSpellsCatched)
		{
			MagicSpellsCatched = true;
			if(!Bukkit.getPluginManager().isPluginEnabled("MagicSpells"))
			{
				ErrorMessage();
			  	Bukkit.getConsoleSender().sendMessage("§eMagicSpells 플러그인을 찾을 수 없습니다!");
			  	Bukkit.getConsoleSender().sendMessage("§eMagicSpells 다운로드 URL");
			  	Bukkit.getConsoleSender().sendMessage("§ehttp://nisovin.com/magicspells/Start");
			}
			else
			{
				MagicSpellsEnable = true;
				new otherplugins.SpellMain(Main.plugin);
			}
		}
		return;
	}
	
	public void CitizensCatch()
	{
		if(!CitizensCatched)
		{
			CitizensCatched = true;
			if(!Bukkit.getPluginManager().isPluginEnabled("Citizens"))
			{
				ErrorMessage();
			  	Bukkit.getConsoleSender().sendMessage("§eCitizens 플러그인을 찾을 수 없습니다!");
			  	Bukkit.getConsoleSender().sendMessage("§eCitizens 다운로드 URL");
			  	Bukkit.getConsoleSender().sendMessage("§ehttp://www.curse.com/bukkit-plugins/minecraft/citizens#t1:other-downloads");
			}
			else
				new otherplugins.CitizensMain(Main.plugin);
		}
		return;
	}
	
	public void ErrorMessage()
	{
	  	Bukkit.getConsoleSender().sendMessage("§e　　　［경　고］");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　　　■");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　　■■■");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　　■■■"); 
	  	Bukkit.getConsoleSender().sendMessage("§e　　　■■■■■");
	  	Bukkit.getConsoleSender().sendMessage("§e　　　■■　■■");
	  	Bukkit.getConsoleSender().sendMessage("§e　　■■■　■■■");
	  	Bukkit.getConsoleSender().sendMessage("§e　　■■■　■■■");
	  	Bukkit.getConsoleSender().sendMessage("§e　■■■■■■■■■");
	  	Bukkit.getConsoleSender().sendMessage("§e　■■■■　■■■■"); 
	  	Bukkit.getConsoleSender().sendMessage("§e■■■■■■■■■■■");
	  	Bukkit.getConsoleSender().sendMessage("§e[플레이에 지장은 없습니다]");
	  	Bukkit.getConsoleSender().sendMessage("§e");
	}
}
