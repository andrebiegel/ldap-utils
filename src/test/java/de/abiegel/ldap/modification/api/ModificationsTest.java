package de.abiegel.ldap.modification.api;

import javax.naming.directory.ModificationItem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;


public class ModificationsTest {

	@Test
	public void testReplace() {
	
		ModificationItem[] mods = new Modifications().replace().attribute("KEY", "value").build();
	}

	@Test
	public void testAdd() {
		ModificationItem[] mods = new Modifications().add().attribute("KEY", "value").build();
	}

	@Test
	public void testRemove() {
		ModificationItem[] mods = new Modifications().remove().attribute("KEY").build();
	}

}
