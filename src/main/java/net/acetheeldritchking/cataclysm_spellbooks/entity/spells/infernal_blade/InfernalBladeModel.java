package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class InfernalBladeModel extends AnimatedGeoModel<InfernalBladeProjectile> {

    @Override
    public ResourceLocation getModelResource(InfernalBladeProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/infernal_blade_small.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InfernalBladeProjectile object) {
        if (object.getIsSoul())
        {
            return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/infernal_blade_small/infernal_blade_small_soul.png");
        } else
        {
            return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/infernal_blade_small/infernal_blade_small.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(InfernalBladeProjectile animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/infernal_blade_small.animation.json");
    }
}
