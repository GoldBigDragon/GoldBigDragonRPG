package main;

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
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import effect.SoundEffect;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import party.PartyDataManager;
import user.UserDataObject;
import util.YamlLoader;



public class Main extends JavaPlugin implements Listener
{
	public static JavaPlugin plugin = null;

	@EventHandler
	public void SongEndEvent(com.xxmicloxx.noteblockapi.SongEndEvent event)
	{
		event.getSongPlayer().setPlaying(false);
		Player player = null;
		for(int count = 0; count < event.getSongPlayer().getPlayerList().size(); count++)
		{
			player = Bukkit.getPlayer(event.getSongPlayer().getPlayerList().get(count));
			new otherplugins.NoteBlockApiMain().SongPlay(player, event.getSongPlayer().getSong());
		}
	}

    @EventHandler
    public void craftItem(PrepareItemCraftEvent event)
    {
    	Inventory inv = event.getInventory();
    	ItemStack item = null;
    	boolean cantCraft = false;
    	for(int count = 0; count < inv.getSize(); count++)
    	{
    		item = inv.getItem(count);
    		if(item != null && item.getType() != Material.AIR)
    		{
    			if(item.hasItemMeta()&&item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)&&item.getItemMeta().hasLore()&&item.getItemMeta().hasDisplayName()&&
    			item.getItemMeta().getLore().get(0).contains("[돈]"))
    			{
    				cantCraft = true;
    				break;
    			}
    				
    		}
    	}
    	if(cantCraft)
    		inv.setItem(0, new ItemStack(Material.AIR));
    }
	
	public void onEnable()
	{
		plugin = this;
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new battle.BattleMain(), this);
		getServer().getPluginManager().registerEvents(new event.EventBlockPlace(), this);
		getServer().getPluginManager().registerEvents(new map.Map(), this);
		getServer().getPluginManager().registerEvents(new event.EventBlockBreak(), this);
		getServer().getPluginManager().registerEvents(new event.EventFishing(), this);
		getServer().getPluginManager().registerEvents(new event.EventPlayerChat(), this);
		getServer().getPluginManager().registerEvents(new event.EventChangeHotBar(), this);
		getServer().getPluginManager().registerEvents(new event.EventPlayerJoin(), this);
		new otherplugins.NoteBlockApiMain(Main.plugin);
		new MainServerOption().initialize();
		
		if(getServer().getPluginManager().getPlugin("Vault")!=null)
		{
			RegisteredServiceProvider<Economy> rspE = getServer().getServicesManager().getRegistration(Economy.class);
			if(rspE != null)
				main.MainServerOption.economy = rspE.getProvider();
		}
	  	return;
	}
	
	public void onDisable()
	{
		new corpse.CorpseMain().removeAllCorpse();
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] a = new Player[playerlist.size()];
    	playerlist.toArray(a);
    	for(int count = 0; count <a.length;count++)
    		new otherplugins.NoteBlockApiMain().Stop(a[count]);
	  	new PartyDataManager().saveParty();

		new servertick.ServerTickScheduleManager().saveCategoriFile();
		Object[] players = Bukkit.getOnlinePlayers().toArray();
		for(int count = 0; count < players.length; count++)
			MainServerOption.PlayerList.get(((Player)players[count]).getUniqueId().toString()).saveAll();
	  	Bukkit.getConsoleSender().sendMessage("§c[Clossing GoldBigDragon Advanced...]");
	  	return;
	}
	
	@EventHandler
	private void PlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
	  	YamlLoader userYaml = new YamlLoader();
		if(player.getLocation().getWorld().getName().equals("Dungeon"))
			new dungeon.DungeonMain().EraseAllDungeonKey(player, true);
		
		if(new corpse.CorpseMain().deathCapture(player,false))
			new corpse.CorpseMain().removeCorpse(player.getName());
		
		if(MainServerOption.partyJoiner.containsKey(player))
			MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).QuitParty(player);
		
		new otherplugins.NoteBlockApiMain().Stop(event.getPlayer());

		userYaml.getConfig("UserData/"+ player.getUniqueId()+".yml");
		userYaml.removeKey("Data");
		userYaml.saveConfig();
		MainServerOption.PlayerCurrentArea.remove(player);
    	new user.EquipGui().FriendJoinQuitMessage(player, false);

	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		if(configYaml.getString("Server.QuitMessage") != null)
		{
			String message = configYaml.getString("Server.QuitMessage").replace("%player%",event.getPlayer().getName());
			event.setQuitMessage(message);
		}
		else
			event.setQuitMessage(null);
	  main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).saveAll();
	  main.MainServerOption.PlayerList.remove(player.getUniqueId().toString());
	}
	
	@EventHandler
	private void PlayerRespawn(PlayerRespawnEvent event)
	{
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		if(configYaml.getBoolean("Death.SystemOn"))
		{
			Player player = event.getPlayer();
	    	event.setRespawnLocation(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint());
	    	player.setGameMode(GameMode.SPECTATOR);
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
			{
	    		new otherplugins.NoteBlockApiMain().Stop(player);
				if(configYaml.contains("Death.Track"))
					if(configYaml.getInt("Death.Track")!=-1)
						new otherplugins.NoteBlockApiMain().Play(player, configYaml.getInt("Death.Track"));
			}
	    	new corpse.CorpseGui().openReviveSelectGui(player);
		}
		return;
	}

	
	@EventHandler
	private void PlayerItemDrop(PlayerDropItemEvent event)
	{
		ItemStack IT = event.getItemDrop().getItemStack();
		if(IT.hasItemMeta() == true)
			if(IT.getItemMeta().hasLore() == true)
				if(IT.getItemMeta().getLore().size() == 4)
					if(IT.getItemMeta().getLore().get(3).equals("§e[클릭시 퀵슬롯에서 삭제]")==true)
						event.setCancelled(true);
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
				new structure.StructureMain().StructureUse(player,AS.getCustomName());
				return;
			}
			else if(name.charAt(0)=='§'&&name.charAt(1)=='c'&&name.charAt(2)=='§'&&name.charAt(3)=='0')
			{
				event.setCancelled(true);
				if(player.getInventory().getItemInMainHand() != null)
				{
					if(MainServerOption.DeathRescue != null)
					{
						if(MainServerOption.DeathRescue.getTypeId()==player.getInventory().getItemInMainHand().getTypeId())
						{
							ItemStack Pitem = player.getInventory().getItemInMainHand();
							if(MainServerOption.DeathRescue.getAmount()<=Pitem.getAmount())
							{
								
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
									  	if(main.MainServerOption.PlayerList.get(target.getUniqueId().toString()).isDeath())
									  	{
											if(new util.UtilPlayer().deleteItem(player, MainServerOption.DeathRescue, MainServerOption.DeathRescue.getAmount()))
											{
												new corpse.CorpseMain().removeCorpse(Name);
												player.updateInventory();
												player.sendMessage("§d[구조] : §e"+target.getName()+"§d님을 부활시켰습니다!");
												target.sendMessage("§d[부활] : §e"+player.getName()+"§d님에 의해 부활하였습니다!");
												target.setGameMode(GameMode.SURVIVAL);
												target.closeInventory();
												Location l = target.getLocation();
												l.add(0, 1, 0);
												target.teleport(l);
												for(int count2=0;count2<210;count2++)
													new effect.ParticleEffect().PL(target.getLocation(), org.bukkit.Effect.SMOKE, new util.UtilNumber().RandomNum(0, 14));
												SoundEffect.SL(target.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.5F, 1.8F);
									    		new otherplugins.NoteBlockApiMain().Stop(target);
											  	YamlLoader configYaml = new YamlLoader();
										    	configYaml.getConfig("config.yml");
										    	new corpse.CorpseGui().penalty(target, configYaml.getString("Death.Spawn_Help.SetHealth"), configYaml.getString("Death.Spawn_Help.PenaltyEXP"), configYaml.getString("Death.Spawn_Help.PenaltyMoney"));
												return;
											}
											else
											{
												SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
												player.sendMessage("§c[SYSTEM] : 부활 아이템이 부족하여 부활시킬 수 없습니다!");
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
														if(Name.equals(Name2))
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
					area.AreaMain A = new area.AreaMain();
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
		if(MainServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().equals("Dungeon"))
			event.setCancelled(true);
	}
	@EventHandler
	private void BlockIgniteEvent(BlockIgniteEvent event)
	{
		if((MainServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().equals("Dungeon"))&&event.getIgnitingEntity()==null)
			event.setCancelled(true);
	}
	
	
	@EventHandler
	private void PlayerDeath(PlayerDeathEvent event)
	{
		final Player player = event.getEntity();
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		if(configYaml.getBoolean("Death.SystemOn"))
		{
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setDeath(true);
		    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		    {
		      public void run()
		      {
		        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
		        ((CraftPlayer)player).getHandle().playerConnection.a(packet);
		      }
		    }, 1L);
		}
		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setLastDeathPoint(new Location(event.getEntity().getLocation().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ(), event.getEntity().getLocation().getYaw(), event.getEntity().getLocation().getPitch()));
		if(event.getKeepInventory()==false)
		{
			List<ItemStack> Ilist = event.getDrops();
			event.setKeepInventory(true);
			event.getEntity().getInventory().clear();
			for(int count = 0; count < Ilist.size(); count++)
			{
				ItemStack IT = Ilist.get(count);
				if(IT.isSimilar(MainServerOption.DeathRevive))
				{
					Ilist.remove(count);
					event.getEntity().getInventory().addItem(IT);
				}
				
				/*
				else if(IT.hasItemMeta() == true)
					if(IT.getItemMeta().hasLore() == true)
						if(IT.getItemMeta().getLore().size() >= 4)
							if(IT.getItemMeta().getLore().get(3).equals("§e[클릭시 퀵슬롯에서 삭제]")==true)
								Ilist.remove(count);
				*/
			}
			for(int count = 0; count < Ilist.size(); count++)
				new event.EventItemDrop().CustomItemDrop(event.getEntity().getLocation(), Ilist.get(count));
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
	private void EntitySpawn(CreatureSpawnEvent event) {new monster.MonsterSpawn().EntitySpawn(event);return;}
    @EventHandler
    private void ITBlock(PlayerInteractEvent event)
    {
		if (event.getAction() == Action.PHYSICAL)
		{
			Block block = event.getClickedBlock();
			if (block != null)
			{
				if (block.getTypeId() == 60) 
				{
					area.AreaMain A = new area.AreaMain();
					String[] Area = A.getAreaName(event.getClickedBlock());
					if(Area != null)
					{
						if(A.getAreaOption(Area[0], (char) 7) == false)
						{
							event.setCancelled(true);
							if(event.getPlayer().isOp() == false)
							{
								SoundEffect.SP(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
								event.getPlayer().sendMessage("§c[SYSTEM] : §e"+ Area[1] + "§c 지역에 있는 작물은 손 댈 수없습니다!");
							}
							return;
						}
					}
				}
			}
		}
    	new util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new event.EventInteract().PlayerInteract(event);
    	return;
    }
    @EventHandler
    private void ITEnity(PlayerInteractEntityEvent event)
    {
    	new util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new main.MainServerOption().CitizensCatch();
    	new event.EventInteract().PlayerInteractEntity(event);
    	return;
    }
    
    @EventHandler
    private void ItemGetMessage(PlayerPickupItemEvent event) {new event.EventInteract().PlayerGetItem(event);}
	@EventHandler
	private void MonsterKill(EntityDeathEvent event)	{new monster.MonsterKill().MonsterKilling(event);return;}

	@EventHandler
	private void EntityExplode(EntityExplodeEvent event)
	{
		if(MainServerOption.AntiExplode || event.getEntity().getLocation().getWorld().getName().equals("Dungeon"))
			event.blockList().clear();
	}
	
	@EventHandler
	private void ExplosionPrime(ExplosionPrimeEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().equals("Dungeon"))
		{
			if(event.getEntityType()==EntityType.ENDER_CRYSTAL || event.getEntityType()==EntityType.DRAGON_FIREBALL
					|| event.getEntityType()==EntityType.FIREBALL || event.getEntityType()==EntityType.SMALL_FIREBALL)
				return;
			else
			{
				event.setCancelled(true);
				new monster.MonsterKill().Boomb(event.getEntity());
				new monster.MonsterKill().DungeonKilled((LivingEntity)event.getEntity(), true);
			}
			event.getEntity().remove();
		}
	}
	
	@EventHandler
	private void onArrowHitBlock(ProjectileHitEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().equals("Dungeon"))
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
						for(int count = 0; count < 2; count++)
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
							new dungeon.DungeonMain().TrapChestOpen(b);
						else if(b.getTypeId() == 95)
							new dungeon.DungeonMain().TrapGlassTouch(b, player);
					}
				}
			}
		}
	}
	
	@EventHandler
	private void applyHealthRegen(EntityRegainHealthEvent event)
	{
		if (event.isCancelled())
			return;
		if (((event.getEntity() instanceof Player)) &&(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED))
	    {
	    	util.ETC ETC = new util.ETC();
	    	ETC.UpdatePlayerHPMP((Player)event.getEntity());
	    }
		return;
	}
	
	@EventHandler
	private void InventoryClick(InventoryClickEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true &&MainServerOption.MagicSpellsCatched==true)
		{
			util.ETC ETC = new util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getWhoClicked());
		}
		if(event.getClickedInventory() == null || event.getCurrentItem()==null || event.getCurrentItem().getType()==Material.AIR)
			return;
		if(event.getCurrentItem().hasItemMeta())
		{
			if(event.getCurrentItem().getItemMeta().hasLore())
			{
				if(event.getCurrentItem().getItemMeta().getLore().size() == 4)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(3).equals(("§e[클릭시 퀵슬롯에서 삭제]")))
					{
						event.setCancelled(true);
						event.getWhoClicked().getInventory().setItem(event.getSlot(), null);
						SoundEffect.SP((Player)event.getWhoClicked(), Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
						return;
					}
				}
			}
		}
		if(event.getInventory().getName().charAt(0)=='§')
		{
			String InventoryCode = event.getInventory().getName().split("§r")[0].replaceAll("§", "");
			//[§0] [§0§0] [§0§0] [§r]
			//1번째 색 코드 표 = 클릭시 이벤트 캔슬 여부
			//2,3번째 색 코드 표 = 해당 GUI화면 타입
			//4,5번째 색 코드 표 = 해당 GUI화면 타입 중, 몇 번째 GUI인지
			//6번째 되돌림 색 코드 표 = split을 위한 코드
			
			//1번째 색 코드가 0이면, 클릭시 무조건 취소되는 것
			if(event.getInventory().getName().charAt(1)=='0')
				event.setCancelled(true);
			new event.EventInventoryClick().InventoryClickRouter(event, InventoryCode);
		}
		return;
	}
	
	@EventHandler
	private void InventoryClose(InventoryCloseEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&MainServerOption.MagicSpellsCatched==true)
		{
			util.ETC ETC = new util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getPlayer());
		}

		if(event.getInventory().getName().charAt(0)=='§')
		{
			String InventoryCode = event.getInventory().getName().split("§r")[0].replaceAll("§", "");
			//[§0] [§0§0] [§0§0] [§r]
			//1번째 색 코드 표 = 클릭시 이벤트 캔슬 여부
			//2,3번째 색 코드 표 = 해당 GUI화면 타입
			//4,5번째 색 코드 표 = 해당 GUI화면 타입 중, 몇 번째 GUI인지
			//6번째 되돌림 색 코드 표 = split을 위한 코드
			new event.EventInventoryClose().InventoryCloseRouter(event, InventoryCode);
		}
		return;
	}

	public boolean onCommand(CommandSender talker, org.bukkit.command.Command command, String string, String[] args)
    {
		new main.MainServerOption().MagicSpellCatch();
		new main.MainServerOption().CitizensCatch();
		for(int count = 0; count <args.length; count++)
			args[count] = ChatColor.translateAlternateColorCodes('&', args[count]);
		
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			
			
			switch(string)
			{
				case"gui사용":
				case"gbdenablegui":
					if(player.isOp() == true)
					{
					 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_VILLAGER_YES, 1.0F, 1.8F);
					    player.sendMessage("§a[NPC] : GUI를 활성화 시킬 NPC를 우클릭 하세요!");
					    new UserDataObject().setInt(player, (byte) 4, 114);
					}
					else
					{
						talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
				case"친구":
				case"gbdfriend":
				 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				 	new user.EtcGui().FriendsGUI(player, (short) 0);
					return true;
				case"스킬":
				case"gbdskill":
				 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				    skill.UserSkillGui PSKGUI = new skill.UserSkillGui();
					PSKGUI.MainSkillsListGUI(player, (short) 0);
					return true;
		  		case "아이템" :
		  		case "gbditem" :
		  			customitem.CustomItemCommand ItemC = new customitem.CustomItemCommand();
		  			if(args.length <= 0)
		  			{
		  				if(args.length <=0)
		  				{
		  					ItemC.helpMessage(player);
			  				return true;	
		  				}
		  				if(ChatColor.stripColor(args[0]).equalsIgnoreCase("설명제거") ==true)
		  				 ItemC.onCommand2(talker, command, string, args);
		  				else
		  				{
		  					ItemC.helpMessage(player);
			  				return true;	
		  				}
		  			}
		  			 if(ChatColor.stripColor(args[0]).equalsIgnoreCase("목록") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("등록") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("삭제") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("받기") == false&&ChatColor.stripColor(args[0]).equalsIgnoreCase("주기") == false)
		  				 ItemC.onCommand2(talker, command, string, args);
		  			 else
				  		ItemC.onCommand1(talker, command, string, args);
		  			break;
		  		case "파티":
		  		case "gbdparty":
		  			party.PartyCommand PartyC = new party.PartyCommand();
		  			PartyC.onCommand(talker, command, string, args);
		  			return true;
				case "테스트":
				case "테스트2":
		  		case "타입추가":
				case "엔티티제거":
				case "아이템제거":
		  		case "강제철거":
		  		case "오피박스":
		  		case "스텟초기화권":
		  		case "경주":
		  		case "경험치주기":
				case "gbdtest":
				case "gbdtest2":
				case "gbdaddtype":
				case "gbdremoveentity":
				case "gbdremoveitem":
				case "gbdforceremove":
				case "opbox":
				case "gbdbacktothenewbie":
				case "giveexp":
		  			new admin.AdminCommand().onCommand(player, args, string);
		  			return true;
				case "수락":
				case "거절":
		  		case "돈":
				case "gbdaccept":
				case "gbddeny":
		  		case "gbdmoney":
		  			new user.UserCommand().onCommand(player, args, string);
		  			return true;
		  		case "스텟":
		  		case "gbdstat":
				 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				 	new user.StatsGui().StatusGUI((Player)talker);
					return true;
		  		case "옵션":
		  		case "gbdoption":
				 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				 	new user.OptionGui().optionGUI((Player)talker);
					return true;
		  		case "기타":
		  		case "gbdetc":
				 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				 	new user.EtcGui().ETCGUI_Main((Player) talker);
					return true;
		  		case "몬스터" :
		  		case "gbdmobs" :
				  if(talker.isOp() == true)
				  {
					  SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
		  			new monster.MonsterGui().MonsterListGUI(player, 0);
				  }
				  else
				  {
					  talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
					  SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				  }
		  			return true;
		  		case "워프":
		  		case "gbdwarp":
		  			warp.WarpCommand WarpC = new warp.WarpCommand();
		  			WarpC.onCommand(talker, command, string, args);
		  			return true;
		  		case "영역":
		  		case "gbdarea":
		  			area.AreaCommand AreaC = new area.AreaCommand();
		  			AreaC.onCommand(talker, command, string, args);
		  			return true;
		  		case "상점":
		  		case "gbdshop":
		  			npc.NpcCommand NPCC = new npc.NpcCommand();
		  			NPCC.onCommand(talker, command, string, args);
		  			return true;
		  		case "퀘스트":
		  		case "gbdquest":
		  			quest.QuestCommand QC = new quest.QuestCommand();
		  			QC.onCommand(talker, command, string, args);
		  			return true;
		  		case "커맨드":
		  		case "gbdcommand":
		  			if(player.isOp() == true)
		  			{
		  				UserDataObject u = new UserDataObject();
						if(u.getType(player)!=null&&u.getType(player).equals("Skill"))
						{
							if(u.getString(player, (byte)1).equalsIgnoreCase("SKC"))
							{
								String CommandString = "";
								for(int count = 0; count <args.length-1; count ++)
									CommandString = CommandString+args[count]+" ";
								CommandString = CommandString+args[args.length-1];
							  	YamlLoader skillYaml = new YamlLoader();
								skillYaml.getConfig("Skill/SkillList.yml");
								if(CommandString.contains("/")==false)
									CommandString = "/"+CommandString;
								if(CommandString.equalsIgnoreCase("/없음"))
									skillYaml.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command","null");
								else
									skillYaml.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command",CommandString);
								skillYaml.saveConfig();
								skill.OPboxSkillGui SKGUI = new skill.OPboxSkillGui();
								SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
								u.clearAll(player);
							}
						}
						else
						{
		  					player.sendMessage("§c[스킬 설정] : 이 명령어는 스킬 설정시 사용됩니다!");
							SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  					return true;
						}
		  			}
					else
					{
						talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
						SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
			}
			return false;
		}
		else
		{
			if(string.equals("경주")||string.equals("giveexp")||string.equals("경험치주기"))
			{
				if(args.length==2)
				{
  					Player target = Bukkit.getServer().getPlayer(args[0]);
	  				if(target.isOnline())
	  				{
	  					int EXP = 0;
	  					try
	  					{
	  						EXP = Integer.parseInt(args[1]);
	  					}
	  					catch(NumberFormatException e)
	  					{
	  					  	Bukkit.getConsoleSender().sendMessage("§c[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요!");
		  					return true;
	  					}
	  					main.MainServerOption.PlayerList.get(target.getUniqueId().toString()).addStat_MoneyAndEXP(0, EXP, true);
  					  	Bukkit.getConsoleSender().sendMessage("§a[SYSTEM] : " + args[0] + "님에게 경험치 " + EXP + "을 지급하였습니다!");
	  				}
	  				else
	  				{
					  	Bukkit.getConsoleSender().sendMessage("§c[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
	  				}
				}
				else
				{
					  	Bukkit.getConsoleSender().sendMessage("§c[SYSTEM] : /경주 [닉네임] [경험치]");
				}
			}
		}
		return false;
    }

	@EventHandler
	public void Sign(SignChangeEvent event)
	{
		for (int i = 0; i <= 3; i++)
	    {
			String line = event.getLine(i);
	        line = ChatColor.translateAlternateColorCodes('&', line);
	        event.setLine(i, line);
	    }
	}
}