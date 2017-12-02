package fr.tenebrae;

import org.bukkit.entity.EntityType;

final public class CustomRegistryEntry {

	private String name;
	private int id;
	private EntityType entityType;
	private Class<?> nmsClass;
	private Class<?> customClass;

	public CustomRegistryEntry(String name, int id, EntityType entityType, Class<?> nmsClass, Class<?> customClass) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.nmsClass = nmsClass;
		this.customClass = customClass;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public Class<?> getNMSClass() {
		return nmsClass;
	}

	public Class<?> getCustomClass() {
		return customClass;
	}
}