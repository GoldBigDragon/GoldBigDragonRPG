package corpse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

import corpse.gui.ReviveSelectGui;
import effect.PottionBuff;
import effect.SoundEffect;
import util.YamlLoader;

public class CorpseAPI
{
    public static HashMap<String, ArrayList<ArmorStand>> corpses = new HashMap<>();

	public boolean deathCapture(Player player, boolean isJoin)
	{
		if(player.getGameMode()==GameMode.SPECTATOR)
		{
		  	if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isDeath())
		  	{
	  			Location l = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint();
		  		if(l.getBlockY() < 0)
		  			l.setY(0);
		  		if(l.getBlockX()!=player.getLocation().getBlockX()||l.getBlockY()!=player.getLocation().getBlockY()||l.getBlockZ()!=player.getLocation().getBlockZ())
		  			player.teleport(l);
		  		if(!isJoin)
		  			new ReviveSelectGui().openReviveSelectGui(player);
		  		else if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
		  		{
	  	    		new otherplugins.NoteBlockApiMain().Stop(player);
	  			  	YamlLoader configYaml = new YamlLoader();
	  				configYaml.getConfig("config.yml");
	  				if(configYaml.getInt("Death.Track")!=-1)
	  					new otherplugins.NoteBlockApiMain().Play(player, configYaml.getInt("Death.Track"));
		  		}
		  		if(corpses.containsKey(player.getName())==false)
		  			createCorpse(player);
		  		return true;
		  	}
		}
		return false;
	}
	
	public void asyncDeathCapture(final Player player)
	{
		if(player.getGameMode()==GameMode.SPECTATOR)
		{
		  	if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isDeath())
		  	{
	  			Location l = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint();
		  		if(l.getBlockY() < 0)
		  			l.setY(0);
		  		if(l.getBlockX()!=player.getLocation().getBlockX()||l.getBlockY()!=player.getLocation().getBlockY()||l.getBlockZ()!=player.getLocation().getBlockZ())
		  		{
		  			final Location loc = l.clone();
		  			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main.Main.plugin, new Runnable()
		  	        {
		  	            @Override
		  	            public void run() 
		  	            {
		  	            	try
		  	            	{
					  			player.teleport(loc);
		  	            	}
		  	            	catch(Exception e)
		  	            	{
		  	            	}
		  	            }
		  	        }, 0);
		  		}
	  			new ReviveSelectGui().openReviveSelectGui(player);
		  		if(!corpses.containsKey(player.getName()))
		  		{
		  			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main.Main.plugin, new Runnable()
		  	        {
		  	            @Override
		  	            public void run() 
		  	            {
				  			createCorpse(player);
		  	            }
		  	        }, 0);
		  		}
		  	}
		}
	}
	
	public void createCorpse(Player player)
	{
		if(corpses.containsKey(player.getName()))
			return;
		else
		{
			removeCorpse(player.getName());
			String name = player.getName();
			ArrayList<ArmorStand> AL = new ArrayList<ArmorStand>();
	        player.setGameMode(GameMode.SPECTATOR);
	        int playerRandom = new Random().nextInt((int) (91))-45;
			//if(CorpseStyle == 0)
			{
				Location loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY()-0.1, player.getLocation().getZ());
		        loc.setDirection(loc.getDirection());
		        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
		        stand.setBasePlate(false);
		        stand.setVisible(false);
		        stand.setRemoveWhenFarAway(false);
		        stand.setGravity(false);
		        stand.setArms(true);
		        stand.setCustomName("§c§0h");
		        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		        SkullMeta meta = (SkullMeta) head.getItemMeta();
		        meta.setDisplayName(name);
		        meta.setOwner(name);
		        head.setItemMeta(meta);
		        stand.setHelmet(head);
		        stand.setHeadPose(new EulerAngle(0.75d, playerRandom, 0));
		        player.teleport(stand.getLocation());
		        //player.setSpectatorTarget(stand);
		        AL.add(stand);
		        
		        loc.add(0.41,-0.5,-0.6);
		        ItemStack item = new ItemStack(Material.STICK);
		        ItemMeta itemMeta = item.getItemMeta();
		        itemMeta.setDisplayName(name);
		        item.setItemMeta(itemMeta);
		        stand = loc.getWorld().spawn(loc, ArmorStand.class);
		        stand.setBasePlate(false);
		        stand.setVisible(false);
		        stand.setRemoveWhenFarAway(false);
		        stand.setGravity(false);
		        stand.setArms(true);
		        stand.setCustomName("§c§0b");
		        stand.setItemInHand(item);
		        stand.setRightArmPose(new EulerAngle(55f,0,0));
		        AL.add(stand);
		        
		        loc.add(0,-0.8,-0.155);
		        stand = loc.getWorld().spawn(loc, ArmorStand.class);
		        stand.setBasePlate(false);
		        stand.setVisible(false);
		        stand.setRemoveWhenFarAway(false);
		        stand.setGravity(false);
		        stand.setArms(true);
		        stand.setCustomName("§c§0b");
		        stand.setItemInHand(item);
		        stand.setRightArmPose(new EulerAngle(55f,0,0));
		        AL.add(stand);
		        
		        loc.add(0.42,1.45,0.7);
		        stand = loc.getWorld().spawn(loc, ArmorStand.class);
		        stand.setBasePlate(false);
		        stand.setVisible(false);
		        stand.setRemoveWhenFarAway(false);
		        stand.setGravity(false);
		        stand.setArms(true);
		        stand.setCustomName("§c§0b");
		        stand.setItemInHand(item);
		        stand.setRightArmPose(new EulerAngle(0 ,1.5d, 0.2d));
		        AL.add(stand);

		        corpses.put(name, AL);
		        
		        //?���?? ?��?��?��?�� ?��?�� ?�� ?��?��?�� ?��름에 모두 ?��?��?��?�� ?���?? ?���??
		        //?��?�� ?�� ?���?? ?��?��?�� ?��름을 보고, h�?? 머리 ?��?��?��?��, b�?? ?�� ?��?��?��?��
		        //?��름에 ?��?�� ?��?��?��?�� ?��름을 보고 ?��?���?? ?��리고 ?���?? ?��.
			}
		}
	}

	public void removeCorpse(String player)
	{
        LivingEntity entity = (LivingEntity) Bukkit.getPlayer(player);
        entity.setCollidable(true);
		if(corpses.containsKey(player))
		{
			for(int count = 0; count < corpses.get(player).size(); count ++)
				corpses.get(player).get(count).remove();
			corpses.remove(player);
		}
	}
	
	public void removeAllCorpse()
	{
		Object[] players = corpses.keySet().toArray();
		for(int count = 0; count < corpses.size(); count++)
			if(corpses.containsKey(players[count].toString()))
				for(int count2 = 0; count2 < corpses.get(players[count].toString()).size(); count2 ++)
					corpses.get(players[count].toString()).get(count2).remove();
    	corpses.clear();
	}

	public void reviveAtLastVisitedArea(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
	  	YamlLoader areaYaml = new YamlLoader();
		YamlLoader configYaml = new YamlLoader();
		new util.ETC().updatePlayerHPMP(player);

	  	configYaml.getConfig("config.yml");
		areaYaml.getConfig("Area/AreaList.yml");
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited() != null)
		{
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited().equals("null"))
				player.teleport(player.getWorld().getSpawnLocation());
			else
			{
				String respawnCity = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited();
				String[] arealist = areaYaml.getKeys().toArray(new String[0]);
				for(int count =0; count <arealist.length;count++)
				{
					if(arealist[count].equals(respawnCity))
					{
						if(areaYaml.getBoolean(arealist[count]+".SpawnPoint"))
						{
							String world = areaYaml.getString(arealist[count]+".World");
							double x = areaYaml.getDouble(arealist[count]+".SpawnLocation.X");
							float y = (float) areaYaml.getDouble(arealist[count]+".SpawnLocation.Y");
							double z = areaYaml.getDouble(arealist[count]+".SpawnLocation.Z");
							double pitch = areaYaml.getDouble(arealist[count]+".SpawnLocation.Pitch");
							double yaw = areaYaml.getDouble(arealist[count]+".SpawnLocation.Yaw");

							penalty(player, configYaml.getString("Death.Spawn_Home.SetHealth"), configYaml.getString("Death.Spawn_Home.PenaltyEXP"), configYaml.getString("Death.Spawn_Home.PenaltyMoney"));

							Location loc = new Location(Bukkit.getServer().getWorld(world), x, y,z, (float)yaw, (float)pitch);
							player.teleport(loc);
							PottionBuff.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
							SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
							return;
						}
					}
				}
				player.teleport(player.getWorld().getSpawnLocation());
			}
		}
		else
			player.teleport(player.getWorld().getSpawnLocation());
		penalty(player, configYaml.getString("Death.Spawn_Home.SetHealth"), configYaml.getString("Death.Spawn_Home.PenaltyEXP"), configYaml.getString("Death.Spawn_Home.PenaltyMoney"));
	}
	
	public void reviveAtDeadPoint(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
	  	YamlLoader configYaml = new YamlLoader();
		new util.ETC().updatePlayerHPMP(player);
	  	configYaml.getConfig("config.yml");
		penalty(player, configYaml.getString("Death.Spawn_Here.SetHealth"), configYaml.getString("Death.Spawn_Here.PenaltyEXP"), configYaml.getString("Death.Spawn_Here.PenaltyMoney"));
	}
	
	public void penalty(Player player, String health, String exp, String money)
	{
		int healthPercent = Integer.parseInt(health.replace("%", ""));
		int expPercent = Integer.parseInt(exp.replace("%", ""));
		int moneyPercent = Integer.parseInt(money.replace("%", ""));
		if(healthPercent < 0)
			healthPercent = 1;
		if(healthPercent > 100)
			healthPercent = 100;
		if(expPercent < 0)
			expPercent = 0;
		if(expPercent > 100)
			expPercent = 100;
		if(moneyPercent < 0)
			moneyPercent = 0;
		if(moneyPercent > 100)
			moneyPercent = 100;
		player.setHealth((player.getMaxHealth()/100)*healthPercent);
		long pEXP = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP();
		long pMaxEXP = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP();
	  	if(pEXP - ((pMaxEXP/100)*expPercent)<pMaxEXP*-1)
	  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(-1 * pMaxEXP);
	  	else
	  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(pEXP - ((pMaxEXP/100)*expPercent));
	  	long pMoney = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP((((pMoney/100)*moneyPercent) * -1), 0, false);
  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setDeath(false);
	}
}
