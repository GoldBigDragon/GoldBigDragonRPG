package GoldBigDragon_RPG.ETC;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Job
{	
	public void AllPlayerFixAllSkillAndJobYML()
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config  = YC.getNewConfig("config.yml");
	  	if(Config.contains("Time.LastSkillChanged")==false)
	  	{
	  		Config.set("Time.LastSkillChanged", -1);
	  		Config.saveConfig();
	  	}
	  	
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
		for(short count = 0; count < players.length;count++)
		{
			YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+players[count].getUniqueId().toString()+".yml");
	  		if(Config.getInt("Time.LastSkillChanged")!=PlayerList.getInt("Update") || PlayerList.contains("Update")==false)
	  		{
	  			PlayerList.set("Update", Config.getInt("Time.LastSkillChanged"));
	  			PlayerList.saveConfig();
	  			FixJobList();
				FixPlayerJobList(players[count]);
	  		}
		}
		return;
	}

	public void PlayerFixAllSkillAndJobYML(Player player)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config  = YC.getNewConfig("config.yml");
	  	if(Config.contains("Time.LastSkillChanged")==false)
	  	{
	  		Config.set("Time.LastSkillChanged", -1);
	  		Config.saveConfig();
	  	}
		YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
  		if(Config.getInt("Time.LastSkillChanged")!=PlayerList.getInt("Update") || PlayerList.contains("Update")==false)
  		{
  			PlayerList.set("Update", Config.getInt("Time.LastSkillChanged"));
  			PlayerList.saveConfig();
  			FixJobList();
			FixPlayerJobList(player);
  		}
		return;
	}
	
	public void AllPlayerSkillRankFix()
	{
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
		for(int count = 0; count < players.length;count++)
			SkillRankFix(players[count]);
		return;
	}
	
	public void FixJobList()
	//직업 스킬에서 스킬 목록에 등록되지 않은 스킬을 삭제 해 주는 메소드
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager JobList = YC.getNewConfig("Skill/JobList.yml");
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		
		if(YC.getNewConfig("config.yml").getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			Object[] Categori = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			for(short counter = 0; counter < Categori.length; counter++)
			{
				if(Categori[counter].toString().compareTo("Added")!=0)
				{
					Object[] Skills = JobList.getConfigurationSection("Mabinogi."+Categori[counter].toString()).getKeys(false).toArray();
					for(short countta = 0; countta < Skills.length; countta++)
					{
						if(SkillList.contains(Skills[countta].toString())==false)
							JobList.removeKey("Mabinogi."+Categori[counter].toString()+"."+Skills[countta].toString());
					}
				}
			}
			JobList.saveConfig();
		}
		else
		{
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short counter = 0; counter < Job.length; counter++)
			{
				Object[] SubJob = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray();
				for(short count = 0; count < SubJob.length; count++)
				{
					Object[] SubJobSkills = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()+"."+SubJob[count]+".Skill").getKeys(false).toArray();
					for(short countta = 0; countta < SubJobSkills.length; countta++)
					{
						if(SkillList.contains(SubJobSkills[countta].toString())==false)
						{
							JobList.removeKey("MapleStory."+Job[counter].toString()+"."+SubJob[count].toString()+".Skill."+SubJobSkills[countta].toString());
							JobList.saveConfig();
						}
					}
				}
			}
		}
	}
	
	public void FixPlayerJobList(Player player)
	//직업 중에서 직업 목록에 등록되지 않은 직업을 가진 플레이어를 변경 해 주는 메소드
	//마비노기 버전에서는 삭제된 카테고리를 플레이어 스킬 목록에서 제거해 주며, 새로 나온 카테고리를 플레이어에게 등록해 준다.
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	if(YC.isExit("Skill/PlayerData/"+player.getUniqueId().toString()+".yml") == false)
	  		new GoldBigDragon_RPG.Skill.SkillConfig().CreateNewPlayerSkill(player);

		YamlManager PlayerList = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		
		if(YC.getNewConfig("config.yml").getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			ArrayList<String> Categori = new ArrayList<String>();
			ArrayList<String> PlayerCategori = new ArrayList<String>();
			for(short count=0;count<PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray().length;count++)
				PlayerCategori.add(PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray()[count].toString());
			for(short count=0;count<JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray().length;count++)
				Categori.add(JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray()[count].toString());

			for(short count = 0; count < PlayerCategori.size(); count++)
				if(Categori.contains(PlayerCategori.get(count)) == false)
					PlayerList.removeKey("Mabinogi."+PlayerCategori.get(count).toString());

			for(short count = 0; count < Categori.size(); count++)
				if(Categori.get(count).compareTo("Added") != 0)
					if(PlayerCategori.contains(Categori.get(count)) == false)
						PlayerList.createSection("Mabinogi."+Categori.get(count));
			

			//직업 스킬과 플레이어 스킬을 비교하여 직업에 없는 스킬 삭제
			//직업 스킬과 플레이어 스킬을 비교하여 플레이어에게 없는 스킬 등록
			//마비노기 카테고리에 등록된 모든 스킬을 문자열로 나열한 뒤, 플레이어가 가진 스킬을 대입하여
			//만일 카테고리에는 없지만 플레이어에게 스킬이 있다면 삭제해 주는 구문.
			for(short count = 0; count < Categori.size(); count ++)
			{
				if(Categori.get(count).compareTo("Added") != 0)
				{
					ArrayList<String> JobSkillList = new ArrayList<String>();
					ArrayList<String> PlayerSkillList = new ArrayList<String>();
					for(short countta = 0; countta < JobList.getConfigurationSection("Mabinogi."+Categori.get(count)).getKeys(false).toArray().length; countta ++)
						JobSkillList.add(JobList.getConfigurationSection("Mabinogi."+Categori.get(count)).getKeys(false).toArray()[countta].toString());
					for(short countta = 0; countta < PlayerList.getConfigurationSection("Mabinogi."+Categori.get(count)).getKeys(false).toArray().length; countta ++)
						PlayerSkillList.add(PlayerList.getConfigurationSection("Mabinogi."+Categori.get(count)).getKeys(false).toArray()[countta].toString());
					for(short countta = 0; countta < PlayerSkillList.size(); countta++)
						if(JobSkillList.contains(PlayerSkillList.get(countta))==false)
							PlayerList.removeKey("Mabinogi."+Categori.get(count)+"."+PlayerSkillList.get(countta));
					
					//히든 스킬 외의 일반 스킬들을 추려내어, 일반 스킬이 없는 플레이어에게
					//스킬을 전수해 주는 구문.
					for(short countta = 0; countta < JobSkillList.size(); countta++)
					{
						if(JobList.getBoolean("Mabinogi."+Categori.get(count) + "."+JobSkillList.get(countta)) == true)
							if(PlayerSkillList.contains(JobSkillList.get(countta))==false)
								PlayerList.set("Mabinogi."+Categori.get(count)+"."+JobSkillList.get(countta), 1);
					}
					
					//스킬 최대 레벨 넘긴것들을 최대 레벨로 수정 해 주기.
					for(short countta = 0; countta < PlayerSkillList.size(); countta++)
					{
						short SkillMaxRank = (short) SkillList.getConfigurationSection(PlayerSkillList.get(countta)+".SkillRank").getKeys(false).size();
						if(PlayerList.getInt("Mabinogi."+Categori.get(count)+"."+PlayerSkillList.get(countta)) >  SkillMaxRank)
							PlayerList.set("Mabinogi."+Categori.get(count)+"."+PlayerSkillList.get(countta), SkillMaxRank);
					}
				}
			}
			PlayerList.saveConfig();
			
		}
		else//메이플 스토리
		{
			Object[] Jobs = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			boolean isJobExit = false;
			for(short counter = 0; counter < Jobs.length; counter++)
				for(short count =0; count < JobList.getConfigurationSection("MapleStory."+Jobs[counter].toString()).getKeys(false).size(); count++)
					if(JobList.getConfigurationSection("MapleStory."+Jobs[counter].toString()).getKeys(false).toArray()[count].toString().compareTo(PlayerList.getString("Job.Type"))==0)
					{
						isJobExit = true;
						break;
					}
			if(isJobExit==false)
			{
				YamlManager Config  = YC.getNewConfig("config.yml");
				String ServerDefaultJob = Config.getString("Server.DefaultJob");
				PlayerList = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				PlayerList.set("Job.Type", ServerDefaultJob);
				PlayerList.set("Job.LV", 1);
				Object[] Skills = JobList.getConfigurationSection("MapleStory."+ServerDefaultJob+"."+ServerDefaultJob+".Skill").getKeys(false).toArray();
				for(short count = 0; count < Skills.length;count++)
					if(PlayerList.contains("MapleStory."+ServerDefaultJob+".Skill."+Skills[count].toString())==false)
						PlayerList.set("MapleStory."+ServerDefaultJob+".Skill."+Skills[count].toString(),1);
				PlayerList.saveConfig();
			}

			//삭제된 스킬 지워주고, 못배운 스킬 더해주는 구문
			Object[] PlayerJob = PlayerList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short counter = 0; counter < Jobs.length; counter++)
			{
				for(short count = 0; count < PlayerJob.length; count++)
				{
					Object[] SubJobs = JobList.getConfigurationSection("MapleStory."+Jobs[counter]).getKeys(false).toArray();
					if(PlayerList.contains("MapleStory."+PlayerJob[count].toString()+".Skill")==false)
					{
						PlayerList.createSection("MapleStory."+PlayerJob[count].toString()+".Skill");
						PlayerList.saveConfig();
					}
					for(short countta = 0; countta < SubJobs.length; countta++)
					{
						if(SubJobs[countta].toString().compareTo(PlayerJob[count].toString())==0)
						{
							ArrayList<String> SubJobSkills = new ArrayList<String>();
							ArrayList<String> PlayerJobSkills = new ArrayList<String>();
							
							for(short count1 = 0; count1 < JobList.getConfigurationSection("MapleStory."+Jobs[counter].toString()+"."+SubJobs[countta]+".Skill").getKeys(false).toArray().length; count1 ++)
								SubJobSkills.add(JobList.getConfigurationSection("MapleStory."+Jobs[counter].toString()+"."+SubJobs[countta]+".Skill").getKeys(false).toArray()[count1].toString());
							for(short count1 = 0; count1 < PlayerList.getConfigurationSection("MapleStory."+PlayerJob[count].toString()+".Skill").getKeys(false).toArray().length; count1 ++)
								PlayerJobSkills.add(PlayerList.getConfigurationSection("MapleStory."+PlayerJob[count].toString()+".Skill").getKeys(false).toArray()[count1].toString());
							
							for(short cc=0;cc<PlayerJobSkills.size();cc++)
								if(SubJobSkills.contains(PlayerJobSkills.get(cc))==false)
									PlayerList.removeKey("MapleStory."+PlayerJob[count].toString()+".Skill."+PlayerJobSkills.get(cc).toString());

							for(short cc=0;cc<SubJobSkills.size();cc++)
								if(PlayerJobSkills.contains(SubJobSkills.get(cc))==false)
									PlayerList.set("MapleStory."+SubJobs[countta]+".Skill."+SubJobSkills.get(cc),1);

							//스킬 최대 레벨 넘긴것들을 최대 레벨로 수정 해 주기.
							for(short cc = 0; cc < PlayerJobSkills.size();cc++)
							{
								short SkillMaxRank = (short) SkillList.getConfigurationSection(PlayerJobSkills.get(cc)+".SkillRank").getKeys(false).size();
								if(PlayerList.getInt("MapleStory."+PlayerJob[counter]+".Skill."+PlayerJobSkills.get(cc)) >  SkillMaxRank)
									PlayerList.set("MapleStory."+PlayerJob[counter]+".Skill."+PlayerJobSkills.get(cc), SkillMaxRank);
							}
							
							PlayerList.saveConfig();
							
						}
					}
				}
			}
		}
		return;
	}
	
	public void SkillRankFix(Player player)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config  = YC.getNewConfig("config.yml");
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		//스킬 최대 레벨보다 레벨이 높은 스킬을 최대 레벨치로 수정해 주는 구문
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Object[] CategoriList = PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			PlayerList.saveConfig();
			for(short count = 0; count < CategoriList.length; count ++)
			{
				Object[] PlayerSkills = PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
				for(short countta = 0; countta < PlayerSkills.length; countta++)
				{
					short SkillMaxRank = (short) SkillList.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
					if(PlayerList.getInt("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta]) >  SkillMaxRank)
					{
						PlayerList.set("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta].toString(), SkillMaxRank);
					}
				}
			}
		}
		else
		{
			if(PlayerList.contains("MapleStory"))
			{
				Object[] PlayerJob = PlayerList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short counter = 0; counter < PlayerJob.length; counter++)
				{
					Object[] PlayerSkills = PlayerList.getConfigurationSection("MapleStory."+PlayerJob[counter]+".Skill").getKeys(false).toArray();
					for(short countta = 0; countta < PlayerSkills.length;countta++)
					{
						short SkillMaxRank = (short) SkillList.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
						if(PlayerList.getInt("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta]) >  SkillMaxRank)
						{
							PlayerList.set("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta], SkillMaxRank);
						}
					}
				}
			}
		}
		PlayerList.saveConfig();
	}
}
