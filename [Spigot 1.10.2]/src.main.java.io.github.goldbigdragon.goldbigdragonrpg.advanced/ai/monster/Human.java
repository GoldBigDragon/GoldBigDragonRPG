package ai.monster;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.server.v1_10_R1.DataWatcher;
import net.minecraft.server.v1_10_R1.DataWatcherObject;
import net.minecraft.server.v1_10_R1.DataWatcherRegistry;
import net.minecraft.server.v1_10_R1.EnumItemSlot;
import net.minecraft.server.v1_10_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_10_R1.PacketPlayOutBed;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntity;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;

public class Human {

	String name;
	World world;
	public int id;
	public static JavaPlugin plugin;
	Location l;
	int itemInHand;
	private UUID uuid;

	public static ArrayList<Location> locations = new ArrayList<>();
	public static ArrayList<Human> humans = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	private void setPrivateField(Class type, Object object, String name, Object value) {
		try {
			Field f = type.getDeclaredField(name);
			f.setAccessible(true);
			f.set(object, value);
			f.setAccessible(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setPitch(float pitch) {
		this.walk(0.0d, 0.0d, 0.0d, l.getYaw(), pitch);
	}

	public void setYaw(float yaw) {
		this.walk(0.0d, 0.0d, 0.0d, yaw, l.getPitch());
	}

	public Human(World w, String name, int id, Location l, int itemInHand) {
		this.name = name;
		this.world = w;
		this.id = id;
		this.l = l;
		this.itemInHand = itemInHand;
		this.uuid = UUID.randomUUID();
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn();

		/*
		[16:21:55 INFO]: private int net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.a
		[16:21:55 INFO]: private java.util.UUID net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.b
		[16:21:55 INFO]: private double net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.c
		[16:21:55 INFO]: private double net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.d
		[16:21:55 INFO]: private double net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.e
		[16:21:55 INFO]: private byte net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.f
		[16:21:55 INFO]: private byte net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.g
		[16:21:55 INFO]: private net.minecraft.server.v1_10_R1.DataWatcher net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.h
		[16:21:55 INFO]: private java.util.List net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn.i
		*/
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "a", id);
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "b", uuid);
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "c", ((int) l.getX() * 32));
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "d", ((int) l.getY() * 32));
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "e", ((int) l.getZ() * 32));
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "f", (byte)0);
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "g", (byte)0);
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "h", d);
			setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "i", null);

		try
		{
		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "a", id);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "b", (double)l.getX());
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "c", (double)l.getY());
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "d", (double)l.getZ());
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "e", (byte) 0);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "f", (byte) 0);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "g", true);

		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawn);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(tp);
		}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		locations.add(l);
		humans.add(this);
	}

	public void teleport(Location loc) {
		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "a", id);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "b", ((int) (loc.getX() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "c", ((int) (loc.getY() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "d", ((int) (loc.getZ() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "e", getCompressedAngle(loc.getYaw()));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "f", getCompressedAngle(loc.getPitch()));
		locations.remove(l);
		humans.remove(this);
		this.l = loc;
		locations.add(l);
		humans.add(this);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(tp);
		}

	}

	private byte getCompressedAngle(float value)
	{
		return (byte) ((value * 256.0F) / 360.0F);
	}

	private byte getCompressedAngle2(float value)
	{
		return (byte) ((value * 256.0F) / 360.0F);
	}

	public void remove() {
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(id);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void updateItems(ItemStack hand, ItemStack hand2, ItemStack boots, ItemStack legs, ItemStack chest, ItemStack helmet)
	{
		PacketPlayOutEntityEquipment[] ps = new PacketPlayOutEntityEquipment[] {
				new PacketPlayOutEntityEquipment(id, EnumItemSlot.FEET, CraftItemStack.asNMSCopy(boots)),
				new PacketPlayOutEntityEquipment(id, EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(legs)),
				new PacketPlayOutEntityEquipment(id, EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(chest)),
				new PacketPlayOutEntityEquipment(id, EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(helmet)),
				new PacketPlayOutEntityEquipment(id, EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(hand)),
				new PacketPlayOutEntityEquipment(id, EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(hand2))
				};
		for (PacketPlayOutEntityEquipment pack : ps) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack);
			}
		}
	}

	@Deprecated
	public void setName(String s)
	{
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		d.register(new DataWatcherObject<>(10, DataWatcherRegistry.a), (String)s);
		// d.a(11, (Object) (byte) 0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void hideForPlayer(Player p)
	{
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)32);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
	}

	public void showForPlayer(Player p)
	{
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
	}

	public void addPotionColor(Color r)
	{
		int color = r.asBGR();
		final DataWatcher dw = new DataWatcher(null);
		dw.register(new DataWatcherObject<>(7, DataWatcherRegistry.a), Integer.valueOf(color));
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, dw, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void addPotionColor(int color)
	{
		final DataWatcher dw = new DataWatcher(null);
		dw.register(new DataWatcherObject<>(7, DataWatcherRegistry.a), Integer.valueOf(color));
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, dw, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void walk(double a, double b, double c) {
		walk(a, b, c, l.getYaw(), l.getPitch());
	}

	public void walk(double a, double b, double c, float yaw, float pitch) {
		byte x = (byte) a;
		byte y = (byte) b;
		byte z = (byte) c;
		PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutRelEntityMoveLook();
		setPrivateField(PacketPlayOutEntity.class, packet, "a", id);
		setPrivateField(PacketPlayOutEntity.class, packet, "b", x);
		setPrivateField(PacketPlayOutEntity.class, packet, "c", y);
		setPrivateField(PacketPlayOutEntity.class, packet, "d", z);
		setPrivateField(PacketPlayOutEntity.class, packet, "e", getCompressedAngle(yaw));
		setPrivateField(PacketPlayOutEntity.class, packet, "f", getCompressedAngle2(pitch));

		PacketPlayOutEntityHeadRotation p2 = new PacketPlayOutEntityHeadRotation();
		setPrivateField(PacketPlayOutEntityHeadRotation.class, p2, "a", id);
		setPrivateField(PacketPlayOutEntityHeadRotation.class, p2, "b", getCompressedAngle(yaw));

		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(p2);
		}
		locations.remove(l);
		humans.remove(this);
		l.setPitch(pitch);
		l.setYaw(yaw);
		l.add(a, b, c);
		locations.add(l);
		humans.add(this);
	}

	public void sendToPlayer(Player who) {
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn();
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "a", id);
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "b", new GameProfile(uuid, name));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "c", ((int) (l.getX() * 32)));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "d", ((int) (l.getY() * 32)));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "e", ((int) (l.getZ() * 32)));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "f", getCompressedAngle(l.getYaw()));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "g", getCompressedAngle(l.getPitch()));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "h", itemInHand);
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "i", d);

		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "a", id);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "b", ((int) (l.getX() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "c", ((int) (l.getY() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "d", ((int) (l.getZ() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "e", getCompressedAngle(l.getYaw()));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "f", getCompressedAngle(l.getPitch()));

		((CraftPlayer) who).getHandle().playerConnection.sendPacket(spawn);
		((CraftPlayer) who).getHandle().playerConnection.sendPacket(tp);
		locations.remove(l);
		humans.remove(this);
		this.l = who.getLocation();
		locations.add(l);
		humans.add(this);
	}

	public void setInvisible() {
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)32);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void setCrouched() {
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)2);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void reset() {
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void sprint() {
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)8);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(8, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	@Deprecated
	public void block() {
		DataWatcher d = new DataWatcher(null);
		d.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)16);
		d.register(new DataWatcherObject<>(1, DataWatcherRegistry.a), (short)0);
		d.register(new DataWatcherObject<>(6, DataWatcherRegistry.a), (byte)0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void damage() {
		PacketPlayOutAnimation packet18 = new PacketPlayOutAnimation();
		setPrivateField(PacketPlayOutAnimation.class, packet18, "a", id);
		setPrivateField(PacketPlayOutAnimation.class, packet18, "b", 2);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet18);
		}
	}

	public void swingArm() {
		PacketPlayOutAnimation packet18 = new PacketPlayOutAnimation();
		setPrivateField(PacketPlayOutAnimation.class, packet18, "a", id);
		setPrivateField(PacketPlayOutAnimation.class, packet18, "b", 0);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet18);
		}
	}

	@Deprecated
	public void eatInHand() {
		PacketPlayOutAnimation packet18 = new PacketPlayOutAnimation();
		setPrivateField(PacketPlayOutAnimation.class, packet18, "a", id);
		setPrivateField(PacketPlayOutAnimation.class, packet18, "b", 5);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet18);
		}
	}

	public void sleep(Location loc) {
		PacketPlayOutBed packet17 = new PacketPlayOutBed();
		setPrivateField(PacketPlayOutBed.class, packet17, "a", id);
		setPrivateField(PacketPlayOutBed.class, packet17, "b", (int) loc.getX());
		setPrivateField(PacketPlayOutBed.class, packet17, "c", (int) loc.getY());
		setPrivateField(PacketPlayOutBed.class, packet17, "d", (int) loc.getZ());
		// setPrivateField(PacketPlayOutBed.class, packet17, "e", 0);

		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet17);
		}
		locations.remove(l);
		this.l = loc;
		locations.add(l);
	}

	public double getX() {
		return l.getX();
	}

	public double getY() {
		return l.getY();
	}

	public double getZ() {
		return l.getZ();
	}

	public Location getLocation() {
		return l;
	}

	public static void serialize() {
		File file = new File(plugin.getDataFolder(), "npcs.yml");
		FileConfiguration npcs = YamlConfiguration.loadConfiguration(file);
		int current = 1;
		for (Human human : humans) {
			npcs.set("npcs.npc " + current + ".name", human.name);
			npcs.set("npcs.npc " + current + ".world", human.world.getName());
			npcs.set("npcs.npc " + current + ".id", human.id);
			npcs.set("npcs.npc " + current + ".location", locationToString(human.l));
			npcs.set("npcs.npc " + current + ".item", human.itemInHand);
			current++;
		}
		try {
			npcs.save(file);
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	public static ArrayList<Human> deserialize() {
		ArrayList<Human> humans = new ArrayList<Human>();
		File file = new File(plugin.getDataFolder() + "/npcs.yml");
		FileConfiguration npcs = YamlConfiguration.loadConfiguration(file);
		int current = 1;
		while (npcs.isConfigurationSection("npcs.npc " + current)) {
			ConfigurationSection sec = npcs.getConfigurationSection("npcs.npc " + current);
			Human human = new Human(Bukkit.getWorld(sec.getString("world")), sec.getString("name"), sec.getInt("id"),
					locationFromString(sec.getString("location")), sec.getInt("item"));
			humans.add(human);
			locations.add(human.l);
			current++;
		}
		try {
			npcs.set("npcs", null);
			npcs.save(file);
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return humans;
	}

	public static Location locationFromString(String string) {
		String[] location = string.split(",");
		return new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]),
				Double.parseDouble(location[2]), Double.parseDouble(location[3]));
	}

	public static String locationToString(Location loc) {
		return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
	}
}