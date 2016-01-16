package GoldBigDragon_RPG.Effect;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
public class PacketSender
{
	  public void sendTitleSubTitle(Player player, String title, String subtitle, int FadeInTime, int ShowTime, int FadeOutTime)
	  {
	    CraftPlayer p = (CraftPlayer)player;
	    PlayerConnection c = p.getHandle().playerConnection;
	    IChatBaseComponent TitleText = ChatSerializer.a(title);
	    IChatBaseComponent SubtitleText = ChatSerializer.a(subtitle);
	    Packet Length = new PacketPlayOutTitle(EnumTitleAction.TIMES, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    Packet TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    Packet SubtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, SubtitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    c.sendPacket(TitlePacket);
	    c.sendPacket(Length);
	    c.sendPacket(SubtitlePacket);
	    c.sendPacket(SubtitlePacket);
	  }

	  public void sendTitle(Player player, String title, int FadeInTime, int ShowTime, int FadeOutTime)
	  {
	    CraftPlayer p = (CraftPlayer)player;
	    PlayerConnection c = p.getHandle().playerConnection;
	    IChatBaseComponent TitleText = ChatSerializer.a(title);
	    Packet Length = new PacketPlayOutTitle(EnumTitleAction.TIMES, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    Packet TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, TitleText, FadeInTime*20, ShowTime*20, FadeOutTime*20);
	    c.sendPacket(TitlePacket);
	    c.sendPacket(Length);
	  }

	    public void sendTitleAllPlayers(String message)
	    {
	    	OfflinePlayer[] offlineplayer = Bukkit.getServer().getOfflinePlayers();
	    	for(int count=0;count<offlineplayer.length;count++)
	    	{
	    		if(offlineplayer[count].isOnline()==true)
	    		sendTitle(Bukkit.getPlayer(offlineplayer[count].getName()), message, 1, 5, 1);
	    	}
	    }
	    
	    public void sendActionBarAllPlayers(String message)
	    {
	    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	    	Player[] player = new Player[playerlist.size()];
	    	playerlist.toArray(player);
	    	for(int count=0;count<player.length;count++)
	    		sendActionBar(player[count], message);
	    }
	    
	  public void sendAreaExplanation(Player player, int AreaNumber)
	  {
		  switch(AreaNumber)
		  {
	  		case 100:
	  			sendTitleSubTitle(player, ChatColor.BLUE+"HDD","{text:\"하드디스크의 중앙 섹터입니다.\"}", 2, 10, 2);
	  			sendActionBar(player,ChatColor.WHITE + ""+ChatColor.BOLD+ "각종 지역으로 워프할 수 있습니다.");
	  			break;
		  }
	  }
	  
	  public void sendActionBar(Player p, String msg)
	  {
	    IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
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
	  
}
