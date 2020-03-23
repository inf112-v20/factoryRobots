package inf112.app.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import inf112.app.map.Map;

public class MapLoader extends AsynchronousAssetLoader<Map, MapLoader.Parameters> {

    public MapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager assetManager, String mapName, FileHandle fileHandle, Parameters parameters) {

    }

    @Override
    public Map loadSync(AssetManager assetManager, String mapName, FileHandle fileHandle, Parameters parameters) {
        Map.setInstance(mapName);
        return Map.getInstance();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String s, FileHandle fileHandle, Parameters parameters) {
        return null;
    }

    public static class Parameters extends AssetLoaderParameters<Map> {
        public Parameters() {
        }
    }
}
