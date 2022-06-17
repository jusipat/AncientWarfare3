package xyz.dylanlogan.ancientwarfare.structure.api;

public interface StructureContentPlugin {

    /**
     * implementing classes should use this callback to register any
     * block handlers with the passed in manager
     */
    public void addHandledBlocks(IStructurePluginManager manager);

    /**
     * implementing classes should use this callback to register any
     * entity handlers with the passed in manager
     */
    public void addHandledEntities(IStructurePluginManager manager);

}
