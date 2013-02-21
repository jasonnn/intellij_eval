package intellijeval.util.cache;

import groovy.lang.Binding;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public
class EvalCache extends ObservableCache<String,Object> {
        private final Binding binding;
    public
    EvalCache() {

        binding = createBinding();
    }


    protected
    Binding createBinding(){
       return new Binding(this.asMap());
    }

    public
    Binding getBinding() {
        return binding;
    }
}
