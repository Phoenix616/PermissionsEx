package ru.tehkode.permissions.backends.memory;

import com.google.common.collect.Sets;
import ru.tehkode.permissions.PermissionsGroupData;
import ru.tehkode.permissions.PermissionsUserData;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Data for in-memory permissions
 */
public class MemoryData implements PermissionsGroupData, PermissionsUserData {
	private String name;
	private final HashMap<String, String> serverPrefix = new HashMap<>();
	private final HashMap<String, String> serverSuffix = new HashMap<>();
	private final HashMap<String, List<String>> serversPermissions = new HashMap<>();
	private final Map<String, Map<String, String>> serversOptions = new HashMap<>();
	private final Map<String, List<String>> parents = new HashMap<>();
	private final Map<String, Boolean> defaultVals = new HashMap<>();

	public MemoryData(String name) {
		this.name = name;
	}

	@Override
	public List<String> getParents(String serverName) {
		return parents.containsKey(serverName) ? parents.get(serverName) : Collections.<String>emptyList();
	}

	@Override
	public void setParents(List<String> parents, String serverName) {
		this.parents.put(serverName, Collections.unmodifiableList(parents));
	}

	@Override
	public void load() {
	}

	@Override
	public String getIdentifier() {
		return name;
	}

	@Override
	public List<String> getPermissions(String serverName) {
		return serversPermissions.containsKey(serverName) ? serversPermissions.get(serverName)
				: Collections.<String>emptyList();
	}

	@Override
	public void setPermissions(List<String> permissions, String worldName) {
		serversPermissions.put(worldName, Collections.unmodifiableList(permissions));
	}

	@Override
	public Map<String, List<String>> getPermissionsMap() {
		return Collections.unmodifiableMap(serversPermissions);
	}

	@Override
	public Set<String> getServers() {
		return Sets.union(serversOptions.keySet(), serverPrefix.keySet());
	}

	@Override
	public String getOption(String option, String serverName) {
		if (serversOptions.containsKey(serverName)) {
			Map<String, String> worldOption = serversOptions.get(serverName);
			if (worldOption.containsKey(option)) {
				return worldOption.get(option);
			}
		}
		return null;
	}

	@Override
	public void setOption(String option, String value, String serverName) {
		Map<String, String> serverOptions = serversOptions.get(serverName);
		if (serverOptions == null) {
			serverOptions = new HashMap<>();
			serversOptions.put(serverName, serverOptions);
		}
		serverOptions.put(option, value);
	}

	@Override
	public Map<String, String> getOptions(String serverName) {
		return serversOptions.containsKey(serverName) ? serversOptions.get(serverName)
				: Collections.<String, String>emptyMap();
	}

	@Override
	public Map<String, Map<String, String>> getOptionsMap() {
		return Collections.unmodifiableMap(serversOptions);
	}

	@Override
	public boolean isVirtual() {
		return true;
	}

	@Override
	public void save() {

	}

	@Override
	public void remove() {

	}

	@Override
	public Map<String, List<String>> getParentsMap() {
		return Collections.unmodifiableMap(parents);
	}

	@Override
	public boolean setIdentifier(String identifier) {
		this.name = identifier;
		return true;
	}
}
