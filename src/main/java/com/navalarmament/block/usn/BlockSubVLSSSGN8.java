package com.navalarmament.block.usn;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESubVLSSSGN8;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockSubVLSSSGN8 extends BlockNavalBase {
    public BlockSubVLSSSGN8() {
        super(Material.iron);
        setBlockName("sub_vls_ssgn_8");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public TileEntity createTileEntity(World world, int metadata) { return new TESubVLSSSGN8(); }
    @Override public String getNationId() { return "usn"; }
}