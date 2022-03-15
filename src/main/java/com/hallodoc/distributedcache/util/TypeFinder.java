package com.hallodoc.distributedcache.util;

public final class TypeFinder {

	private TypeFinder(){
		super();
	}
	public static Class<?> find(String value) {
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return Boolean.class;
		}
		try {
			Double.valueOf(value);
			return Double.class;
		} catch (NumberFormatException ex) {
			try {
				Integer.valueOf(value);
				return Integer.class;
			} catch (NumberFormatException ex1) {
				return String.class;
			}
		}

	}
}
