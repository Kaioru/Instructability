package instructabilty.permission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Permissions {

	private final Set<String> permissions;

	public Permissions() {
		this.permissions = new HashSet<>();
	}

	public Set<String> get() {
		return Collections.unmodifiableSet(permissions);
	}

	public void add(String permission) {
		permissions.add(permission);
	}

	public void remove(String permission) {
		permissions.remove(permission);
	}

}
