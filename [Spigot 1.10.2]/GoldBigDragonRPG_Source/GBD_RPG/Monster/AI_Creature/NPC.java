package GBD_RPG.Monster.AI_Creature;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_10_R1.DataWatcher;
import net.minecraft.server.v1_10_R1.DataWatcherRegistry;
import net.minecraft.server.v1_10_R1.EnumGamemode;
import net.minecraft.server.v1_10_R1.MathHelper;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo.PlayerInfoData;

public class NPC
{

	private int entityID;
	private Location location;
	private GameProfile gameprofile;
	
	
	public NPC(String name,Location location){
		entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
		gameprofile = new GameProfile(UUID.randomUUID(), name);
		changeSkin();
		this.location = location.clone();
	}
	
	public void changeSkin()
	{
		String value = "eyJ0aW1lc3RhbXAiOjE0NDI4MzY1MTU1NzksInByb2ZpbGVJZCI6IjkwZWQ3YWY0NmU4YzRkNTQ4MjRkZTc0YzI1MTljNjU1IiwicHJvZmlsZU5hbWUiOiJDb25DcmFmdGVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMWNlZDMzMjNmYjczMmFjMTc3MTc5Yjg5NWQ5YzJmNjFjNzczZWYxNTVlYmQ1Y2M4YzM5NTZiZjlhMDlkMTIifX19";
		String signature = "tFGNBQNpxNGvD27SN7fqh3LqNinjJJFidcdF8LTRHOdoMNXcE5ezN172BnDlRsExspE9X4z7FPglqh/b9jrLFDfQrdqX3dGm1cKjYbvOXL9BO2WIOEJLTDCgUQJC4/n/3PZHEG2mVADc4v125MFYMfjzkznkA6zbs7w6z8f7pny9eCWNXPOQklstcdc1h/LvflnR+E4TUuxCf0jVsdT5AZsUYIsJa6fvr0+vItUXUdQ3pps0zthObPEnBdLYMtNY3G6ZLGVKcSGa/KRK2D/k69fmu/uTKbjAWtniFB/sdO0VNhLuvyr/PcZVXB78l1SfBR88ZMiW6XSaVqNnSP+MEfRkxgkJWUG+aiRRLE8G5083EQ8vhIle5GxzK68ZR48IrEX/JwFjALslCLXAGR05KrtuTD3xyq2Nut12GCaooBEhb46sipWLq4AXI9IpJORLOW8+GvY+FcDwMqXYN94juDQtbJGCQo8PX670YjbmVx7+IeFjLJJTZotemXu1wiQmDmtAAmug4U5jgMYIJryXMitD7r5pEop/cw42JbCO2u0b5NB7sI/mr4OhBKEesyC5usiARzuk6e/4aJUvwQ9nsiXfeYxZz8L/mh6e8YPJMyhVkFtblbt/4jPe0bs3xSUXO9XrDyhy9INC0jlLT22QjNzrDkD8aiGAopVvfnTTAug=";
		gameprofile.getProperties().put("textures", new Property("textures", value, signature));
	}
	
	
	public void animation(int animation)
	{
		PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		setValue(packet, "a", entityID);
		setValue(packet, "b", (byte)animation);
		sendPacket(packet);
	}
	
	public void status(int status)
	{
		PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
		setValue(packet, "a", entityID);
		setValue(packet, "b", (byte)status);
		sendPacket(packet);
	}
	
	public void spawn()
	{
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
		
		setValue(packet, "a", entityID);
		setValue(packet, "b", gameprofile.getId());
		setValue(packet, "c", getFixLocation(location.getX()));
		setValue(packet, "d", getFixLocation(location.getY()));
		setValue(packet, "e", getFixLocation(location.getZ()));
		setValue(packet, "f", getFixRotation(location.getYaw()));
		setValue(packet, "g", getFixRotation(location.getPitch()));
		setValue(packet, "h", 0);
		DataWatcher w = new DataWatcher(null);
		w.set(DataWatcherRegistry.c.a(6), 20F);
		setValue(packet, "i", w);
		addToTablist();
		sendPacket(packet);
		headRotation(location.getYaw(), location.getPitch());
	}
	
	public void teleport(Location location)
	{
		PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
		setValue(packet, "a", entityID);
		setValue(packet, "b", getFixLocation(location.getX()));
		setValue(packet, "c", getFixLocation(location.getY()));
		setValue(packet, "d", getFixLocation(location.getZ()));
		setValue(packet, "e", getFixRotation(location.getYaw()));
		setValue(packet, "f", getFixRotation(location.getPitch()));

		sendPacket(packet);
		headRotation(location.getYaw(), location.getPitch());
		this.location = location.clone();
	}
	
	public void headRotation(float yaw,float pitch)
	{
		PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
		PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
		setValue(packetHead, "a", entityID);
		setValue(packetHead, "b", getFixRotation(yaw));
		

		sendPacket(packet);
		sendPacket(packetHead);
	}
	
	public void destroy()
	{
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
		rmvFromTablist();
		sendPacket(packet);
	}
	
	public void addToTablist()
	{
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet);
	}
	
	public void rmvFromTablist()
	{
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet);
	}
	
	public int getFixLocation(double pos)
	{
		return (int)MathHelper.floor(pos * 32.0D);
	}
	
	public byte getFixRotation(float yawpitch)
	{
		return (byte) ((int) (yawpitch * 256.0F / 360.0F));
	}


	public void setValue(Object obj,String name,Object value){
		try{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(obj, value);
		}catch(Exception e){}
	}
	
	public Object getValue(Object obj,String name){
		try{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.get(obj);
		}catch(Exception e){}
		return null;
	}
	
	public void sendPacket(Packet<?> packet,Player player){
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public void sendPacket(Packet<?> packet){
		for(Player player : Bukkit.getOnlinePlayers()){
			sendPacket(packet,player);
		}
	}
}
