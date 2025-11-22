package thor.catacombs.info.attributes;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;
import java.util.stream.Collectors;

public record AttributeRegistry(Map<AttributeHolderType, Collection<ConfigAttribute<?>>> attributes) {
    private Set<ConfigAttribute<?>> getAttributesFor(String attributeHolder, boolean isDefault) {
        return attributes.get(AttributeHolderType.valueOf(attributeHolder.toUpperCase())).stream().filter(attribute -> isDefault == attribute.isDefault()).collect(Collectors.toSet());
    }

    public Set<ConfigAttribute<?>> getDefaultAttributesFor(String holderType) {
        return getAttributesFor(holderType, true);
    }

    public Set<ConfigAttribute<?>> getAttributesFor(String attributeHolder) {
        return new HashSet<>(attributes.get(AttributeHolderType.valueOf(attributeHolder.toUpperCase())));
    }

    public Set<ConfigAttribute<?>> getNecessaryAttributesFor(String holderType) {
        return getAttributesFor(holderType, false);
    }

    public Set<String> getHolders() {
        return attributes.keySet().stream().map(AttributeHolderType::getName).collect(Collectors.toSet());
    }

    public <T> T getAttribute(AttributeHolderType holderType, AttributeType type, YamlConfiguration configuration) {
        ConfigAttribute<T> attribute = getAttribute(holderType, type);
        return attribute.getValue(configuration);
    }

    public <T> boolean containsValue(AttributeHolderType holderType, AttributeType type, YamlConfiguration configuration) {
        try {
            ConfigAttribute<T> attribute = getAttribute(holderType, type);
            return attribute.containsValue(configuration);
        }
        catch (Exception e) {
            return false;
        }
    }

    private <T> ConfigAttribute<T> getAttribute(AttributeHolderType holderType, AttributeType type) {
        Optional<ConfigAttribute<?>> optional = attributes.get(holderType).stream().filter(attribute -> attribute.getType() == type).findFirst();
        try {
            return  (ConfigAttribute<T>) optional.get();
        } catch (Exception e) {
            throw new RuntimeException("incompatible attribute types!");
        }
    }

    public static class Builder {
        private final Map<AttributeHolderType, Collection<ConfigAttribute<?>>> attributes = new HashMap<>();

        public void addAttribute(AttributeHolderType type, Collection<ConfigAttribute<?>> attributes) {
            this.attributes.put(type, attributes);
        }

        public AttributeRegistry build() {
            return new AttributeRegistry(attributes);
        }
    }
}
