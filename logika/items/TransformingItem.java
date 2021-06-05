package logika.items;

import logika.people.MainCharacter;

/**
 * Třída předmětu, který se po použití přetvoří na jiný předmět. K tomu je vyždadován propis hlavní postavy
 */
public class TransformingItem extends Item implements Usable {

    private final Item toTransform;

    /**
     * Konstruktor bez propsání hlavní postavy<br>
     * <strong>Je potřeba, aby se předmět dostal do inventáře pomocí příkazu seber</strong>
     * @param name jméno předmětu
     * @param transformTo věc, v kterou by se měla věc přetvořit
     */
    public TransformingItem(String name, Item transformTo) {
        super(name);
        this.toTransform = transformTo;
    }

    /**
     * Konstruktor pro použití, kde se věc nebude sbírat do inventáře příkazem seber
     * @param name jméno předmětu
     * @param toTransform věc, v kterou by se měla věc přetvořit
     * @param character propsání hlavní postavy
     */
    public TransformingItem(String name, Item toTransform, MainCharacter character) {
        super(name, character);
        this.toTransform = toTransform;
    }


    @Override
    public String use() {
        //pokud je možné, propiš hru - předmět byl sebrán příkazem seber
        if (this.getHra() != null) toTransform.setHra(this.getHra());

        this.getCharacter().getInventory().add(toTransform);
        this.getCharacter().getInventory().remove(this);
        return "Věc transformována";
    }
}
