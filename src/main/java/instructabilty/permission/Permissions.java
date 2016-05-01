package instructabilty.permission;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

	private final List<String> permissions;

	public Permissions() {
		this.permissions = new ArrayList<>();
	}

	public List<String> getPermissions() {
		return permissions;
	}
}
