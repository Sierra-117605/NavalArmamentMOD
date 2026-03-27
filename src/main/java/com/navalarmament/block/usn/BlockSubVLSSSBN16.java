package com.navalarmament.block.usn;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESubVLSSSBN16;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockSubVLSSSBN16 extends BlockNavalBase {
    public BlockSubVLSSSBN16() {
        super(Material.iron);
        setBlockName("sub_vls_ssbn_16");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public TileEntity createTileEntity(World world, int metadata) { return new TESubVLSSSBN16(); }
    @Override public String getNationId() { return "usn"; }
}