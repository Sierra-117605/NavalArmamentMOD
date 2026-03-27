package com.navalarmament.block.usn;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESubTorpedo;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockSubTorpedo extends BlockNavalBase {
    public BlockSubTorpedo() {
        super(Material.iron);
        setBlockName("sub_torpedo");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public TileEntity createTileEntity(World world, int metadata) { return new TESubTorpedo(); }
    @Override public String getNationId() { return "usn"; }
}