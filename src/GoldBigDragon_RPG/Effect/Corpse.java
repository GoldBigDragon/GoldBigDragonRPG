package GoldBigDragon_RPG.Effect;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class Corpse
{
	public static JavaPlugin plugin = null;
    public static HashMap<String, Entity> Corpses = new HashMap<>();
    public static HashMap<String, UUID> CorpsesUUIDs = new HashMap<>();
	
	public void setJavaPlugin(JavaPlugin p)
	{
		this.plugin = p;
		return;
	}
	
	public boolean DeathCapture(Player player, boolean isJoin)
	{
		if(player.getGameMode()==GameMode.SPECTATOR)
		{
			YamlController YC_1 = GoldBigDragon_RPG.Main.Main.YC_1;
		  	YamlManager YM = YC_1.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		  	if(YM.getBoolean("Death")==true)
		  	{
				double X = YM.getDouble("LastDeathPoint.X");
				double Y = YM.getDouble("LastDeathPoint.Y");
				double Z = YM.getDouble("LastDeathPoint.Z");
				double Pitch = YM.getDouble("LastDeathPoint.Pitch");
				double Yaw = YM.getDouble("LastDeathPoint.Yaw");
				player.teleport(new Location(Bukkit.getServer().getWorld(YM.getString("LastDeathPoint.World")), X, Y, Z, (float)Yaw, (float)Pitch));
		  		if(isJoin==false)
		  			new GoldBigDragon_RPG.GUI.DeathGUI().OpenReviveSelectGUI(player);
		  		else
		  		{
		  	    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		  	    	{
		  	    		new OtherPlugins.NoteBlockAPIMain().Stop(player);
		  				YamlManager Config = YC_1.getNewConfig("config.yml");
		  				if(Config.getInt("Death.Track")!=-1)
		  					new OtherPlugins.NoteBlockAPIMain().Play(player, Config.getInt("Death.Track"));
		  	    	}
		  		}
		  		CreateCorpse(player);
		  		return true;
		  	}
		}
		return false;
	}
	
	public void CreateCorpse(Player player)
	{
		if(Corpses.containsKey(ChatColor.stripColor(player.getName()))==false)
		{
			Location loc = player.getLocation();
			loc.setYaw(0F);
			loc.setPitch(-220F);
		    
	        UUID UID = Bukkit.getServer().getOfflinePlayer(player.getName()).getUniqueId();
	        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
	        WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
	        String CorpseName = ChatColor.RED+player.getName();
	        if(CorpseName.length() > 16)
	        	CorpseName = CorpseName.substring(0, 16);
	        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UID, CorpseName), new PlayerInteractManager(nmsWorld));
	        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0F, -220F);
	        npc.setSneaking(true);
	        for(Player Everyone : Bukkit.getOnlinePlayers())
	        {
	        	PlayerConnection con =  ((CraftPlayer) Everyone).getHandle().playerConnection;
	        	con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
	        	con.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
	        	//con.sendPacket(new PacketPlayOutBed(npc,new BlockPosition(loc.getX(), loc.getY(), loc.getZ())));
	        	//닉네임은 누운것 처럼 아래로 가는데, 정작 캐릭터가 눕는 액션을 안함...
	        }
	    	Corpses.put(player.getName(), (Entity)npc);
	    	CorpsesUUIDs.put(player.getName(), UID);
		}
	}

	public void ShowCorpse(Player player)
	{
    	PlayerConnection con =  ((CraftPlayer) player).getHandle().playerConnection;
		for (final String CorpseKey : Corpses.keySet())
		{
		    Entity Corpse = Corpses.get(CorpseKey);
		    if (Corpse != null)
		    {
		    	con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, (EntityPlayer)Corpse));
		    	con.sendPacket(new PacketPlayOutNamedEntitySpawn((EntityPlayer)Corpse));
		    }
		}
	}
	
	public void RemoveCorpse(String player)
	{
		Entity Corpse = Corpses.get(ChatColor.stripColor(player));
		if(Corpse != null)
		{
			Corpses.remove(ChatColor.stripColor(player));
        	MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
	        WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
	        UUID UID = Bukkit.getServer().getPlayer(player).getUniqueId();
	        EntityPlayer Corpse2 = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UID, ChatColor.stripColor(player)), new PlayerInteractManager(nmsWorld));
	    	try
	    	{
	    		Corpse.world.removeEntity(Corpse);
	    	} catch (Exception e) {
	    	    e.printStackTrace();
	    	}
	        for(Player Everyone : Bukkit.getOnlinePlayers())
	        {
	        	PlayerConnection con = ((CraftPlayer) Everyone).getHandle().playerConnection;
	        	con.sendPacket(new PacketPlayOutEntityDestroy(Corpse.getId()));
	        	con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, (EntityPlayer)Corpse));
	        	con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, Corpse2));
	        	con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_LATENCY, Corpse2));
	        }
		}
	}
	
	public void RemoveAllCorpse()
	{
    	Object[] CorpseIDList = Corpses.keySet().toArray();
    	for(int count = 0; count < CorpseIDList.length; count++)
    		RemoveCorpse(CorpseIDList[count].toString());
    	Corpses.clear();
	}
	
	public Player getCorpseOwner(org.bukkit.entity.Entity Corpse)
	{
		List<org.bukkit.entity.Entity> e = Corpse.getNearbyEntities(2D, 2D, 2D);
		
		for(int count=0; count < e.size();count++)
		{
			if(e.get(count).getType()==EntityType.PLAYER)
			{
				Player player = (Player)e.get(count);
				if(player.getGameMode()==GameMode.SPECTATOR)
				{
					YamlController YC_1 = GoldBigDragon_RPG.Main.Main.YC_1;
				  	YamlManager YM = YC_1.getNewConfig("Stats/" + player.getUniqueId()+".yml");
				  	if(YM.getBoolean("Death")==true)
				  	{
				  		return player;
				  	}
				}
			}
		}
		return null;
	}
}
