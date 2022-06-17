package xyz.dylanlogan.ancientwarfare.structure.gui;

import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplateClient;

import java.util.Comparator;
import java.util.Locale;

public class ComparatorStructureTemplateClient implements Comparator<StructureTemplateClient> {

    private String filterText = "";

    public void setFilterText(String tex) {
        this.filterText = tex;
    }

    @Override
    public int compare(StructureTemplateClient arg0, StructureTemplateClient arg1) {
        if(arg0 == arg1){
            return 0;
        }
        if (arg0 == null && arg1 != null) {
            return 1;
        } else if (arg0 != null && arg1 == null) {
            return -1;
        }
        String a = arg0.name.toLowerCase(Locale.ENGLISH);
        String b = arg1.name.toLowerCase(Locale.ENGLISH);
        String tex = filterText.toLowerCase(Locale.ENGLISH);
        if (a.startsWith(tex) && b.startsWith(tex)) {
            return arg0.name.compareTo(arg1.name);
        } else if (a.startsWith(tex)) {
            return -1;
        } else if (b.startsWith(tex)) {
            return 1;
        }
        return a.compareTo(b);
    }
}
