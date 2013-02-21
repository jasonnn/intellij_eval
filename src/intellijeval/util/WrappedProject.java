package intellijeval.util;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.picocontainer.PicoContainer;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/21/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public
class WrappedProject implements Project {
    private final Project wrappedProject;

    public
    WrappedProject(Project wrappedProject) {
        this.wrappedProject = wrappedProject;
    }

    @Override
    @NotNull
    @NonNls
    public
    String getName() {
        return wrappedProject.getName();
    }

    @Override
    public
    VirtualFile getBaseDir() {
        return wrappedProject.getBaseDir();
    }

    @Override
    @NonNls
    public
    String getBasePath() {
        return wrappedProject.getBasePath();
    }

    @Override
    @Nullable
    public
    VirtualFile getProjectFile() {
        return wrappedProject.getProjectFile();
    }

    @Override
    @NotNull
    @NonNls
    public
    String getProjectFilePath() {
        return wrappedProject.getProjectFilePath();
    }

    @Override
    @Nullable
    @NonNls
    public
    String getPresentableUrl() {
        return wrappedProject.getPresentableUrl();
    }

    @Override
    @Nullable
    public
    VirtualFile getWorkspaceFile() {
        return wrappedProject.getWorkspaceFile();
    }

    @Override
    @NotNull
    @NonNls
    public
    String getLocationHash() {
        return wrappedProject.getLocationHash();
    }

    @Override
    @Nullable
    @NonNls
    public
    String getLocation() {
        return wrappedProject.getLocation();
    }

    @Override
    public
    void save() {
        wrappedProject.save();
    }

    @Override
    public
    boolean isOpen() {
        return wrappedProject.isOpen();
    }

    @Override
    public
    boolean isInitialized() {
        return wrappedProject.isInitialized();
    }

    @Override
    public
    boolean isDefault() {
        return wrappedProject.isDefault();
    }

    @Override
    public
    BaseComponent getComponent(String name) {
        return wrappedProject.getComponent(name);
    }

    @Override
    public
    <T>
    T getComponent(Class<T> interfaceClass) {
        return wrappedProject.getComponent(interfaceClass);
    }

    @Override
    public
    <T>
    T getComponent(Class<T> interfaceClass, T defaultImplementationIfAbsent) {
        return wrappedProject.getComponent(interfaceClass, defaultImplementationIfAbsent);
    }

    @Override
    public
    boolean hasComponent(@NotNull Class interfaceClass) {
        return wrappedProject.hasComponent(interfaceClass);
    }

    @Override
    @NotNull
    public
    <T>
    T[] getComponents(Class<T> baseClass) {
        return wrappedProject.getComponents(baseClass);
    }

    @Override
    @NotNull
    public
    PicoContainer getPicoContainer() {
        return wrappedProject.getPicoContainer();
    }

    @Override
    public
    MessageBus getMessageBus() {
        return wrappedProject.getMessageBus();
    }

    @Override
    public
    boolean isDisposed() {
        return wrappedProject.isDisposed();
    }

    @Override
    public
    <T> T[] getExtensions(ExtensionPointName<T> extensionPointName) {
        return wrappedProject.getExtensions(extensionPointName);
    }

    @Override
    @NotNull
    public
    Condition getDisposed() {
        return wrappedProject.getDisposed();
    }

    @Override
    @Nullable
    public
    <T> T getUserData(@NotNull Key<T> key) {
        return wrappedProject.getUserData(key);
    }

    @Override
    public
    <T> void putUserData(@NotNull Key<T> key,
                         @Nullable T value) {
        wrappedProject.putUserData(key, value);
    }

    @Override
    public
    void dispose() {
        wrappedProject.dispose();
    }
}
