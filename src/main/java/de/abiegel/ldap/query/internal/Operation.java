package de.abiegel.ldap.query.internal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * &(() ()())
 * 
 * @author usiabiegel
 *
 */
public interface Operation extends Token {
	default List<Token> children() {
		return Collections.emptyList();
	}

	default String childrenAsString() {
		return children().stream().map(Token::asString).collect(Collectors.joining());
	}

	default String operation(String op) {
		return "(" + op + childrenAsString() + ")";
	}
}
