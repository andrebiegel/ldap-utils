package de.abiegel.ldap.modification.api;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * fluent ldap util to build Modifications
 * 
 * @author abiegel
 *
 */
public class Modifications {

	private static Modifications instance;
	private int type;

	// yyyyMMddHHmmss.SSSZ or yyyyMMddHHmmss.SSS'Z' both is functional! on OpenLdap
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS'Z'");
	private List<ModificationItem> items = new ArrayList<>();

	public Modifications replace() {

		this.setType(DirContext.REPLACE_ATTRIBUTE);
		return this;
	}

	public Modifications add() {
		this.setType(DirContext.ADD_ATTRIBUTE);
		return this;
	}

	public Modifications remove() {
		this.setType(DirContext.REMOVE_ATTRIBUTE);
		return this;
	}

	private void setType(int type) {
		this.type = type;
	}
	
	public Modifications attribute(Attribute attr) {

		this.items.add(new ModificationItem(type, attr));
		return this;
	}

	
	public Modifications attribute(String name) {

		this.items.add(new ModificationItem(type, new BasicAttribute(name)));
		return this;
	}

	public Modifications attribute(String name, String value) {

		this.items.add(new ModificationItem(type, new BasicAttribute(name, value)));
		return this;
	}

	public Modifications attribute(String name, byte[] value) {

		this.items.add(new ModificationItem(type, new BasicAttribute(name, value)));
		return this;
	}

	public Modifications attribute(String name, Instant value) {

		attribute(name, formatter.format(ZonedDateTime.from(value.atZone(ZoneId.of("UTC")))));
		return this;
	}

	public ModificationItem[] build() {
		return this.items.toArray(new ModificationItem[items.size()]);

	}

	@Override
	public String toString() {
		return "LdapModifications [items=" + items + "]";
	}

	public Modifications attribute(String name, Queue<String> values) {
		String elem =  values.remove();
		BasicAttribute attr = new BasicAttribute(name, elem);
		values.stream().forEach(it-> attr.add(it));
		this.items.add(new ModificationItem(type, attr));
		return this;
	}
}
