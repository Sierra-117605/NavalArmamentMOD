package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESQS53Sonar;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSQS53Sonar extends BlockNavalBase {
    public BlockSQS53Sonar() {
        super(Material.iron);
        setBlockName("sqs53_sonar");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TESQS53Sonar(); }
    @Override public String getNationId() { return "usn"; }
}