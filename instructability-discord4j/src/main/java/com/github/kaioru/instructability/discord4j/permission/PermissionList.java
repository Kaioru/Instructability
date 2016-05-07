package com.github.kaioru.instructability.discord4j.permission;

import com.github.kaioru.instructability.discord4j.Discord4JCommandPermission;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;

public class PermissionList {

	private final Map<String, Permissions> userPermissions;
	private final Map<String, Permissions> rolePermissions;

	public PermissionList() {
		this.userPermissions = new HashMap();
		this.rolePermissions = new HashMap();
	}

	public Permissions getUserPermissions(String id) {
		if (!userPermissions.containsKey(id))
			userPermissions.put(id, new Permissions());

		return userPermissions.get(id);
	}

	public Permissions getRolePermissions(String id) {
		if (!rolePermissions.containsKey(id))
			rolePermissions.put(id, new Permissions());

		return rolePermissions.get(id);
	}

	public boolean checkPermissions(IUser user, IGuild guild, Discord4JCommandPermission perm) {
		Predicate<String> func = (s) -> {
			LinkedList ll = new LinkedList();

			if (s.contains(".")) ll.addAll(Arrays.asList(s.split(".")));
			else ll.add(s);

			return perm.checkPermission(ll);
		};

		return getUserPermissions(user.getID())
				.get()
				.stream()
				.anyMatch(func)
				||
				user.getRolesForGuild(guild)
						.stream()
						.anyMatch(r -> getRolePermissions(r.getID())
								.get()
								.stream()
								.anyMatch(func));
	}

}