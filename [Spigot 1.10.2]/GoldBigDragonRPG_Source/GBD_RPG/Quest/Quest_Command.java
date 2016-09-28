package GBD_RPG.Quest;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Quest_Command
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) talker;
		if(args.length==0)
		{
			s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 0.8F);
			new GBD_RPG.Quest.Quest_GUI().MyQuestListGUI(player, (short) 0);
			return;
		}
		if(talker.isOp() == true)
		{
			if(player.isOp() == true)
			{
			    YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			    YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		    	if(YC.isExit("Quest/QuestList.yml")==false)
		    	{
		    		QuestConfig.set("Do_not_Touch_This", true);
		    		QuestConfig.saveConfig();
		    	}

			    
				switch(ChatColor.stripColor(args[0]))
				{
		  			case "구성" :
		  			{
		  					s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 0.8F);
		  					new GBD_RPG.Quest.Quest_GUI().AllOfQuestListGUI(player, (short) 0,false);
		  			}
		  			break;
			  		case "생성" :
				  		{
				  			if(args.length <= 2)
				  			{
								HelpMessage(player);
							  	return;
				  			}
				  			if(args[1].equalsIgnoreCase("일반")||args[1].equalsIgnoreCase("반복")||args[1].equalsIgnoreCase("일일")
				  			||args[1].equalsIgnoreCase("일주")||args[1].equalsIgnoreCase("한달"))
				  			{
							  	StringBuffer QN = new StringBuffer();
							    for(byte i =2; i<args.length-1;i++)
						    	{
							    	QN.append(args[i]+" ");
						    	}
							    QN.append(args[args.length-1]);
							    String QuestName = QN.toString().replace(".", "");
							    String QuestNameString = ChatColor.stripColor(QuestName);
								if(QuestConfig.contains(QuestNameString))
						    	{
								  	s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
							    	player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름의 퀘스트가 이미 존재합니다!");
								    return;
						    	}
								String QuestType = null;
								switch(args[1])
								{
									case "일반" : QuestType = "N"; break;
									case "반복" : QuestType = "R"; break;
									case "일일" : QuestType = "D"; break;
									case "일주" : QuestType = "W"; break;
									case "한달" : QuestType = "M"; break;
								}
								QuestConfig.set(QuestNameString + ".QuestMaker", player.getName());
								QuestConfig.set(QuestNameString + ".Type", QuestType);
								QuestConfig.set(QuestNameString + ".Server.Limit", 0);
								QuestConfig.set(QuestNameString + ".Need.LV", 0);
								QuestConfig.set(QuestNameString + ".Need.Love", 0);
								QuestConfig.createSection(QuestNameString + ".Need.Skill");
								QuestConfig.set(QuestNameString + ".Need.STR", 0);
								QuestConfig.set(QuestNameString + ".Need.DEX", 0);
								QuestConfig.set(QuestNameString + ".Need.INT", 0);
								QuestConfig.set(QuestNameString + ".Need.WILL", 0);
								QuestConfig.set(QuestNameString + ".Need.LUK", 0);
								QuestConfig.set(QuestNameString + ".Need.PrevQuest", "null");
								QuestConfig.createSection(QuestNameString + ".FlowChart");
								QuestConfig.saveConfig();
							    player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+QuestNameString+ChatColor.DARK_AQUA+" 퀘스트가 생성되었습니다!");
			  					s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 0.8F);
			  					new GBD_RPG.Quest.Quest_GUI().FixQuestGUI(player, (short) 0, QuestNameString);
				  			}
				  			else
							HelpMessage(player);
				  		}
				  		return;
				    default:
						{
							HelpMessage(player);
						}
				  		return;
				}
			}
			else
			{
				talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
				s.SP((Player)talker, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
	  			return;
			}
		}
	}

	private void HelpMessage(Player player)
	{
		player.sendMessage(ChatColor.YELLOW+"────────────[퀘스트 명령어]────────────");
		player.sendMessage(ChatColor.GOLD + "/퀘스트" + ChatColor.YELLOW + " - 현재 자신이 진행중인 퀘스트 목록을 열람합니다.");
		player.sendMessage(ChatColor.GOLD + "/퀘스트 구성" + ChatColor.YELLOW + " - 새로운 퀘스트를 만들거나, 기존의 퀘스트를 삭제합니다.");
		player.sendMessage(ChatColor.GOLD + "/퀘스트 생성 [타입] [이름]" + ChatColor.YELLOW + " - 새로운 퀘스트를 생성하며, 설정창으로 바로 넘어갑니다.");
		player.sendMessage(ChatColor.GREEN + "[일반 / 반복 / 일일 / 일주 / 한달]");
		player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
	}
}