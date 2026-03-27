package com.navalarmament.block.usn;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESubVLS8;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockSubVLS8 extends BlockNavalBase {
    public BlockSubVLS8() {
        super(Material.iron);
        setBlockName("sub_vls_8");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public TileEntity createTileEntity(World world, int metadata) { return new TESubVLS8(); }
    @Override public String getNationId() { return "usn"; }
}