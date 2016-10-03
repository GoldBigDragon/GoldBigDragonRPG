package GBD_RPG.Corpse;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _CorpseGUIManager
{
	//Corpse GUI Click Unique Number = 09
	//시체 관련 GUI의 고유 번호는 09입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 시체 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//부활 방법 선택 창
			new GBD_RPG.Corpse.Corpse_GUI().ReviveSelectClick(event);
	}
}
