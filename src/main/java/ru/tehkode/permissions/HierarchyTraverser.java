package ru.tehkode.permissions;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class created to simplify hierarchy traversal for entities
 */
public abstract class HierarchyTraverser<Return> {
	private final PermissionEntity start;
	private final String server;
	private final boolean traverseInheritance;

	public HierarchyTraverser(PermissionEntity entity, String server) {
		this(entity, server, true);
	}
	public HierarchyTraverser(PermissionEntity entity, String server, boolean traverseInheritance) {
		this.start = entity;
		this.server = server;
		this.traverseInheritance = traverseInheritance;
	}


	/**
	 * Performs a traversal of permissions hierarchy
	 *
	 * Ordering:
	 * For each entity (traversed depth-first):
	 * <ol>
	 *     <li>Chosen server</li>
	 *     <li>server inheritance for chosen server</li>
	 *     <li>Global scope</li>
	 * </ol>
	 *
	 * @return a value if any found
	 */
	public Return traverse() {
		LinkedList<PermissionEntity> entities = new LinkedList<>();
		Set<PermissionEntity> visited = new HashSet<>();
		entities.add(start);
		Return ret = null;
		while (!entities.isEmpty()) {
			PermissionEntity current = entities.removeFirst();
			// Circular inheritance detection
			if (visited.contains(current)) {
				if (current.isDebug()) {
					current.manager.getLogger().warning("Potential circular inheritance detected involving group " + current.getIdentifier() + " (when performing traversal for entity " + start + ")");
				}
				continue;
			}
			visited.add(current);

			// server-specific
			if (server != null) {
				ret = fetchLocal(current, server);
				if (ret != null) {
					break;
				}

				// server inheritance
				ret = traverseserverInheritance(current);
				if (ret != null) {
					break;
				}
			}
			// Global scope
			ret = fetchLocal(current, null);
			if (ret != null) {
				break;
			}

			// Add parents
			if (traverseInheritance) {
				List<PermissionGroup> parents = current.getParents(server);
				for (int i = parents.size() - 1; i >= 0; --i) { // Add parents to be traversed in order provided by getParents
					entities.addFirst(parents.get(i));
				}
			}
		}
		return ret;
	}

	/**
	 * Traverses server inheritance depth-first.
	 *
	 * @param entity Entity to perform local action on
	 * @return Any detected results
	 */
	private Return traverseserverInheritance(PermissionEntity entity) {
		List<String> serverInheritance = entity.manager.getServerInheritance(server);
		if (serverInheritance.size() > 0) {
			Deque<String> servers = new LinkedList<>(serverInheritance);
			Set<String> visitedservers = new HashSet<>();
			Return ret = null;
			while (!servers.isEmpty()) {
				String current = servers.removeFirst();
				if (visitedservers.contains(current)) {
					if (entity.isDebug()) {
						entity.manager.getLogger().warning("Potential circular inheritance detected with server inheritance for server " + current);
					}
					continue;
				}
				visitedservers.add(current);

				ret = fetchLocal(entity, current);
				if (ret != null) {
					break;
				}

				final List<String> nextLevel = entity.manager.getServerInheritance(current);
				for (int i = nextLevel.size() - 1; i >= 0; --i) {
					servers.add(nextLevel.get(i));
				}
			}
			return ret;
		}
		return null;
	}

	/**
	 * Collects the potential return value from a single entity
	 * @param entity Entity being checked in
	 * @param server server being checked in
	 * @return The value, or null if not present
	 */
	protected abstract Return fetchLocal(PermissionEntity entity, String server);
}
