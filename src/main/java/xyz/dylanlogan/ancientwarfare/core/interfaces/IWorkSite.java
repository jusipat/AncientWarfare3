package xyz.dylanlogan.ancientwarfare.core.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import xyz.dylanlogan.ancientwarfare.core.config.AWCoreStatics;
import xyz.dylanlogan.ancientwarfare.core.interfaces.ITorque.ITorqueTile;
import xyz.dylanlogan.ancientwarfare.core.upgrade.WorksiteUpgrade;

import java.util.EnumSet;

public interface IWorkSite extends ITorqueTile {

    /**
     * workers should call this before calling doWork() to make sure that the site
     * actually has work to do.
     */
    public boolean hasWork();

    /**
     * can be called by a worker if hasWork() returns true.
     */
    public void addEnergyFromWorker(IWorker worker);

    public void addEnergyFromPlayer(EntityPlayer player);

    /**
     * called by workers to validate work-type when IWorker.canWorkAt(IWorkSite) is called
     * workers should be responsible for maintaining their own list of acceptable work types
     */
    public WorkType getWorkType();

    public Team getTeam();

    public EnumSet<WorksiteUpgrade> getUpgrades();

    public EnumSet<WorksiteUpgrade> getValidUpgrades();

    /**
     * Add the input upgrade to the present upgrade set.  Apply any necessary bonuses at this time.<br>
     * Calling this method with an upgrade that is already present has undefined results.
     */
    public void addUpgrade(WorksiteUpgrade upgrade);

    /**
     * Remove the input upgrade from the present upgrade set.  Remove any bonuses that it had applied.<br>
     * Calling this method with an upgrade that is not present has undefined results.
     */
    public void removeUpgrade(WorksiteUpgrade upgrade);

    public void onBlockBroken();

    public static enum WorkType {
        /**
         * any change to reg. names will fubar npc-leveling system, as they use these names to store accumulated xp
         */
        MINING("work_type.mining"),
        FARMING("work_type.farming"),
        FORESTRY("work_type.forestry"),
        CRAFTING("work_type.crafting"),
        RESEARCH("work_type.research"),
        NONE("work_type.none");
        public final String regName;

        WorkType(String regName) {
            this.regName = regName;
        }
    }

    /**
     * Static methods for default implementation of worksite logic.
     *
     * 
     */
    public static final class WorksiteImplementation {

        private WorksiteImplementation() {
        }

        public static double getEnergyPerActivation(double efficiencyBonusFactor) {
            return AWCoreStatics.energyPerWorkUnit * 1.f - efficiencyBonusFactor;
        }

        public static double getEfficiencyFactor(EnumSet<WorksiteUpgrade> upgrades) {
            double efficiencyBonusFactor = 0.d;
            if (upgrades.contains(WorksiteUpgrade.ENCHANTED_TOOLS_1)) {
                efficiencyBonusFactor += 0.05;
            }
            if (upgrades.contains(WorksiteUpgrade.ENCHANTED_TOOLS_2)) {
                efficiencyBonusFactor += 0.1;
            }
            if (upgrades.contains(WorksiteUpgrade.TOOL_QUALITY_1)) {
                efficiencyBonusFactor += 0.05;
            }
            if (upgrades.contains(WorksiteUpgrade.TOOL_QUALITY_2)) {
                efficiencyBonusFactor += 0.15;
            }
            if (upgrades.contains(WorksiteUpgrade.TOOL_QUALITY_3)) {
                efficiencyBonusFactor += 0.25;
            }
            return efficiencyBonusFactor;
        }

    }

}
