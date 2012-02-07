package jp.strippers.eclipse.exploreit.preferences;

import jp.strippers.eclipse.exploreit.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.SHOW_DIRECTORY_TREE, false);
		store.setDefault(PreferenceConstants.OPEN_AS_ROOT, false);
	}

}
