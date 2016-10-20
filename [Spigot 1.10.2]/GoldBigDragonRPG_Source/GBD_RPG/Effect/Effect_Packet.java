package GBD_RPG.Effect;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_10_R1.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_10_R1.PlayerConnection;

public class Effect_Packet
{
	public void sendTitleSubTitle(Player player, String title, String subtitle, byte FadeInTime, byte ShowTime, byte FadeOutTime)
	{
		CraftPlayer p = (CraftPlayer)player;
	    PlayerConnection c = p.getHandle().playerConnection;
	    IChatBaseComponent TitleText = ChatSerializer.b(title);
	    IChatBaseComponent SubtitleText = ChatSerializer.b(subtitle);
	    Packet Length = new PacketPlayOutTitle(EnumTitleAction.TIMES, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    Packet TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    Packet SubtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, SubtitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    c.sendPacket(TitlePacket);
	    c.sendPacket(Length);
	    c.sendPacket(SubtitlePacket);
	    c.sendPacket(SubtitlePacket);
	}

	public void sendTitle(Player player, String title, byte FadeInTime, byte ShowTime, byte FadeOutTime)
	{
		CraftPlayer p = (CraftPlayer)player;
	    PlayerConnection c = p.getHandle().playerConnection;
	    IChatBaseComponent TitleText = ChatSerializer.b(title);
	    Packet Length = new PacketPlayOutTitle(EnumTitleAction.TIMES, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    Packet TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    c.sendPacket(TitlePacket);
	    c.sendPacket(Length);
	}

	public void sendTitleAllPlayers(String message)
	{
		Object[] PlayerList = Bukkit.getServer().getOnlinePlayers().toArray();
		for(int count=0;count<PlayerList.length;count++)
			if(((Player)PlayerList[count]).isOnline())
				sendTitle(((Player)PlayerList[count]), message, (byte)1, (byte)5, (byte)1);
	}
	    
	public void sendActionBarAllPlayers(String message)
	{
		Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		Player[] player = new Player[playerlist.size()];
		playerlist.toArray(player);
		for(int count=0;count<player.length;count++)
			sendActionBar(player[count], message);
	}
	  
	public void sendActionBar(Player p, String msg)
	{
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \""  +msg+  "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public void switchHotbarSlot(Player p, int slot)
	{
		PacketPlayOutHeldItemSlot ppoc = new PacketPlayOutHeldItemSlot(slot);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	}
	  
	public void changeItemSlot(Player p, int slot)
	{
		Packet slotChange = new PacketPlayOutHeldItemSlot(slot);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(slotChange);
	}

	public void openOwnInventory(Player p)
	{
		/*
			minecraft:chest
			minecraft:crafting_table
			minecraft:furnace
			minecraft:dispenser
			minecraft:enchanting_table
			minecraft:brewing_stand
			minecraft:villager
			minecraft:beacon
			minecraft:anvil
			minecraft:hopper
			minecraft:dropper
			EntityHorse
		 */
		Packet slotChange = new PacketPlayOutOpenWindow(1, "PLAYER",IChatBaseComponent.ChatSerializer.a("인벤토리"), 63, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(slotChange);
	}
	
	public void SpawnHallucinations(Player player, Player Hallucination)
	{
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(((CraftPlayer)Hallucination).getHandle());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(spawn);
	}
	
}
