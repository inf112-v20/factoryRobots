package inf112.app.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;

public class MapLoader extends AsynchronousAssetLoader<Map, MapLoader.Parameters> {
    private RoboRally game;
    public MapLoader(FileHandleResolver resolver, RoboRally game) {
        super(resolver);
        this.game = game;
    }

    /**
     * Unused function
     * @param assetManager The current AssetManager
     * @param mapName The map to load
     * @param fileHandle ..
     * @param parameters ..
     */
    @Override
    public void loadAsync(AssetManager assetManager, String mapName, FileHandle fileHandle, Parameters parameters) {

    }

    /**
     * Load the map in the background. Uses the already stored TiledMaps: MapName and Laser
     * @param assetManager The current AssetManager
     * @param mapName The map to load
     * @param fileHandle ..
     * @param parameters ..
     * @return The map instance
     */
    @Override
    public Map loadSync(AssetManager assetManager, String mapName, FileHandle fileHandle, Parameters parameters) {
        Map.setInstance(game.manager.get("assets/" + mapName + ".tmx"),
                game.manager.get("assets/Lasers.tmx"));
        return Map.getInstance();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String s, FileHandle fileHandle, Parameters parameters) {
        return null;
    }

    public static class Parameters extends AssetLoaderParameters<Map> {
    }
}
