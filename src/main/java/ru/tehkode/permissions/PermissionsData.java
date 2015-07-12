package ru.tehkode.permissions;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionsData {
	/**
	 * Preload data from entity
	 */
	public void load();

	/**
	 * Returns the current identifier of the user
	 * @return
	 */
	public String getIdentifier();

	/**
	 * Returns all permissions for specified world
	 *
	 * @param serverName
	 * @return
	 */
	public List<String> getPermissions(String serverName);

	/**
	 * Set permissions for specified world
	 *
	 * @param permissions
	 * @param worldName
	 */
	public void setPermissions(List<String> permissions, String worldName);

	/**
	 * Returns ALL permissions for each world
	 *
	 * @return
	 */
	public Map<String, List<String>> getPermissionsMap();

	/**
	 * Returns worlds where entity has permissions/options
	 *
	 * @return
	 */
	public Set<String> getServers();

	/**
	 * Returns option value in specified worlds.
	 * null if option is not defined in that world
	 *
	 * @param option
	 * @param serverName
	 * @return
	 */
	public String getOption(String option, String serverName);

	/**
	 * Sets option value in specified world
	 *
	 * @param option
	 * @param value
	 * @param serverName
	 */
	public void setOption(String option, String value, String serverName);

	/**
	 * Returns all options in specified world
	 *
	 * @param serverName
	 * @return
	 */
	public Map<String, String> getOptions(String serverName);

	/**
	 * Returns ALL options in each world
	 *
	 * @return
	 */
	public Map<String, Map<String, String>> getOptionsMap();

	/**
	 * Return the parent groups of a user or group
	 *
	 * @param serverName World or null for common
	 * @return Unmodifiable list of parents
	 */
	public List<String> getParents(String serverName);

	/**
	 * Set parent groups of a user or group
	 *
	 * @param parents New list of parents
	 * @param serverName World name or null for common
	 */
	public void setParents(List<String> parents, String serverName);

	/**
	 * Returns true if this User/Group exists only in server memory
	 *
	 * @return
	 */
	public boolean isVirtual();

	/**
	 * Commit data to backend
	 */
	public void save();

	/**
	 * Completely remove data from backend
	 */
	public void remove();

	/**
	 * Return map of parents for all worlds
	 *
	 * @return Parents for all worlds
	 */
	public Map<String,List<String>> getParentsMap();
}
