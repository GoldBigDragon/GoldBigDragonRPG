package GBD.GoldBigDragon_Advanced.Command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class AreaCommand extends HelpMessage
{
	public void onCommand(CommandSender talker, Command command, String string, String[] args)
	{
	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = (Player) talker;
		if(player.isOp() == false)
		{
			talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
			s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			return;
		}
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("목록"))
			{
				GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
				s.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				AGUI.AreaListGUI(player, 0);
				return;
			}
			else
			{
				YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
				YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");
				
				Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

				if(arealist.length <= 0)
				{
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 영역이 없습니다!");
					return;
				}
				for(int count =0; count <arealist.length;count++)
				{
					if(arealist[count].toString().equals(args[0]))
					{
						s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
						GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
						AGUI.AreaGUI_Main(player, args[0]);
						return;
					}
				}
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 영역이 없습니다!");
				return;
			}
		}
		if(args.length == 2)
		{
			GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
			switch(args[1])
			{
			case "생성" :
				if(GBD.GoldBigDragon_Advanced.Main.Main.catchedLocation1.containsKey(player)==true && GBD.GoldBigDragon_Advanced.Main.Main.catchedLocation2.containsKey(player) ==true)
				{
					A.CreateNewArea(player, GBD.GoldBigDragon_Advanced.Main.Main.catchedLocation1.get(player), GBD.GoldBigDragon_Advanced.Main.Main.catchedLocation2.get(player), args[0]);
					return;
				}
				else
				{
					GBD.GoldBigDragon_Advanced.Event.Interact IT = new GBD.GoldBigDragon_Advanced.Event.Interact();
					YamlController Main_YC = GBD.GoldBigDragon_Advanced.Main.Main.Main_YC;
				  	YamlManager Config = Main_YC.getNewConfig("config.yml");
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 먼저 " + IT.SetItemDefaultName(Config.getInt("Server.AreaSettingWand"),(byte)0) +ChatColor.RED+" 아이템을 손에 든 채로 블록을 좌/우 클릭하여 구역을 설정해 주세요!");
					s.SP((Player)player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				}
				return;
			case "삭제" :
				A.RemoveArea(player, args[0]);
				return;
			}
		}
		if(args.length <= 2)
		{
			HelpMessager(player, 6);
			return;
		}
		else
		{
			GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
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
}
