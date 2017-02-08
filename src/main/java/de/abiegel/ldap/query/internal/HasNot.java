package de.abiegel.ldap.query.internal;

import static de.abiegel.ldap.query.internal.Not.not;
import static de.abiegel.ldap.query.internal.Exists.exists;
public interface HasNot extends Attribute {

	static Operation hasNot(String key) {
		return not(exists(key));	
	}
}
