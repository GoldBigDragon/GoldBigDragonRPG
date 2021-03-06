package admin;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _AdminGUIManager
{
	//Admin GUI Click Unique Number = 01
	//어드민 관련 GUI의 고유 번호는 01입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 어드민 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void clickRouting(InventoryClickEvent event, String subjectCode)
	{
		if(subjectCode.equals("00"))//오피박스
			new admin.OPboxGui().opBoxGuiInventoryClick(event);
		else if(subjectCode.equals("01"))//관리자 옵션
			new admin.OPboxGui().opBoxGuiSettingInventoryClick(event);
		else if(subjectCode.equals("02"))//관리자 공지사항
			new admin.OPboxGui().opBoxGuiBroadCastClick(event);
		else if(subjectCode.equals("03"))//관리자 스텟 설정
			new admin.OPboxGui().opBoxGuiStatChangeClick(event);
		else if(subjectCode.equals("04"))//관리자 화폐 설정
			new admin.OPboxGui().opBoxGuiMoneyClick(event);
		else if(subjectCode.equals("05"))//관리자 사망 설정
			new admin.OPboxGui().opBoxGuiDeathClick(event);
		else if(subjectCode.equals("06") || subjectCode.equals("07"))//관리자 부활 설정
			new admin.OPboxGui().opBoxGuiRescueOrReviveClick(event);
		else if(subjectCode.equals("08"))//관리자 가이드
			new admin.OPboxGui().opBoxGuideInventoryClick(event);
		else if(subjectCode.equals("09"))//이벤트 메인
			new admin.EventGui().eventGuiInventoryclick(event);
		else if(subjectCode.equals("0a") || subjectCode.equals("0b"))//이벤트 아이템 지급
			new admin.EventGui().allPlayerGiveEventGuiClick(event, subjectCode);
		else if(subjectCode.equals("0c"))//도박 메인
			new admin.GambleGui().gambleMainGuiClick(event);
		else if(subjectCode.equals("0d"))//도박 상품 목록
			new admin.GambleGui().gamblePresentGuiClick(event);
		else if(subjectCode.equals("0e"))//도박 상품 정보
			new admin.GambleGui().gambleDetailViewPackageGuiClick(event);
		else if(subjectCode.equals("0f"))//도박 기계 목록
			new admin.GambleGui().slotMachineMainGUIClick(event);
		else if(subjectCode.equals("10"))//도박 기계 설정
			new admin.GambleGui().slotMachineDetailGuiClick(event);
		else if(subjectCode.equals("11"))//도박 기계 코인
			new admin.GambleGui().slotMachineCoinGuiClick(event);
		else if(subjectCode.equals("12"))//슬롯 머신
			new admin.GambleGui().slotMachinePlayGuiClick(event);
		else if(subjectCode.equals("13"))//슬롯 머신 (회전 화면)
			event.setCancelled(true);
		else if(subjectCode.equals("14"))//네비게이션 목록
			new admin.NavigationGui().navigationListGuiClick(event);
		else if(subjectCode.equals("15"))//네비게이션 설정
			new admin.NavigationGui().navigationOptionGuiClick(event);
		else if(subjectCode.equals("16"))//네비게이션 사용
			new admin.NavigationGui().useNavigationGuiClick(event);
		else if(subjectCode.equals("17"))//초심자 옵션 메인
			new admin.NewBieGui().newBieGuiMainInventoryClick(event);
		else if(subjectCode.equals("18") || subjectCode.equals("1a"))//초심자 지원 및 초심자 가이드
			new admin.NewBieGui().newbieSupportItemGuiInventoryClick(event, subjectCode);
		else if(subjectCode.equals("19"))//초심자 기본 퀘스트
			new admin.NewBieGui().newbieQuestGuiInventoryClick(event);
		else if(subjectCode.equals("1b"))//월드 생성 메인
			new admin.WorldCreateGui().worldCreateGuiClick(event);
		else if(subjectCode.equals("1c"))//개조식 목록
			new admin.UpgradeGui().upgradeRecipeGuiClick(event);
		else if(subjectCode.equals("1d"))//개조식 설정
			new admin.UpgradeGui().upgradeRecipeSettingGuiClick(event);
	}
	
	public void closeRouting(InventoryCloseEvent event, String subjectCode)
	{
		if(subjectCode.equals("06") || subjectCode.equals("07"))//부활 아이템 설정
			new admin.OPboxGui().opBoxGuiRescueOrReviveClose(event, subjectCode);
		else if(subjectCode.equals("0e"))//도박 상품 정보
			new admin.GambleGui().gambleDetailViewPackageGuiClose(event);
		else if(subjectCode.equals("11"))//도박 코인 설정
			new admin.GambleGui().slotMachineCoinGuiClose(event);
		else if(subjectCode.equals("18")||subjectCode.equals("1a"))//초심자 지원 및 초심자 가이드
			new admin.NewBieGui().newbieInventoryClose(event, subjectCode);
	}
}
