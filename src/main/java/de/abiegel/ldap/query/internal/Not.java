package de.abiegel.ldap.query.internal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Not extends Operation {
	public static final String NOT = "!";

	static Operation not(Token child) {
		return new Not() {
			
			@Override
			public boolean isMultiValueOp() {
				return false;
			}

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
