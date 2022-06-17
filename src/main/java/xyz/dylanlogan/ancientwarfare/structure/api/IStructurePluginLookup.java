package xyz.dylanlogan.ancientwarfare.structure.api;

public interface IStructurePluginLookup {
    public String getPluginNameFor(Class<? extends TemplateRule> clz);
}
