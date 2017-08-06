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
					case "목록":
						{
						 	SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
						 	new party.Party_GUI().PartyListGUI(player, (short) 0);
						}
						return;
					case "탈퇴":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).QuitParty(player);
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "정보":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).getPartyInformation();
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "잠금":
						{
							if(main.Main_ServerOption.PartyJoiner.containsKey(player))
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeLock(player);
							else
							{
								SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
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
					case "생성":
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
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeTitle(player, SB.toString());
			  				}
			  				else
								main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).ChangeTitle(player, args[1]);
						}
						return;
					case "리더":
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
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "인원":
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
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
							}
						}
						return;
					case "강퇴":
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
								player.sendMessage(ChatColor.RED + "[파티] : 당신은 파티에 참여하지 않은 상태입니다!");
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
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+Min+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+Max+ChatColor.RED+" 이하의 숫자를 입력하세요!");
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}

	private void HelpMessage(Player player)
	{
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		player.sendMessage(ChatColor.YELLOW+"────────────[파티 명령어]────────────");
		player.sendMessage(ChatColor.GOLD + "/파티 생성 <이름>" + ChatColor.YELLOW + " - 해당 이름의 파티를 생성합니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 탈퇴" + ChatColor.YELLOW + " - 현재 파티를 탈퇴합니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 목록" + ChatColor.YELLOW + " - 현재 개설된 파티 목록을 봅니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 정보" + ChatColor.YELLOW + " - 현재 파티 정보를 봅니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 제목 <파티제목>" + ChatColor.YELLOW + " - 현재 파티의 제목을 변경합니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 리더 <닉네임>" + ChatColor.YELLOW + " - 해당 플레이어에게 리더 권한을 넘겨줍니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 인원 <2-" + configYaml.getInt("Party.MaxPartyUnit") + ">" + ChatColor.YELLOW + " - 제한 인원을 설정합니다.");
		player.sendMessage(ChatColor.GOLD + "/파티 잠금"+ChatColor.YELLOW+" - 파티 참여 신청을 받거나 받지 않습니다.");
		player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
	}
}
