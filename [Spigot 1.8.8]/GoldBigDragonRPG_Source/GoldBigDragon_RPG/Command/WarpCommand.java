package GoldBigDragon_RPG.Command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		Player player = (Player) talker;
		if(args.length >= 1)
		{
			GoldBigDragon_RPG.ETC.Teleport TP = new GoldBigDragon_RPG.ETC.Teleport();
			switch(args[0])
			{
			case "목록" :
				{
					new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					new GoldBigDragon_RPG.GUI.WarpGUI().WarpListGUI(player, 0);
				}
				return;
			case "등록" : 
				{
					if(args.length >= 2)TP.CreateNewTeleportSpot(player, args[1]); else HelpMessager(player, (byte) 5);
				}
				return;
			case "삭제" :
				{
					if(args.length >= 2)
						TP.RemoveTeleportList(player, args[1]);
					else
						HelpMessager(player, (byte) 5);
				}
				return;
			case "제한" :
				{
					if(args.length >= 2)
						TP.setTeleportPermission(player, args[1]);
					else
						HelpMessager(player, (byte) 5);
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
			HelpMessager(player, (byte) 5);
		}
	}
}
