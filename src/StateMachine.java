import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Typical state machine:
 *  
 * State(S) x Event(E) -> Actions (A), State(S')
 *
 * @author Jitendra Kotamraju
 */
public class StateMachine {
    
    interface Context<T> {
        T data();
        State<T> state();
        void state(State<T> state);
    }
    
    interface State<T> {
        // Process and transition to next state asynchronously
        // @return false if there is no progress
        // Perhaps CompletableFuture<Context> could be returned as well, in that
        // case engine will check for end states explicitly
        CompletableFuture<Boolean> process(Context<T> context);
    }

    
    // Simple example to read data from InputStream using a state machine
    // START --> OPEN* --> CLOSE|ERROR
    public static void main(String ... args) throws Exception {
        StateMachine machine = new StateMachine();
        machine.process(new ContextImpl(States.START));
    }
    
    enum States implements State<InputStream> {
        START {
            public CompletableFuture<Boolean> process(Context<InputStream> context) {
                return CompletableFuture.supplyAsync(() -> {
                    context.state(States.OPEN);
                    return true;
                });
            }
        },
        
        OPEN {
            public CompletableFuture<Boolean> process(Context<InputStream> context) {
                return CompletableFuture.supplyAsync(() -> {
                    InputStream in = context.data();
                    try {
                        context.state(in.read() == -1 ? States.CLOSE : States.OPEN);
                    } catch (IOException e) {
                        context.state(States.ERROR);
                    }
                    return true;
                });
            }
        },
        
        CLOSE {
            public CompletableFuture<Boolean> process(Context<InputStream> context) {
                return CompletableFuture.supplyAsync(() -> false);
            }
        },

        ERROR {
            public CompletableFuture<Boolean> process(Context<InputStream> context) {
                return CompletableFuture.supplyAsync(() -> false);
            }
        }
    }
    
    void process(Context<InputStream> context) throws Exception {
        CompletableFuture<Boolean> f = CompletableFuture.completedFuture(true);
        CompletableFuture<Boolean> g = f.thenCompose(new ProgressFunction(context));
        g.join();
    }
    
    static class ProgressFunction implements Function<Boolean, CompletionStage<Boolean>> {
        private final Context<InputStream> context;

        ProgressFunction(Context<InputStream> context) {
            this.context = context;
        }
        
        @Override
        public CompletionStage<Boolean> apply(Boolean b) {
            return b ? context.state().process(context).thenCompose(this)
                     : CompletableFuture.completedFuture(true);
        }
    }
    
    static class ContextImpl implements Context<InputStream> {
        private State<InputStream> state;
        private final InputStream data;
        
        ContextImpl(State<InputStream> state) {
            this.state = state;
            this.data = new ByteArrayInputStream("Hello World".getBytes());
        }

        @Override
        public InputStream data() {
            return data;
        }

        @Override
        public State<InputStream> state() {
            return state;
        }

        @Override
        public void state(State<InputStream> state) {
            System.out.println(this.state+" -> "+state+" in "+Thread.currentThread());
            this.state = state;
        }
    }
    
}
