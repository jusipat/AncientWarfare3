package xyz.dylanlogan.ancientwarfare.structure.network;

import io.netty.buffer.ByteBuf;
import xyz.dylanlogan.ancientwarfare.core.network.PacketBase;
import xyz.dylanlogan.ancientwarfare.core.util.StringTools;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplateManagerClient;

public class PacketStructureRemove extends PacketBase {

    String structureName;

    public PacketStructureRemove() {
        // receive side constructor
    }

    public PacketStructureRemove(String name) {
        structureName = name;
    }

    @Override
    protected void writeToStream(ByteBuf data) {
        StringTools.writeString(data, structureName);
    }

    @Override
    protected void readFromStream(ByteBuf data) {
        structureName = StringTools.readString(data);
    }

    @Override
    protected void execute() {
        StructureTemplateManagerClient.instance().removeTemplate(structureName);
    }

}
