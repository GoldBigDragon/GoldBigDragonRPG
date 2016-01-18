package GoldBigDragon_RPG.Main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Main extends JavaPlugin implements Listener
{
	public static YamlController YC_1,YC_2,YC_3;

	public static String serverUpdate = "2016-01-19-03:19";
	public static String serverVersion = "Advanced";
	private static String updateCheckURL = "https://goldbigdragon.github.io/";
	public static String currentServerUpdate = "2016-01-19-03:19";
	public static String currentServerVersion = "Advanced";
	
	public static String SpawnMobName;

	public static java.util.Map<Long, PartyDataObject> Party = new LinkedHashMap<Long, PartyDataObject>();
	public static java.util.Map<Player, Long> PartyJoiner = new LinkedHashMap<Player, Long>();
	
	public static HashMap<Player, Location> catchedLocation1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> catchedLocation2 = new HashMap<Player, Location>();
	
	public static HashMap<Player, String> PlayerUseSpell = new HashMap<Player, String>();
	public static HashMap<Player, ItemStack> PlayerlastItem = new HashMap<Player, ItemStack>();
	public static HashMap<Player, String> PlayerCurrentArea = new HashMap<Player, String>();
		
	public static boolean MagicSpellsCatched = false;
	public static boolean MagicSpellsEnable = false;
	public static boolean CitizensCatched = false;
	public static boolean NoteBlockAPI = false;
	public static boolean NoteBlockAPIAble = false;
	
	public static boolean spawntime = true;
	public static boolean Mapping = false;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		YC_1 = new YamlController(this);
		YC_2 = new YamlController(this);
		YC_3 = new YamlController(this);
	  	File MusicFolder = new File(this.getDataFolder().getAbsolutePath() + "/NoteBlockSound/");
		if(!MusicFolder.exists())
			MusicFolder.mkdirs();
		new GoldBigDragon_RPG.Util.SendString().SendForBukkit(0);
	  	GoldBigDragon_RPG.Config.configConfig config = new GoldBigDragon_RPG.Config.configConfig();
	  	config.CreateNewConfig(YC_1);
	  	config.CreateMapImageConfig(YC_1);
	  	if(YC_1.isExit("Skill/SkillList.yml") == false)
	  	  new GoldBigDragon_RPG.Config.SkillConfig().CreateNewSkillList();
	  	if(YC_1.isExit("Skill/JobList.yml") == false)
	  		new GoldBigDragon_RPG.Config.SkillConfig().CreateNewJobList();
	  	if(YC_1.isExit("ETC/NewBie.yml") == false)
	  		new GoldBigDragon_RPG.Config.NewBieConfig().CreateNewConfig();
	  	new GoldBigDragon_RPG.Config.NPCconfig().NPCscriptExample();
	  	
	  	new PartyDataManager().loadParty();
	  	
		YamlManager WorldConfig = YC_2.getNewConfig("WorldList.yml");
		Object[] worldname = WorldConfig.getKeys().toArray();
		for(int count = 0; count < WorldConfig.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count].toString()) == null)
				WorldCreator.name(worldname[count].toString()).createWorld();
		
		VersionCheck();
		UpdateConfig();
		NoteBlockAPICatch();
		
		new GoldBigDragon_RPG.ServerTick.ServerTickMain(this);
		new GoldBigDragon_RPG.ServerTick.ServerTickScheduleManager().loadCategoriFile();
		new GoldBigDragon_RPG.Main.ServerOption().Initialize();
	  	return;
	}
	public void onDisable()
	{
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI"))
		{
	    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	    	Player[] a = new Player[playerlist.size()];
	    	playerlist.toArray(a);
	    	for(int count = 0; count <a.length;count++)
	    		new OtherPlugins.NoteBlockAPIMain().Stop(a[count]);
		}
	  	new PartyDataManager().saveParty();

		new GoldBigDragon_RPG.ServerTick.ServerTickScheduleManager().saveCategoriFile();
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Clossing GoldBigDragon Advanced...]");
	  	return;
	}
	
	public void VersionCheck()
	{
		BufferedInputStream in = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			URL url = new URL(updateCheckURL);
			URLConnection urlConnection = url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			byte[] bufRead = new byte[10];
			int lenRead = 0;
			
			while ((lenRead = in.read(bufRead)) >0)
				sb.append(new String(bufRead, 0, lenRead));
			String[] Parsed = sb.toString().split("<br>");
			
			String Version = Parsed[1].split(": ")[1];
			String Update = Parsed[2].split(": ")[1];
			
		  	Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "최신 버전 : "+Version);
		  	Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "현재 버전 : "+serverVersion);
		  	Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "최신 패치 : "+Update);
		  	Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "현재 패치 : "+serverUpdate);

			currentServerUpdate = Update;
			currentServerVersion = Version;
			if(serverVersion.compareTo(Version)==0&&serverUpdate.compareTo(Update)==0)
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "현재 GoldBigDragonRPG는 최신 버전입니다!");
			else
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "최신 버전이 아닙니다! 아래 주소에서 다운로드 받으세요!");
		  		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "http://cafe.naver.com/goldbigdragon/40109");
			}
			
		}
		catch (IOException ioe)
		{
		  	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[버전 체크 오류] 인터넷 연결을 확인 해 주세요!");
		}
	}
	
	public void UpdateConfig()
	{
		YamlManager Config =YC_2.getNewConfig("config.yml");
		if(Config.contains("Server.CustomWeaponBreak")==false)
		{
			Config.set("Server.CustomWeaponBreak", true);
			Config.saveConfig();
		}
	}
	
	@EventHandler
	private void PlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		MagicSpellCatch();
		CitizensCatch();
		YamlManager Config = YC_2.getNewConfig("config.yml");
		if(Config.getInt("Event.DropChance")>=2||Config.getInt("Event.Multiple_EXP_Get")>=2||Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2||Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
		{
			String alert = "[";
			if(Config.getInt("Event.DropChance")>=2)
				alert =alert+ "드롭률 증가 "+Config.getInt("Event.DropChance")+"배";
			if(Config.getInt("Event.DropChance")>=2)
				alert = alert+", ";
			if(Config.getInt("Event.Multiple_EXP_Get")>=2)
				alert = alert + "경험치 " + Config.getInt("Event.Multiple_EXP_Get")+"배 획득";
			if(Config.getInt("Event.Multiple_EXP_Get")>=2)
				alert = alert+", ";
			if(Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
				alert = alert +"스텟 포인트 "+Config.getInt("Event.Multiple_Level_Up_StatPoint")+"배 획득";
			if(Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
				alert = alert+", ";
			if(Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
				alert = alert +"스킬 포인트 " +Config.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 획득";
			alert = alert+"]";
			new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player, "\'현재 이벤트가 진행중입니다.\'", "\'"+alert+"\'", 1, 10, 1);
		}

		new UserDataObject().UserDataInit(player);
		
		if(player.isOp() == true)
			new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player,"\'§e/오피박스\'", "\'§eGoldBigDragonAdvanced 가이드 및 서버 설정이 가능합니다.\'", 1,10, 1);
	  	if(YC_1.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  	    new GoldBigDragon_RPG.Config.StatConfig().CreateNewStats(player);
	  	if(YC_1.isExit("Quest/PlayerData/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    new GoldBigDragon_RPG.Config.QuestConfig().CreateNewPlayerConfig(player);

			YamlManager QuestConfig=YC_1.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			YamlManager QuestList=YC_1.getNewConfig("Quest/QuestList.yml");
			YamlManager NewBieYM = YC_1.getNewConfig("ETC/NewBie.yml");
			
			QuestConfig.set("PlayerName", player.getName());
			QuestConfig.set("PlayerUUID", player.getUniqueId().toString());
			Object[] Quest = QuestList.getKeys().toArray();
			String QuestName = NewBieYM.getString("FirstQuest");
			if(QuestName.equals("null") ==false)
			{
				for(int count = 0; count < Quest.length; count++)
				{
					if(QuestName.compareTo(Quest[count].toString())==0)
					{
						if(QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
						{
							QuestConfig.set("Started."+QuestName+".Flow", 0);
							QuestConfig.set("Started."+QuestName+".Type", QuestList.getString(QuestName+".FlowChart."+0+".Type"));
							QuestConfig.saveConfig();
							player.sendMessage(ChatColor.YELLOW+"[퀘스트] : 새로운 퀘스트가 도착했습니다! " +ChatColor.GOLD+""+ChatColor.BOLD+"/퀘스트");
							if(QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("Nevigation")==0||
								QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("Whisper")==0||
								QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("BroadCast")==0||
								QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("BlockPlace")==0||
								QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("VarChange")==0||
								QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("TelePort")==0)
								new GoldBigDragon_RPG.GUI.QuestGUI().QuestTypeRouter(player, QuestName);
						}
						break;
					}
				}
			}
			Object[] a= NewBieYM.getConfigurationSection("SupportItem").getKeys(false).toArray();
			for(int count = 0; count < a.length;count++)
				if(NewBieYM.getItemStack("SupportItem."+count) != null)
					player.getInventory().addItem(NewBieYM.getItemStack("SupportItem."+count));
			player.teleport(new Location(Bukkit.getWorld(NewBieYM.getString("TelePort.World")), NewBieYM.getInt("TelePort.X"), NewBieYM.getInt("TelePort.Y"), NewBieYM.getInt("TelePort.Z"), NewBieYM.getInt("TelePort.Yaw"), NewBieYM.getInt("TelePort.Pitch")));
	  	}
	  	if(YC_1.isExit("Skill/PlayerData/" + player.getUniqueId()+".yml") == false)
	  		new GoldBigDragon_RPG.Config.SkillConfig().CreateNewPlayerSkill(player);
	  	
	  	new GoldBigDragon_RPG.ETC.Job().PlayerFixAllSkillAndJobYML(player);
		
		new GoldBigDragon_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GoldBigDragon_RPG.GUI.EquipGUI().FriendJoinQuitMessage(player, true);

		if(Config.getString("Server.JoinMessage") != null)
		{
			String message = Config.getString("Server.JoinMessage").replace("%player%",event.getPlayer().getName());
			event.setJoinMessage(message);
		}
		else
			event.setJoinMessage(null);
	}
	
	@EventHandler
	private void PlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if(PartyJoiner.containsKey(player))
			Party.get(PartyJoiner.get(player)).QuitParty(player);
		
		if(NoteBlockAPIAble == true)
			new OtherPlugins.NoteBlockAPIMain().Stop(event.getPlayer());
		
		YamlManager UserData = YC_1.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.removeKey("Data");
		UserData.saveConfig();
		PlayerCurrentArea.remove(player);
    	new GoldBigDragon_RPG.GUI.EquipGUI().FriendJoinQuitMessage(player, false);

		YamlManager Config = YC_1.getNewConfig("config.yml");
		if(Config.getString("Server.QuitMessage") != null)
		{
			String message = Config.getString("Server.QuitMessage").replace("%player%",event.getPlayer().getName());
			event.setQuitMessage(message);
		}
		else
			event.setQuitMessage(null);
	}
	
	@EventHandler
	private void PlayerRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();

		GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());

	  	if(YC_1.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		new GoldBigDragon_RPG.Config.StatConfig().CreateNewStats(player);
	  	YamlManager YM = YC_1.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		
		YamlManager AreaList = YC_1.getNewConfig("Area/AreaList.yml");
		if(YM.getString("ETC.LastVisited")!="null")
		{
			String respawnCity = YM.getString("ETC.LastVisited");
			Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();
			for(int count =0; count <arealist.length;count++)
			{
				if(arealist[count].toString().equalsIgnoreCase(respawnCity) == true)
				{
					if(AreaList.getBoolean(arealist[count].toString()+".SpawnPoint") == true)
					{
						double X = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.X");
						double Y = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Y");
						double Z = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Z");
				    	event.setRespawnLocation(new Location(Bukkit.getServer().getWorld(AreaList.getString(arealist[count].toString()+".World")), X, Y, Z, (float)AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Yaw"), (float)AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Pitch")));
					}
				}
			}
			return;
		}
		return;
	}
	@EventHandler
	private void Move(PlayerMoveEvent event){GoldBigDragon_RPG.Event.PlayerAction PA = new GoldBigDragon_RPG.Event.PlayerAction();PA.PlayerMove(event);return;}

	@EventHandler
	private void HotBarMove(PlayerItemHeldEvent event)
	{
		//if(MagicSpellsCatched == false)
		//	MagicSpellCatch();
		new GoldBigDragon_RPG.Event.ChangeHotBar().HotBarMove(event);
		return;
	}
	
	@EventHandler
	private void PlayerItemDrop(PlayerDropItemEvent event)
	{
		ItemStack IT = event.getItemDrop().getItemStack();
		if(IT.hasItemMeta() == true)
			if(IT.getItemMeta().hasLore() == true)
				if(IT.getItemMeta().getLore().size() == 4)
					if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[클릭시 퀵슬롯에서 삭제]")==true)
						event.setCancelled(true);
		return;
	}
	
	@EventHandler
	private void PlayerChatting(PlayerChatEvent event)
	{
		if(event.getMessage().contains("&") == true)
		{
			event.setMessage(event.getMessage().replaceAll("&l", ChatColor.BOLD+""));
			event.setMessage(event.getMessage().replaceAll("&0", ChatColor.BLACK+""));
			event.setMessage(event.getMessage().replaceAll("&1", ChatColor.DARK_BLUE+""));
			event.setMessage(event.getMessage().replaceAll("&2", ChatColor.DARK_GREEN+""));
			event.setMessage(event.getMessage().replaceAll("&3", ChatColor.DARK_AQUA+""));
			event.setMessage(event.getMessage().replaceAll("&4", ChatColor.DARK_RED+""));
			event.setMessage(event.getMessage().replaceAll("&5", ChatColor.DARK_PURPLE+""));
			event.setMessage(event.getMessage().replaceAll("&6", ChatColor.GOLD+""));
			event.setMessage(event.getMessage().replaceAll("&7", ChatColor.GRAY+""));
			event.setMessage(event.getMessage().replaceAll("&8", ChatColor.DARK_GRAY+""));
			event.setMessage(event.getMessage().replaceAll("&9", ChatColor.BLUE+""));
			event.setMessage(event.getMessage().replaceAll("&a", ChatColor.GREEN+""));
			event.setMessage(event.getMessage().replaceAll("&b", ChatColor.AQUA+""));
			event.setMessage(event.getMessage().replaceAll("&c", ChatColor.RED+""));
			event.setMessage(event.getMessage().replaceAll("&d", ChatColor.LIGHT_PURPLE+""));
			event.setMessage(event.getMessage().replaceAll("&e", ChatColor.YELLOW+""));
			event.setMessage(event.getMessage().replaceAll("&f", ChatColor.WHITE+""));
		}
		GoldBigDragon_RPG.Event.PlayerAction PA = new GoldBigDragon_RPG.Event.PlayerAction();
		PA.PlayerChatting(event);
		return;
	}

	@EventHandler
	private void PlayerFishing(PlayerFishEvent event)
	{
		GoldBigDragon_RPG.Event.Fishing F = new GoldBigDragon_RPG.Event.Fishing();
		F.PlayerFishing(event);
		return;
	}
	
	@EventHandler
	private void Map(MapInitializeEvent event)
	{
		new GoldBigDragon_RPG.ETC.Map().onMap(event);
		return;
	}
	
	@EventHandler
	private void PlayerDeath(PlayerDeathEvent event)
	{
		List<ItemStack> Ilist = event.getDrops();
		for(int count = 0; count < Ilist.size(); count++)
		{
			ItemStack IT = Ilist.get(count);
			if(IT.hasItemMeta() == true)
			{
				if(IT.getItemMeta().hasLore() == true)
				{
					if(IT.getItemMeta().getLore().size() >= 4)
					{
						if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[클릭시 퀵슬롯에서 삭제]")==true)
						{
							Ilist.set(count,new ItemStack(0));
						}
					}
				}
			}
		}
		return;
	}
	
	@EventHandler
	private void KeepItemDurability(PlayerItemDamageEvent event)
	{
		ItemStack item = event.getItem();
		if(item.hasItemMeta())
			if(item.getItemMeta().hasLore() == true)
				if(item.getItemMeta().getLore().toString().contains("내구도"))
					event.setCancelled(true);
		return;
	}
	
	public void MagicSpellCatch()
	{
		if(MagicSpellsCatched == false)
		{
			MagicSpellsCatched = true;
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == false)
			{
				new GoldBigDragon_RPG.Util.SendString().SendForBukkit(1);
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MagicSpells 플러그인을 찾을 수 없습니다!");
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MagicSpells 다운로드 URL");
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "http://nisovin.com/magicspells/Start");
			}
			else
			{
				MagicSpellsEnable = true;
				OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain(this);
			}
		}
		return;
	}
	
	public void CitizensCatch()
	{
		if(CitizensCatched == false)
		{
			CitizensCatched = true;
			if(Bukkit.getPluginManager().isPluginEnabled("Citizens") == false)
			{
				new GoldBigDragon_RPG.Util.SendString().SendForBukkit(1);
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Citizens 플러그인을 찾을 수 없습니다!");
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Citizens 다운로드 URL");
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "http://www.curse.com/bukkit-plugins/minecraft/citizens#t1:other-downloads");
			}
			else
			{
				OtherPlugins.CitizensMain CZ = new OtherPlugins.CitizensMain(this);
			}
		}
		return;
	}
	
	public void NoteBlockAPICatch()
	{
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI"))
			new OtherPlugins.NoteBlockAPIMain(this);
		return;
	}
	
	public void NoteBlockAPICatchPJ()
	{
		if(NoteBlockAPI == false)
		{
			NoteBlockAPI = true;
			if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == false)
			{
				new GoldBigDragon_RPG.Util.SendString().SendForBukkit(1);
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "노트블록 재생 플러그인을 찾을 수 없습니다!");
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "NoteBlockAPI 다운로드 URL");
			  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "http://www.curse.com/bukkit-plugins/minecraft/noteblockapi");
			}
			else
			{
				new OtherPlugins.NoteBlockAPIMain(this);
			}
		}
		return;
	}
	
	@EventHandler
	private void RA(EntityShootBowEvent event) {new GoldBigDragon_RPG.Event.Attack().RangeAttack(event);return;}
    @EventHandler
    private void Attack(EntityDamageByEntityEvent event) {new GoldBigDragon_RPG.Event.Attack().AttackRouter(event);return;}
    @EventHandler
	private void EntitySpawn(CreatureSpawnEvent event) {new GoldBigDragon_RPG.Event.MonsterSpawn().EntitySpawn(event);return;}
    @EventHandler
    private void ITBlock(PlayerInteractEvent event)
    {
    	GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());
    	new GoldBigDragon_RPG.Event.Interact().PlayerInteract(event);
    	return;
    }
    @EventHandler
    private void ITEnity(PlayerInteractEntityEvent event)
    {
    	GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());
    	CitizensCatch();
    	new GoldBigDragon_RPG.Event.Interact().PlayerInteractEntity(event);
    	return;
    }
    @EventHandler
    private void ItemGetMessage(PlayerPickupItemEvent event) {new GoldBigDragon_RPG.Event.Interact().PlayerGetItem(event);}
	@EventHandler
	private void MonsterKill(EntityDeathEvent event)	{new GoldBigDragon_RPG.Event.MonsterKill().MonsterKill(event);return;}
	@EventHandler
	private void BBreak(BlockBreakEvent event)
	{
		new GoldBigDragon_RPG.Event.BlockBreak().BlockBreaking(event);return;
	}
	@EventHandler
	private void BlockPlace(BlockPlaceEvent event){GoldBigDragon_RPG.Event.BlockPlace BP = new GoldBigDragon_RPG.Event.BlockPlace();BP.BlockPlace(event);return;}
	
	@EventHandler
	private void applyHealthRegen(EntityRegainHealthEvent event)
	{
		if (event.isCancelled())
			return;
		if (((event.getEntity() instanceof Player)) &&(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED))
	    {
	    	GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
	    	ETC.UpdatePlayerHPMP((Player)event.getEntity());
	    }
		return;
	}
	
	@EventHandler
	private void InventoryClick(InventoryClickEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&MagicSpellsCatched==true)
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getWhoClicked());
		}
		if(event.getClickedInventory() == null)
			return;
		if(event.getCurrentItem().hasItemMeta() == true)
		{
			if(event.getCurrentItem().getItemMeta().hasLore() == true)
			{
				if(event.getCurrentItem().getItemMeta().getLore().size() == 4)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(3).equals((ChatColor.YELLOW+"[클릭시 퀵슬롯에서 삭제]")))
					{
						event.setCancelled(true);
						event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(0));
						GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
						s.SP((Player)event.getWhoClicked(), Sound.ANVIL_LAND, 1.0F, 1.9F);
					}
				}
			}
		}
		if(ChatColor.stripColor(event.getWhoClicked().getOpenInventory().getTitle()).compareTo("교환")==0)
		{
			new GoldBigDragon_RPG.GUI.EquipGUI().ExchangeInventoryclick(event);
		}
		
		if(event.getInventory().getName().length() >= 3)
		{
			if(event.getInventory().getName().charAt(0)=='§'
			&&event.getInventory().getName().charAt(1)=='0')
			{
				String InventoryName = ChatColor.stripColor(event.getInventory().getName().toString());
				if(event.getInventory().getType()==InventoryType.CHEST)
				{
					if(!(InventoryName.equals("몬스터 장비 설정")||InventoryName.equals("모아야 할 아이템 등록")
					||InventoryName.equals("보상 아이템 등록")||InventoryName.equals("초심자 지원")
					||InventoryName.equals("해당 블록을 캐면 나올 아이템")||InventoryName.equals("영역 추가 어류")
					||InventoryName.equals("NPC 룬 장착")||InventoryName.equals("container.chest")
					||InventoryName.equals("container.chestDouble")||InventoryName.equals("초심자 가이드")
					||InventoryName.equals("container.minecart")||InventoryName.equals("이벤트 전체 지급")
					||InventoryName.equals("이벤트 랜덤 지급")||InventoryName.equals("교환") ))
					{
						event.setCancelled(true);
					}
				}
				
				if(InventoryName.contains("스텟")||InventoryName.contains("옵션")||InventoryName.contains("개조식")||
				   InventoryName.equals("기타")||InventoryName.contains("가이드")||
				   InventoryName.contains("파티")||InventoryName.contains("NPC")
				   ||InventoryName.contains("던전")||InventoryName.equals("이벤트 진행")
				   ||InventoryName.contains("목록")||InventoryName.contains("퀘스트")||
				   InventoryName.contains("관리자")||InventoryName.contains("등록")||InventoryName.equals("오브젝트 추가")
				   ||InventoryName.contains("장비")||InventoryName.contains("선택")||InventoryName.contains("아이템")
				    ||InventoryName.contains("[MapleStory]")||InventoryName.contains("[Mabinogi]")
				    ||InventoryName.contains("스킬")||InventoryName.contains("랭크")||InventoryName.contains("몬스터")
				    ||InventoryName.contains("등록된")||InventoryName.contains("직업군")||InventoryName.contains("초심자")
				    ||InventoryName.contains("카테고리")||InventoryName.equals("해당 블록을 캐면 나올 아이템")||InventoryName.contains("영역")
				    ||InventoryName.contains("월드")||InventoryName.contains("워프")||InventoryName.contains("매직스펠")
				    ||InventoryName.contains("이벤트")||InventoryName.contains("친구")||InventoryName.contains("네비")
				    ||InventoryName.equals("교환"))
				{
					GoldBigDragon_RPG.Event.InventoryClick IC = new GoldBigDragon_RPG.Event.InventoryClick();
					IC.InventoryClickRouter(event, InventoryName);
				}
			}
		}
		return;
	}
	
	@EventHandler
	private void InventoryClose(InventoryCloseEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&MagicSpellsCatched==true)
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getPlayer());
		}
		new GoldBigDragon_RPG.Event.InventoryClose().InventoryCloseRouter(event);
		return;
	}

	public boolean onCommand(CommandSender talker, Command command, String string, String[] args)
    {
		MagicSpellCatch();
    	CitizensCatch();
		for(int count = 0; count <args.length; count++)
		{
			if(args[count].contains("&"))
			{
				args[count]=args[count].replaceAll("&l", ChatColor.BOLD+"");
				args[count]=args[count].replaceAll("&0", ChatColor.BLACK+"");
				args[count]=args[count].replaceAll("&1", ChatColor.DARK_BLUE+"");
				args[count]=args[count].replaceAll("&2", ChatColor.DARK_GREEN+"");
				args[count]=args[count].replaceAll("&3", ChatColor.DARK_AQUA+"");
				args[count]=args[count].replaceAll("&4", ChatColor.DARK_RED+"");
				args[count]=args[count].replaceAll("&5", ChatColor.DARK_PURPLE+"");
				args[count]=args[count].replaceAll("&6", ChatColor.GOLD+"");
				args[count]=args[count].replaceAll("&7", ChatColor.GRAY+"");
				args[count]=args[count].replaceAll("&8", ChatColor.DARK_GRAY+"");
				args[count]=args[count].replaceAll("&9", ChatColor.BLUE+"");
				args[count]=args[count].replaceAll("&a", ChatColor.GREEN+"");
				args[count]=args[count].replaceAll("&b", ChatColor.AQUA+"");
				args[count]=args[count].replaceAll("&c", ChatColor.RED+"");
				args[count]=args[count].replaceAll("&d", ChatColor.LIGHT_PURPLE+"");
				args[count]=args[count].replaceAll("&e", ChatColor.YELLOW+"");
				args[count]=args[count].replaceAll("&f", ChatColor.WHITE+"");
			}
		}
		
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			
			switch(string)
			{
				case"gui사용":
					if(player.isOp() == true)
					{
					 	s.SP((Player)talker, org.bukkit.Sound.VILLAGER_HAGGLE, 1.0F, 1.8F);
					    player.sendMessage(ChatColor.GREEN+"[NPC] : GUI를 활성화 시킬 NPC를 우클릭 하세요!");
					    new UserDataObject().setInt(player, (byte) 4, 114);
					}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
				case"친구":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.ETCGUI().FriendsGUI(player, 0);
					return true;
				case"스킬":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				    GoldBigDragon_RPG.GUI.PlayerSkillGUI PSKGUI = new GoldBigDragon_RPG.GUI.PlayerSkillGUI();
					PSKGUI.MainSkillsListGUI(player, 0);
					return true;
		  		case "아이템" :
		  			GoldBigDragon_RPG.Command.ItemsCommand ItemC = new GoldBigDragon_RPG.Command.ItemsCommand();
		  			if(args.length <= 0)
		  			{
		  				if(args.length <=0)
		  				{
		  					GoldBigDragon_RPG.Command.HelpMessage HM = new GoldBigDragon_RPG.Command.HelpMessage();
			  				HM.HelpMessager(player, 1);
			  				return true;	
		  				}
		  				if(ChatColor.stripColor(args[0]).equalsIgnoreCase("설명제거") ==true)
		  				 ItemC.onCommand2(talker, command, string, args);
		  				else
		  				{
		  					GoldBigDragon_RPG.Command.HelpMessage HM = new GoldBigDragon_RPG.Command.HelpMessage();
			  				HM.HelpMessager(player, 1);
			  				return true;	
		  				}
		  			}
		  			 if(ChatColor.stripColor(args[0]).equalsIgnoreCase("목록") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("등록") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("삭제") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("받기") == false&&ChatColor.stripColor(args[0]).equalsIgnoreCase("주기") == false)
		  				 ItemC.onCommand2(talker, command, string, args);
		  			 else
				  		ItemC.onCommand1(talker, command, string, args);
		  			break;
		  		case "파티":
		  			GoldBigDragon_RPG.Command.PartyCommand PartyC = new GoldBigDragon_RPG.Command.PartyCommand();
		  			PartyC.onCommand(talker, command, string, args);
		  			return true;
				case "테스트":
				case "엔티티제거":
				case "아이템제거":
				case "수락":
				case "거절":
		  		case "돈":
		  			new GoldBigDragon_RPG.Command.SystemCommand().onCommand(player, args, string);
		  			return true;
		  		case "스텟":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.StatsGUI().StatusGUI((Player)talker);
					return true;
		  		case "옵션":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.OptionGUI().optionGUI((Player)talker);
					return true;
		  		case "기타":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.ETCGUI().ETCGUI_Main((Player) talker);
					return true;
		  		case "오피박스":
					  if(talker.isOp() == true)
					  {
						  new UserDataObject().clearAll(player);
				  		  GoldBigDragon_RPG.GUI.OPBoxGUI opgui = new GoldBigDragon_RPG.GUI.OPBoxGUI();
						  s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
						  opgui.OPBoxGUI_Main((Player)talker,1);
						  return true;
					  }
					  else
					  {
						  talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						  s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						  return true;
					  }
		  		case "몬스터" :
				  if(talker.isOp() == true)
				  {

					  s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
		  			new GoldBigDragon_RPG.GUI.MonsterGUI().MonsterListGUI(player, 0);
				  }
				  else
				  {
					  talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					  s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				  }
		  			return true;
		  		case "워프":
		  			GoldBigDragon_RPG.Command.WarpCommand WarpC = new GoldBigDragon_RPG.Command.WarpCommand();
		  			WarpC.onCommand(talker, command, string, args);
		  			return true;
		  		case "영역":
		  			GoldBigDragon_RPG.Command.AreaCommand AreaC = new GoldBigDragon_RPG.Command.AreaCommand();
		  			AreaC.onCommand(talker, command, string, args);
		  			return true;
		  		case "상점":
		  			GoldBigDragon_RPG.Command.NPCcommand NPCC = new GoldBigDragon_RPG.Command.NPCcommand();
		  			NPCC.onCommand(talker, command, string, args);
		  			return true;
		  		case "퀘스트":
		  			GoldBigDragon_RPG.Command.QuestCommand QC = new GoldBigDragon_RPG.Command.QuestCommand();
		  			QC.onCommand(talker, command, string, args);
		  			return true;
		  		case "커맨드":
		  			if(player.isOp() == true)
		  			{
		  				UserDataObject u = new UserDataObject();
						if(u.getType(player).equals("Skill"))
						{
							if(u.getString(player, (byte)1).equalsIgnoreCase("SKC"))
							{
								String CommandString = "";
								for(int count = 0; count <args.length-1; count ++)
									CommandString = CommandString+args[count]+" ";
								CommandString = CommandString+args[args.length-1];
								YamlController GUI_YC = GoldBigDragon_RPG.Main.Main.YC_1;
								YamlManager SkillList  = GUI_YC.getNewConfig("Skill/SkillList.yml");
								if(CommandString.contains("/")==false)
									CommandString = "/"+CommandString;
								if(CommandString.equalsIgnoreCase("/없음"))
									SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command","null");
								else
									SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command",CommandString);
								SkillList.saveConfig();
								GoldBigDragon_RPG.GUI.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.GUI.OPBoxSkillGUI();
								SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), u.getInt(player, (byte)4));
								u.clearAll(player);
							}
						}
						else
						{
		  					player.sendMessage(ChatColor.RED+"[스킬 설정] : 스킬 설정을 위한 커맨드 입니다!");
							s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  					return true;
						}
		  			}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
			}
			return false;
		}
		else
		{
			
		}
		return false;
    }

}