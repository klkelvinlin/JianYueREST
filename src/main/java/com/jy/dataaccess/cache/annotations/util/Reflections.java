package com.jy.dataaccess.cache.annotations.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * @author wdong
 * 
 */
public abstract class Reflections {

	private static final int INITIAL_HASH = 7;

	private static final int MULTIPLIER = 31;

	/**
	 * <p>
	 * This method uses reflection to build a valid hash code.
	 * </p>
	 * 
	 * <p>
	 * It uses <code>AccessibleObject.setAccessible</code> to gain access to
	 * private fields. This means that it will throw a security exception if run
	 * under a security manager, if the permissions are not set up correctly. It
	 * is also not as efficient as testing explicitly.
	 * </p>
	 * 
	 * <p>
	 * Transient members will not be used, as they are likely derived fields,
	 * and not part of the value of the <code>Object</code>.
	 * </p>
	 * 
	 * <p>
	 * Static fields will not be tested. Superclass fields will be included.
	 * </p>
	 * 
	 * @param obj
	 *            the object to create a <code>hashCode</code> for
	 * @return the generated hash code, or zero if the given object is
	 *         <code>null</code>
	 */
	public static int reflectionHashCode(Object obj) {
		if (obj == null)
			return 0;

		Class<?> targetClass = obj.getClass();
		if (isArrayOfPrimitives(obj)) {
			// || ObjectUtils.isPrimitiveOrWrapper(targetClass)) {
			return ObjectUtils.nullSafeHashCode(obj);
		}

		if (targetClass.isArray()) {
			return reflectionHashCode((Object[]) obj);
		}

		if (obj instanceof Collection) {
			return reflectionHashCode((Collection<?>) obj);
		}

		if (obj instanceof Map) {
			return reflectionHashCode((Map<?,?>) obj);
		}

		// determine whether the object's class declares hashCode() or has a
		// superClass other than java.lang.Object that declares hashCode()
		Class<?> clazz = (obj instanceof Class) ? (Class<?>) obj : obj.getClass();
		Method hashCodeMethod = ReflectionUtils.findMethod(clazz, "hashCode",
				new Class[0]);

		if (hashCodeMethod != null) {
			return obj.hashCode();
		}

		// could not find a hashCode other than the one declared by
		// java.lang.Object
		int hash = INITIAL_HASH;

		try {
			while (targetClass != null) {
				Field[] fields = targetClass.getDeclaredFields();
				AccessibleObject.setAccessible(fields, true);

				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					int modifiers = field.getModifiers();

					if (!Modifier.isStatic(modifiers)
							&& !Modifier.isTransient(modifiers)) {
						hash = MULTIPLIER * hash
								+ reflectionHashCode(field.get(obj));
					}
				}
				targetClass = targetClass.getSuperclass();
			}
		} catch (IllegalAccessException exception) {
			// ///CLOVER:OFF
			ReflectionUtils.handleReflectionException(exception);
			// ///CLOVER:ON
		}

		return hash;
	}

	private static int reflectionHashCode(Collection<?> collection) {
		int hash = INITIAL_HASH;

		for (Iterator<?> i = collection.iterator(); i.hasNext();) {
			hash = MULTIPLIER * hash + reflectionHashCode(i.next());
		}

		return hash;
	}

	private static int reflectionHashCode(Map<?,?> map) {
		int hash = INITIAL_HASH;

		for (Iterator<?> i = map.entrySet().iterator(); i.hasNext();) {
			Map.Entry<?,?> entry =  (Map.Entry<?,?>)i.next();
			hash = MULTIPLIER * hash + reflectionHashCode(entry);
		}

		return hash;
	}

	private static int reflectionHashCode(Map.Entry<?,?> entry) {
		int hash = INITIAL_HASH;
		hash = MULTIPLIER * hash + reflectionHashCode(entry.getKey());
		hash = MULTIPLIER * hash + reflectionHashCode(entry.getValue());
		return hash;
	}

	private static int reflectionHashCode(Object[] array) {
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + reflectionHashCode(array[i]);
		}

		return hash;
	}

	public static boolean isArrayOfPrimitives(Object array) {
		boolean primitiveArray = false;

		if (array != null) {
			Class<?> clazz = array.getClass();

			primitiveArray = clazz.isArray()
					&& clazz.getComponentType().isPrimitive();
		}

		return primitiveArray;
	}
}
