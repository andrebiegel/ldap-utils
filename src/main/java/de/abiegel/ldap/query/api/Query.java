package de.abiegel.ldap.query.api;

import java.util.stream.Stream;

import de.abiegel.ldap.query.internal.And;
import de.abiegel.ldap.query.internal.Attribute;
import de.abiegel.ldap.query.internal.Exists;
import de.abiegel.ldap.query.internal.HasNot;
import de.abiegel.ldap.query.internal.Not;
import de.abiegel.ldap.query.internal.Operation;
import de.abiegel.ldap.query.internal.Or;
import de.abiegel.ldap.query.internal.Token;
/**
 * API Access 
 * @author abiegel
 *
 */
public final class Query {

	/**
	 * outer query wrapper for an {@link Attribute}
	 * @param child
	 * @return
	 */
	public static Attribute query(Attribute child) {
		return child;
	}

	/**
	 * outer query wrapper for an {@link Operation}
	 * @param child
	 * @return
	 */
	public static Operation query(Operation child) {
		return child;
	}


	/**
	 * public factory method for an {@link And} Operation
	 * @param children
	 * @return
	 */
	public static Operation and(Token... children) {
		return And.and(children);
	}
	public static Operation and(Stream<Token> children) {
		return And.and(children);
	}

	/**
	 * public factory method for an {@link Or} Operation
	 * @param children
	 * @return
	 */
	public static Operation or(Token... children) {
		return Or.or(children);
	}
	public static Operation or(Stream<Token> children) {
		return Or.or(children);
	}

	/**
	 * public factory method for an {@link Not} Operation
	 * @param children
	 * @return
	 */

	public static Operation not(Token children) {
		return Not.not(children);
	}

	/**
	 * public factory method for an {@link Attribute} 
	 * @param children
	 * @return
	 */
	public static Attribute attr(String key,String value) {
		return Attribute.attr(key, value);
	}
	
	/**
	 * public factory method for an {@link Exists} 
	 * @param key, Attribute key
	 * @return
	 */
	public static Attribute exists(String key) {
		return Exists.exists(key);
	}

	public static Operation hasNot(String... keys) {
		return HasNot.hasNot(keys);
	}

	
}
