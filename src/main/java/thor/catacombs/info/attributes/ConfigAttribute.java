package thor.catacombs.info.attributes;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class ConfigAttribute<T> {
    private final Collection<String> suggestValues;
    private boolean isDefault;
    private final Predicate<T> validation;
    private final T defaultValue;
    private final AttributeType type;
    public ConfigAttribute(AttributeType type, Collection<String> suggestValues, Predicate<T> validation) {
        this(type, suggestValues, validation, null);
        isDefault=false;
    }
    public ConfigAttribute(AttributeType type, Collection<String> suggestValues, Predicate<T> validation, T defaultValue) {
        this.suggestValues=suggestValues;
        this.validation=validation;
        this.defaultValue=defaultValue;
        this.type=type;
        isDefault = true;
    }
    public Collection<String> getSuggestValues() {
        return Collections.unmodifiableCollection(suggestValues);
    }
    public boolean containsValue(YamlConfiguration configuration) {
        Object value = configuration.get(type.getName());
        return value != null;
    }
    public boolean isDefault() {
        return isDefault;
    }
    public AttributeType getType() {
        return type;
    }
    public T getValue(YamlConfiguration configuration) {
        if (defaultValue!=null) {
            configuration.addDefault(type.getName(), defaultValue);
        }
        Object valueObj = configuration.get(type.getName());
        if (valueObj==null) throw new RuntimeException(type.getName()+" attribute can't be null!");
        T value = (T)valueObj;
        if (!validation.test(value)) throw new RuntimeException("The value "+value+" of attribute "+type.getName()+" is not valid!");
        return value;
    }
}
