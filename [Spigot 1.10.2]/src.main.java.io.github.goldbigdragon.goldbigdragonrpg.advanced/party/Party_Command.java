package party;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import util.ETC;
import util.YamlLoader;



public class Party_Command
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
    {
	    
			Player player = (Player) talker;
			if(args.length == 0)
			{
				SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				new party.Party_GUI().PartyGUI_Main(player); return;
			}
			if(args.length <= 1)
			{
				switch(args[0])
				{
					case "���":
						{
						 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
						 	new party.Party_GUI().PartyListGUI(player, (short) 0);
						}
						return;
					case "Ż��":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).QuitParty(player);
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "����":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).getPartyInformation();
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "���":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeLock(player);
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					default :
						{
							HelpMessage(player);
						}
						return;
				}
			}
			else
			{
				switch(args[0])
				{
					case "����":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player)==false)
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
				  					main.Main_ServerOption.Party.put(nowSec, new Party_Object(nowSec, player, SB.toString()));
				  				}
				  				else
				  					main.Main_ServerOption.Party.put(nowSec, new Party_Object(nowSec, player, args[1]));
				  				SoundEffect.SP(player, Sound.BLOCK_WOODEN_DOOR_OPEN, 1.0F, 1.1F);
				  				new party.Party_GUI().PartyGUI_Main(player);
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� �̹� ��Ƽ�� ������ �����Դϴ�!");
							}
						}
						return;
					case "����":
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
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeTitle(player, SB.toString());
			  				}
			  				else
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeTitle(player, args[1]);
						}
						return;
					case "����":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
							{
				  				if(args.length >= 3)
				  				{
				  					HelpMessage(player); return;
				  				}
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeLeader(player, args[1]);
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "�ο�":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
							{
				  				if(args.length >= 3)
				  					HelpMessage(player);
				  				else
			  					{
				  					YamlLoader configYaml = new YamlLoader();
				  					configYaml.getConfig("config.yml");
				  					if(isIntMinMax(args[1], player, 2, configYaml.getInt("Party.MaxPartyUnit")))
				  						main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeMaxCpacity(player, (byte) Integer.parseInt(args[1]));
			  					}
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					case "����":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
							{
				  				if(args.length >= 3)
				  				{
				  					HelpMessage(player); return;
				  				}
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).KickPartyMember(player, args[1]);
							}
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ�� �������� ���� �����Դϴ�!");
							}
						}
						return;
					default :
						{
							HelpMessage(player);
						}
						return;
				}
			}
    }
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : �ּ� "+ChatColor.YELLOW +""+Min+ChatColor.RED+", �ִ� "+ChatColor.YELLOW+""+Max+ChatColor.RED+" ������ ���ڸ� �Է��ϼ���!");
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : ���� ������ ��(����)�� �Է��ϼ���. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}

	private void HelpMessage(Player player)
	{
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		player.sendMessage(ChatColor.YELLOW+"������������������������[��Ƽ ���ɾ�]������������������������");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ ���� <�̸�>" + ChatColor.YELLOW + " - �ش� �̸��� ��Ƽ�� �����մϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ Ż��" + ChatColor.YELLOW + " - ���� ��Ƽ�� Ż���մϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ ���" + ChatColor.YELLOW + " - ���� ������ ��Ƽ ����� ���ϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ ����" + ChatColor.YELLOW + " - ���� ��Ƽ ������ ���ϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ ���� <��Ƽ����>" + ChatColor.YELLOW + " - ���� ��Ƽ�� ������ �����մϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ ���� <�г���>" + ChatColor.YELLOW + " - �ش� �÷��̾�� ���� ������ �Ѱ��ݴϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ �ο� <2-" + configYaml.getInt("Party.MaxPartyUnit") + ">" + ChatColor.YELLOW + " - ���� �ο��� �����մϴ�.");
		player.sendMessage(ChatColor.GOLD + "/��Ƽ ���"+ChatColor.YELLOW+" - ��Ƽ ���� ��û�� �ްų� ���� �ʽ��ϴ�.");
		player.sendMessage(ChatColor.YELLOW+"����������������������������������������������������������������");
	}
}