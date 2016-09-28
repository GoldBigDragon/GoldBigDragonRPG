package GBD_RPG.Warp;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp_Command
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		if(args.length >= 1)
		{
			GBD_RPG.Warp.Warp_Main TP = new GBD_RPG.Warp.Warp_Main();
			switch(args[0])
			{
			case "목록" :
				{
					new GBD_RPG.Effect.Effect_Sound().SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					new GBD_RPG.Warp.Warp_GUI().WarpListGUI(player, 0);
				}
				return;
			case "등록" : 
				{
					if(args.length >= 2)
						TP.CreateNewTeleportSpot(player, args[1]);
					else
						HelpMessage(player);
				}
				return;
			case "삭제" :
				{
					if(args.length >= 2)
						TP.RemoveTeleportList(player, args[1]);
					else
						HelpMessage(player);
				}
				return;
			case "제한" :
				{
					if(args.length >= 2)
						TP.setTeleportPermission(player, args[1]);
					else
						HelpMessage(player);
				}
				return;
			default :
				{
					TP.TeleportUser(player, args[0]);
				}
				return;
			}
		}
		else
		{
			HelpMessage(player);
		}
	}
	
	private void HelpMessage(Player player)
	{
		player.sendMessage(ChatColor.YELLOW+"────────────[텔레포트 명령어]────────────");
		player.sendMessage(ChatColor.GOLD + "/워프 목록" + ChatColor.YELLOW + " - 워프 가능 지역 목록을 봅니다.");
		player.sendMessage(ChatColor.GOLD + "/워프 <지역 이름>" + ChatColor.YELLOW + " - 해당 이름으로 등록된 지점으로 이동합니다.");
		player.sendMessage(ChatColor.GOLD + "/워프 등록 <지역 이름>" + ChatColor.YELLOW + " - 현재 위치를 워프 지점으로 등록합니다.");
		player.sendMessage(ChatColor.GOLD + "/워프 삭제 <지역 이름>" + ChatColor.YELLOW + " - 해당 워프 지점을 삭제합니다.");
		player.sendMessage(ChatColor.GOLD + "/워프 제한 <지역 이름>" + ChatColor.YELLOW + " - 일반 유저가 이동 가능/불가능 하도록 설정합니다.");
		player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
	}
}
