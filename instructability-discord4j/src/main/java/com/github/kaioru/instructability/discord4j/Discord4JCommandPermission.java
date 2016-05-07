package com.github.kaioru.instructability.discord4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Discord4JCommandPermission {

	private final String name;
	private final List<Discord4JCommandPermission> subPermissions;

	public Discord4JCommandPermission(String permission) {
		this(new LinkedList<>(Arrays.asList(permission.split("\\."))));
	}

	public Discord4JCommandPermission(LinkedList<String> permission) {
		this.name = permission.removeFirst();
		this.subPermissions = new ArrayList<>();

		if (permission.size() > 0)
			this.subPermissions.add(new Discord4JCommandPermission(permission));
	}

	public Discord4JCommandPermission(String name, List<Discord4JCommandPermission> subPermissions) {
		this.name = name;
		this.subPermissions = subPermissions;
	}

	public String getName() {
		return name;
	}

	public List<Discord4JCommandPermission> getSubPermissions() {
		return subPermissions;
	}

	public boolean checkPermission(LinkedList<String> permissions) {
		String first = permissions.removeFirst();

		if (first.equals(name) || first.equals("*")) {
			if (permissions.size() == 0)
				return true;
			else
				return subPermissions.stream().anyMatch(p -> p.checkPermission(permissions));
		}
		return false;
	}

}
