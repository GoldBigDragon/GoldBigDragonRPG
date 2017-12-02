package user;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _UserGUIManager
{
	//User GUI Click Unique Number = 00
	//유저 관련 GUI의 고유 번호는 00입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 유저 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("00"))//스텟
			new user.StatsGui().StatusInventoryclick(event);
		if(SubjectCode.equals("01"))//옵션
			new user.OptionGui().optionInventoryclick(event);
		if(SubjectCode.equals("02"))//기타
			new user.EtcGui().ETCInventoryclick(event);
		if(SubjectCode.equals("03"))//가이드
			new user.EtcGui().GuideInventoryclick(event);
		if(SubjectCode.equals("04"))//친구
			new user.EtcGui().FriendsGUIclick(event);
		if(SubjectCode.equals("05"))//친구 신청 목록
			new user.EtcGui().WaittingFriendsGUIclick(event);
		if(SubjectCode.equals("06"))//장비 구경
			new user.EquipGui().EquipSeeInventoryclick(event);
		if(SubjectCode.equals("07"))//교환
		{
			new user.EquipGui().ExchangeInventoryclick(event);
			new user.EquipGui().ExchangeGUIclick(event);
		}
	}
	
	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("07"))//교환
			new user.EquipGui().ExchangeGUI_Close(event);
	}
}
