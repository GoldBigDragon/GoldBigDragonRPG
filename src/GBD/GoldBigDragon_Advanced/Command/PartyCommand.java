package GBD.GoldBigDragon_Advanced.Command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Main.PartyDataObject;
import GBD.GoldBigDragon_Advanced.Util.ETC;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class PartyCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
    {
	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
			Player player = (Player) talker;
			if(args.length == 0)
			{
				s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				new GBD.GoldBigDragon_Advanced.GUI.PartyGUI().PartyGUI_Main(player); return;
			}
			if(args.length <= 1)
			{
				switch(args[0])
				{
					case "목록":
						{
						 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
						 	new GBD.GoldBigDragon_Advanced.GUI.PartyGUI().PartyListGUI(player, 0);
						}
						return;
					case "탈퇴":
						{
							if(Main.PartyJoiner.containsKey(player))
								Main.Party.get(Main.PartyJoiner.get(player)).QuitParty(player);
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "정보":
						{
							if(Main.PartyJoiner.containsKey(player))
								Main.Party.get(Main.PartyJoiner.get(player)).getPartyInformation();
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "잠금":
						{
							if(Main.PartyJoiner.containsKey(player))
								Main.Party.get(Main.PartyJoiner.get(player)).ChangeLock(player);
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					default :
						{
							HelpMessager(player, 3);
						}
						return;
				}
			}
			else
			{
				switch(args[0])
				{
					case "생성":
						{
							if(Main.PartyJoiner.containsKey(player)==false)
							{
								ETC e = new ETC();
			  					long nowSec = e.getSec();
				  				if(args.length >= 3)
				  				{
				  					String SB=null;
				  					for(int a = 1; a<= ((args.length)-1);a++)
				  					{
				  						if(a == (args.length)-2)
				  							SB=SB+args[a]+" ";
				  						else
				  							SB=SB+args[a];
				  					}
				  					Main.Party.put(nowSec, new PartyDataObject(nowSec, player, SB.toString()));
				  				}
				  				else
				  					Main.Party.put(nowSec, new PartyDataObject(nowSec, player, args[1]));
				  				s.SP(player, Sound.DOOR_OPEN, 1.0F, 1.1F);
				  				new GBD.GoldBigDragon_Advanced.GUI.PartyGUI().PartyGUI_Main(player);
							}
							else
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 이미 파티에 참여한 상태입니다!");
							}
						}
						return;
					case "제목":
						{
			  				if(args.length >= 3)
			  				{
			  					String SB=null;
			  					for(int a = 1; a<= ((args.length)-1);a++)
			  					{
			  						if(a == (args.length)-2)
			  							SB=SB+args[a]+" ";
			  						else
			  							SB=SB+args[a];
			  					}
								Main.Party.get(Main.PartyJoiner.get(player)).ChangeTitle(player, SB.toString());
			  				}
			  				else
								Main.Party.get(Main.PartyJoiner.get(player)).ChangeTitle(player, args[1]);
						}
						return;
					case "리더":
						{
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
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "인원":
						{
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
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "강퇴":
						{
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
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					default :
						{
							HelpMessager(player, 3);
						}
						return;
				}
			}
    }
}
