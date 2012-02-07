package jp.strippers.eclipse.exploreit.action;

import java.io.File;
import java.io.IOException;

import jp.strippers.eclipse.exploreit.Activator;
import jp.strippers.eclipse.exploreit.preferences.PreferenceConstants;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class Action implements IObjectActionDelegate {

	private Object selected = null;

	private Class selectedClass = null;

	public Action() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	public void run(IAction action) {
		if (selected == null) {
			MessageDialog.openInformation(new Shell(), "Easy Explorer", "Unable to explorer "
					+ selectedClass.getName());
			return;
		}
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean openAsRoot = store.getBoolean(PreferenceConstants.OPEN_AS_ROOT);
		boolean showFolderTree = store.getBoolean(PreferenceConstants.SHOW_DIRECTORY_TREE);

		File dir = null;
		if (selected instanceof IResource) {
			dir = ((IResource)selected).getLocation().toFile();
		} else if (selected instanceof File) {
			dir = (File)selected;
		}
		if (selected instanceof IFile) {
			dir = dir.getParentFile();
		}
		if (selected instanceof File) {
			dir = dir.getParentFile();
		}

		StringBuilder sb = new StringBuilder("explorer.exe ");
		if (showFolderTree)
			sb.append("/e, ");
		if (openAsRoot)
			sb.append("/root, ");
		sb.append(dir.toString());
		try {
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		IAdaptable adaptable = null;
		if (selection instanceof IStructuredSelection) {
			adaptable = (IAdaptable)((IStructuredSelection)selection).getFirstElement();
			this.selectedClass = adaptable.getClass();
			if (adaptable instanceof IResource) {
				this.selected = (IResource)adaptable;
			} else {
				this.selected = (IResource)adaptable.getAdapter(IResource.class);
			}
		}
	}

}
