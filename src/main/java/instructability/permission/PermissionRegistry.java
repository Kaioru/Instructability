package instructability.permission;

import java.util.HashMap;
import java.util.Map;

public class PermissionRegistry {

	private Map<String, PermissionList> permissions = new HashMap<>();

	public PermissionList getForGuild(String guild) {

		if (!permissions.containsKey(guild))
			permissions.put(guild, new PermissionList());

		return permissions.get(guild);
	}

}
