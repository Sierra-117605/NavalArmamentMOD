package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEHarpoonLauncher;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHarpoonLauncher extends BlockNavalBase {
    public BlockHarpoonLauncher() {
        super(Material.iron);
        setBlockName("harpoon_launcher");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEHarpoonLauncher(); }
    @Override public String getNationId() { return "usn"; }
}