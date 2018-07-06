package customitem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import customitem.gui.ApplyToolGui;
import effect.SoundEffect;
import main.MainServerOption;
import servertick.ServerTickMain;
import util.YamlLoader;

public class CustomItemAPI {
	
	public String[] loreCreater(int itemNumber)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");
		String lore = "";
		String type = ChatColor.stripColor(itemYaml.getString(itemNumber+".ShowType"));
		if(type.equals("[분류]"))
		{
			lore = lore+itemYaml.getString(itemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumber+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%§6장비 가능 직업 : §f" + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%";
				

			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore = lore+"§c ▩ "+main.MainServerOption.damage+" : §f" + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore = lore+"§3 ▦ "+main.MainServerOption.magicDamage+" : §f" + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore = lore+"§7 ▧ 방어 : §f" + itemYaml.getInt(itemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore = lore+"§f ■ 보호 : §f" +itemYaml.getInt(itemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore = lore+"§9 ◇ 마법 방어 : §f" +itemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore = lore+"§1 ◆ 마법 보호 : §f" + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore = lore+"§2 △ 밸런스 : §f" + itemYaml.getInt(itemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore = lore+"§e ▲ 크리티컬 : §f" + itemYaml.getInt(itemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore = lore+"§6 ▣ 내구도 : §f" + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore + "§f ♣ 숙련도 : 0.0%§f%enter%";
			
			lore = lore+"§6___--------------------___%enter%§6       [아이템 설명]";
			lore = lore+"%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%";
			lore = lore+"§6---____________________---%enter%";


			if(itemYaml.getInt(itemNumber+".HP")!=0||itemYaml.getInt(itemNumber+".MP")!=0||
					itemYaml.getInt(itemNumber+".STR")!=0||itemYaml.getInt(itemNumber+".DEX")!=0||
					itemYaml.getInt(itemNumber+".INT")!=0||itemYaml.getInt(itemNumber+".WILL")!=0||
					itemYaml.getInt(itemNumber+".LUK")!=0)
			{
				lore = lore+"§3___--------------------___%enter%§3       [보너스 옵션]%enter%";
				if(itemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 ▲ 생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c ▼ 생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 ▲ 마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c ▼ 마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(itemYaml.getInt(itemNumber+".STR") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".STR") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
				if(itemYaml.getInt(itemNumber+".DEX") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".DEX") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
				if(itemYaml.getInt(itemNumber+".INT") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".INT") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
				if(itemYaml.getInt(itemNumber+".WILL") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".WILL") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
				if(itemYaml.getInt(itemNumber+".LUK") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
				else if(itemYaml.getInt(itemNumber+".LUK") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
				lore = lore+"§3---____________________---%enter%";
			}

			if(itemYaml.getInt(itemNumber+".MaxSocket")!=0||itemYaml.getInt(itemNumber+".MaxUpgrade")!=0)
			{
				lore = lore+"§d___--------------------___%enter%§d       [아이템 강화]%enter%";
				if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0 && itemYaml.getInt(itemNumber+".MaxSocket") > 0)
				{
					lore = lore+"§5 ★ 개조 : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					lore = lore+"§9 ⓛ 룬 : ";
					for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
						lore = lore+"§7○ ";
				}
				else if(itemYaml.getInt(itemNumber+".MaxUpgrade") <= 0 && itemYaml.getInt(itemNumber+".MaxSocket") > 0)
				{
					lore = lore+"§9 ⓛ 룬 : ";
					for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
						lore = lore+"§7○ ";
				}
				else
					lore = lore+"§5 ★ 개조 : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade");
				lore = lore+"%enter%§d---____________________---%enter%";
			}
			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
			{
				lore = lore+"§4___--------------------___%enter%§4       [제한 스텟]%enter%";
				if(itemYaml.getInt(itemNumber+".MinLV") > 0)
					lore = lore+"§4 Ω 최소 레벨 : " + itemYaml.getInt(itemNumber+".MinLV")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
					lore = lore+"§4 Ω 최소 누적레벨 : " + itemYaml.getInt(itemNumber+".MinRLV")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MinINT") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL")+"%enter%";
				if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK")+"%enter%";
				lore = lore+"§4---____________________---%enter%";
				
			}
		}
		else if(type.equals("[기호]"))
		{
			lore = lore+itemYaml.getString(itemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumber+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%§6장비 가능 직업 : §f" + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore = lore+"§c ▩ "+main.MainServerOption.damage+" : §f" + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore = lore+"§3 ▦ "+main.MainServerOption.magicDamage+" : §f" + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore = lore+"§7 ▧ 방어 : §f" + itemYaml.getInt(itemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore = lore+"§f ■ 보호 : §f" +itemYaml.getInt(itemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore = lore+"§9 ◇ 마법 방어 : §f" +itemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore = lore+"§1 ◆ 마법 보호 : §f" + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore = lore+"§2 △ 밸런스 : §f" + itemYaml.getInt(itemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore = lore+"§e ▲ 크리티컬 : §f" + itemYaml.getInt(itemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore = lore+"§6 ▣ 내구도 : §f" + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";

			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore + "§f ♣ 숙련도 : 0.0%§f%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%";


			if(itemYaml.getInt(itemNumber+".HP") > 0)
				lore = lore+"§3 ▲ 생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".HP") < 0)
				lore = lore+"§c ▼ 생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MP") > 0)
				lore = lore+"§3 ▲ 마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".MP") < 0)
				lore = lore+"§c ▼ 마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
			if(itemYaml.getInt(itemNumber+".STR") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".STR") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEX") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".DEX") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
			if(itemYaml.getInt(itemNumber+".INT") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".INT") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
			if(itemYaml.getInt(itemNumber+".WILL") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".WILL") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
			if(itemYaml.getInt(itemNumber+".LUK") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".LUK") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
			
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore+"%enter%§5 ★ 개조 : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxSocket") > 0)
			{
				lore = lore+"%enter%§9 ⓛ 룬 : ";
				for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
					lore = lore+"§7○ ";
			}

			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
				lore = lore+"%enter%";
			
			if(itemYaml.getInt(itemNumber+".MinLV") > 0)
				lore = lore+"§4%enter% Ω 최소 레벨 : " + itemYaml.getInt(itemNumber+".MinLV");
			if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
				lore = lore+"§4%enter% Ω 최소 누적레벨 : " + itemYaml.getInt(itemNumber+".MinRLV");
			if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR");
			if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX");
			if(itemYaml.getInt(itemNumber+".MinINT") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT");
			if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL");
			if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK");
		}
		else if(type.equals("[컬러]"))
		{

			lore = lore+itemYaml.getString(itemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumber+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%§6장비 가능 직업 : §f" + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore = lore+ChatColor.RED + main.MainServerOption.damage+" : §f" + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore = lore+"§3"+main.MainServerOption.magicDamage+" : §f" + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore = lore+"§7방어 : §f" + itemYaml.getInt(itemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore = lore+"§f보호 : §f" +itemYaml.getInt(itemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore = lore+"§9마법 방어 : §f" +itemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore = lore+"§1마법 보호 : §f" + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore = lore+"§2밸런스 : §f" + itemYaml.getInt(itemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore = lore+"§e크리티컬 : §f" + itemYaml.getInt(itemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore = lore+"§6내구도 : §f" + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore + "§f숙련도 : 0.0%§f%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%";


			if(itemYaml.getInt(itemNumber+".HP") > 0)
				lore = lore+"§3생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".HP") < 0)
				lore = lore+"§c생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MP") > 0)
				lore = lore+"§3마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".MP") < 0)
				lore = lore+"§c마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
			if(itemYaml.getInt(itemNumber+".STR") > 0)
				lore = lore+"§3"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".STR") < 0)
				lore = lore+"§c"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEX") > 0)
				lore = lore+"§3"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".DEX") < 0)
				lore = lore+"§c"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
			if(itemYaml.getInt(itemNumber+".INT") > 0)
				lore = lore+"§3"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".INT") < 0)
				lore = lore+"§c"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
			if(itemYaml.getInt(itemNumber+".WILL") > 0)
				lore = lore+"§3"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".WILL") < 0)
				lore = lore+"§c"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
			if(itemYaml.getInt(itemNumber+".LUK") > 0)
				lore = lore+"§3"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".LUK") < 0)
				lore = lore+"§c"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
			
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore+"%enter%§5개조 : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxSocket") > 0)
			{
				lore = lore+"%enter%§9룬 : ";
				for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
					lore = lore+"§7○ ";
			}
			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
				lore = lore+"%enter%";
			if(itemYaml.getInt(itemNumber+".MinLV") > 0)
				lore = lore+"%enter%§4최소 레벨 : " + itemYaml.getInt(itemNumber+".MinLV");
			if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
				lore = lore+"%enter%§4최소 누적레벨 : " + itemYaml.getInt(itemNumber+".MinRLV");
			if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR");
			if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX");
			if(itemYaml.getInt(itemNumber+".MinINT") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT");
			if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL");
			if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK");
		}
		else
		{
			lore = lore+itemYaml.getString(itemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumber+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumber+".Grade")+"%enter%§f장비 가능 직업 : " + itemYaml.getString(itemNumber+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(itemNumber+".MinDamage") != 0||itemYaml.getInt(itemNumber+".MaxDamage") != 0)
				lore = lore+"§f"+main.MainServerOption.damage+" : " + itemYaml.getInt(itemNumber+".MinDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MinMaDamage") != 0||itemYaml.getInt(itemNumber+".MaxMaDamage") != 0)
				lore = lore+"§f"+main.MainServerOption.magicDamage+" : " + itemYaml.getInt(itemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEF") != 0)
				lore = lore+"§f방어 : " + itemYaml.getInt(itemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Protect") != 0)
				lore = lore+"§f보호 : " + itemYaml.getInt(itemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaDEF") != 0)
				lore = lore+"§f마법 방어 : " + itemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaProtect") != 0)
				lore = lore+"§f마법 보호 : " + itemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Balance") != 0)
				lore = lore+"§f밸런스 : " + itemYaml.getInt(itemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumber+".Critical") != 0)
				lore = lore+"§f크리티컬 : " + itemYaml.getInt(itemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxDurability") > 0)
				lore = lore+"§f내구도 : " + itemYaml.getInt(itemNumber+".Durability")+" / "+ itemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore + "§f숙련도 : 0.0%§f%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(itemNumber+".Lore")+"%enter%";


			if(itemYaml.getInt(itemNumber+".HP") > 0)
				lore = lore+"§3생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".HP") < 0)
				lore = lore+"§c생명력 : " + itemYaml.getInt(itemNumber+".HP")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MP") > 0)
				lore = lore+"§3마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".MP") < 0)
				lore = lore+"§c마나 : " + itemYaml.getInt(itemNumber+".MP")+"%enter%";
			if(itemYaml.getInt(itemNumber+".STR") > 0)
				lore = lore+"§3"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".STR") < 0)
				lore = lore+"§c"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".STR")+"%enter%";
			if(itemYaml.getInt(itemNumber+".DEX") > 0)
				lore = lore+"§3"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".DEX") < 0)
				lore = lore+"§c"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".DEX")+"%enter%";
			if(itemYaml.getInt(itemNumber+".INT") > 0)
				lore = lore+"§3"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".INT") < 0)
				lore = lore+"§c"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".INT")+"%enter%";
			if(itemYaml.getInt(itemNumber+".WILL") > 0)
				lore = lore+"§3"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".WILL") < 0)
				lore = lore+"§c"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".WILL")+"%enter%";
			if(itemYaml.getInt(itemNumber+".LUK") > 0)
				lore = lore+"§3"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
			else if(itemYaml.getInt(itemNumber+".LUK") < 0)
				lore = lore+"§c"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".LUK")+"%enter%";
			
			if(itemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
				lore = lore+"%enter%§f개조 : 0 / "+itemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(itemNumber+".MaxSocket") > 0)
			{
				lore = lore+"%enter%§f룬 : ";
				for(int count = 0; count <itemYaml.getInt(itemNumber+".MaxSocket");count++)
					lore = lore+"§7○ ";
			}
			if(itemYaml.getInt(itemNumber+".MinLV")!=0||itemYaml.getInt(itemNumber+".MinRLV")!=0||
					itemYaml.getInt(itemNumber+".MinSTR")!=0||itemYaml.getInt(itemNumber+".MinDEX")!=0||
					itemYaml.getInt(itemNumber+".MinINT")!=0||itemYaml.getInt(itemNumber+".MinWILL")!=0||
					itemYaml.getInt(itemNumber+".MinLUK")!=0)
				lore = lore+"%enter%";
			if(itemYaml.getInt(itemNumber+".MinLV") > 0)
				lore = lore+"%enter%§f최소 레벨 : " + itemYaml.getInt(itemNumber+".MinLV");
			if(itemYaml.getInt(itemNumber+".MinRLV") > 0)
				lore = lore+"%enter%§f최소 누적레벨 : " + itemYaml.getInt(itemNumber+".MinRLV");
			if(itemYaml.getInt(itemNumber+".MinSTR") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumber+".MinSTR");
			if(itemYaml.getInt(itemNumber+".MinDEX") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumber+".MinDEX");
			if(itemYaml.getInt(itemNumber+".MinINT") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumber+".MinINT");
			if(itemYaml.getInt(itemNumber+".MinWILL") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumber+".MinWILL");
			if(itemYaml.getInt(itemNumber+".MinLUK") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumber+".MinLUK");
		}
		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}

	public String[] useableLoreCreater(int itemNumber)
	{
	  	YamlLoader useableItemYaml = new YamlLoader();
		useableItemYaml.getConfig("Item/Consume.yml");
		String lore = "";
		String type = ChatColor.stripColor(useableItemYaml.getString(itemNumber+".ShowType"));

		lore = lore+useableItemYaml.getString(itemNumber+".Type");
		for(int count = 0; count<20-useableItemYaml.getString(itemNumber+".Type").length();count++)
			lore = lore+" ";
		lore = lore+useableItemYaml.getString(itemNumber+".Grade")+"%enter% %enter%";
		
		switch(type)
		{
		case "[분류]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 Ω 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"§9 Ω X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"§9 Ω Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"§9 Ω Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"§3 + 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"§c - 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"§3 + 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"§c - 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"§3 + 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"§c - 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"§3 + 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"§c - 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"§3 + 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"§c - 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"§3 + 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"§c - 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 + 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"§c - 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"§3 + 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"§c - 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"§c - 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";


				if(useableItemYaml.getInt(itemNumber+".HP")!=0||useableItemYaml.getInt(itemNumber+".MP")!=0||
				useableItemYaml.getInt(itemNumber+".STR")!=0||useableItemYaml.getInt(itemNumber+".DEX")!=0||
				useableItemYaml.getInt(itemNumber+".INT")!=0||useableItemYaml.getInt(itemNumber+".WILL")!=0||
				useableItemYaml.getInt(itemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 + 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c - 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			case "[공구]":
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
					lore = lore+"§3 + 숙련도 증가 : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
				break;
			}

			lore = lore+"%enter%§6___--------------------___%enter%§6       [아이템 설명]";
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%";
			lore = lore+"§6---____________________---%enter%";
		}
		break;
		case "[기호]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 Ω 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"§9 Ω X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"§9 Ω Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"§9 Ω Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"§3 + 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"§c - 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"§3 + 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"§c - 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"§3 + 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"§c - 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"§3 + 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"§c - 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"§3 + 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"§c - 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"§3 + 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"§c - 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 + 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"§c - 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"§3 + 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"§c - 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"§c - 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".HP")!=0||useableItemYaml.getInt(itemNumber+".MP")!=0||
				useableItemYaml.getInt(itemNumber+".STR")!=0||useableItemYaml.getInt(itemNumber+".DEX")!=0||
				useableItemYaml.getInt(itemNumber+".INT")!=0||useableItemYaml.getInt(itemNumber+".WILL")!=0||
				useableItemYaml.getInt(itemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 + 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c - 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			case "[공구]":
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
					lore = lore+"§3 + 숙련도 증가 : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
				break;
			}			
			
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";

		}
		break;
		case "[컬러]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"§9 X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"§9 Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"§9 Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"§3 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"§c 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"§3 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"§c 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"§3 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"§c 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"§3 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"§c 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"§3 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"§c 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"§3 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"§c 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"§c 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"§3 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"§c 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"§c 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";


					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				break;
				case "[공구]":
					if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
						lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
						lore = lore+"§3 숙련도 증가 : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
				break;
			}

			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";


		}
		break;
		
			case "[깔끔]":
			{
				switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
				{
				case "[귀환서]":
					lore = lore+"§f 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
					lore = lore+"§f X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
					lore = lore+"§f Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
					lore = lore+"§f Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
					break;
				case "[주문서]":
					if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
						lore = lore+"§3 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
						lore = lore+"§c 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
						lore = lore+"§3 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
						lore = lore+"§c 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEF")>0)
						lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
						lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")>0)
						lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")<0)
						lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
						lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
						lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
						lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
						lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")>0)
						lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")<0)
						lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")>0)
						lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")<0)
						lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					
						if(useableItemYaml.getInt(itemNumber+".HP") > 0)
							lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
							lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MP") > 0)
							lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
							lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".STR") > 0)
							lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
							lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
							lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
							lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".INT") > 0)
							lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
							lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
							lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
							lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
							lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
							lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					break;
				case "[스킬북]":
					if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
						lore = lore+"%enter%§f [아무것도 없는 빈 책]%enter%";
					else
						lore = lore+"%enter%§f"  +" [우 클릭시 아래 스킬 획득]%enter%§f + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
					break;
				case "[소비]":
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
						lore = lore+"§3 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
						lore = lore+"§c 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
					if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
						lore = lore+"§6§l + 환생%enter%";
					break;
				case "[룬]":
					if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
						lore = lore+"§3 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
						lore = lore+"§c 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
						lore = lore+"§3 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
						lore = lore+"§c 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
						lore = lore+"§3 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
						lore = lore+"§c 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
						lore = lore+"§3 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
						lore = lore+"§c 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
					
					
						
					if(useableItemYaml.getInt(itemNumber+".DEF")>0)
						lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
						lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")>0)
						lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")<0)
						lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
						lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
						lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
						lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
						lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")>0)
						lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")<0)
						lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")>0)
						lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")<0)
						lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Durability")>0)
						lore = lore+"§3 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
						lore = lore+"§c 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
						lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
						lore = lore+"§c 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					
						if(useableItemYaml.getInt(itemNumber+".HP") > 0)
							lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
							lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MP") > 0)
							lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
							lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".STR") > 0)
							lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
							lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
							lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
							lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".INT") > 0)
							lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
							lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
							lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
							lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
							lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
							lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
							lore = lore+"§5 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
							lore = lore+"§3 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					break;
					case "[공구]":
						if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
							lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".Proficiency") > 0)
							lore = lore+"§3 숙련도 증가 : " + useableItemYaml.getInt(itemNumber+".Proficiency")+"%enter%";
					break;
				}
				
				lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";

			}
			break;
		}

		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}

	public void useAbleItemUse(Player player, String type)
	{
		ItemStack item = player.getInventory().getItemInMainHand();
		if(type.equals("귀환서"))
		{
			if(ServerTickMain.PlayerTaskList.containsKey(player.getName())==true)
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				new effect.SendPacket().sendActionBar(player, "§c§l[현재 텔레포트를 할 수 없는 상태입니다!]", false);
				return;
			}
			util.ETC etc = new util.ETC();
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime() >= etc.getSec())
			{
				player.sendMessage("§c[이동 불가] : §e"+((main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - etc.getSec())/1000)+"§c 초 후에 이동 가능합니다!");
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			String world = "";
			int x = 0;
			short y = 0;
			int z = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("월드"))
						world = nowlore.split(" : ")[1];
					else if(nowlore.contains("X 좌표"))
						x = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Y 좌표"))
						y = Short.parseShort(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Z 좌표"))
						z = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			long utc= servertick.ServerTickMain.nowUTC-1;
			servertick.ServerTickObject STSO = new servertick.ServerTickObject(utc, "P_UTS");
			Location loc = player.getLocation();
			STSO.setTick(utc);//텔레포트 시작 시간
			STSO.setCount(5);//텔레포트 시간
			STSO.setString((byte)0, world+","+x+","+y+","+z);//이동 위치 저장
			STSO.setString((byte)1, loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());//현재 위치 저장
			STSO.setString((byte)2, player.getName());//플레이어 이름 저장
			servertick.ServerTickMain.Schedule.put(utc, STSO);
			ServerTickMain.PlayerTaskList.put(player.getName(), ""+utc);
			new effect.PottionBuff().givePotionEffect(player, PotionEffectType.CONFUSION, 8, 255);
			SoundEffect.playSound(player, Sound.BLOCK_CLOTH_BREAK, 0.7F, 0.5F);
			SoundEffect.playSound(player, Sound.BLOCK_PORTAL_TRAVEL, 0.6F, 1.4F);
		}
		else if(type.equals("주문서"))
		{
			if(item.getItemMeta().getDisplayName().equals("§2§3§4§3§3§l[스텟 초기화 주문서]"))
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				if(!configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
				{
					if(item.getAmount() != 1)
					{
						item.setAmount(item.getAmount()-1);
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
					}
					else
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
					int totalStatPoint = configYaml.getInt("DefaultStat.StatPoint")+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() - configYaml.getInt("DefaultStat.STR");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() - configYaml.getInt("DefaultStat.DEX");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() - configYaml.getInt("DefaultStat.INT");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() - configYaml.getInt("DefaultStat.WILL");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() - configYaml.getInt("DefaultStat.LUK");
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_STR(configYaml.getInt("DefaultStat.STR"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_DEX(configYaml.getInt("DefaultStat.DEX"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_INT(configYaml.getInt("DefaultStat.INT"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_WILL(configYaml.getInt("DefaultStat.WILL"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_LUK(configYaml.getInt("DefaultStat.LUK"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_StatPoint(totalStatPoint);
					SoundEffect.playSound(player, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.2F, 0.5F);
					player.sendMessage("§e§l[SYSTEM] : 스텟이 초기화되었습니다!");
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[System] : 메이플 스토리 시스템일 경우만 사용 가능합니다!");
				}
				return;
			}
			
			int statPoint = 0;
			int skillPoint = 0;
			int def = 0;
			int protect = 0;
			int magicDef = 0;
			int magicProtect  = 0;
			int balance = 0;
			int critical  = 0;
			int hp  = 0;
			int mp  = 0;
			int str  = 0;
			int dex  = 0;
			int INT  = 0;
			int will  = 0;
			int luk  = 0;
			
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("포인트"))
					{
						if(nowlore.contains("스텟"))
							statPoint = Integer.parseInt(nowlore.split(" : ")[1]);
						if(nowlore.contains("스킬"))
							skillPoint = Integer.parseInt(nowlore.split(" : ")[1]);
					}
					if(nowlore.contains("방어"))
						if(nowlore.contains("마법"))
							magicDef = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							def = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("보호"))
						if(nowlore.contains("마법"))
							magicProtect = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							protect = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("밸런스"))
						balance = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("크리티컬"))
						critical = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("생명력"))
						hp = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("마나"))
						mp = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statSTR))
						str = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statDEX))
						dex = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statINT))
						INT = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statWILL))
						will = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statLUK))
						luk = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(skillPoint!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(skillPoint);
			if(statPoint!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(statPoint);
			if(def!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(def);
			if(protect!=0)
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(protect);
			if(magicDef!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(magicDef);
			if(magicProtect!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(magicProtect);
			if(balance!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(balance);
			if(critical!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(critical);
			if(hp!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(hp);
			if(mp!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(mp);
			if(str!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(str);
			if(dex!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(dex);
			if(INT!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(INT);
			if(will!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(will);
			if(luk!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(luk);

			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(skillPoint>=0&&statPoint>=0&&def>=0&&protect>=0&&magicDef>=0&&magicProtect>=0&&balance>=0&&critical>=0&&hp>0
			&&mp>=0&&str>=0&&dex>=0&&INT>=0&&will>=0&&luk>0)
			{
				SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 0.5F);
				player.sendMessage("§a§l[      능력치가 상승 하였습니다!      ]");
			}
			else if(skillPoint<0&&statPoint<0&&def<0&&protect<0&&magicDef<0&&magicProtect<0&&balance<0&&critical<0&&hp<0
					&&mp<0&&str<0&&dex<0&&INT<0&&will<0&&luk<0)
			{
				SoundEffect.playSound(player, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.8F, 0.5F);
				player.sendMessage("§c§l[      능력치가 감소 하였습니다!      ]");
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.5F);
				player.sendMessage("§e§l[      능력치에 변화가 생겼습니다!      ]");
			}
		}
		else if(type.equals("스킬북"))
		{
		  	YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
			{
				String skillName = null;
				for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
				{
					String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
					if(nowlore.contains("[우"))
					{
						if(nowlore.contains("클릭시"))
							if(nowlore.contains("아래"))
								if(nowlore.contains("스킬"))
									if(nowlore.contains("획득]"))
									{
										nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter+1));
										skillName = nowlore.replace(" + ", "");
										break;
									}
					}
				}
				if(skillName == null)
					return;
			  	YamlLoader skillYaml = new YamlLoader();
				skillYaml.getConfig("Skill/SkillList.yml");
				if(skillYaml.contains(skillName))
				{
					skillYaml.getConfig("Skill/JobList.yml");
					if(skillYaml.contains("Mabinogi.Added."+skillName))
					{
					  	YamlLoader playerSkillYaml = new YamlLoader();
						playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						if(!playerSkillYaml.contains("Mabinogi."+skillYaml.getString("Mabinogi.Added."+skillName)+"."+skillName))
						{
							playerSkillYaml.set("Mabinogi."+skillYaml.getString("Mabinogi.Added."+skillName)+"."+skillName, 1);
							playerSkillYaml.saveConfig();
							if(item.getAmount() != 1)
							{
								item.setAmount(item.getAmount()-1);
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
							}
							else
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
							SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
							player.sendMessage("§d§l[새로운 스킬을 획득 하였습니다!] §e§l"+ChatColor.UNDERLINE+skillName);
							return;
						}
						else
						{
							SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c§l[         당신은 이미 해당 스킬을 알고 있습니다!         ]");
							return;
						}
					}
					else
					{
						SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c§l[해당 스킬은 어느 카테고리에도 존재하지 않습니다! 관리자에게 문의하세요!]");
						return;
					}
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c§l[서버에 해당 스킬이 존재하지 않습니다! 관리자에게 문의하세요!]");
					return;
				}
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c§l[   서버 시스템이 '마비노기'일 경우만 사용 가능합니다!   ]");
				return;
			}
		}
		else if(type.equals("소비"))
		{
			int health = 0;
			int mana = 0;
			int food = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("생명력"))
						health = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("마나"))
						mana = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("포만감"))
						food = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(health > 0)
			{
				SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2.0F, 0.8F);
				Damageable damageable = player;
				if(damageable.getMaxHealth() < damageable.getHealth()+health)
					damageable.setHealth(damageable.getMaxHealth());
				else
					damageable.setHealth(damageable.getHealth() + health);
			}
			if(mana >0)
			{
				if(main.MainServerOption.MagicSpellsCatched)
				{
					otherplugins.SpellMain spellMain = new otherplugins.SpellMain();
					spellMain.DrinkManaPotion(player, mana);
					SoundEffect.playSoundLocation(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 2.0F, 1.9F);
				}
			}
			if(food > 0)
			{
				SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 2.0F, 1.2F);
				if(player.getFoodLevel()+food > 20)
					player.setFoodLevel(20);
				player.setFoodLevel(player.getFoodLevel()+food);
			}
		}
		else if(type.equals("돈"))
		{
			int money=Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(1).split(" ")[0]));
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + money <= 2000000000)
			{
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, 0, false);
				if(item.getAmount() != 1)
				{
					item.setAmount(item.getAmount()-1);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
				}
				else
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
				SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
				player.sendMessage("§a[SYSTEM] : §f§l"+money+" "+MainServerOption.money+"§a 입금 완료!");
				player.sendMessage("§7(현재 "+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+ChatColor.stripColor(MainServerOption.money)+" 보유중)");
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[System] : "+MainServerOption.money+"§c 을(를) 2000000000(20억)이상 가질 수 없습니다!");
			}
		}
		else if(type.equals("공구"))
		{
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			effect.SoundEffect.playSound(player, Sound.BLOCK_ANVIL_HIT, 0.8F, 1.7F);
			item.setAmount(1);
			new ApplyToolGui().applyToolGui(player, item);
		}
		return;
	}

}