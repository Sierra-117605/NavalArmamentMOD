package com.navalarmament.block.usn;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESubVLSSSGN16;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockSubVLSSSGN16 extends BlockNavalBase {
    public BlockSubVLSSSGN16() {
        super(Material.iron);
        setBlockName("sub_vls_ssgn_16");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public TileEntity createTileEntity(World world, int metadata) { return new TESubVLSSSGN16(); }
    @Override public String getNationId() { return "usn"; }
}