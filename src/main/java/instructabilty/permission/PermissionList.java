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

	public Map<String, Permissions> getUserPermissions() {
		return userPermissions;
	}

	public Map<String, Permissions> getRolePermissions() {
		return rolePermissions;
	}

	public boolean checkPermissions(IUser user, IGuild guild, CommandPermission perm) {
		return userPermissions.get(user.getID())
				.getPermissions()
				.stream()
				.anyMatch(p -> {
					LinkedList ll = new LinkedList(Arrays.asList(p.split(".")));
					return perm.checkPermission(ll);
				})
				||
				user.getRolesForGuild(guild)
						.stream()
						.anyMatch(r -> rolePermissions.get(r)
								.getPermissions()
								.stream()
								.anyMatch(p -> {
									LinkedList ll = new LinkedList(Arrays.asList(p.split(".")));
									return perm.checkPermission(ll);
								}));
	}

}
