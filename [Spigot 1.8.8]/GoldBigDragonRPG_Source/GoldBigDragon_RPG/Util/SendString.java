package GoldBigDragon_RPG.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class SendString
{
	public void SendForBukkit(byte number)
	{
		switch(number)
		{
			case 0://플러그인 실행시 나오는 로고
			{
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
			}
			return;
			case 1://경고 표시
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
			}
			return;
		}
	}
}
