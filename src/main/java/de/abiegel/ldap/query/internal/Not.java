package de.abiegel.ldap.query.internal;

import java.util.Arrays;
import java.util.List;

public interface Not extends Operation {
	public static final String NOT = "!";

	static Operation not(Token child) {
		return new Not() {
			
			@Override
			public String asString() {
				return operation(NOT);
			}

			@Override
			public List<Token> children() {
				return Arrays.asList(child);
			}

		};
	}
}
