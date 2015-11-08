package GBD.GoldBigDragon_Advanced.Command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Main.PartyDataObject;
import GBD.GoldBigDragon_Advanced.Util.ETC;

public class PartyCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
    {
			GBD.GoldBigDragon_Advanced.GUI.PartyGUI PGUI = new GBD.GoldBigDragon_Advanced.GUI.PartyGUI();
			Player player = (Player) talker;
		  	YM = Main_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
			if(args.length == 0)
			{
		 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				PGUI.PartyGUI_Main(player); return;
			}
			if(args.length <= 1)
			{
				switch(args[0])
				{
					case "���":
					 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
						PGUI.PartyListGUI(player, 0);
						return;
					case "Ż��":
						if(Main.PartyJoiner.containsKey(player))
							Main.Party.get(Main.PartyJoiner.get(player)).QuitParty(player);
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
						}
						return;
					case "����":
						if(Main.PartyJoiner.containsKey(player))
							Main.Party.get(Main.PartyJoiner.get(player)).getPartyInformation();
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
						}
						return;
					case "���":
						if(Main.PartyJoiner.containsKey(player))
							Main.Party.get(Main.PartyJoiner.get(player)).ChangeLock(player);
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
						}
  					return;
					default : HelpMessager(player, 3);
					return;
				}
			}
			else
			{
				switch(args[0])
				{
					case "����":
						if(Main.PartyJoiner.containsKey(player)==false)
						{
							ETC e = new ETC();
		  					long nowSec = e.getSec();
			  				if(args.length >= 3)
			  				{
			  					String s="";
			  					for(int a = 1; a<= ((args.length)-1);a++)
			  					{
			  						if(a == (args.length)-2)
			  							s = s+args[a]+" ";
			  						else
			  							s = s+args[a];
			  					}
			  					Main.Party.put(nowSec, new PartyDataObject(nowSec, player, s));
			  				}
			  				else
			  					Main.Party.put(nowSec, new PartyDataObject(nowSec, player, args[1]));
			  				s.SP(player, Sound.DOOR_OPEN, 1.0F, 1.1F);
		  					PGUI.PartyGUI_Main(player);
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� �̹� ��Ƽ�� ������ �����Դϴ�!");
						}
						return;
					case "����":
		  				if(args.length >= 3)
		  				{
		  					String s="";
		  					for(int a = 1; a<= ((args.length)-1);a++)
		  					{
		  						if(a == (args.length)-2)
		  							s = s+args[a]+" ";
		  						else
		  							s = s+args[a];
		  					}
							Main.Party.get(Main.PartyJoiner.get(player)).ChangeTitle(player, s);
		  				}
		  				else
							Main.Party.get(Main.PartyJoiner.get(player)).ChangeTitle(player, args[1]);
						return;
					case "����":
						if(Main.PartyJoiner.containsKey(player))
						{
			  				if(args.length >= 3)
			  				{
			  					HelpMessager(player, 3); return;
			  				}
							Main.Party.get(Main.PartyJoiner.get(player)).ChangeLeader(player, args[1]);
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
						}
						return;
					case "�ο�":
						if(Main.PartyJoiner.containsKey(player))
						{
			  				if(args.length >= 3)
			  				{
			  					HelpMessager(player, 3); return;
			  				}
							Main.Party.get(Main.PartyJoiner.get(player)).ChangeMaxCpacity(player, Integer.parseInt(args[1]));
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
						}
						return;
					case "����":
						if(Main.PartyJoiner.containsKey(player))
						{
			  				if(args.length >= 3)
			  				{
			  					HelpMessager(player, 3); return;
			  				}
							Main.Party.get(Main.PartyJoiner.get(player)).KickPartyMember(player, args[1]);
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
						}
						return;
					default : HelpMessager(player, 3);
					return;
				}
			}
    }
}