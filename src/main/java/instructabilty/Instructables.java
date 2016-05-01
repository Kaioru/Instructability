package instructabilty;

import instructabilty.command.CommandRegistry;
import instructabilty.permission.PermissionRegistry;

public class Instructables {

    private final static CommandRegistry reg = new CommandRegistry();
    private final static PermissionRegistry preg = new PermissionRegistry();

    public static CommandRegistry getRegistry() {
        return reg;
    }

    public static PermissionRegistry getPermissionRegistry() {
        return preg;
    }

}
