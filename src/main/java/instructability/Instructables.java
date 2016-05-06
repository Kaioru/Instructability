package instructability;

import instructability.command.CommandRegistry;
import instructability.permission.PermissionRegistry;

public class Instructables {

    private static CommandRegistry reg = new CommandRegistry();
    private static PermissionRegistry preg = new PermissionRegistry();

    public static CommandRegistry getRegistry() {
        return reg;
    }

    public static void setRegistry(CommandRegistry reg) {
        Instructables.reg = reg;
    }

    public static PermissionRegistry getPermissionsRegistry() {
        return preg;
    }

    public static void setPermissionsRegistry(PermissionRegistry preg) {
        Instructables.preg = preg;
    }

}
