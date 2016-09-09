package OtherPlugins;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.ManaChangeEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.events.SpellTargetLocationEvent;
import com.nisovin.magicspells.mana.ManaChangeReason;

import GoldBigDragon_RPG.Attack.Damage;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class SpellMain implements Listener
{
	public SpellMain(JavaPlugin plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	public SpellMain()
	{}

	@EventHandler
	public void MagicSpellsTargeted (SpellTargetEvent event)
	{
		event.setPower(event.getPower()+BonusPowerCalculator(event.getCaster(),event.getTarget()));
		//Bukkit.broadcastMessage(event.getCaster().getName()+"가"+event.getPower()+"강도로 때려서 "+event.getTarget().getName()+"이 맞음!");
	}
	@EventHandler
	public void MagicSpellsTargeted (SpellTargetLocationEvent event)
	{
		event.setPower(event.getPower()+BonusPowerCalculator(event.getCaster(),null));
		//Bukkit.broadcastMessage(event.getCaster().getName()+"가"+event.getPower()+"강도로 광역 공격을 가함!");
	}
	
	public float BonusPowerCalculator (Player player, LivingEntity target)
	{
	    Damage damage = new Damage();
		Damage dam = new Damage();
		int bonuspower = 0;
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.containsKey(player) == true)
		{
			Damageable p = player;
			String switchCheck = GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.get(player);
			if(switchCheck.compareTo("생명력")==0)
				bonuspower = dam.MagicSpellsDamageBonus((int) p.getHealth())+damage.getPlayerEquipmentStat(player, "HP", null, false)[0];
			else if(switchCheck.compareTo("마나")==0)
				bonuspower = dam.MagicSpellsDamageBonus(getPlayerMana(player))+damage.getPlayerEquipmentStat(player, "MP", null, false)[0];
			else if(switchCheck.compareTo(ServerOption.STR)==0)
				bonuspower = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR()+damage.getPlayerEquipmentStat(player, "STR", null, false)[0];
			else if(switchCheck.compareTo(ServerOption.DEX)==0)
				bonuspower = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX()+damage.getPlayerEquipmentStat(player, "DEX", null, false)[0];
			else if(switchCheck.compareTo(ServerOption.INT)==0)
				bonuspower = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT()+damage.getPlayerEquipmentStat(player, "INT", null, false)[0];
			else if(switchCheck.compareTo(ServerOption.WILL)==0)
				bonuspower = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL()+damage.getPlayerEquipmentStat(player, "WILL", null, false)[0];
			else if(switchCheck.compareTo(ServerOption.LUK)==0)
				bonuspower = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()+damage.getPlayerEquipmentStat(player, "LUK", null, false)[0];
			else
				bonuspower = 0;
		}
		int[] WeaponPower = dam.getPlayerEquipmentStat(player, "MagicDamage", GoldBigDragon_RPG.Main.ServerOption.PlayerlastItem.get(player), false);
		int WeaponPowerFixed = new GoldBigDragon_RPG.Util.Number().RandomNum(WeaponPower[0], WeaponPower[1]);
		bonuspower = bonuspower+WeaponPowerFixed;
		int negativeBonus = 0;
		if(target!=null)
		{
			if(target.getType()==EntityType.PLAYER)
			{
				Player t = (Player) target;
				if(t.isOnline())
				  	negativeBonus = dam.getMagicProtect(t, GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT());
				else
				{
					if(t.isCustomNameVisible() == true)
					{
						String name = new GoldBigDragon_RPG.Monster.MonsterKill().getRealName(target);
						if(GoldBigDragon_RPG.Main.ServerOption.MonsterList.containsKey(name))
							negativeBonus = GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(name).getMPRO();
					}
				}
			}
			else
			{
				if(target.isCustomNameVisible() == true)
				{
					String name = new GoldBigDragon_RPG.Monster.MonsterKill().getRealName(target);
					if(GoldBigDragon_RPG.Main.ServerOption.MonsterList.containsKey(name))
						negativeBonus = GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(name).getMPRO();
				}
			}
		}
		GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.put(player, "us");
		return (float) (bonuspower*0.006)-negativeBonus;
	}
	
	
	
	public void getSpellsList (Player player)
	{
		Collection<Spell> SC = MagicSpells.spells();
		Object[] SO = SC.toArray();
		for(short count = 0; count < SO.length; count++)
			player.sendMessage(SO[count].toString());
	}

	public void ShowAllMaigcGUI(Player player, short page,String SkillName, short SkillLevel,byte sort)
	{
		Object[] spells = new Object[46];
		int temp = 0;
		byte counter=0;
		switch(sort)
		{
		case 0:
			for(counter = 0;counter<46;counter++)
				if(counter+(page*45) < MagicSpells.spells().toArray().length)
					spells[counter] = MagicSpells.spells().toArray()[counter+(page*45)];
				else
					break;
			break;
		case 1:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&& temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().equals("DummySpell"))
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 2:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().equals("AreaEffectSpell"))
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 3:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().equals("MultiSpell"))
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 4:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().equals("PainSpell"))
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		case 5:
			for(counter = 0;counter<46;)
				if(counter+(page*45) < MagicSpells.spells().toArray().length&&
					temp+(page*45) < MagicSpells.spells().toArray().length)
				{
					if(!MagicSpells.spells().toArray()[temp+(page*45)].getClass().getSimpleName().equals("ParticleProjectileSpell"))
					{
						spells[counter] = MagicSpells.spells().toArray()[temp+(page*45)];
						counter++;
					}
					temp++;
				}
				else
					break;
			break;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "매직스펠 목록 : " + (page+1));

		byte loc=0;
		Spell spell;
		for(short count = 0; count < spells.length;count++)
		{
			spell = (Spell) spells[count];
			if(spell!=null)
			{
				String SpellName = spell.getName();
				short ID = 403;
				if(spell.getClass().getSimpleName().compareTo("PainSpell")==0)
					ID= 370;
				else if(spell.getClass().getSimpleName().compareTo("DummySpell")==0)
					ID= 416;
				else if(spell.getClass().getSimpleName().compareTo("AreaEffectSpell")==0)
					ID= 384;
				else if(spell.getClass().getSimpleName().compareTo("MultiSpell")==0)
					ID= 345;
				else if(spell.getClass().getSimpleName().compareTo("ParticleProjectileSpell")==0)
					ID= 401;
				else if(spell.getClass().getSimpleName().compareTo("ArmorSpell")==0)
					ID= 315;
				else if(spell.getClass().getSimpleName().compareTo("EmpowerSpell")==0)
					ID= 373;
				else if(spell.getClass().getSimpleName().compareTo("ForcetossSpell")==0)
					ID= 33;
				else if(spell.getClass().getSimpleName().compareTo("ExplodeSpell")==0)
					ID= 46;
				else if(spell.getClass().getSimpleName().compareTo("ThrowBlockSpell")==0)
					ID= 145;
				else if(spell.getClass().getSimpleName().compareTo("VolleySpell")==0)
					ID= 262;
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SpellName, ID, (byte)0,1,Arrays.asList(ChatColor.WHITE+spell.getClass().getSimpleName()+"",ChatColor.YELLOW+"[좌 클릭시 스킬 등록]"), (byte)count, inv);
			}
		}
		
		if(counter>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,(byte)0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), (byte)50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,(byte)0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), (byte)48, inv);
		switch(sort)
		{
		case 0:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[모두 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[더미 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 2:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[영역 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 3:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[멀티 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 4:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[pain 스펠 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		case 5:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클래스별 정렬", 154,(byte)0,1,Arrays.asList(ChatColor.GRAY + "[P.P 제외 보기]",ChatColor.BLACK+""+sort), (byte)49, inv);break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,(byte)0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+SkillLevel), (byte)45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,(byte)0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+SkillName), (byte)53, inv);
		player.openInventory(inv);
	}
	
	public void Stack2(String Display, int ID, byte DATA, int Stack, List<String> Lore, byte Loc, Inventory inventory)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
	}

	public void ShowAllMaigcGUIClick(InventoryClickEvent event)
	{
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		int SkillLevel = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		int sort = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(1)));
		
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		event.setCancelled(true);
		switch (event.getSlot())
		{
			case 45://이전 목록으로
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				new GoldBigDragon_RPG.Skill.OPBoxSkillGUI().SkillRankOptionGUI(player, SkillName, (short) SkillLevel);
				break;
			case 48://이전 페이지
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				ShowAllMaigcGUI(player, (byte)(page-1), SkillName, (short)SkillLevel, (byte)sort);
				break;
			case 49://정렬 방식 변경
				s.SP(player, Sound.PISTON_RETRACT, 1.0F, 0.8F);
				if(sort <= 4)
					sort = sort +1;
				else
					sort = 0;
				ShowAllMaigcGUI(player, (byte)0, SkillName, (short) SkillLevel, (byte)sort);
				break;
			case 50://다음 페이지
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				ShowAllMaigcGUI(player, (byte)(page+1), SkillName, (short) SkillLevel, (byte)sort);
				break;
			case 53://나가기
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.8F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".MagicSpells", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				SkillList.saveConfig();
				new GoldBigDragon_RPG.Skill.OPBoxSkillGUI().SkillRankOptionGUI(player, SkillName, (short) SkillLevel);
				return;
		}
	}
	
	
	public void CastSpell(Player player, String SpellName)
	{
		Object[] spells = MagicSpells.spells().toArray();
		Spell spell = null;
		for(short count = 0; count < spells.length;count++)
		{
			spell = (Spell) spells[count];
			if(spell.getName().equals(SpellName) == true)
				spell.cast(player);
		}
	}
	
	@EventHandler
	public void manaregen(ManaChangeEvent event)
	{//ManaChangeEvent
		if(event.getReason() == ManaChangeReason.REGEN)
		{
			Player player = event.getPlayer();
			setPlayerMaxAndNowMana(player);
		}
	}
	
	
	
	public int getPlayerMana(Player player)
	{
		return MagicSpells.getManaHandler().getMana(player);
	}

	public void setPlayerMaxAndNowMana(Player player)
	{
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		
		int BonusMana = d.getPlayerEquipmentStat(player, "마나", null, false)[0];
		int MaxMana = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxMP()+BonusMana;
		int Mana = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MP()+BonusMana;
		if(MaxMana > 0 && ServerOption.MagicSpellsEnable)
		{
			try
			{
				MagicSpells.getManaHandler().setMaxMana(player, MaxMana);
			}
			catch(NullPointerException e)
			{				
			}
		}
		return;
	}

	public void setSlotChangePlayerMaxAndNowMana(Player player, ItemStack newSlot, boolean isHotbarChange)
	{
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		
		int BonusMana = d.getPlayerEquipmentStat(player, "마나",newSlot, isHotbarChange)[0];
		int MaxMana = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxMP()+BonusMana;

		if(MaxMana > 0 && GoldBigDragon_RPG.Main.ServerOption.MagicSpellsEnable == true)
		{
			try
			{
				MagicSpells.getManaHandler().setMaxMana(player, MaxMana);
			}
			catch(NoClassDefFoundError e)
			{
				System.out.println("매직 스펠의 com/nisovin/magicspells/MagicSpells 클래스를 찾을 수 없습니다!");
			}
			catch(NullPointerException e)
			{
			}
		}
		return;
	}
	
	public void DrinkManaPotion(Player player, int mana)
	{
		int m = MagicSpells.getManaHandler().getMana(player)+mana;
		if(MagicSpells.getManaHandler().getMana(player)+mana >= MagicSpells.getManaHandler().getMaxMana(player))
			m = MagicSpells.getManaHandler().getMaxMana(player);
		MagicSpells.getManaHandler().setMana(player, m, ManaChangeReason.POTION);
	}
}