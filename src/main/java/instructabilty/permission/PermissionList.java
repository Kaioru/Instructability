package instructabilty.permission;

import instructabilty.command.CommandPermission;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

	public boolean checkPermissions(IUser user, IGuild guild, CommandPermission perm) {
		return getUserPermissions(user.getID())
				.getPermissions()
				.stream()
				.anyMatch(p -> {
					LinkedList ll = new LinkedList(Arrays.asList(p.split(".")));
					return perm.checkPermission(ll);
				})
				||
				user.getRolesForGuild(guild)
						.stream()
						.anyMatch(r -> getRolePermissions(r.getID())
								.getPermissions()
								.stream()
								.anyMatch(p -> {
									LinkedList ll = new LinkedList(Arrays.asList(p.split(".")));
									return perm.checkPermission(ll);
								}));
	}

}
