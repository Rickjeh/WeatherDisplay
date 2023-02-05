package dev.rickjeh.weatherdisplay.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class WeatherdisplayClient implements ClientModInitializer {
    public static MinecraftClient mc;
    public static int state;
    public static boolean notDisabled;
    public static boolean clearDisabled;
    private static KeyBinding keyBinding1;
    private static KeyBinding keyBinding2;
    private static KeyBinding keyBinding3;
    @Override
    public void onInitializeClient() {
        mc = MinecraftClient.getInstance();

        notDisabled = true;

        clearDisabled = false;

        //move location
        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Change position",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "Weather Display"
        ));

        //hide display
        keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Hide display",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "Weather Display"
        ));

        //disable day/night
        keyBinding3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Hide clear weather",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "Weather Display"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {


                while (keyBinding1.wasPressed()) {
                    if(notDisabled){
                        state++;
                        if(state == 5){
                            state = 0;
                        }
                    }
                }

            while (keyBinding2.wasPressed()) {
                if(notDisabled) {
                    notDisabled = false;
                    mc.player.sendMessage(Text.of("Weatherdisplay disabled."),true);
                }else{
                    notDisabled = true;
                    mc.player.sendMessage(Text.of("Weatherdisplay enabled."),true);
                }
            }


            while (keyBinding3.wasPressed()) {
                if(!clearDisabled) {
                    clearDisabled = true;
                    mc.player.sendMessage(Text.of("Clear weather disabled"),true);
                }else{
                    clearDisabled = false;
                    mc.player.sendMessage(Text.of("Clear weather enabled."), true);
                }
            }
        });

        HudRenderCallback.EVENT.register(new WeatherHudOverlay());
    }
}
