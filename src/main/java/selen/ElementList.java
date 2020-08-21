package selen;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ElementList<T> extends ArrayList<T> {
    public <R> ElementList<R> map(Function<? super T, ? extends R> mapper) {
        return this.stream().map(mapper)
                .collect(Collectors.toCollection(ElementList::new));
    }

    public ElementList<T> each(Consumer<? super T> action) {
        this.forEach(action);
        return this;
    }

    public ElementList<T> filter(Predicate<? super T> predicate) {
        return this.stream().filter(predicate)
                .collect(Collectors.toCollection(ElementList::new));
    }

    public boolean anyMatch(Predicate<? super T> predicate) {
        return this.stream().anyMatch(predicate);
    }

    public boolean allMatch(Predicate<? super T> predicate) {
        return this.stream().allMatch(predicate);
    }

    public T first() {
        return this.get(0);
    }

    public T last() {
        return this.get(this.size() - 1);
    }
}
