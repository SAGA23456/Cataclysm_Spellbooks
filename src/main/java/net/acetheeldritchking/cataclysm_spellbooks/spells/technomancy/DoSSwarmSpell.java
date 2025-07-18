package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedCounterspellWatcher;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedWatcher;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class DoSSwarmSpell extends AbstractHarbingerSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "dos_swarm");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.watcher_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(100)
            .build();

    public DoSSwarmSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 35;
        this.baseManaCost = 90;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_CONSTRUCT_SUMMON;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnWatcher(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer);
        }

        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.WATCHER_TIMER.get());
        entity.addEffect(effect);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnWatcher(double x, double y, double z, LivingEntity caster, Level level, int summonTimer)
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.WATCHER_TIMER.get(),
                summonTimer, 0, false, false, false);

        SummonedCounterspellWatcher watcher = new SummonedCounterspellWatcher(level, caster);

        watcher.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(watcher.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        watcher.moveTo(x, y, z);

        watcher.addEffect(effect);

        level.addFreshEntity(watcher);
    }
}
