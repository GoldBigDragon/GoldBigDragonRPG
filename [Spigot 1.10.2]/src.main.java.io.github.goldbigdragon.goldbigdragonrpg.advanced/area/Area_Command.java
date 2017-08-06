package area;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import util.YamlLoader;



public class Area_Command
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			SoundEffect.SP((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("목록"))
			{
				area.Area_GUI AGUI = new area.Area_GUI();
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				AGUI.AreaListGUI(player, (short) 0);
				return;
			}
			else
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				
				if(areaYaml.contains(args[0]))
				{
					SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
					area.Area_GUI AGUI = new area.Area_GUI();
					AGUI.AreaSettingGUI(player, args[0]);
				}
				else
				{
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 영역이 없습니다!");
				}
				return;
			}
		}
		if(args.length == 2)
		{
			area.Area_Main A = new area.Area_Main();
			switch(args[1])
			{
			case "생성" :
				if(main.Main_ServerOption.catchedLocation1.containsKey(player)==true && main.Main_ServerOption.catchedLocation2.containsKey(player) ==true)
				{
					A.CreateNewArea(player, main.Main_ServerOption.catchedLocation1.get(player), main.Main_ServerOption.catchedLocation2.get(player), args[0]);
					return;
				}
				else
				{
					event.Main_Interact IT = new event.Main_Interact();
				  	YamlLoader configYaml = new YamlLoader();
				  	configYaml.getConfig("config.yml");
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 먼저 " + IT.SetItemDefaultName((short) configYaml.getInt("Server.AreaSettingWand"),(byte)0) +ChatColor.RED+" 아이템을 손에 든 채로 블록을 좌/우 클릭하여 구역을 설정해 주세요!");
					SoundEffect.SP((Player)player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
				return;
			case "삭제" :
				A.RemoveArea(player, args[0]);
				return;
			}
		}
		if(args.length <= 2)
		{
			HelpMessage(player);
			return;
		}
		else
		{
			area.Area_Main A = new area.Area_Main();
			String SB = "";
			for(int a =2; a<= ((args.length)-1);a++)
				SB = SB+args[a]+" ";
			switch(args[1])
			{
			case "이름" :
				A.OptionSetting(player, args[0],(char) 0, SB);
				return;
			case "설명" :
				A.OptionSetting(player, args[0],(char) 1, SB);
				return;
			}
		}
	}
	
	private void HelpMessage(Player player)
	{
		player.sendMessage(ChatColor.YELLOW+"────────────[지역 설정 명령어]────────────");
		player.sendMessage(ChatColor.GOLD + "/영역 목록" + ChatColor.YELLOW + " - 영역 목록을 확인합니다.");
		player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름>" + ChatColor.YELLOW + " - 해당 영역 설정창을 엽니다.");
		player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 생성" + ChatColor.YELLOW + " - 해당 이름을 가진 영역을 생성합니다.");
		player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 삭제" + ChatColor.YELLOW + " - 해당 이름을 가진 영역을 삭제합니다.");
		player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 이름 <문자열>" + ChatColor.YELLOW + " - 해당 구역의 알림 메시지에 보일 이름을 정합니다.");
		player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 설명 <문자열>" + ChatColor.YELLOW + " - 해당 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
		player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
	}
}
