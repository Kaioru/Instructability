package com.github.kaioru.instructability.permission;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PermissionRegistry {

	private final Map<String, Permissions> permissions;

	public PermissionRegistry() {
		this.permissions = new HashMap<>();
	}

	public Map<String, Permissions> getAllPermissions() {
		return Collections.unmodifiableMap(permissions);
	}

	public Permissions getPermissions(String key) {
		if (!permissions.containsKey(key))
			permissions.put(key, new Permissions());
		return permissions.get(key);
	}

}
