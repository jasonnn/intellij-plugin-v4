package org.antlr.intellij.plugin.psi.iter;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * Created by jason on 2/13/15.
 *
 * @see com.intellij.util.containers.FluentIterable
 */
public abstract class PsiIterator<T extends PsiElement> implements Iterable<T>, Iterator<T> {
	private final Iterable<T> myIterable;

	/**
	 * Constructor for use by subclasses.
	 */
	protected PsiIterator() {
		myIterable = this;
	}

	PsiIterator(@NotNull Iterable<T> iterable) {
		myIterable = iterable;
	}

	/**
	 * Returns a fluent iterable that wraps {@code iterable}, or {@code iterable} itself if it
	 * is already a {@code FluentIterable}.
	 */
	public static <E extends PsiElement> PsiIterator<E> from(final Iterable<E> iterable) {
		return iterable instanceof PsiIterator ? (PsiIterator<E>) iterable :
				new PsiIterator<E>(iterable) {
					@NotNull
					@Override
					public Iterator<E> newIterator() {
						return iterable.iterator();
					}
				};
	}

	protected abstract Iterator<T> newIterator();

	@NotNull
	@Override
	public final Iterator<T> iterator() {
		return this.it = newIterator();
	}

	Iterator<T> it;

	Iterator<T> delegate() {
		Iterator<T> i = it;
		if (i == null) i = it = iterator();
		return i;
	}


	@Override
	public boolean hasNext() {
		return delegate().hasNext();
	}

	@Override
	public T next() {
		return delegate().next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
