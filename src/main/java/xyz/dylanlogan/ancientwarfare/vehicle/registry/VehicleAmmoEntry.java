package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;

public class VehicleAmmoEntry {

    public IAmmo baseAmmoType;
    public int ammoCount;

    public VehicleAmmoEntry(IAmmo ammo) {
        this.baseAmmoType = ammo;
    }

}