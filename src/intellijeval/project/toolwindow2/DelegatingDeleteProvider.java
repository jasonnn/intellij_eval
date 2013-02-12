package intellijeval.project.toolwindow2;

import com.intellij.ide.DeleteProvider;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.fileChooser.actions.VirtualFileDeleteProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatingDeleteProvider implements DeleteProvider {

    private DeleteProvider delegate;

    public DelegatingDeleteProvider(DeleteProvider delegate) {
        this.delegate = delegate;
    }

    public DelegatingDeleteProvider(){
        this(new VirtualFileDeleteProvider());
    }

    @Override
    public void deleteElement(@NotNull DataContext dataContext) {
        delegate.deleteElement(dataContext);
    }

    @Override
    public boolean canDeleteElement(@NotNull DataContext dataContext) {
            return delegate.canDeleteElement(dataContext);
        }
}
