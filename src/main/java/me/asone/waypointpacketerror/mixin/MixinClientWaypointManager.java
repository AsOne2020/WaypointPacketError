package me.asone.waypointpacketerror.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.waypoints.ClientWaypointManager;
import net.minecraft.world.waypoints.TrackedWaypoint;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(ClientWaypointManager.class)
public class MixinClientWaypointManager {

	@Shadow @Final private Map<Either<UUID, String>, TrackedWaypoint> waypoints;

	@Inject(
			method = "updateWaypoint(Lnet/minecraft/world/waypoints/TrackedWaypoint;)V",
			at = @At("HEAD"),
			cancellable = true
	)
	private void guardNullWaypoint(TrackedWaypoint trackedWaypoint, CallbackInfo ci) {
		if (this.waypoints.get(trackedWaypoint.id()) == null) {
			ci.cancel();
		}
	}

}
