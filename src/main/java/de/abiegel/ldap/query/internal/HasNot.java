package de.abiegel.ldap.query.internal;

import static de.abiegel.ldap.query.internal.Exists.exists;
import static de.abiegel.ldap.query.internal.Not.not;

import java.util.stream.Stream;
public interface HasNot extends Attribute {

	static Operation hasNot(String... keys) {
		return not(And.and(Stream.of(keys).map(key->exists(key))));	
	}
}
