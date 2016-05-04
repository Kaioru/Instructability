package instructability.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandPermission {

	private final String name;
	private final List<CommandPermission> subPermissions;

	public CommandPermission(String permission) {
		this(new LinkedList<>(Arrays.asList(permission.split("."))));
	}

	public CommandPermission(LinkedList<String> permission) {
		this.name = permission.removeFirst();
		this.subPermissions = new ArrayList<>();

		if (permission.size() > 0)
			this.subPermissions.add(new CommandPermission(permission));
	}

	public CommandPermission(String name, List<CommandPermission> subPermissions) {
		this.name = name;
		this.subPermissions = subPermissions;
	}

	public String getName() {
		return name;
	}

	public List<CommandPermission> getSubPermissions() {
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
