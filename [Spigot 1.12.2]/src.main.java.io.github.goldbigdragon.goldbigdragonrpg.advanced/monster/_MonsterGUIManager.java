package monster;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _MonsterGUIManager
{
	//Monster GUI Click Unique Number = 08
	//몬스터 관련 GUI의 고유 번호는 08입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 몬스터 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String subjectCode)
	{
		if(subjectCode.equals("00"))//몬스터 목록
			new monster.gui.MonsterListGui().monsterListClick(event);
		else if(subjectCode.equals("01"))//몬스터 설정
			new monster.gui.MonsterOptionSettingGui().monsterOptionSettingGUIClick(event);
		else if(subjectCode.equals("02"))//몬스터 포션 설정
			new monster.gui.MonsterPotionGui().monsterPotionClick(event);
		else if(subjectCode.equals("03"))//몬스터 장비 설정
			new monster.gui.MonsterEquipGui().monsterEquipClick(event);
		else if(subjectCode.equals("0b"))//몬스터 타입 선택
			new monster.gui.MonsterTypeGui().monsterTypeClick(event);
		
	}
	
	public void CloseRouting(InventoryCloseEvent event, String subjectCode)
	{
		if(subjectCode.equals("03"))//장비 설정창
			new monster.gui.MonsterEquipGui().monsterEquipClose(event);
	}
}