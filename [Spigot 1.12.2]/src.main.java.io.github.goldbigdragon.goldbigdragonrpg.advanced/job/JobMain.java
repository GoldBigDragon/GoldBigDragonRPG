package job;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import util.YamlLoader;




public class JobMain
{	
	public void AllPlayerFixAllSkillAndJobYML()
	{
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	  	if(configYaml.contains("Time.LastSkillChanged")==false)
	  	{
	  		configYaml.set("Time.LastSkillChanged", -1);
	  		configYaml.saveConfig();
	  	}
	  	
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
	  	YamlLoader playerSkillYaml = new YamlLoader();
		for(int count = 0; count < players.length;count++)
		{
			playerSkillYaml.getConfig("Skill/PlayerData/"+players[count].getUniqueId().toString()+".yml");
	  		if(configYaml.getInt("Time.LastSkillChanged")!=playerSkillYaml.getInt("Update") || playerSkillYaml.contains("Update")==false)
	  		{
	  			playerSkillYaml.set("Update", configYaml.getInt("Time.LastSkillChanged"));
	  			playerSkillYaml.saveConfig();
	  			FixJobList();
				FixPlayerJobList(players[count]);
	  		}
		}
		return;
	}

	public void PlayerFixAllSkillAndJobYML(Player player)
	{
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	  	if(configYaml.contains("Time.LastSkillChanged")==false)
	  	{
	  		configYaml.set("Time.LastSkillChanged", -1);
	  		configYaml.saveConfig();
	  	}
	  	YamlLoader playerSkillYaml = new YamlLoader();
		playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
  		if(configYaml.getInt("Time.LastSkillChanged")!=playerSkillYaml.getInt("Update") || playerSkillYaml.contains("Update")==false)
  		{
  			playerSkillYaml.set("Update", configYaml.getInt("Time.LastSkillChanged"));
  			playerSkillYaml.saveConfig();
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
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		
		if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			String[] Categori = jobYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray(new String[0]);
			for(int counter = 0; counter < Categori.length; counter++)
			{
				if(!Categori[counter].equals("Added"))
				{
					String[] Skills = jobYaml.getConfigurationSection("Mabinogi."+Categori[counter]).getKeys(false).toArray(new String[0]);
					for(int countta = 0; countta < Skills.length; countta++)
					{
						if(skillYaml.contains(Skills[countta])==false)
							jobYaml.removeKey("Mabinogi."+Categori[counter]+"."+Skills[countta]);
					}
				}
			}
			jobYaml.saveConfig();
		}
		else
		{
			String[] Job = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
			for(int counter = 0; counter < Job.length; counter++)
			{
				String[] SubJob = jobYaml.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray(new String[0]);
				for(int count = 0; count < SubJob.length; count++)
				{
					String[] SubJobSkills = jobYaml.getConfigurationSection("MapleStory."+Job[counter].toString()+"."+SubJob[count]+".Skill").getKeys(false).toArray(new String[0]);
					for(int countta = 0; countta < SubJobSkills.length; countta++)
					{
						if(skillYaml.contains(SubJobSkills[countta])==false)
						{
							jobYaml.removeKey("MapleStory."+Job[counter]+"."+SubJob[count]+".Skill."+SubJobSkills[countta]);
							jobYaml.saveConfig();
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
	  	YamlLoader playerSkillYaml = new YamlLoader();
	  	if(playerSkillYaml.isExit("Skill/PlayerData/"+player.getUniqueId().toString()+".yml") == false)
	  		new skill.SkillConfig().CreateNewPlayerSkill(player);

		playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
	  	YamlLoader jobYaml = new YamlLoader();
	  	YamlLoader skillYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
		skillYaml.getConfig("Skill/SkillList.yml");
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		
		if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			ArrayList<String> categori = new ArrayList<>();
			ArrayList<String> playerCategori = new ArrayList<>();
			if(playerSkillYaml.getConfigurationSection("Mabinogi") != null)
				for(int count=0;count<playerSkillYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray().length;count++)
					playerCategori.add(playerSkillYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray()[count].toString());
			if(jobYaml.getConfigurationSection("Mabinogi") != null)
				for(int count=0;count<jobYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray().length;count++)
					categori.add(jobYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray()[count].toString());

			for(int count = 0; count < playerCategori.size(); count++)
				if(categori.contains(playerCategori.get(count)) == false)
					playerSkillYaml.removeKey("Mabinogi."+playerCategori.get(count).toString());

			for(int count = 0; count < categori.size(); count++)
				if(!categori.get(count).equals("Added"))
					if(playerCategori.contains(categori.get(count)) == false)
						playerSkillYaml.createSection("Mabinogi."+categori.get(count));
			

			//직업 스킬과 플레이어 스킬을 비교하여 직업에 없는 스킬 삭제
			//직업 스킬과 플레이어 스킬을 비교하여 플레이어에게 없는 스킬 등록
			//마비노기 카테고리에 등록된 모든 스킬을 문자열로 나열한 뒤, 플레이어가 가진 스킬을 대입하여
			//만일 카테고리에는 없지만 플레이어에게 스킬이 있다면 삭제해 주는 구문.
			for(int count = 0; count < categori.size(); count ++)
			{
				if(!categori.get(count).equals("Added"))
				{
					ArrayList<String> JobSkillList = new ArrayList<String>();
					ArrayList<String> PlayerSkillList = new ArrayList<String>();
					for(int countta = 0; countta < jobYaml.getConfigurationSection("Mabinogi."+categori.get(count)).getKeys(false).toArray().length; countta ++)
						JobSkillList.add(jobYaml.getConfigurationSection("Mabinogi."+categori.get(count)).getKeys(false).toArray()[countta].toString());
					for(int countta = 0; countta < playerSkillYaml.getConfigurationSection("Mabinogi."+categori.get(count)).getKeys(false).toArray().length; countta ++)
						PlayerSkillList.add(playerSkillYaml.getConfigurationSection("Mabinogi."+categori.get(count)).getKeys(false).toArray()[countta].toString());
					for(int countta = 0; countta < PlayerSkillList.size(); countta++)
						if(JobSkillList.contains(PlayerSkillList.get(countta))==false)
							playerSkillYaml.removeKey("Mabinogi."+categori.get(count)+"."+PlayerSkillList.get(countta));
					
					//히든 스킬 외의 일반 스킬들을 추려내어, 일반 스킬이 없는 플레이어에게
					//스킬을 전수해 주는 구문.
					for(int countta = 0; countta < JobSkillList.size(); countta++)
					{
						if(jobYaml.getBoolean("Mabinogi."+categori.get(count) + "."+JobSkillList.get(countta)) == true)
							if(PlayerSkillList.contains(JobSkillList.get(countta))==false)
								playerSkillYaml.set("Mabinogi."+categori.get(count)+"."+JobSkillList.get(countta), 1);
					}
					
					//스킬 최대 레벨 넘긴것들을 최대 레벨로 수정 해 주기.
					for(int countta = 0; countta < PlayerSkillList.size(); countta++)
					{
						short SkillMaxRank = (short) skillYaml.getConfigurationSection(PlayerSkillList.get(countta)+".SkillRank").getKeys(false).size();
						if(playerSkillYaml.getInt("Mabinogi."+categori.get(count)+"."+PlayerSkillList.get(countta)) >  SkillMaxRank)
							playerSkillYaml.set("Mabinogi."+categori.get(count)+"."+PlayerSkillList.get(countta), SkillMaxRank);
					}
				}
			}
			playerSkillYaml.saveConfig();
			
		}
		else//메이플 스토리
		{
			String[] jobs = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
			boolean isJobExit = false;
			for(int counter = 0; counter < jobs.length; counter++)
				for(int count =0; count < jobYaml.getConfigurationSection("MapleStory."+jobs[counter]).getKeys(false).size(); count++)
					if(jobYaml.getConfigurationSection("MapleStory."+jobs[counter]).getKeys(false).toArray()[count].toString().equals(playerSkillYaml.getString("Job.Type")))
					{
						isJobExit = true;
						break;
					}
			if(!isJobExit)
			{
				String serverDefaultJob = configYaml.getString("Server.DefaultJob");
				playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				playerSkillYaml.set("Job.Type", serverDefaultJob);
				playerSkillYaml.set("Job.LV", 1);
				if(jobYaml.getConfigurationSection("MapleStory."+serverDefaultJob+"."+serverDefaultJob+".Skill") != null)
				{
					String[] skills = jobYaml.getConfigurationSection("MapleStory."+serverDefaultJob+"."+serverDefaultJob+".Skill").getKeys(false).toArray(new String[0]);
					for(int count = 0; count < skills.length;count++)
						if(!playerSkillYaml.contains("MapleStory."+serverDefaultJob+".Skill."+skills[count]))
							playerSkillYaml.set("MapleStory."+serverDefaultJob+".Skill."+skills[count],1);
				}
				playerSkillYaml.saveConfig();
			}

			//삭제된 스킬 지워주고, 못배운 스킬 더해주는 구문
			if(playerSkillYaml.getConfigurationSection("MapleStory") != null)
			{
				String[] playerJob = playerSkillYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
				for(int counter = 0; counter < jobs.length; counter++)
				{
					for(int count = 0; count < playerJob.length; count++)
					{
						String[] subJobs = jobYaml.getConfigurationSection("MapleStory."+jobs[counter]).getKeys(false).toArray(new String[0]);
						if(!playerSkillYaml.contains("MapleStory."+playerJob[count]+".Skill"))
						{
							playerSkillYaml.createSection("MapleStory."+playerJob[count]+".Skill");
							playerSkillYaml.saveConfig();
						}
						for(int countta = 0; countta < subJobs.length; countta++)
						{
							if(subJobs[countta].equals(playerJob[count]))
							{
								ArrayList<String> subJobSkills = new ArrayList<>();
								ArrayList<String> playerJobSkills = new ArrayList<>();
								
								for(int count1 = 0; count1 < jobYaml.getConfigurationSection("MapleStory."+jobs[counter]+"."+subJobs[countta]+".Skill").getKeys(false).toArray().length; count1 ++)
									subJobSkills.add(jobYaml.getConfigurationSection("MapleStory."+jobs[counter]+"."+subJobs[countta]+".Skill").getKeys(false).toArray()[count1].toString());
								for(int count1 = 0; count1 < playerSkillYaml.getConfigurationSection("MapleStory."+playerJob[count]+".Skill").getKeys(false).toArray().length; count1 ++)
									playerJobSkills.add(playerSkillYaml.getConfigurationSection("MapleStory."+playerJob[count]+".Skill").getKeys(false).toArray()[count1].toString());
								
								for(int cc=0;cc<playerJobSkills.size();cc++)
									if(!subJobSkills.contains(playerJobSkills.get(cc)))
										playerSkillYaml.removeKey("MapleStory."+playerJob[count]+".Skill."+playerJobSkills.get(cc));

								for(int cc=0;cc<subJobSkills.size();cc++)
									if(!playerJobSkills.contains(subJobSkills.get(cc)))
										playerSkillYaml.set("MapleStory."+subJobs[countta]+".Skill."+subJobSkills.get(cc),1);

								//스킬 최대 레벨 넘긴것들을 최대 레벨로 수정 해 주기.
								for(int cc = 0; cc < playerJobSkills.size();cc++)
								{
									if(skillYaml.contains(playerJobSkills.get(cc)+".SkillRank"))
									{
										short skillMaxRank = (short) skillYaml.getConfigurationSection(playerJobSkills.get(cc)+".SkillRank").getKeys(false).size();
										if(playerSkillYaml.getInt("MapleStory."+playerJob[counter]+".Skill."+playerJobSkills.get(cc)) >  skillMaxRank)
											playerSkillYaml.set("MapleStory."+playerJob[counter]+".Skill."+playerJobSkills.get(cc), skillMaxRank);
									}
								}
								
								playerSkillYaml.saveConfig();
								
							}
						}
					}
				}
			}
		}
		return;
	}
	
	public void SkillRankFix(Player player)
	{
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");
	  	YamlLoader playerSkillYaml = new YamlLoader();
		playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		//스킬 최대 레벨보다 레벨이 높은 스킬을 최대 레벨치로 수정해 주는 구문
		if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			String[] CategoriList = playerSkillYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray(new String[0]);
			playerSkillYaml.saveConfig();
			for(int count = 0; count < CategoriList.length; count ++)
			{
				String[] PlayerSkills = playerSkillYaml.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray(new String[0]);
				for(int countta = 0; countta < PlayerSkills.length; countta++)
				{
					short SkillMaxRank = (short) skillYaml.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
					if(playerSkillYaml.getInt("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta]) >  SkillMaxRank)
					{
						playerSkillYaml.set("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta], SkillMaxRank);
					}
				}
			}
		}
		else
		{
			if(playerSkillYaml.contains("MapleStory"))
			{
				String[] PlayerJob = playerSkillYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
				for(int counter = 0; counter < PlayerJob.length; counter++)
				{
					String[] PlayerSkills = playerSkillYaml.getConfigurationSection("MapleStory."+PlayerJob[counter]+".Skill").getKeys(false).toArray(new String[0]);
					for(int countta = 0; countta < PlayerSkills.length;countta++)
					{
						short SkillMaxRank = (short) skillYaml.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
						if(playerSkillYaml.getInt("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta]) >  SkillMaxRank)
						{
							playerSkillYaml.set("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta], SkillMaxRank);
						}
					}
				}
			}
		}
		playerSkillYaml.saveConfig();
	}
}
