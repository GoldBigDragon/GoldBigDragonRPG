package quest;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;



public class Quest_Chat extends Util_Chat
{
	
	public void QuestTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
	    Player player = event.getPlayer();
    	quest.Quest_GUI QGUI = new quest.Quest_GUI();
	  	YamlLoader questYaml = new YamlLoader();
		questYaml.getConfig("Quest/QuestList.yml");

    	event.setCancelled(true);
    	String message = ChatColor.stripColor(event.getMessage());
    	switch(u.getString(player, (byte)1))
    	{
	    	case "Cal"://Caluclate
	    	{
				if(isIntMinMax(message, player, 1, 5))
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			    	u.setString(player, (byte)1, "CVS");
			    	u.setInt(player, (byte)1, Integer.parseInt(message));
			    	String Example=null;
			    	switch(Integer.parseInt(message))
			    	{
			    	case 1:
			    		Example = "플레이어 변수 ＋ B";
			    		break;
			    	case 2:
			    		Example = "플레이어 변수 － B";
			    		break;
			    	case 3:
			    		Example = "플레이어 변수 × B";
			    		break;
			    	case 4:
			    		Example = "플레이어 변수 ÷ B";
			    		break;
			    	case 5:
			    		Example = "플레이어 변수 ％ B";
			    		break;
			    	}
					player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"B"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
			    	if(Integer.parseInt(message) <= 2)
						player.sendMessage(ChatColor.GRAY + "(최소 -1000 ~ 최대 20000)");
			    	else
						player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 100)");
					player.sendMessage(ChatColor.GRAY + "(계산 결과 -2000 이하거나 40000 이상일 경우 각각 -2000과 40000으로 고정)");
					player.sendMessage(ChatColor.GRAY + "(저장 타입이  Integer이므로, 결과 값이 너무 크거나 작으면 이상한 값이 나올수도 있음)");
				}
	    	}
	    	return;
	    	case "CVS"://Calculate Value Set
	    	{
	    		if(u.getInt(player, (byte)1)<=2)
	    		{
	    			if(isIntMinMax(message, player, -1000, 20000))
					{
		    			short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
						questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "Cal");
		        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
			    		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	    	questYaml.saveConfig();
		    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 계산 공식 설정이 완료되었습니다!");
		    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
		    	    	u.clearAll(player);
					}
	    		}
	    		else
	    		{
	    			if(isIntMinMax(message, player, 1, 100))
					{
		    			short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
						questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "Cal");
		        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
			    		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	    	questYaml.saveConfig();
		    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 계산 공식 설정이 완료되었습니다!");
		    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
		    	    	u.clearAll(player);
					}
	    		}
	    	}
	    	return;
    	case "IFMVS"://IF Max Value Set
    	{
			if(isIntMinMax(message, player, u.getInt(player, (byte)2), 40000))
			{
    			short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "IF");
        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
	    		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Min", u.getInt(player, (byte)2));
	    		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Max", Integer.parseInt(message));
    	    	questYaml.saveConfig();
    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : IF문 설정이 완료되었습니다!");
    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
    	    	u.clearAll(player);
			}
    	}
    	return;
    	case "IFVS"://IF Value Set
    	{
			if(isIntMinMax(message, player, -2000, 40000))
			{
    			short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
		    	if(u.getInt(player, (byte)1)!=7)
		    	{
	        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "IF");
	        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Comparison", u.getInt(player, (byte)1));
		    		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	}
		    	else
		    	{
		    		u.setInt(player, (byte)2, Integer.parseInt(message));
		    		questYaml.saveConfig();
			    	u.setString(player, (byte)1, "IFMVS");
					player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Integer.parseInt(message)+" <= 플레이어 변수 <= B"+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"C"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
					player.sendMessage(ChatColor.GRAY + "(최소 "+Integer.parseInt(message)+" ~ 최대 40000)");
		    		return;
		    	}
    	    	questYaml.saveConfig();
    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : IF문 설정이 완료되었습니다!");
    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
    	    	u.clearAll(player);
			}
    	}
    	return;
    	case "IFTS"://IF Type Select
    	{
			if(isIntMinMax(message, player, 1, 7))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
		    	u.setString(player, (byte)1, "IFVS");
		    	u.setInt(player, (byte)1, Integer.parseInt(message));
		    	String Example=null;
		    	switch(Integer.parseInt(message))
		    	{
		    	case 1:
		    		Example = "플레이어 변수 == B";
		    		break;
		    	case 2:
		    		Example = "플레이어 변수 != B";
		    		break;
		    	case 3:
		    		Example = "플레이어 변수 > B";
		    		break;
		    	case 4:
		    		Example = "플레이어 변수 < B";
		    		break;
		    	case 5:
		    		Example = "플레이어 변수 >= B";
		    		break;
		    	case 6:
		    		Example = "플레이어 변수 <= B";
		    		break;
		    	case 7:
		    		Example = "C <= 플레이어 변수 <= B";
					player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"C"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
					player.sendMessage(ChatColor.GRAY + "(최소 -2000 ~ 최대 40000)");
		    		return;
		    	}
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"B"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
				player.sendMessage(ChatColor.GRAY + "(최소 -2000 ~ 최대 40000)");
			}
    	}
    	return;
	    	case "CV"://ChangeVariable
	    	{
				if(isIntMinMax(message, player, -1000, 30000))
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
	    			short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
	        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "VarChange");
	        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
	    	    	questYaml.saveConfig();
	    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 변수 변경 설정이 완료되었습니다!");
	    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
	    	    	u.clearAll(player);
				}
	    	}
	    	return;
    	case "SCV"://SetChoiceVariable
    	{
			if(isIntMinMax(message, player, -1000, 30000))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setInt(player, (byte)(u.getInt(player, (byte)1)+2),Integer.parseInt(message));
				u.setInt(player, (byte)1,u.getInt(player, (byte)1)+1);
	    		if(u.getInt(player, (byte)0)==u.getInt(player, (byte)1))
	    		{
	    			short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
	        		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", "Choice");
	        		for(int count = 0; count <u.getInt(player, (byte)0);count++)
	        		{
	        			questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Choice."+count+".Lore", u.getString(player, (byte)(count+4)));
	        			questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Choice."+count+".Var", u.getInt(player, (byte)(count+2)));
	        		}
	    	    	questYaml.saveConfig();
	    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 선택지가 성공적으로 등록되었습니다!");
	    			QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
	    	    	u.clearAll(player);
	    		}
	    		else
	    		{
			    	u.setString(player, (byte)1, "SCL");
			    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+(u.getInt(player, (byte)1)+1)+"번째 선택에 적힐 말을 입력 하세요!");
					player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
					player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
					player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
					ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
							ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
					ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
							ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
	    		}
			}
			
    	}
    	return;
    	case "SCL"://SetChoiceLore
    	{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)(u.getInt(player, (byte)1)+4), event.getMessage());
	    	u.setString(player, (byte)1, "SCV");
			player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+(u.getInt(player, (byte)1)+1)+"번 선택지를 고를 경우, 플레이어 변수는 몇으로 변환시킬까요?");
			player.sendMessage(ChatColor.GRAY + "(최소 -1000 ~ 최대 30000)");
    	}
    	return;
    	case "CS"://ChoiceSize
			if(isIntMinMax(message, player, 1, 4))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setInt(player, (byte)0, Integer.parseInt(message));
				u.setInt(player, (byte)1, 0);
		    	u.setString(player, (byte)1, "SCL");
		    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : 1번째 선택에 적힐 말을 입력 하세요!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
	    	return;
    	case "Whisper":
    	case "BroadCast":
    	case "PScript":
	    	{
	    		short size = (short) questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false).size();
	    		questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Type", u.getString(player, (byte)1));
		    	questYaml.set(u.getString(player, (byte)2)+".FlowChart."+size+".Message", event.getMessage());
		    	questYaml.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[퀘스트] : 대사가 성공적으로 등록되었습니다!");
				QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
		    	u.clearAll(player);
	    	}
	    	return;
    	case "BPID"://BlockPlaceID
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				event.Main_Interact I = new event.Main_Interact();
				if(I.SetItemDefaultName(Short.parseShort(message),(byte)0).compareTo("지정되지 않은 아이템")==0)
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				String QuestName = u.getString(player, (byte)2);
				int size = u.getInt(player, (byte)1);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				questYaml.set(QuestName+".FlowChart."+size+".ID", Integer.parseInt(message));
				questYaml.saveConfig();
		    	u.setString(player, (byte)1, "BPDATA");
		    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : 설치 될 블록 DATA를 입력 해 주세요!");
			}
	    	return;
    	case "BPDATA"://BlockPlaceDATA
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				String QuestName = u.getString(player, (byte)2);
				int size = u.getInt(player, (byte)1);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				questYaml.set(QuestName+".FlowChart."+size+".DATA", Integer.parseInt(message));
				questYaml.saveConfig();
		    	u.setString(player, (byte)1, "BPDATA");
		    	u.clearAll(player);
		    	QGUI.FixQuestGUI(player, (short) 0, QuestName);
			}
	    	return;
    	case "Script":
	    	u.setString(player, (byte)3,ChatColor.WHITE + event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 해당 대사를 말할 NPC를 우클릭 하세요.");
	    	return;
    	case "Visit":
    		YamlLoader areaListYaml = new YamlLoader();
			areaListYaml.getConfig("Area/AreaList.yml");
			String[] arealist = areaListYaml.getConfigurationSection("").getKeys(false).toArray(new String[0]);

			if(arealist.length <= 0)
			{
				
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 생성된 영역이 없습니다!");
				u.clearAll(player);
				return;
			}
			for(int count =0; count <arealist.length;count++)
			{
				if(event.getMessage().compareTo(arealist[count])==0)
				{
					player.sendMessage(ChatColor.GREEN+"[퀘스트] : "+ChatColor.YELLOW + arealist[count] + ChatColor.GREEN+" 지역을 방문하도록 등록 되었습니다!");

					Set<String> b4 = questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false);
					
			    	questYaml.set(u.getString(player, (byte)2)+".FlowChart."+b4.size()+".Type", "Visit");
			    	questYaml.set(u.getString(player, (byte)2)+".FlowChart."+b4.size()+".AreaName", arealist[count]);
			    	questYaml.saveConfig();

					QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
					u.clearAll(player);
					return;
				}
				player.sendMessage(ChatColor.GREEN +"  "+ arealist[count]);
			}
			player.sendMessage(ChatColor.GREEN + "┌────────영역 목록────────┐");
			for(int count =0; count <arealist.length;count++)
				player.sendMessage(ChatColor.GREEN +"  "+ arealist[count]);
			player.sendMessage(ChatColor.GREEN + "└────────영역 목록────────┘");
			player.sendMessage(ChatColor.DARK_AQUA + "[퀘스트] : 방문해야 할 영역 이름을 적어 주세요!");
	    	return;
    	case "Hunt":
			if(isIntMinMax(event.getMessage(), player, 1, Integer.MAX_VALUE))
			{
				short Flownumber=0;
				short Monsternumber =0;
				Set<String> b = questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false);
				if(u.getInt(player, (byte)3) != -1)
					Flownumber = (short) (b.size()-1);
				else
					Flownumber = (short) b.size();
				if(questYaml.contains(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster")==false)
				{
					questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Type","Hunt");
					questYaml.createSection(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster");
					questYaml.saveConfig();
				}
				Set<String> c = questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster").getKeys(false);
				if(u.getInt(player, (byte)2) != -1)
					Monsternumber = (short) u.getInt(player, (byte)2);
				else
					Monsternumber = (short) c.size();
				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster."+Monsternumber+".MonsterName", u.getString(player, (byte)3));
				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Monster."+Monsternumber+".Amount", Integer.parseInt(event.getMessage()));
				questYaml.saveConfig();
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : " + ChatColor.YELLOW + QGUI.SkullType(u.getString(player, (byte)3)) + ChatColor.GREEN + " (을)를 " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" 마리 사냥하도록 설정되었습니다!");

				if(u.getInt(player, (byte)2) < 17)
					QGUI.KeepGoing(player, u.getString(player, (byte)2), (short) Flownumber, (short) Monsternumber,false);
				else
					QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
    	case "Harvest":
	    	if(u.getString(player, (byte)3)!=null)
	    	{
	    		if(ChatColor.stripColor(event.getMessage()).compareTo("x")==0 ||ChatColor.stripColor(event.getMessage()).compareTo("X")==0 ||
	    				ChatColor.stripColor(event.getMessage()).compareTo("o")==0 ||ChatColor.stripColor(event.getMessage()).compareTo("O")==0)
	    		{
		    		if(ChatColor.stripColor(event.getMessage()).compareTo("x")==0 ||ChatColor.stripColor(event.getMessage()).compareTo("X")==0)
		    			u.setBoolean(player, (byte)1, false);
		    		else
		    			u.setBoolean(player, (byte)1, true);
					u.setString(player, (byte)3,null);
			    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 블록을 얼마나 채집해야 할지 설정하세요! ("+ChatColor.YELLOW + "1"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"개)");
	    		}
	    		else
	    		{
    				player.sendMessage(ChatColor.RED + "[SYSTEM] : x혹은 o를 입력 해  주세요.");
      				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	    		}
  				return;
	    	}
	    	else
	    	{
				if(isIntMinMax(event.getMessage(), player, 1, Integer.MAX_VALUE))
				{
    				short Flownumber=0;
    				short BlockNumber =0;
    				Set<String> b = questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart").getKeys(false);
    				if(u.getInt(player, (byte)3) != -1)
    					Flownumber = (short) (b.size()-1);
    				else
    					Flownumber = (short) b.size();
    				if(questYaml.contains(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block")==false)
    				{
        				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Type","Harvest");
        				questYaml.createSection(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block");
        				questYaml.saveConfig();
    				}
    				Set<String> c = questYaml.getConfigurationSection(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block").getKeys(false);
    				if(u.getInt(player, (byte)4) != -1)
    					BlockNumber = (short) u.getInt(player, (byte)4);
    				else
    					BlockNumber = 0;
    				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".BlockID", u.getInt(player, (byte)1));
    				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".BlockData", u.getInt(player, (byte)2));
    				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".Amount", Integer.parseInt(event.getMessage()));
    				questYaml.set(u.getString(player, (byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".DataEquals", u.getBoolean(player, (byte)1));
    				questYaml.saveConfig();
    				
    				if(u.getBoolean(player, (byte)1) == false)
    					player.sendMessage(ChatColor.GREEN + "[퀘스트] : 아이템 ID가 " + ChatColor.YELLOW + u.getInt(player, (byte)1) +ChatColor.GREEN + " 인 모든 블록을 " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" 개 채집하도록 설정되었습니다!");
    				else
    					player.sendMessage(ChatColor.GREEN + "[퀘스트] : 아이템 코드 " + ChatColor.YELLOW + u.getInt(player, (byte)1) +  ":"+ u.getInt(player, (byte)2) + ChatColor.GREEN + " 인 블록을 " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" 개 채집하도록 설정되었습니다!");

    				if(u.getInt(player, (byte)2) < 17)
    					QGUI.KeepGoing(player, u.getString(player, (byte)2), (short) Flownumber, (short) BlockNumber,true);
    				else
    					QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte)2));
    				u.clearAll(player);
				}
    			return;
	    	}
    	}
    	
    	if(u.getString(player, (byte)2)!=null)
    	{
    		if(u.getString(player, (byte)1).contains("District") == true)
    		{
				if(isIntMinMax(event.getMessage(), player, 0, Integer.MAX_VALUE))
				{
    				String QuestName = u.getString(player, (byte)2);
    				int value = Integer.parseInt(event.getMessage());
    				YamlLoader questListYaml = new YamlLoader();
    				questListYaml.getConfig("Quest/QuestList.yml");
    				switch(u.getString(player, (byte)1))
    				{
    				case "Level District":
    					questListYaml.set(QuestName+".Need.LV", value);
    					break;
    				case "Love District":
    					questListYaml.set(QuestName+".Need.Love", value);
    					break;
    				case "STR District":
    					questListYaml.set(QuestName+".Need.STR", value);
    					break;
    				case "DEX District":
    					questListYaml.set(QuestName+".Need.DEX", value);
    					break;
    				case "INT District":
    					questListYaml.set(QuestName+".Need.INT", value);
    					break;
    				case "WILL District":
    					questListYaml.set(QuestName+".Need.WILL", value);
    					break;
    				case "LUK District":
    					questListYaml.set(QuestName+".Need.LUK", value);
    					break;
    				case "Accept District":
    					questListYaml.set(QuestName+".Server.Limit", value);
    					break;
    				}
    				questListYaml.saveConfig();
    				u.clearAll(player);
    				QGUI.QuestOptionGUI(player, QuestName);
				}
    			return;
    		}
    	}
    	
		if(u.getString(player, (byte)4)!=null)
		{
			if(isIntMinMax(event.getMessage(), player, 0, Integer.MAX_VALUE))
			{
    			switch(u.getString(player, (byte)4))
    			{
	    			case "M":
	    		    	event.setCancelled(true);
	    				u.setString(player, (byte)4,null);
	    				u.setInt(player, (byte)1, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.PresentItemSettingGUI(player, u.getString(player, (byte)3));
	    				break;
	    			case "E":
	    		    	event.setCancelled(true);
	    				u.setString(player, (byte)4,null);
	    				u.setInt(player, (byte)2, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.PresentItemSettingGUI(player, u.getString(player, (byte)3));
	    				break;
	    			case "L":
	    		    	event.setCancelled(true);
	    				u.setString(player, (byte)4,null);
	    				u.setInt(player, (byte)3, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.PresentItemSettingGUI(player, u.getString(player, (byte)3));
	    				break;
    				default :
    					break;
    			}
			}
			return;
    	}
		return;
	}
	
}
