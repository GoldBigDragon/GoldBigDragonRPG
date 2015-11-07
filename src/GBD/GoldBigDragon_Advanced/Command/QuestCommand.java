package GBD.GoldBigDragon_Advanced.Command;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class QuestCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
		GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
		Player player = (Player) talker;
		if(talker.isOp() == true)
		{
			if(args.length==0)
			{
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 0.8F);
				QGUI.MyQuestListGUI(player, 0);
				return;
			}
			
			if(player.isOp() == true)
			{
			    YamlManager QuestConfig;
				YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
				QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");
		    	if(Config_YC.isExit("Quest/QuestList.yml")==false)
		    	{
		    		QuestConfig.set("Do_not_Touch_This", true);
		    		QuestConfig.saveConfig();
		    	}

			    
				switch(ChatColor.stripColor(args[0]))
				{
		  			case "구성" :
		  					s.SP(player, Sound.HORSE_ARMOR, 1.0F, 0.8F);
		  					QGUI.AllOfQuestListGUI(player, 0,false);
		  					break;
			  		case "생성" :
			  			if(args.length <= 2)
			  			{
							HelpMessager((Player)talker,8);
						  	return;
			  			}
			  			if(args[1].equalsIgnoreCase("일반")||args[1].equalsIgnoreCase("반복")||args[1].equalsIgnoreCase("일일")
			  			||args[1].equalsIgnoreCase("일주")||args[1].equalsIgnoreCase("한달"))
			  			{
							Set<String> a = QuestConfig.getConfigurationSection("").getKeys(false);
							Object[] questList =a.toArray();

						  	String QuestName = "";
						  	
						    for(int i =2; i<args.length-1;i++)
						    {
						    	QuestName = QuestName + args[i]+" ";
						    }
						    QuestName = QuestName+args[args.length-1];
						    QuestName = QuestName.replace(".","");
						    QuestName = ChatColor.stripColor(QuestName);
							for(int count = 0; count < questList.length;count++)
						    {
								if(questList[count].toString().equalsIgnoreCase(QuestName) == true)
						    	{
								  	s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
							    	player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름의 퀘스트가 이미 존재합니다!");
								    return;
						    	}
						    }
							String QuestType = "";
							switch(args[1])
							{
							case "일반" : QuestType = "N"; break;
							case "반복" : QuestType = "R"; break;
							case "일일" : QuestType = "D"; break;
							case "일주" : QuestType = "W"; break;
							case "한달" : QuestType = "M"; break;
							}
							QuestConfig.set(QuestName + ".QuestMaker", player.getName());
							QuestConfig.set(QuestName + ".Type", QuestType);
							QuestConfig.set(QuestName + ".Server.Limit", 0);
							QuestConfig.set(QuestName + ".Need.LV", 0);
							QuestConfig.set(QuestName + ".Need.Love", 0);
							QuestConfig.set(QuestName + ".Need.Skill.0", null);
							QuestConfig.set(QuestName + ".Need.STR", 0);
							QuestConfig.set(QuestName + ".Need.DEX", 0);
							QuestConfig.set(QuestName + ".Need.INT", 0);
							QuestConfig.set(QuestName + ".Need.WILL", 0);
							QuestConfig.set(QuestName + ".Need.LUK", 0);
							QuestConfig.set(QuestName + ".Need.PrevQuest", "null");
							QuestConfig.set(QuestName + ".FlowChart.0", null);
							QuestConfig.saveConfig();
						    player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+QuestName+ChatColor.DARK_AQUA+" 퀘스트가 생성되었습니다!");
		  					s.SP(player, Sound.HORSE_ARMOR, 1.0F, 0.8F);
						    QGUI.FixQuestGUI(player, 0, QuestName);
			  			}
			  			else
						HelpMessager((Player)talker,8);
		  				return;
				    default:
					HelpMessager((Player)talker,8);
					return;
					    	
				}
			}
			else
			{
				talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
				s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  			return;
			}
		  }
	}
}