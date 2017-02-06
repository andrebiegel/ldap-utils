package de.abiegel.ldap.modification.api;

import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class ModificationsTest {

	private static final String VALUE = "value";
	private static final String KEY = "KEY";

	@Test
	public void testReplace() {
		 // Mixing Junit 5 and AssertJ !!
		ModificationItem[] mods = new Modifications().replace().attribute(KEY, VALUE).build();
		org.assertj.core.api.Assertions.assertThat(mods).hasOnlyOneElementSatisfying(x -> Assertions.assertAll(() -> {
			org.assertj.core.api.Assertions.assertThat(x.getModificationOp()).isEqualTo(DirContext.REPLACE_ATTRIBUTE);
			org.assertj.core.api.Assertions.assertThat(x.getAttribute().getID()).isEqualTo(KEY);
			org.assertj.core.api.Assertions.assertThat((String) x.getAttribute().get()).isEqualTo(VALUE);
		}));
	}

	@Test
	public void testAdd() {
		ModificationItem[] mods = new Modifications().add().attribute(KEY, VALUE).build();
		org.assertj.core.api.Assertions.assertThat(mods)
				.hasOnlyOneElementSatisfying(x -> org.assertj.core.api.Assertions.assertThat(x.getModificationOp())
						.isEqualTo(DirContext.ADD_ATTRIBUTE));

	}

	@Test
	public void testRemove() {
		ModificationItem[] mods = new Modifications().remove().attribute(KEY).build();
		org.assertj.core.api.Assertions.assertThat(mods)
				.hasOnlyOneElementSatisfying(x -> org.assertj.core.api.Assertions.assertThat(x.getModificationOp())
						.isEqualTo(DirContext.REMOVE_ATTRIBUTE));

	}

}
