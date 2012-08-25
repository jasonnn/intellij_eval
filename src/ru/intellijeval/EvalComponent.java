package ru.intellijeval;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import ru.intellijeval.toolwindow.PluginToolWindowManager;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.intellij.openapi.project.Project.DIRECTORY_STORE_FOLDER;

/**
 * @author DKandalov
 */
public class EvalComponent implements ApplicationComponent, Configurable { // TODO implement DumbAware?
	private static final Logger LOG = Logger.getInstance(EvalComponent.class);

	public static final String COMPONENT_NAME = "EvalComponent";
	public static final String MAIN_SCRIPT = "plugin.groovy";

	private static final String DEFAULT_PLUGIN_PATH = "/ru/intellijeval/exampleplugins";
	private static final String DEFAULT_PLUGIN_SCRIPT = "default-plugin.groovy";

	private static final String DEFAULT_IDEA_OUTPUT_FOLDER = "out";

	public static String pluginsRootPath() {
		return PathManager.getPluginsPath() + "/intellij-eval-plugins/";
	}

	public static Map<String, String> pluginToPathMap() {
		final boolean containsIdeaProjectFolder = new File(pluginsRootPath() + "/" + DIRECTORY_STORE_FOLDER).exists();

		File[] files = new File(pluginsRootPath()).listFiles(new FileFilter() {
			@SuppressWarnings("SimplifiableIfStatement")
			@Override public boolean accept(File file) {
				if (containsIdeaProjectFolder && file.getName().equals(DEFAULT_IDEA_OUTPUT_FOLDER)) return false;
				if (file.getName().equals(DIRECTORY_STORE_FOLDER)) return false;
				return file.isDirectory();
			}
		});
		if (files == null) return new HashMap<String, String>();

		HashMap<String, String> result = new HashMap<String, String>();
		for (File file : files) {
			result.put(file.getName(), file.getAbsolutePath());
		}
		return result;
	}

	public static boolean isInvalidPluginFolder(VirtualFile virtualFile) {
		File file = new File(virtualFile.getPath());
		if (!file.isDirectory()) return false;
		String[] files = file.list(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.equals(MAIN_SCRIPT);
			}
		});
		return files.length < 1;
	}

	public static String defaultPluginScript() {
		return readSampleScriptFile(DEFAULT_PLUGIN_PATH, DEFAULT_PLUGIN_SCRIPT);
	}

	public static String readSampleScriptFile(String pluginPath, String file) {
		try {
			String path = pluginPath + "/" + file;
			return FileUtil.loadTextAndClose(EvalComponent.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			LOG.error(e);
			return "";
		}
	}

	public static boolean pluginExists(String pluginId) {
		return pluginToPathMap().keySet().contains(pluginId);
	}

	@Override
	public void initComponent() {
		new PluginToolWindowManager().init();
	}

	@Override
	public void disposeComponent() {
	}

	@Override
	@NotNull
	public String getComponentName() {
		return COMPONENT_NAME;
	}

	@Override
	public JComponent createComponent() {
		return null;
	}

	@Override
	@Nls
	public String getDisplayName() {
		return getComponentName();
	}

	@Override
	public String getHelpTopic() {
		return null;
	}

	@Override
	public void disposeUIResources() {
	}

	@Override
	public void apply() throws ConfigurationException {
	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public void reset() {
	}
}
