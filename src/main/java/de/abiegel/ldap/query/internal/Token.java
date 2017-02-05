package de.abiegel.ldap.query.internal;

/**
 * Base Token for all Languages Elements
 * 
 * @author usiabiegel
 *
 */

public interface Token {
	/**
	 * Every Elements has a String representation
	 * 
	 * @return
	 */
	default String asString(){
		return "";
	}
}
