package area;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import util.YamlLoader;

public class AreaCommand
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    
		Player player = (Player) talker;
		if(!player.isOp())
		{
			talker.sendMessage("§c[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			SoundEffect.playSound((Player)talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length > 0)
		{
			if(args.length == 1)
			{
				if(args[0].equals("목록"))
				{
					area.AreaGui areaGui = new area.AreaGui();
					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
					areaGui.areaListGui(player, (short) 0);
					return;
				}
				else
				{
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					
					if(areaYaml.contains(args[0]))
					{
						SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
						area.AreaGui areaGui = new area.AreaGui();
						areaGui.areaSettingGui(player, args[0]);
					}
					else
					{
						SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						player.sendMessage("§c[SYSTEM] : 해당 이름의 영역이 없습니다!");
					}
					return;
				}
			}
			else if(args.length == 2)
			{
				for(int count = 0; count < 10; count++)
				{
					if(args[0].contains("."))
						args[0] = args[0].replace('.', '_');
					if(args[0].contains(":"))
						args[0] = args[0].replace(':', '_');
					if(args[0].contains("["))
						args[0] = args[0].replace('[', '_');
					if(args[0].contains("]"))
						args[0] = args[0].replace(']', '_');
					if(args[0].contains("\\"))
						args[0] = args[0].replace('\\', '_');
					if(args[0].contains("-"))
						args[0] = args[0].replace('-', '_');
				}
				area.AreaMain areaMain = new area.AreaMain();
				if(args[1].equals("생성"))
				{
					if(main.MainServerOption.catchedLocation1.containsKey(player)&&main.MainServerOption.catchedLocation2.containsKey(player))
					{
						areaMain.CreateNewArea(player, main.MainServerOption.catchedLocation1.get(player), main.MainServerOption.catchedLocation2.get(player), args[0]);
					}
					else
					{
						event.EventInteract interact = new event.EventInteract();
					  	YamlLoader configYaml = new YamlLoader();
					  	configYaml.getConfig("config.yml");
						player.sendMessage("§c[SYSTEM] : 먼저 " + interact.setItemDefaultName(configYaml.getInt("Server.AreaSettingWand"), 0) +"§c 아이템을 손에 든 채로 블록을 좌/우 클릭하여 구역을 설정해 주세요!");
						SoundEffect.playSound((Player)player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
				}
				else if(args[1].equals("삭제"))
					areaMain.RemoveArea(player, args[0]);
			}
			else if(args.length <= 2)
			{
				helpMessage(player);
				return;
			}
			else
			{
				area.AreaMain areaMain = new area.AreaMain();
				StringBuilder sb = new StringBuilder();
				for(int a =2; a<= ((args.length)-1);a++)
				{
					sb.append(args[a]);
					sb.append(" ");
				}
				if(args[1].equals("이름"))
					areaMain.OptionSetting(player, args[0],(char) 0, sb.toString());
				else if(args[1].equals("설명"))
					areaMain.OptionSetting(player, args[0],(char) 1, sb.toString());
			}
		}
	}
	
	private void helpMessage(Player player)
	{
		player.sendMessage("§e────────────[지역 설정 명령어]────────────");
		player.sendMessage("§6/영역 목록§e - 영역 목록을 확인합니다.");
		player.sendMessage("§6/영역 <영역 이름>§e - 해당 영역 설정창을 엽니다.");
		player.sendMessage("§6/영역 <영역 이름> 생성§e - 해당 이름을 가진 영역을 생성합니다.");
		player.sendMessage("§6/영역 <영역 이름> 삭제§e - 해당 이름을 가진 영역을 삭제합니다.");
		player.sendMessage("§6/영역 <영역 이름> 이름 <문자열>§e - 해당 구역의 알림 메시지에 보일 이름을 정합니다.");
		player.sendMessage("§6/영역 <영역 이름> 설명 <문자열>§e - 해당 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
		player.sendMessage("§e────────────────────────────────");
	}
}
