package me.cybermaxke.elementarrows.forge.v1710.enchant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.enchant.Enchant;
import me.cybermaxke.elementarrows.common.enchant.EnchantFactory;
import me.cybermaxke.elementarrows.forge.v1710.util.Fields;

public class FEnchantFactory implements EnchantFactory {
	private final Map<Integer, FEnchant> items0 = new HashMap<Integer, FEnchant>();
	private final Map<String, FEnchant> items1 = new HashMap<String, FEnchant>();

	public FEnchantFactory() throws Exception {
		Field[] fields0 = Fields.findFields(Enchantment.class, Enchantment.class);
		Field[] fields1 = Fields.findFields(Enchant.class, Enchant.class);

		/**
		 * The current field.
		 */
		int fieldIndex = 0;

		for (int i = 0; i < fields0.length; i++) {
			Field field = fields0[i];
			field.setAccessible(true);

			if (Modifier.isStatic(field.getModifiers())) {
				Enchantment enchantment = (Enchantment) field.get(null);

				if (enchantment != null) {
					String id = getIdFor(enchantment);
					FEnchant type = new FEnchant(id, enchantment);

					this.items0.put(enchantment.effectId, type);
					this.items1.put(id, type);

					if (fieldIndex > fields1.length) {
						continue;
					}

					Field field1 = fields1[fieldIndex++];

					Field field0 = Field.class.getDeclaredField("modifiers");
					field0.setAccessible(true);
					field0.set(field, field.getModifiers() & ~Modifier.FINAL);

					field1.setAccessible(true);
					field1.set(null, type);
				}
			}
		}
	}

	@Override
	public FEnchant typeById(String id) {
		Preconditions.checkNotNull(id);

		int index = id.indexOf(':');
		if (index != -1) {
			id = id.substring(index + 1, id.length());
		}
		id = id.toLowerCase();

		FEnchant enchant = this.items0.get(id.toLowerCase());
		if (enchant != null) {
			return enchant;
		}

		Enchantment[] types = Enchantment.enchantmentsList;

		for (int i = 0; i < types.length; i++) {
			Enchantment type0 = types[i];

			if (type0 != null) {
				String id0 = getIdFor(type0);

				if (id0.equals(id)) {
					FEnchant type1 = new FEnchant(id0, type0);

					this.items1.put(id0, type1);
					this.items0.put(i, type1);

					return type1;
				}
			}
		}

		return null;
	}

	@Override
	public FEnchant typeById(int internalId) {
		FEnchant type = this.items1.get(internalId);

		if (type != null) {
			return type;
		}

		if (internalId >= Enchantment.enchantmentsList.length) {
			return null;
		}

		Enchantment enchant = Enchantment.enchantmentsList[internalId];
		if (enchant != null) {
			String id0 = getIdFor(enchant);
			FEnchant type0 = new FEnchant(id0, enchant);

			this.items0.put(internalId, type0);
			this.items1.put(id0, type0);

			return type0;
		}

		return null;
	}

	/**
	 * Gets the id for the minecraft potion.
	 * 
	 * @param potion the potion
	 * @return the id
	 */
	public static String getIdFor(Enchantment enchantment) {
		return enchantment.getName().replaceFirst("enchantment.", "").replaceAll(".", "");
	}

}