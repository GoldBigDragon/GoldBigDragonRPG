package otherplugins;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.xxmicloxx.noteblockapi.NBSDecoder;
import com.xxmicloxx.noteblockapi.NoteBlockPlayerMain;
import com.xxmicloxx.noteblockapi.RadioSongPlayer;
import com.xxmicloxx.noteblockapi.Song;
import com.xxmicloxx.noteblockapi.SongPlayer;

public class NoteBlockAPIMain implements Listener
{
	public static List<Song> Musics = new ArrayList<Song>();
	HashMap<Player,SongPlayer> currentStations = new HashMap<Player,SongPlayer>();
	HashMap<Player,SongPlayer> joinedPlayer = new HashMap<Player,SongPlayer>();
	short maxStations = 1000;
	static boolean MusicAdded =false;
  	public File MusicFolder = new File("plugins/GoldBigDragonRPG/NoteBlockSound/");

	public NoteBlockAPIMain(JavaPlugin plugin)
	{
		NoteBlockAPIAddMusic();
	}
	
	public NoteBlockAPIMain()
	{}
	
	public void NoteBlockAPIAddMusic()
	{
		if(!MusicFolder.exists())
			MusicFolder.mkdirs();
		else
			if(Musics.size() ==0&&MusicAdded==false)
			{
				if(MusicFolder.listFiles().length >= 0)
				{
					for (File child : MusicFolder.listFiles())
					{
						if(child.getName().contains("nbs"))
						Musics.add(new Song(NBSDecoder.parse(child)));
					}
				}
				else
					MusicAdded = false;
			}
	}

	public boolean SoundList(Player player, boolean isGUIclicked)
	{
		if(MusicAdded==false)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			if(isGUIclicked == false)
				return true;
		}
		if(Musics.size()==0)
		{
			player.sendMessage("§c[BGM] : 재생 가능한 nbs파일이 없습니다! nbs 파일을 아래 경로에 넣어 주세요.");
			player.sendMessage("§c[자신의 버킷 폴더] -> [plugins] -> [GoldBigDragonRPG] -> [NoteBlockSound]");
			return false;
		}
		else
			return true;
	}
	
	public String getTitle(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0||Musics.size() < MusicNumber)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			return "[음반 없음]";
		}
		if(Musics.size()>=1)
		{
			if(Musics.get(MusicNumber).getTitle() == null||Musics.get(MusicNumber).getTitle().length()<=0)
				return "[제목 없음]";
			else
				return Musics.get(MusicNumber).getTitle();
		}
		else
			return "[음반 없음]";
	}

	public String getAuthor(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0||Musics.size() < MusicNumber)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			return "[음반 없음]";
		}
		if(Musics.size()>=1)
		{
			if(Musics.get(MusicNumber).getAuthor() == null||Musics.get(MusicNumber).getAuthor().length()<=0)
				return "[작자 미상]";
			else
				return Musics.get(MusicNumber).getAuthor();
		}
		else
			return "[음반 없음]";
	}

	public String getDescription(int MusicNumber)
	{
		if(MusicAdded==false||Musics.size()==0||Musics.size() < MusicNumber)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
			return "[음반 없음]";
		}
		if(Musics.size()>=1)
		{
			if(Musics.get(MusicNumber).getDescription() == null||Musics.get(MusicNumber).getDescription().length()<=0)
				return "[설명 없음]";
			else
				return Musics.get(MusicNumber).getDescription();
		}
		else
			return "[음반 없음]";
	}
	public int SoundSize()
	{
		if(MusicAdded==false||Musics.size()==0)
		{
			MusicAdded = true;
			NoteBlockAPIAddMusic();
		}
		return Musics.size();
	}
	
	public void Play(Player player,int number)
	{
		if(number < Musics.size())
		{
			Stop(player);
			SongPlayer sp = new RadioSongPlayer(Musics.get(number));
			sp.addPlayer(player);
			sp.setPlaying(true);
			sp.setAutoDestroy(true);
			currentStations.put(player, sp);
		}
	}

	public void SongPlay(Player player,Song song)
	{
		Stop(player);
		SongPlayer sp = new RadioSongPlayer(song);
		
		sp.addPlayer(player);
		sp.setPlaying(true);
		sp.setAutoDestroy(true);
		currentStations.put(player, sp);
	}
	
	public void Stop(Player player)
	{
		currentStations.remove(player);
		joinedPlayer.remove(player);
		NoteBlockPlayerMain.stopPlaying(player);
	}
}
