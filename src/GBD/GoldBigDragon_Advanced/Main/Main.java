package GBD.GoldBigDragon_Advanced.Main;

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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
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
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class Main extends JavaPlugin implements Listener
{
	public static YamlController Main_YC,GUI_YC,Party_YC,Config_YC,Event_YC,Monster_YC,Location_YC,Scheduler_YC;

	public static String serverUpdate = "2015-11-22-14:24";
	public static String serverVersion = "Advanced";
	private static String updateCheckURL = "https://goldbigdragon.github.io/";
	
	public static String currentServerUpdate = "2015-11-22-14:24";
	public static String currentServerVersion = "Advanced";
	
	public static String SpawnMobName;
	
	public static HashMap<Player, String> TEMP = new HashMap<Player, String>();
	public static java.util.Map<Player, UserDataObject> UserData = new LinkedHashMap<Player, UserDataObject>();
	public static java.util.Map<Long, PartyDataObject> Party = new LinkedHashMap<Long, PartyDataObject>();
	public static java.util.Map<Player, Long> PartyJoiner = new LinkedHashMap<Player, Long>();
	
	public static HashMap<Player, Location> catchedLocation1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> catchedLocation2 = new HashMap<Player, Location>();
	
	public static HashMap<Player, String> PlayerUseSpell = new HashMap<Player, String>();
	public static HashMap<Player, ItemStack> PlayerlastItem = new HashMap<Player, ItemStack>();
	public static HashMap<Player, String> PlayerClickedNPCuuid = new HashMap<Player, String>();
	public static HashMap<Player, String> PlayerCurrentArea = new HashMap<Player, String>();
	
	public static boolean MagicSpellsCatched = false;
	public static boolean CitizensCatched = false;
	public static boolean NoteBlockAPI = false;
	public static boolean NoteBlockAPIAble = false;
	
	public static boolean spawntime = true;
	public static boolean Mapping = false;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	  	Main_YC = new YamlController(this);
	  	GUI_YC = new YamlController(this);
	  	Config_YC = new YamlController(this);
	  	Party_YC = new YamlController(this);
	  	Event_YC = new YamlController(this);
	  	Monster_YC = new YamlController(this);
	  	Location_YC = new YamlController(this);
	  	Scheduler_YC = new YamlController(this);
	  	File MusicFolder = new File(this.getDataFolder().getAbsolutePath() + "/NoteBlockSound/");
		if(!MusicFolder.exists())
			MusicFolder.mkdirs();

	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "──────────────────────");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "I changed My Symbol!");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Like this Oriental Dragon...");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Because some peoples claimed");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "my original Dragon symbol...");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "(They said my original symbol");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "looks like the 'Nazi''s Hakenkreuz)");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "　");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　◆"); 
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　◆　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　◆　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　◆　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　◆　　　◆　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　◆　　　◆　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　◆　　　◆　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　◆　◆　　　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　◆　　　　　　　　　◆");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "　GoldBigDragon Advanced");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "http://cafe.naver.com/goldbigdragon");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "　　　　");
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "──────────────────────");

	  	GBD.GoldBigDragon_Advanced.Config.configConfig config = new GBD.GoldBigDragon_Advanced.Config.configConfig();
	  	config.CreateNewConfig(Main_YC);
	  	config.CreateMapImageConfig(Main_YC);
	  	if(Main_YC.isExit("Skill/SkillList.yml") == false)
	  	  new GBD.GoldBigDragon_Advanced.Config.SkillConfig().CreateNewSkillList();
	  	if(Main_YC.isExit("Skill/JobList.yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.SkillConfig().CreateNewJobList();
	  	if(Main_YC.isExit("ETC/NewBie.yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.NewBieConfig().CreateNewConfig();
	  	new GBD.GoldBigDragon_Advanced.Config.NPCconfig().NPCscriptExample();
	  	
	  	new UserDataManager().loadCategoriFile();
	  	new PartyDataManager().loadParty();
	  	
		YamlManager WorldConfig =GUI_YC.getNewConfig("WorldList.yml");
		Object[] worldname = WorldConfig.getKeys().toArray();
		for(int count = 0; count < WorldConfig.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count].toString()) == null)
				WorldCreator.name(worldname[count].toString()).createWorld();
		
		VersionCheck();
		UpdateConfig();
		NoteBlockAPICatch();
		
		new GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain(this);
		new GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleManager().loadCategoriFile();
		new GBD.GoldBigDragon_Advanced.Main.ServerOption().Initialize();
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
	  	new UserDataManager().saveCategoriFile();
	  	new PartyDataManager().saveParty();

		new GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleManager().saveCategoriFile();
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
			System.out.println(ioe.getMessage());
		  	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[버전 체크 오류] 인터넷 연결을 확인 해 주세요!");
		}
	}
	
	public void UpdateConfig()
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager Config =GUI_YC.getNewConfig("config.yml");
		if(Config.contains("Server.CustomWeaponBreak")==false)
		{
			Config.set("Server.CustomWeaponBreak", true);
			Config.saveConfig();
		}
	}
	
	@EventHandler
	private void PlayerJoin(PlayerJoinEvent event)
	{
		NoteBlockAPICatchPJ();
		Player player = event.getPlayer();
		MagicSpellCatch();
		CitizensCatch();
		YamlManager Config = GUI_YC.getNewConfig("config.yml");
		if(Config.getInt("Event.DropChance")>=2||Config.getInt("Event.Multiple_EXP_Get")>=2||Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2||Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
		{
			GBD.GoldBigDragon_Advanced.Effect.PacketSender PS = new GBD.GoldBigDragon_Advanced.Effect.PacketSender();
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
			PS.sendTitleSubTitle(player, "\'현재 이벤트가 진행중입니다.\'", "\'"+alert+"\'", 1, 10, 1);
		}

		if(player.isOp() == true)
		{
			Main.UserData.put(player, new UserDataObject(player));
			GBD.GoldBigDragon_Advanced.Effect.PacketSender PS = new GBD.GoldBigDragon_Advanced.Effect.PacketSender();
			PS.sendTitleSubTitle(player,"\'§e/오피박스\'", "\'§eGoldBigDragonAdvanced 가이드 및 서버 설정이 가능합니다.\'", 1,10, 1);
		}
	  	if(Main_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();
	  		stat.CreateNewStats(player);
	  	}
	  	if(Main_YC.isExit("Quest/PlayerData/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    GBD.GoldBigDragon_Advanced.Config.QuestConfig quest = new GBD.GoldBigDragon_Advanced.Config.QuestConfig();
	  	    quest.CreateNewPlayerConfig(player);
	
			YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
			YamlManager QuestConfig=Location_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			YamlManager QuestList=Location_YC.getNewConfig("Quest/QuestList.yml");
			YamlManager NewBieYM = Location_YC.getNewConfig("ETC/NewBie.yml");
			
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
								new GBD.GoldBigDragon_Advanced.GUI.QuestGUI().QuestTypeRouter(player, QuestName);
							
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
	  	if(Main_YC.isExit("Skill/PlayerData/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    GBD.GoldBigDragon_Advanced.Config.SkillConfig skill = new GBD.GoldBigDragon_Advanced.Config.SkillConfig();
	  	  skill.CreateNewPlayerSkill(player);
	  	}
		GBD.GoldBigDragon_Advanced.ETC.Job J = new GBD.GoldBigDragon_Advanced.ETC.Job();
		J.FixJobList();
		J.FixPlayerJobList(player);
		J.FixPlayerSkillList(player);
		
    	GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());
    	new GBD.GoldBigDragon_Advanced.GUI.EquipGUI().FriendJoinQuitMessage(player, true);

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
		{
			OtherPlugins.NoteBlockAPIMain NBAPIM = new OtherPlugins.NoteBlockAPIMain();
			NBAPIM.Stop(event.getPlayer());
		}
		PlayerClickedNPCuuid.remove(event.getPlayer());
		Main.UserData.remove(event.getPlayer());
		PlayerCurrentArea.remove(player);
		catchedLocation1.remove(event.getPlayer());
		catchedLocation2.remove(event.getPlayer());
    	new GBD.GoldBigDragon_Advanced.GUI.EquipGUI().FriendJoinQuitMessage(player, false);

		YamlManager Config = GUI_YC.getNewConfig("config.yml");
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
	  	if(Config_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();
	  		stat.CreateNewStats(player);
	  	}
	  	YamlManager YM = Config_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		YM.set("ETC.Death", true);
		YM.saveConfig();
    	GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());
		GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();

	  	if(Config_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);
		YM = Config_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		
		if(YM.getBoolean("ETC.Death") == true)
		{
			YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
			YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");

			if(YM.getString("ETC.LastVisited")=="null")
			{
				YM.set("ETC.Death", false);
				YM.saveConfig();
				return;
			}
			else
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
				YM.set("ETC.Death", false);
				YM.saveConfig();
				return;
			}
		}
		return;
	}
	@EventHandler
	private void Move(PlayerMoveEvent event){GBD.GoldBigDragon_Advanced.Event.PlayerAction PA = new GBD.GoldBigDragon_Advanced.Event.PlayerAction();PA.PlayerMove(event);return;}

	@EventHandler
	private void HotBarMove(PlayerItemHeldEvent event)
	{
		if(MagicSpellsCatched == false)
			MagicSpellCatch();
		 new GBD.GoldBigDragon_Advanced.Event.ChangeHotBar().HotBarMove(event);
		 return;
	}
	
	@EventHandler
	private void PlayerItemDrop(PlayerDropItemEvent event)
	{
		ItemStack IT = event.getItemDrop().getItemStack();
		if(IT.hasItemMeta() == true)
		{
			if(IT.getItemMeta().hasLore() == true)
			{
				if(IT.getItemMeta().getLore().size() == 4)
				{
					if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[클릭시 퀵슬롯에서 삭제]")==true)
					{
						event.setCancelled(true);
					}
				}
			}
		}
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
		GBD.GoldBigDragon_Advanced.Event.PlayerAction PA = new GBD.GoldBigDragon_Advanced.Event.PlayerAction();
		PA.PlayerChatting(event);
		return;
	}

	@EventHandler
	private void PlayerFishing(PlayerFishEvent event)
	{
		GBD.GoldBigDragon_Advanced.Event.Fishing F = new GBD.GoldBigDragon_Advanced.Event.Fishing();
		F.PlayerFishing(event);
		return;
	}
	
	@EventHandler
	private void Map(MapInitializeEvent event)
	{
		new GBD.GoldBigDragon_Advanced.ETC.Map().onMap(event);
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
	
	
	
	public void MagicSpellCatch()
	{
		if(MagicSpellsCatched == false)
		{
			MagicSpellsCatched = true;
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == false)
			{
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　［경　고］");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　　■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■"); 
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■　■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■　■■■■"); 
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "■■■■■■■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[플레이에 지장은 없습니다]");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MagicSpells 플러그인을 찾을 수 없습니다!");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MagicSpells 다운로드 URL");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "http://nisovin.com/magicspells/Start");
			}
			else
			{
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
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　［경　고］");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　　■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■"); 
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■　■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■　■■■■"); 
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "■■■■■■■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[플레이에 지장은 없습니다]");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "");
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
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　［경　고］");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　　■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■"); 
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■　■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■　■■■■"); 
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "■■■■■■■■■■■");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[플레이에 지장은 없습니다]");
			  	  Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "");
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
	private void RA(EntityShootBowEvent event) {new GBD.GoldBigDragon_Advanced.Event.Attack().RangeAttack(event);return;}
    @EventHandler
    private void Attack(EntityDamageByEntityEvent event) {new GBD.GoldBigDragon_Advanced.Event.Attack().AttackRouter(event);return;}
    @EventHandler
	private void EntitySpawn(CreatureSpawnEvent event) {new GBD.GoldBigDragon_Advanced.Event.MonsterSpawn().EntitySpawn(event);return;}
    @EventHandler
    private void ITBlock(PlayerInteractEvent event)
    {
    	GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());
    	new GBD.GoldBigDragon_Advanced.Event.Interact().PlayerInteract(event);
    	return;
    }
    @EventHandler
    private void ITEnity(PlayerInteractEntityEvent event)
    {
    	GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
    	ETC.UpdatePlayerHPMP(event.getPlayer());
    	CitizensCatch();
    	new GBD.GoldBigDragon_Advanced.Event.Interact().PlayerInteractEntity(event);
    	return;
    }
    @EventHandler
    private void ItemGetMessage(PlayerPickupItemEvent event) {new GBD.GoldBigDragon_Advanced.Event.Interact().PlayerGetItem(event);}
	@EventHandler
	private void MonsterKill(EntityDeathEvent event)	{new GBD.GoldBigDragon_Advanced.Event.MonsterKill().MonsterKill(event);return;}
	@EventHandler
	private void BBreak(BlockBreakEvent event)
	{
	    GBD.GoldBigDragon_Advanced.Event.BlockBreak BB = new GBD.GoldBigDragon_Advanced.Event.BlockBreak();
		BB.BlockBreaking(event);return;
	}
	@EventHandler
	private void BlockPlace(BlockPlaceEvent event){GBD.GoldBigDragon_Advanced.Event.BlockPlace BP = new GBD.GoldBigDragon_Advanced.Event.BlockPlace();BP.BlockPlace(event);return;}
	
	@EventHandler
	private void applyHealthRegen(EntityRegainHealthEvent event)
	{
		if (event.isCancelled())
			return;
		if (((event.getEntity() instanceof Player)) &&(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED))
	    {
	    	GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
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
			GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
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
						GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
						s.SP((Player)event.getWhoClicked(), Sound.ANVIL_LAND, 1.0F, 1.9F);
					}
				}
			}
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
					||InventoryName.equals("이벤트 랜덤 지급") ))
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
				    ||InventoryName.contains("이벤트")||InventoryName.contains("친구")||InventoryName.contains("네비"))
				{
					GBD.GoldBigDragon_Advanced.Event.InventoryClick IC = new GBD.GoldBigDragon_Advanced.Event.InventoryClick();
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
			GBD.GoldBigDragon_Advanced.Util.ETC ETC = new GBD.GoldBigDragon_Advanced.Util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getPlayer());
		}
		new GBD.GoldBigDragon_Advanced.Event.InventoryClose().InventoryCloseRouter(event);
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
			GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();

			if(player.isOp() == true&&UserData.containsKey(player)==false)
				UserData.put(player, new UserDataObject(player));
			
			switch(string)
			{
			case"테스트":
				if(player.isOp() == true)
				{
					/*
				    for(int count= 0; count < GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.size(); count++)
				    	player.sendMessage(ChatColor.GREEN+"현재 몬스터 스폰 중인 영역 : "+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.get(count));
				    for(int count= 0; count < GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.size(); count++)
				    {
				    	long UTC = Long.parseLong(GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.keySet().toArray()[count].toString());
				    	player.sendMessage(ChatColor.GREEN+"["+UTC+"] : "+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.get(UTC).getType());
				    	player.sendMessage(ChatColor.GREEN+"["+UTC+"] : "+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.get(UTC).getString((byte)0));
				    	player.sendMessage(ChatColor.GREEN+"["+UTC+"] : "+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.get(UTC).getString((byte)1));
				    	player.sendMessage(ChatColor.GREEN+"["+UTC+"] : "+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.get(UTC).getString((byte)2));
				    	player.sendMessage(ChatColor.GREEN+"["+UTC+"] : "+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.get(UTC).getString((byte)3));
				    	player.sendMessage(ChatColor.BLUE+"────────────────────────────");
				    }
				    */
				}
				else
				{
					talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				}
				return true;
				case"gui사용":
					if(player.isOp() == true)
					{
					 	s.SP((Player)talker, org.bukkit.Sound.VILLAGER_HAGGLE, 1.0F, 1.8F);
					    player.sendMessage(ChatColor.GREEN+"[NPC] : GUI를 활성화 시킬 NPC를 우클릭 하세요!");
					    UserData.get(player).setInt((byte)4, 114);
					}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
				case"친구":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				 	new GBD.GoldBigDragon_Advanced.GUI.ETCGUI().FriendsGUI(player, 0);
					return true;
				case"스킬":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				    GBD.GoldBigDragon_Advanced.GUI.PlayerSkillGUI PSKGUI = new GBD.GoldBigDragon_Advanced.GUI.PlayerSkillGUI();
					PSKGUI.MainSkillsListGUI(player, 0);
					return true;
				case "엔티티제거":
					if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : /엔티티제거 [1~10000]");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						return true;
					}
					if(player.isOp() == true)
					{
					    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
					    int amount = 0;
					    for(int count = 0; count < entities.size(); count++)
					    {
					    	if(entities.get(count).getType() != EntityType.PLAYER &&entities.get(count).getType() != EntityType.ITEM_FRAME)
					    	{
					    		entities.get(count).remove();
					    		amount = amount+1;
					    	}
					    }
					    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"마리의 엔티티를 삭제하였습니다!");
					}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						return true;
					}
					return true;
				case "아이템제거":
					if(args.length != 1 ||Integer.parseInt(args[0]) > 10000)
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : /아이템제거 [1~10000]");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						return true;
					}
					if(player.isOp() == true)
					{
					    List<Entity> entities = player.getNearbyEntities(Integer.parseInt(args[0]), Integer.parseInt(args[0]), Integer.parseInt(args[0]));
					    int amount = 0;
					    for(int count = 0; count < entities.size(); count++)
					    {
					    	if(entities.get(count).getType() == EntityType.DROPPED_ITEM)
					    	{
					    		entities.get(count).remove();
					    		amount = amount+1;
					    	}
					    }
					    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 반경 "+args[0]+"블록 이내에 있던 "+amount+"개의 아이템을 삭제하였습니다!");
					}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						return true;
					}
					return true;
		  		case "아이템" :
		  			GBD.GoldBigDragon_Advanced.Command.ItemsCommand ItemC = new GBD.GoldBigDragon_Advanced.Command.ItemsCommand();
		  			if(args.length <= 0)
		  			{
		  				if(args.length <=0)
		  				{
		  					GBD.GoldBigDragon_Advanced.Command.HelpMessage HM = new GBD.GoldBigDragon_Advanced.Command.HelpMessage();
			  				HM.HelpMessager(player, 1);
			  				return true;	
		  				}
		  				if(ChatColor.stripColor(args[0]).equalsIgnoreCase("설명제거") ==true)
		  				 ItemC.onCommand2(talker, command, string, args);
		  				else
		  				{
		  					GBD.GoldBigDragon_Advanced.Command.HelpMessage HM = new GBD.GoldBigDragon_Advanced.Command.HelpMessage();
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
		  			GBD.GoldBigDragon_Advanced.Command.PartyCommand PartyC = new GBD.GoldBigDragon_Advanced.Command.PartyCommand();
		  			PartyC.onCommand(talker, command, string, args);
		  			return true;
		  		case "돈":
				 	s.SP((Player)talker, org.bukkit.Sound.LAVA_POP, 0.8F, 1.8F);
				 	YamlManager YM = Main_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
				 	player.sendMessage(ChatColor.YELLOW + "[현재 소지 금액] " + ChatColor.YELLOW+ChatColor.BOLD +"" +YM.getInt("Stat.Money") + " "+ServerOption.Money);
					return true;
		  		case "스텟":
		  			GBD.GoldBigDragon_Advanced.GUI.StatsGUI sgui = new GBD.GoldBigDragon_Advanced.GUI.StatsGUI();
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	sgui.StatusGUI((Player)talker);
					return true;
		  		case "옵션":
		  			GBD.GoldBigDragon_Advanced.GUI.OptionGUI ogui = new GBD.GoldBigDragon_Advanced.GUI.OptionGUI();
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	ogui.optionGUI((Player)talker);
					return true;
		  		case "기타":
		  			GBD.GoldBigDragon_Advanced.GUI.ETCGUI egui = new GBD.GoldBigDragon_Advanced.GUI.ETCGUI();
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	egui.ETCGUI_Main((Player) talker);
					return true;
		  		case "오피박스":
					  if(talker.isOp() == true)
					  {
						  Main.UserData.get(player).clearAll();
				  		  GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI opgui = new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI();
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
					  s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
		  			new GBD.GoldBigDragon_Advanced.GUI.MonsterGUI().MonsterListGUI(player, 0);
		  			return true;
		  		case "워프":
		  			GBD.GoldBigDragon_Advanced.Command.WarpCommand WarpC = new GBD.GoldBigDragon_Advanced.Command.WarpCommand();
		  			WarpC.onCommand(talker, command, string, args);
		  			return true;
		  		case "영역":
		  			GBD.GoldBigDragon_Advanced.Command.AreaCommand AreaC = new GBD.GoldBigDragon_Advanced.Command.AreaCommand();
		  			AreaC.onCommand(talker, command, string, args);
		  			return true;
		  		case "상점":
		  			GBD.GoldBigDragon_Advanced.Command.NPCcommand NPCC = new GBD.GoldBigDragon_Advanced.Command.NPCcommand();
		  			NPCC.onCommand(talker, command, string, args);
		  			return true;
		  		case "퀘스트":
		  			GBD.GoldBigDragon_Advanced.Command.QuestCommand QC = new GBD.GoldBigDragon_Advanced.Command.QuestCommand();
		  			QC.onCommand(talker, command, string, args);
		  			return true;
		  		case "커맨드":
		  			if(player.isOp() == true)
		  			{
		  				if(UserData.containsKey(player)==false)
		  				{
		  					UserData.put(player, new UserDataObject(player));
		  					player.sendMessage(ChatColor.RED+"[스킬 설정] : 스킬 설정을 위한 커맨드 입니다!");
							s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  					return true;
		  				}
						if(UserData.get(player).getType().equals("Skill"))
						{
							if(UserData.get(player).getString((byte)1).equalsIgnoreCase("SKC"))
							{
								String CommandString = "";
								for(int count = 0; count <args.length-1; count ++)
									CommandString = CommandString+args[count]+" ";
								CommandString = CommandString+args[args.length-1];
								YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
								YamlManager SkillList  = GUI_YC.getNewConfig("Skill/SkillList.yml");
								if(CommandString.contains("/")==false)
									CommandString = "/"+CommandString;
								if(CommandString.equalsIgnoreCase("/없음"))
									SkillList.set(UserData.get(player).getString((byte)2)+".SkillRank."+UserData.get(player).getInt((byte)4)+".Command","null");
								else
									SkillList.set(UserData.get(player).getString((byte)2)+".SkillRank."+UserData.get(player).getInt((byte)4)+".Command",CommandString);
								SkillList.saveConfig();
								GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI SKGUI = new GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI();
								SKGUI.SkillRankOptionGUI(player, UserData.get(player).getString((byte)2), UserData.get(player).getInt((byte)4));
								UserData.get(player).clearAll();
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