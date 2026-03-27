package com.navalarmament.block.common;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.common.TEWeaponElevator;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWeaponElevator extends BlockNavalBase {
    public BlockWeaponElevator() {
        super(Material.iron);
        setBlockName("weapon_elevator");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEWeaponElevator(); }
}