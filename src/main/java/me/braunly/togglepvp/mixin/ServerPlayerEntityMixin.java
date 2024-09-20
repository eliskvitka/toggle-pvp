package me.braunly.togglepvp.mixin;


import me.braunly.togglepvp.PvpAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "shouldDamagePlayer", cancellable = true)
    private void onShouldDamagePlayer(PlayerEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        boolean isVictimPvpEnabled = PvpAbility.get((ServerPlayerEntity)(Object)this);
        boolean isAttackerPvpEnabled = PvpAbility.get(attacker);

        // TODO: Add check for server.properties flag

        // NOTE: Ignoring result from teammates check
        cir.setReturnValue(isVictimPvpEnabled && isAttackerPvpEnabled);
    }
}