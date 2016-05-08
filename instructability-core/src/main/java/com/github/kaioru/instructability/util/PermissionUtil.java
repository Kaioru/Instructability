package com.github.kaioru.instructability.util;

import com.github.kaioru.instructability.Defaults;

import java.util.Arrays;
import java.util.Iterator;

public class PermissionUtil {

	public static boolean checkPermission(String permission, String required) {
		if (required.equals(Defaults.PERMISSION))
			return true;

		Iterator<String> i1 = Arrays.asList(permission.concat(".").split("\\.")).iterator();
		Iterator<String> i2 = Arrays.asList(required.concat(".").split("\\.")).iterator();

		while (i2.hasNext()) {
			String p, r;

			if (i1.hasNext()) {
				p = i1.next();
				r = i2.next();

				if (p.equalsIgnoreCase(r) || p.equalsIgnoreCase("*"))
					if (!i1.hasNext())
						return true;
			} else break;
		}

		return i1.hasNext();
	}

}
