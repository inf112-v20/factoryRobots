package inf112.app.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import inf112.app.game.CardUI;
import inf112.app.game.RoboRally;

public class CardUILoader extends AsynchronousAssetLoader<CardUI, CardUILoader.Parameters> {
    private RoboRally game;
    public CardUILoader(FileHandleResolver resolver, RoboRally game){
        super(resolver);
        this.game = game;
    }

    @Override
    public void loadAsync(AssetManager assetManager, String s, FileHandle fileHandle, CardUILoader.Parameters parameters) {

    }

    /**
     * Loads the CardUI in the background.
     * First it sets the instance with the necessary assets, then retrieves it when done
     * @param assetManager The current AssetManager
     * @param s ..
     * @param fileHandle ..
     * @param parameters ..
     * @return The CardUI
     */
    @Override
    public CardUI loadSync(AssetManager assetManager, String s, FileHandle fileHandle, CardUILoader.Parameters parameters) {
        CardUI.setInstance(game.manager.get("assets/CardUI2.tmx"), game.manager.get("assets/GameButtons/Buttons.tmx"),
                game.manager.get("assets/Lasers.tmx"), game.manager.get("assets/HealthLight.png"),
                game.manager.get("assets/Card/Lock.png"));
        return CardUI.getInstance();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String s, FileHandle fileHandle, CardUILoader.Parameters parameters) {
        return null;
    }

    public static class Parameters extends AssetLoaderParameters<CardUI> {
    }
}
