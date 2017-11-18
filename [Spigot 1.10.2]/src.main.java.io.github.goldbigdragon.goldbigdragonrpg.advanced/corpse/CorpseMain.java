package corpse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;

import util.YamlLoader;

public class CorpseMain
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
		  			new CorpseGui().openReviveSelectGui(player);
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
				new CorpseGui().openReviveSelectGui(player);
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
			int corpseStyle = new Random().nextInt((int) (11)); //(0 ~ 10 까�??�� ?��)
			String name = player.getName();
			ArrayList<ArmorStand> AL = new ArrayList<ArmorStand>();
	        player.setGameMode(GameMode.SPECTATOR);
	        int playerRandom = new Random().nextInt((int) (91))-45;
	        Location playerLoc = player.getLocation();
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
}
