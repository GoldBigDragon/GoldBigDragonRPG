package fr.tenebrae;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.RegistryMaterials;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

final public class CustomRegistry {

	public final static Map<String, CustomRegistryEntry> registryEntries = new HashMap<String, CustomRegistryEntry>();


	/**
	 * Register all pre-registered entities.<br>
	 * The plugin call this method on enable, you shouldn't have to call it manually.<br><br>
	 * 
	 * To register a custom entity, see {@link CustomRegistry#register(CustomRegistryEntry)}
	 */
	public static void registerEntities() {
		if (registryEntries.isEmpty()) return;
		for (CustomRegistryEntry cre : registryEntries.values()) register(cre);
	}

	/**
	 * Unregister all registered entities.<br>
	 * The plugin call this method on disable, you shouldn't have to call it manually.<br><br>
	 * 
	 * To unregister a custom entity, see {@link CustomRegistry#unregister(CustomRegistryEntry)}
	 */
	public static void unregisterEntities() {
		if (registryEntries.isEmpty()) return;
		for (CustomRegistryEntry cre : registryEntries.values()) unregister(cre);

		registryEntries.clear();
	}

	/**
	 * Register a new custom entity.<br>
	 * If the entity is already registered, does nothing.<br><br>
	 * 
	 * When an entity is registered, it will load every already spawned instance of this entity, so it may or may not create a little lag spike.
	 * 
	 * @param cre
	 * The registry entry to register.
	 */
	@SuppressWarnings("unchecked")
	public static void register(final CustomRegistryEntry cre) {
	if (registryEntries.containsKey(cre.getName())) return;
		final String paramString = cre.getName();
		try {
			MinecraftKey key = new MinecraftKey(paramString);
			((Set<MinecraftKey>) getPrivateStatic(EntityTypes.class, "d")).add(key);
			((RegistryMaterials<MinecraftKey,Class<?>>) getPrivateStatic(EntityTypes.class, "b")).a(cre.getID(), key, cre.getCustomClass());
			registryEntries.put(paramString, cre);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (World w : Bukkit.getWorlds()) {
			final File wFile = new File("entityNBT", w.getName());
			if (wFile.exists()) {
				for (Chunk c : w.getLoadedChunks()) {
					if (new File(wFile, ""+c.getX()).exists() && new File(wFile, c.getX()+System.getProperty("file.separator")+c.getZ()).exists()) {
						final File cFile = new File(wFile, c.getX()+System.getProperty("file.separator")+c.getZ());

						for (File data : cFile.listFiles(new FilenameFilter() {
							public boolean accept(File dir, String name) {
								return name.toLowerCase().endsWith(".tbp");
							}
						})) if (data.getName().contains(cre.getName())) loadEntity(data);

					}
				}
			}
		}
	}

	/**
	 * Force the register of a custom entity, even if it is already registered.<br>
	 * <b>Registering the same entity twice may not be what you would like to do.</b>
	 * 
	 * @param cre
	 * The registry entry to register.
	 * 
	 * @see CustomRegistry#register(CustomRegistryEntry cre)
	 */
	@SuppressWarnings("unchecked")
	public static void unsafeRegister(final CustomRegistryEntry cre) {
		final String paramString = cre.getName();
		try {
			MinecraftKey key = new MinecraftKey(paramString);
			((Set<MinecraftKey>) getPrivateStatic(EntityTypes.class, "d")).add(key);
			((RegistryMaterials<MinecraftKey,Class<?>>) getPrivateStatic(EntityTypes.class, "b")).a(cre.getID(), key, cre.getCustomClass());
			registryEntries.put(paramString, cre);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (World w : Bukkit.getWorlds()) {
			final File wFile = new File("entityNBT", w.getName());
			if (wFile.exists()) {
				for (Chunk c : w.getLoadedChunks()) {
					if (new File(wFile, ""+c.getX()).exists() && new File(wFile, c.getX()+System.getProperty("file.separator")+c.getZ()).exists()) {
						final File cFile = new File(wFile, c.getX()+System.getProperty("file.separator")+c.getZ());

						for (File data : cFile.listFiles(new FilenameFilter() {
							public boolean accept(File dir, String name) {
								return name.toLowerCase().endsWith(".tbp");
							}
						})) if (data.getName().contains(cre.getName())) loadEntity(data);
					}
				}
			}
		}
	}

	/**
	 * Unregister a custom entity, using its registry entry.<br>
	 * <b>Will not work if you create a new instance of CustomRegistryEntry, even if it has the same parameters.</b><br><br>
	 * 
	 * Unregistering an entity will save and remove it, and may cause lag spikes - bigger than if you registered one.
	 * 
	 * @param cre
	 * The registry entry to unregister.
	 */
	@SuppressWarnings("unchecked")
	public static void unregister(final CustomRegistryEntry cre) {
		if (!registryEntries.containsValue(cre)) return;
		final String paramString = cre.getName();
		try {
			((Set<MinecraftKey>) getPrivateStatic(EntityTypes.class, "d")).remove(new MinecraftKey(paramString));
			registryEntries.remove(cre);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (World w : Bukkit.getWorlds()) {
			for (org.bukkit.entity.Entity e : w.getEntities()) {
				Entity nmsEntity = ((CraftEntity)e).getHandle();
				if (nmsEntity instanceof EntityInsentient) if (CustomRegistry.registryEntries.containsKey(EntityTypes.b(nmsEntity))) {
					CustomRegistry.saveEntity((EntityInsentient) nmsEntity);
					e.remove();
				}
			}
		}
	}

	private static Object getPrivateStatic(final Class<?> clazz, final String f) throws Exception {
		Field field = clazz.getDeclaredField(f);
		field.setAccessible(true);
		return field.get(null);
	}

	protected static void saveEntity(EntityInsentient e) {
		try {
			final Chunk c = e.getBukkitEntity().getLocation().getChunk();
			final File cDir = new File("entityNBT"+System.getProperty("file.separator")+c.getWorld().getName()+System.getProperty("file.separator")+c.getX()+System.getProperty("file.separator")+c.getZ());
			cDir.mkdirs();

			final NBTTagCompound nbt = new NBTTagCompound();
			e.f(nbt);
			nbt.setString("cid", EntityTypes.b(e));
			e.getBukkitEntity().remove();

			NBTCompressedStreamTools.a(nbt, (OutputStream)(new FileOutputStream(new File(cDir, (cDir.list().length+1)+" - "+nbt.getString("cid")+".tbp"))));
		} catch (Exception e1) { e1.printStackTrace(); }
	}

	@SuppressWarnings("unchecked")
	protected static boolean loadEntity(NBTTagCompound nbt, World w) {
		if (!registryEntries.containsKey(nbt.getString("cid"))) return false;
		try {
			final Class<? extends EntityInsentient> clazz = (Class<? extends EntityInsentient>) registryEntries.get(nbt.getString("cid")).getCustomClass();
			final net.minecraft.server.v1_12_R1.World nmsW = ((CraftWorld)w).getHandle();
			EntityInsentient e = clazz.getConstructor(net.minecraft.server.v1_12_R1.World.class).newInstance(nmsW);
			NBTTagList pos = nbt.getList("Pos", 6);
			NBTTagList rot = nbt.getList("Rotation", 5);

			e.setPositionRotation(pos.f(0), pos.f(1), pos.f(2), rot.g(0), rot.g(1));
			nmsW.addEntity(e, SpawnReason.NATURAL);

			e.f(nbt);
			return true;
		} catch (Exception e1) { e1.printStackTrace(); }
		
		return false;
	}

	protected static void loadEntity(File entityFile) {
		try {
			String[] pathElements = entityFile.getAbsolutePath().split(System.getProperty("file.separator"));
			if (loadEntity(NBTCompressedStreamTools.a((InputStream)(new FileInputStream(entityFile))), Bukkit.getWorld(pathElements[pathElements.length-4]))) entityFile.delete();
		} catch (Exception e1) { e1.printStackTrace(); }
	}

	protected static boolean chunkHasCustomEntities(Chunk c) {
		final File cDir = new File("entityNBT"+System.getProperty("file.separator")+c.getWorld().getName()+System.getProperty("file.separator")+c.getX()+System.getProperty("file.separator")+c.getZ());
		if (cDir.mkdirs()) return false;
		else {
			if (cDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".tbp");
				}
			}).length <= 0) return false;
			else return true;
		}
	}
}
