package inf112.app.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import inf112.app.cards.CardDeck;

public class CardDeckLoader extends AsynchronousAssetLoader<CardDeck, CardDeckLoader.Parameters> {

    public CardDeckLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /**
     * Unused function
     * @param assetManager The current AssetManager
     * @param s ..
     * @param fileHandle ..
     * @param parameters ..
     */
    @Override
    public void loadAsync(AssetManager assetManager, String s, FileHandle fileHandle, Parameters parameters) {
        // Not used
    }

    @Override
    public CardDeck loadSync(AssetManager assetManager, String s, FileHandle fileHandle, Parameters parameters) {
        return new CardDeck();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String s, FileHandle fileHandle, Parameters parameters) {
        return null;
    }

    public static class Parameters extends AssetLoaderParameters<CardDeck> {
    }
}
