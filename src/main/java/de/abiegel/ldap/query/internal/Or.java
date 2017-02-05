package de.abiegel.ldap.query.internal;

import java.util.Arrays;
import java.util.List;

public interface Or extends Operation {
	public static final String OR = "|";

	static Operation or(Token... children) {
		return new Or() {
			@Override
			public String asString() {
				return operation(OR);
			}

			@Override
			public List<Token> children() {
				return Arrays.asList(children);
			}
		};
	}

}
