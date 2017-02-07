package de.abiegel.ldap.query.internal;

/**
 * Base Token for all Language Elements
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
