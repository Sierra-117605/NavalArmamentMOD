package com.navalarmament.block.common;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.common.TEShutter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockShutter extends BlockNavalBase {
    public BlockShutter() {
        super(Material.iron);
        setBlockName("shutter");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEShutter(); }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TEShutter) ((TEShutter) te).toggle();
        }
        return true;
    }
}