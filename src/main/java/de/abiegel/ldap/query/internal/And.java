package de.abiegel.ldap.query.internal;

import java.util.Arrays;
import java.util.List;

public interface And extends Operation {
	public static final String AND = "&";

	static And and(Token... children) {
		return new And() {
			@Override
			public String asString() {
				return operation(AND);
			}

			@Override
			public List<Token> children() {
				return Arrays.asList(children);
			}
		};
	}
}
