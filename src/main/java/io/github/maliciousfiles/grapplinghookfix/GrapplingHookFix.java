package io.github.maliciousfiles.grapplinghookfix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@Mod("grapplemod-fix")
public class GrapplingHookFix {

    public static final KeyBinding TOGGLE_DOUBLE_JUMP = new KeyBinding("key.toggle_double_jump", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.grapplemod.category");


    public static boolean doubleJumpActive = true;

    public GrapplingHookFix() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (TOGGLE_DOUBLE_JUMP.isPressed()) {
            doubleJumpActive = !doubleJumpActive;

            DoubleJumpToggleToast.addOrUpdate(Minecraft.getInstance().getToastGui(), doubleJumpActive);
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(TOGGLE_DOUBLE_JUMP);
    }
}
