package de.abiegel.ldap.query.internal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.directory.InvalidSearchFilterException;

/**
 * Base Operation
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

	default List<Token> childrenAsList(Stream<Token> children) {
		return children.collect(Collectors.toList());
	}

	default boolean isMultiValueOp() {
		return true;
	}

	/**
	 * Adds {@link Token} to Operation
	 * @param child, {@link Token} to add
	 * @throws UnsupportedOperationException, if {@link Operation} does not support to add former tokens,like {@link Not}
	 */
	default void add(Token child) {
		if (isMultiValueOp()) {
			children().add(child);
		}else{
			throw new UnsupportedOperationException("Not cannot have multiple values");
		}
	}
	
	default String operation(String op) {
		return "(" + op + childrenAsString() + ")";
	}
	
}
