package GBD_RPG.Main_Main;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
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

import GBD_RPG.Party.Party_DataManager;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.User.User_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;
import net.minecraft.server.v1_10_R1.PacketPlayInClientCommand;

public class Main_Main extends JavaPlugin implements Listener
{
	public static JavaPlugin plugin = null;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		plugin = this;
		new Main_ServerOption().Initialize();
	  	return;
	}
	
	public void onDisable()
	{
		new GBD_RPG.Corpse.Corpse_Main().RemoveAllCorpse();
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI"))
		{
	    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	    	Player[] a = new Player[playerlist.size()];
	    	playerlist.toArray(a);
	    	for(short count = 0; count <a.length;count++)
	    		new OtherPlugins.NoteBlockAPIMain().Stop(a[count]);
		}
	  	new Party_DataManager().saveParty();

		new GBD_RPG.ServerTick.ServerTick_ScheduleManager().saveCategoriFile();
		Object[] players = Bukkit.getOnlinePlayers().toArray();
		for(short count = 0; count < players.length; count++)
			Main_ServerOption.PlayerList.get(((Player)players[count]).getUniqueId().toString()).saveAll();
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Clossing GoldBigDragon Advanced...]");
	  	return;
	}
	
	@EventHandler
	private void PlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
	  	YamlController YC = new YamlController(this);
	  	if(YC.isExit("Skill/PlayerData/" + player.getUniqueId()+".yml") == false)
	  		new GBD_RPG.Skill.Skill_Config().CreateNewPlayerSkill(player);
	  	else
	  		new GBD_RPG.Job.Job_Main().PlayerFixAllSkillAndJobYML(player);
		User_Object PO = new User_Object(player);
		Main_ServerOption.PlayerList.put(player.getUniqueId().toString(), PO);
		if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			new GBD_RPG.Util.Util_Player().teleportToCurrentArea(player, true);
			new GBD_RPG.Dungeon.Dungeon_Main().EraseAllDungeonKey(player, false);
			GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
			GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
		}
		if(new GBD_RPG.Corpse.Corpse_Main().DeathCapture(player,true))
			new GBD_RPG.Corpse.Corpse_Main().CreateCorpse(player);
		
    	new GBD_RPG.Main_Main.Main_ServerOption().MagicSpellCatch();
    	new GBD_RPG.Main_Main.Main_ServerOption().CitizensCatch();

		new UserData_Object().UserDataInit(player);
		
		if(player.isOp() == true)
			new GBD_RPG.Effect.Effect_Packet().sendTitleSubTitle(player,"\'§e/오피박스\'", "\'§eGoldBigDragonAdvanced 가이드 및 서버 설정이 가능합니다.\'", (byte)1,(byte)10, (byte)1);
		else
		{
			YamlManager Config = YC.getNewConfig("config.yml");
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
				new GBD_RPG.Effect.Effect_Packet().sendTitleSubTitle(player, "\'현재 이벤트가 진행중입니다.\'", "\'"+alert+"\'", (byte)1, (byte)10, (byte)1);
			}
		}
	  	if(YC.isExit("Quest/PlayerData/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    new GBD_RPG.Quest.Quest_Config().CreateNewPlayerConfig(player);

			YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
			for(byte count = 0; count < YC.getNewConfig("ETC/NewBie.yml").getConfigurationSection("SupportItem").getKeys(false).toArray().length;count++)
				if(NewBieYM.getItemStack("SupportItem."+count) != null)
					player.getInventory().addItem(NewBieYM.getItemStack("SupportItem."+count));
			player.teleport(new Location(Bukkit.getWorld(NewBieYM.getString("TelePort.World")), NewBieYM.getInt("TelePort.X"), NewBieYM.getInt("TelePort.Y"), NewBieYM.getInt("TelePort.Z"), NewBieYM.getInt("TelePort.Yaw"), NewBieYM.getInt("TelePort.Pitch")));
	  	}
		new GBD_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GBD_RPG.User.Equip_GUI().FriendJoinQuitMessage(player, true);

		if(YC.getNewConfig("config.yml").getString("Server.JoinMessage") != null)
			event.setJoinMessage(YC.getNewConfig("config.yml").getString("Server.JoinMessage").replace("%player%",event.getPlayer().getName()));
		else
			event.setJoinMessage(null);
	}
	
	@EventHandler
	private void PlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
	  	YamlController YC = new YamlController(this);
		if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
			new GBD_RPG.Dungeon.Dungeon_Main().EraseAllDungeonKey(player, true);
		
		if(new GBD_RPG.Corpse.Corpse_Main().DeathCapture(player,false))
			new GBD_RPG.Corpse.Corpse_Main().RemoveCorpse(player.getName());
		
		if(Main_ServerOption.PartyJoiner.containsKey(player))
			Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).QuitParty(player);
		
		if(Main_ServerOption.NoteBlockAPIAble == true)
			new OtherPlugins.NoteBlockAPIMain().Stop(event.getPlayer());

		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.removeKey("Data");
		UserData.saveConfig();
		Main_ServerOption.PlayerCurrentArea.remove(player);
    	new GBD_RPG.User.Equip_GUI().FriendJoinQuitMessage(player, false);

		YamlManager Config = YC.getNewConfig("config.yml");
		if(Config.getString("Server.QuitMessage") != null)
		{
			String message = Config.getString("Server.QuitMessage").replace("%player%",event.getPlayer().getName());
			event.setQuitMessage(message);
		}
		else
			event.setQuitMessage(null);
	  GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).saveAll();
	  GBD_RPG.Main_Main.Main_ServerOption.PlayerList.remove(player.getUniqueId().toString());
	}
	
	@EventHandler
	private void PlayerRespawn(PlayerRespawnEvent event)
	{
	  	YamlController YC = new YamlController(this);
	  	YamlManager Config = YC.getNewConfig("config.yml");
		if(Config.getBoolean("Death.SystemOn"))
		{
			Player player = event.getPlayer();
	    	event.setRespawnLocation(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint());
	    	player.setGameMode(GameMode.SPECTATOR);
			if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
			{
		    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		    	{
		    		new OtherPlugins.NoteBlockAPIMain().Stop(player);
					if(Config.contains("Death.Track"))
						if(Config.getInt("Death.Track")!=-1)
							new OtherPlugins.NoteBlockAPIMain().Play(player, Config.getInt("Death.Track"));
		    	}
			}
	    	new GBD_RPG.Corpse.Corpse_GUI().OpenReviveSelectGUI(player);
		}
		return;
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent event){new GBD_RPG.Main_Event.Main_PlayerMove().PlayerMove(event);return;}

	@EventHandler
	private void HotBarMove(PlayerItemHeldEvent event)
	{
		//if(MagicSpellsCatched == false)
		//	MagicSpellCatch();
		new GBD_RPG.Main_Event.Main_ChangeHotBar().HotBarMove(event);
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
		new GBD_RPG.Main_Event.Main_PlayerChat().PlayerChatting(event);
		return;
	}

	@EventHandler
	private void PlayerFishing(PlayerFishEvent event)
	{
		GBD_RPG.Main_Event.Main_Fishing F = new GBD_RPG.Main_Event.Main_Fishing();
		F.PlayerFishing(event);
		return;
	}
	
	@EventHandler
	private void Map(MapInitializeEvent event)
	{
		new GBD_RPG.Map.Map().onMap(event);
		return;
	}
	
	@EventHandler
	private void AmorStand(PlayerArmorStandManipulateEvent event)
	{
		Player player = event.getPlayer();
		ArmorStand AS = event.getRightClicked();
		String name = AS.getCustomName();
		if(name!=null)
		{
			if(name.charAt(0)=='§'&&name.charAt(1)=='0'&&name.charAt(2)=='§'&&name.charAt(3)=='l')
			{
				event.setCancelled(true);
				new GBD_RPG.Structure.Structure_Main().StructureUse(player,AS.getCustomName());
				return;
			}
			else if(name.charAt(0)=='§'&&name.charAt(1)=='c'&&name.charAt(2)=='§'&&name.charAt(3)=='0')
			{
				event.setCancelled(true);
				if(player.getInventory().getItemInMainHand() != null)
				{
					if(Main_ServerOption.DeathRescue != null)
					{
						if(Main_ServerOption.DeathRescue.getTypeId()==player.getInventory().getItemInMainHand().getTypeId())
						{
							ItemStack Pitem = player.getInventory().getItemInMainHand();
							if(Main_ServerOption.DeathRescue.getAmount()<=Pitem.getAmount())
							{
								GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
								String Name = null;
								if(AS.getItemInHand().getType() != Material.AIR)
									Name = AS.getItemInHand().getItemMeta().getDisplayName();
								else if(AS.getHelmet().getType() != Material.AIR)
									Name = AS.getHelmet().getItemMeta().getDisplayName();
								if(Name != null)
								{
									Player target = Bukkit.getPlayer(Name);
									if(target != null)
									{
									  	if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(target.getUniqueId().toString()).isDeath())
									  	{
											if(new GBD_RPG.Util.Util_Player().deleteItem(player, Main_ServerOption.DeathRescue, Main_ServerOption.DeathRescue.getAmount()))
											{
												new GBD_RPG.Corpse.Corpse_Main().RemoveCorpse(Name);
												player.updateInventory();
												player.sendMessage(ChatColor.LIGHT_PURPLE+"[구조] : "+ChatColor.YELLOW+target.getName()+ChatColor.LIGHT_PURPLE+"님을 부활시켰습니다!");
												target.sendMessage(ChatColor.LIGHT_PURPLE+"[부활] : "+ChatColor.YELLOW+player.getName()+ChatColor.LIGHT_PURPLE+"님에 의해 부활하였습니다!");
												target.setGameMode(GameMode.SURVIVAL);
												target.closeInventory();
												Location l = target.getLocation();
												l.add(0, 1, 0);
												target.teleport(l);
												for(short count2=0;count2<210;count2++)
													new GBD_RPG.Effect.Effect_Particle().PL(target.getLocation(), org.bukkit.Effect.SMOKE, new GBD_RPG.Util.Util_Number().RandomNum(0, 14));
												s.SL(target.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.5F, 1.8F);
										    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
										    		new OtherPlugins.NoteBlockAPIMain().Stop(target);
											  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
										    	YamlManager Config = YC.getNewConfig("config.yml");
										    	new GBD_RPG.Corpse.Corpse_GUI().Penalty(target, Config.getString("Death.Spawn_Help.SetHealth"), Config.getString("Death.Spawn_Help.PenaltyEXP"), Config.getString("Death.Spawn_Help.PenaltyMoney"));
												return;
											}
											else
											{
												s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
												player.sendMessage(ChatColor.RED+"[SYSTEM] : 부활 아이템이 부족하여 부활시킬 수 없습니다!");
												return;
											}
									  	}
									}
									else
									{
										Collection<Entity> aa = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 3, 3, 3);
										for(int count = 0; count < aa.size(); count++)
										{
											Entity now = ((Entity)aa.toArray()[count]);
											if(now.getType()==EntityType.ARMOR_STAND)
											{
												String CustomName = now.getCustomName();
												if(CustomName != null)
												{
													if(CustomName.charAt(0)=='§'&&CustomName.charAt(1)=='c'&&CustomName.charAt(2)=='§'&&CustomName.charAt(3)=='0')
													{
														String Name2 = null;
														if(AS.getItemInHand().getType() != Material.AIR)
															Name2 = AS.getItemInHand().getItemMeta().getDisplayName();
														else if(AS.getHelmet().getType() != Material.AIR)
															Name2 = AS.getHelmet().getItemMeta().getDisplayName();
														if(Name.compareTo(Name2)==0)
															now.remove();
													}
												}
											}
										}
										return;
									}
								}
								else
								{
									AS.remove();
									return;
								}
							}
						}
					}
				}
			}
			else
			{
				if(event.getPlayer().isOp()==false)
				{
					String TargetArea = null;
					GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
					if(A.getAreaName((Entity)AS) != null)
						TargetArea = A.getAreaName((Entity)AS)[0];
					if(TargetArea != null && A.getAreaOption(TargetArea, (char) 7) == false)
					{
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	private void BlockBurnEvent(BlockBurnEvent event)
	{
		if(Main_ServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0)
			event.setCancelled(true);
	}
	@EventHandler
	private void BlockIgniteEvent(BlockIgniteEvent event)
	{
		if((Main_ServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0)&&event.getIgnitingEntity()==null)
			event.setCancelled(true);
	}
	
	
	@EventHandler
	private void PlayerDeath(PlayerDeathEvent event)
	{
		final Player player = event.getEntity();
	  	YamlController YC = new YamlController(this);
	  	YamlManager Config = YC.getNewConfig("config.yml");
		if(Config.getBoolean("Death.SystemOn"))
		{
			GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setDeath(true);
		    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		    {
		      public void run()
		      {
		        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
		        ((CraftPlayer)player).getHandle().playerConnection.a(packet);
		      }
		    }, 1L);
		}
		GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setLastDeathPoint(new Location(event.getEntity().getLocation().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ(), event.getEntity().getLocation().getYaw(), event.getEntity().getLocation().getPitch()));
		if(event.getKeepInventory()==false)
		{
			List<ItemStack> Ilist = event.getDrops();
			event.setKeepInventory(true);
			event.getEntity().getInventory().clear();
			for(byte count = 0; count < Ilist.size(); count++)
			{
				ItemStack IT = Ilist.get(count);
				if(IT.isSimilar(Main_ServerOption.DeathRevive))
				{
					Ilist.remove(count);
					event.getEntity().getInventory().addItem(IT);
				}
				
				/*
				else if(IT.hasItemMeta() == true)
					if(IT.getItemMeta().hasLore() == true)
						if(IT.getItemMeta().getLore().size() >= 4)
							if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[클릭시 퀵슬롯에서 삭제]")==true)
								Ilist.remove(count);
				*/
			}
			for(int count = 0; count < Ilist.size(); count++)
				new GBD_RPG.Main_Event.Main_ItemDrop().CustomItemDrop(event.getEntity().getLocation(), Ilist.get(count));
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
	
	@EventHandler
	private void RA(EntityShootBowEvent event) {new GBD_RPG.Battle.Battle_Main().RangeAttack(event);return;}
    @EventHandler
    private void Attack(EntityDamageByEntityEvent event) {new GBD_RPG.Battle.Battle_Main().AttackRouter(event);return;}
    @EventHandler
	private void EntitySpawn(CreatureSpawnEvent event) {new GBD_RPG.Monster.Monster_Spawn().EntitySpawn(event);return;}
    @EventHandler
    private void ITBlock(PlayerInteractEvent event)
    {
    	new GBD_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GBD_RPG.Main_Event.Main_Interact().PlayerInteract(event);
    	return;
    }
    @EventHandler
    private void ITEnity(PlayerInteractEntityEvent event)
    {
    	new GBD_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GBD_RPG.Main_Main.Main_ServerOption().CitizensCatch();
    	new GBD_RPG.Main_Event.Main_Interact().PlayerInteractEntity(event);
    	return;
    }
    @EventHandler
    private void ItemGetMessage(PlayerPickupItemEvent event) {new GBD_RPG.Main_Event.Main_Interact().PlayerGetItem(event);}
	@EventHandler
	private void MonsterKill(EntityDeathEvent event)	{new GBD_RPG.Monster.Monster_Kill().MonsterKilling(event);return;}
	@EventHandler
	private void BBreak(BlockBreakEvent event)
	{new GBD_RPG.Main_Event.Main_BlockBreak().BlockBreaking(event); return;}
	@EventHandler
	private void BlockPlace(BlockPlaceEvent event){new GBD_RPG.Main_Event.Main_BlockPlace().BlockPlaceE(event); return;}

	@EventHandler
	private void EntityExplode(EntityExplodeEvent event)
	{
		if(Main_ServerOption.AntiExplode || event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
			event.blockList().clear();
	}
	
	@EventHandler
	private void ExplosionPrime(ExplosionPrimeEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(event.getEntityType()==EntityType.ENDER_CRYSTAL || event.getEntityType()==EntityType.DRAGON_FIREBALL
					|| event.getEntityType()==EntityType.FIREBALL || event.getEntityType()==EntityType.SMALL_FIREBALL)
				return;
			else
			{
				event.setCancelled(true);
				new GBD_RPG.Monster.Monster_Kill().Boomb(event.getEntity());
				new GBD_RPG.Monster.Monster_Kill().DungeonKilled((LivingEntity)event.getEntity(), true);
			}
			event.getEntity().remove();
		}
	}
	
	@EventHandler
	private void onArrowHitBlock(ProjectileHitEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(event.getEntity().getType()==EntityType.ARROW)
			{
				Arrow a = (Arrow)event.getEntity();
				if(a.getShooter() instanceof Player)
				{
					Player player = (Player)a.getShooter();
					if(player.isOnline())
					{
						Location down = new Location(event.getEntity().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ());
						Block b = null;
						int yaw = (int) event.getEntity().getLocation().getYaw();
						for(byte count = 0; count < 2; count++)
						{
							if(yaw >= -46 && yaw <=45)
								down.add(0, 0, 1);
							else if(yaw >= 46 && yaw <= 135)
								down.add(1, 0, 0);
							else if(yaw >= -136 && yaw <= -45)
								down.add(-1, 0, 0);
							else
								down.add(0, 0, -1);
							b = down.getBlock();
							if(b.getTypeId()!=0)
								break;
						}
						if(b.getTypeId() == 146)
							new GBD_RPG.Dungeon.Dungeon_Main().TrapChestOpen(b);
						else if(b.getTypeId() == 95)
							new GBD_RPG.Dungeon.Dungeon_Main().TrapGlassTouch(b, player);
					}
				}
			}
		}
	}
	
	/*
	private void changeMainHand(PlayerChangedMainHandEvent event)
	{
		
	}
	*/
	
	@EventHandler
	private void applyHealthRegen(EntityRegainHealthEvent event)
	{
		if (event.isCancelled())
			return;
		if (((event.getEntity() instanceof Player)) &&(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED))
	    {
	    	GBD_RPG.Util.ETC ETC = new GBD_RPG.Util.ETC();
	    	ETC.UpdatePlayerHPMP((Player)event.getEntity());
	    }
		return;
	}
	
	@EventHandler
	private void InventoryClick(InventoryClickEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&Main_ServerOption.MagicSpellsCatched==true)
		{
			GBD_RPG.Util.ETC ETC = new GBD_RPG.Util.ETC();
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
						event.getWhoClicked().getInventory().setItem(event.getSlot(), null);
						new GBD_RPG.Effect.Effect_Sound().SP((Player)event.getWhoClicked(), Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
					}
				}
			}
		}
		if(ChatColor.stripColor(event.getWhoClicked().getOpenInventory().getTitle()).compareTo("교환")==0)
			new GBD_RPG.User.Equip_GUI().ExchangeInventoryclick(event);
		
		if(event.getInventory().getName().length() >= 3)
		{
			if(event.getInventory().getName().charAt(0)=='§')
			{
				if(event.getInventory().getName().charAt(1)=='c')//개체GUI
					new GBD_RPG.Structure.Structure_Main().InventoryClickRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
				else if(event.getInventory().getName().charAt(1)=='2')//던전GUI
					new GBD_RPG.Dungeon.Dungeon_GUI().InventoryClickRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
				else if(event.getInventory().getName().charAt(1)=='0')//일반GUI
				{
					String InventoryName = ChatColor.stripColor(event.getInventory().getName().toString());
					if(event.getInventory().getType()==InventoryType.CHEST)
					{
						if(!(InventoryName.compareTo("몬스터 장비 설정")==0||InventoryName.compareTo("모아야 할 아이템 등록")==0
						||InventoryName.compareTo("보상 아이템 등록")==0||InventoryName.compareTo("초심자 지원")==0
						||InventoryName.compareTo("해당 블록을 캐면 나올 아이템")==0||InventoryName.compareTo("영역 추가 어류")==0
						||InventoryName.compareTo("NPC 룬 장착")==0||InventoryName.compareTo("container.chest")==0
						||InventoryName.compareTo("container.chestDouble")==0||InventoryName.compareTo("초심자 가이드")==0
						||InventoryName.compareTo("container.minecart")==0||InventoryName.compareTo("이벤트 전체 지급")==0
						||InventoryName.compareTo("이벤트 랜덤 지급")==0||InventoryName.compareTo("교환")==0||InventoryName.compareTo("부활 아이템")==0
						||InventoryName.compareTo("구조 아이템")==0||InventoryName.compareTo("도박 상품 정보")==0||InventoryName.compareTo("도박 기계 코인")==0
						||InventoryName.compareTo("[NPC] 선물 아이템을 올려 주세요")==0
						))
						{
							event.setCancelled(true);
						}
					}
					
					if(InventoryName.contains("스텟")||InventoryName.contains("옵션")||InventoryName.contains("개조식")||
					   InventoryName.compareTo("기타")==0||InventoryName.contains("가이드")||
					   InventoryName.contains("파티")||InventoryName.contains("NPC")
					   ||InventoryName.contains("던전")||InventoryName.compareTo("이벤트 진행")==0
					   ||InventoryName.contains("목록")||InventoryName.contains("퀘스트")||
					   InventoryName.contains("관리자")||InventoryName.contains("등록")||InventoryName.compareTo("오브젝트 추가")==0
					   ||InventoryName.contains("장비")||InventoryName.contains("선택")||InventoryName.contains("아이템")
					    ||InventoryName.contains("[MapleStory]")||InventoryName.contains("[Mabinogi]")
					    ||InventoryName.contains("스킬")||InventoryName.contains("랭크")||InventoryName.contains("몬스터")
					    ||InventoryName.contains("등록된")||InventoryName.contains("직업군")||InventoryName.contains("초심자")
					    ||InventoryName.contains("카테고리")||InventoryName.compareTo("해당 블록을 캐면 나올 아이템")==0||InventoryName.contains("영역")
					    ||InventoryName.contains("월드")||InventoryName.contains("워프")||InventoryName.contains("매직스펠")
					    ||InventoryName.contains("이벤트")||InventoryName.contains("친구")||InventoryName.contains("네비")
					    ||InventoryName.compareTo("교환")==0||InventoryName.contains("부활")||InventoryName.contains("도박")
					    ||InventoryName.compareTo("슬롯 머신")==0||InventoryName.contains("개체")
					    )
					{
						new GBD_RPG.Main_Event.Main_InventoryClick().InventoryClickRouter(event, InventoryName);
					}
				}
			}
		}
		return;
	}
	
	@EventHandler
	private void InventoryClose(InventoryCloseEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&Main_ServerOption.MagicSpellsCatched==true)
		{
			GBD_RPG.Util.ETC ETC = new GBD_RPG.Util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getPlayer());
		}

		if(event.getInventory().getName().charAt(0)=='§')
		{
			if(event.getInventory().getName().charAt(1)=='c')
				new GBD_RPG.Structure.Structure_Main().InventoryCloseRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
			else if(event.getInventory().getName().charAt(1)=='2')
				new GBD_RPG.Dungeon.Dungeon_GUI().InventoryCloseRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
			else
				new GBD_RPG.Main_Event.Main_InventoryClose().InventoryCloseRouter(event);
		}
		return;
	}

	public boolean onCommand(CommandSender talker, org.bukkit.command.Command command, String string, String[] args)
    {
		new GBD_RPG.Main_Main.Main_ServerOption().MagicSpellCatch();
		new GBD_RPG.Main_Main.Main_ServerOption().CitizensCatch();
		for(byte count = 0; count <args.length; count++)
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
			GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
			
			switch(string)
			{
				case"gui사용":
					if(player.isOp() == true)
					{
					 	s.SP((Player)talker, org.bukkit.Sound.ENTITY_VILLAGER_YES, 1.0F, 1.8F);
					    player.sendMessage(ChatColor.GREEN+"[NPC] : GUI를 활성화 시킬 NPC를 우클릭 하세요!");
					    new UserData_Object().setInt(player, (byte) 4, 114);
					}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
				case"친구":
				 	s.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				 	new GBD_RPG.User.ETC_GUI().FriendsGUI(player, (short) 0);
					return true;
				case"스킬":
				 	s.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				    GBD_RPG.Skill.UserSkill_GUI PSKGUI = new GBD_RPG.Skill.UserSkill_GUI();
					PSKGUI.MainSkillsListGUI(player, (short) 0);
					return true;
		  		case "아이템" :
		  			GBD_RPG.CustomItem.CustomItem_Command ItemC = new GBD_RPG.CustomItem.CustomItem_Command();
		  			if(args.length <= 0)
		  			{
		  				if(args.length <=0)
		  				{
		  					ItemC.HelpMessage(player);
			  				return true;	
		  				}
		  				if(ChatColor.stripColor(args[0]).equalsIgnoreCase("설명제거") ==true)
		  				 ItemC.onCommand2(talker, command, string, args);
		  				else
		  				{
		  					ItemC.HelpMessage(player);
			  				return true;	
		  				}
		  			}
		  			 if(ChatColor.stripColor(args[0]).equalsIgnoreCase("목록") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("등록") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("삭제") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("받기") == false&&ChatColor.stripColor(args[0]).equalsIgnoreCase("주기") == false)
		  				 ItemC.onCommand2(talker, command, string, args);
		  			 else
				  		ItemC.onCommand1(talker, command, string, args);
		  			break;
		  		case "파티":
		  			GBD_RPG.Party.Party_Command PartyC = new GBD_RPG.Party.Party_Command();
		  			PartyC.onCommand(talker, command, string, args);
		  			return true;
				case "테스트":
				case "테스트2":
		  		case "타입추가":
				case "엔티티제거":
				case "아이템제거":
		  		case "강제철거":
		  		case "오피박스":
		  			new GBD_RPG.Admin.Admin_Command().onCommand(player, args, string);
		  			return true;
				case "수락":
				case "거절":
		  		case "돈":
		  			new GBD_RPG.User.User_Command().onCommand(player, args, string);
		  			return true;
		  		case "스텟":
				 	s.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				 	new GBD_RPG.User.Stats_GUI().StatusGUI((Player)talker);
					return true;
		  		case "옵션":
				 	s.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				 	new GBD_RPG.User.Option_GUI().optionGUI((Player)talker);
					return true;
		  		case "기타":
				 	s.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				 	new GBD_RPG.User.ETC_GUI().ETCGUI_Main((Player) talker);
					return true;
		  		case "몬스터" :
				  if(talker.isOp() == true)
				  {
					  s.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
		  			new GBD_RPG.Monster.Monster_GUI().MonsterListGUI(player, 0);
				  }
				  else
				  {
					  talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					  s.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				  }
		  			return true;
		  		case "워프":
		  			GBD_RPG.Warp.Warp_Command WarpC = new GBD_RPG.Warp.Warp_Command();
		  			WarpC.onCommand(talker, command, string, args);
		  			return true;
		  		case "영역":
		  			GBD_RPG.Area.Area_Command AreaC = new GBD_RPG.Area.Area_Command();
		  			AreaC.onCommand(talker, command, string, args);
		  			return true;
		  		case "상점":
		  			GBD_RPG.NPC.NPC_Command NPCC = new GBD_RPG.NPC.NPC_Command();
		  			NPCC.onCommand(talker, command, string, args);
		  			return true;
		  		case "퀘스트":
		  			GBD_RPG.Quest.Quest_Command QC = new GBD_RPG.Quest.Quest_Command();
		  			QC.onCommand(talker, command, string, args);
		  			return true;
		  		case "커맨드":
		  			if(player.isOp() == true)
		  			{
		  				UserData_Object u = new UserData_Object();
						if(u.getType(player).compareTo("Skill")==0)
						{
							if(u.getString(player, (byte)1).equalsIgnoreCase("SKC"))
							{
								String CommandString = "";
								for(byte count = 0; count <args.length-1; count ++)
									CommandString = CommandString+args[count]+" ";
								CommandString = CommandString+args[args.length-1];
							  	YamlController YC = new YamlController(this);
								YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
								if(CommandString.contains("/")==false)
									CommandString = "/"+CommandString;
								if(CommandString.equalsIgnoreCase("/없음"))
									SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command","null");
								else
									SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command",CommandString);
								SkillList.saveConfig();
								GBD_RPG.Skill.OPboxSkill_GUI SKGUI = new GBD_RPG.Skill.OPboxSkill_GUI();
								SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
								u.clearAll(player);
							}
						}
						else
						{
		  					player.sendMessage(ChatColor.RED+"[스킬 설정] : 스킬 설정을 위한 커맨드 입니다!");
							s.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  					return true;
						}
		  			}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						s.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
			}
			return false;
		}
		return false;
    }

}