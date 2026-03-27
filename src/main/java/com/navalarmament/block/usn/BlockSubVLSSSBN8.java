package com.navalarmament.block.usn;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESubVLSSSBN8;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockSubVLSSSBN8 extends BlockNavalBase {
    public BlockSubVLSSSBN8() {
        super(Material.iron);
        setBlockName("sub_vls_ssbn_8");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public TileEntity createTileEntity(World world, int metadata) { return new TESubVLSSSBN8(); }
    @Override public String getNationId() { return "usn"; }
}