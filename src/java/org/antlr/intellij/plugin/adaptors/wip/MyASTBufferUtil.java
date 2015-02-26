package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import com.intellij.psi.impl.source.tree.LazyParseableElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.util.text.CharArrayCharSequence;
import com.intellij.util.text.StringFactory;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/25/15.
 */
public class MyASTBufferUtil {


    public static int toBuffer(@NotNull ASTNode element, @Nullable char[] buffer, int offset) {
        return toBuffer(element, buffer, offset, false);
    }

    public static int toBuffer(@NotNull ASTNode element, @Nullable char[] buffer, int offset, boolean skipWhitespaceAndComments) {
        BufferVisitor visitor = new BufferVisitor(skipWhitespaceAndComments, skipWhitespaceAndComments, offset, buffer);
        ((AntlrAST) element).accept(visitor);
        return visitor.end;
    }

    public static String getTextSkippingWhitespaceComments(@NotNull ASTNode element) {
        int length = toBuffer(element, null, 0, true);
        char[] buffer = new char[length];
        toBuffer(element, buffer, 0, true);
        return StringFactory.createShared(buffer);
    }

    public static class BufferVisitor extends AbstractParseTreeVisitor<Void> {
        private final boolean skipWhitespace;
        private final boolean skipComments;

        protected final int offset;
        protected int end;
        protected final char[] buffer;

        public BufferVisitor(PsiElement element, boolean skipWhitespace, boolean skipComments) {
            this(skipWhitespace, skipComments, 0, new char[element.getTextLength()]);
            // ((TreeElement)element.getNode()).acceptTree(this);
        }

        public BufferVisitor(boolean skipWhitespace, boolean skipComments, int offset, @Nullable char[] buffer) {
            // super(false);

            this.skipWhitespace = skipWhitespace;
            this.skipComments = skipComments;
            this.buffer = buffer;
            this.offset = offset;
            end = offset;
        }

        public int getEnd() {
            return end;
        }

        public char[] getBuffer() {
            assert buffer != null;
            return buffer;
        }

        public CharSequence createCharSequence() {
            assert buffer != null;
            return new CharArrayCharSequence(buffer, offset, end);
        }

        // @Override
        public void visitLeaf(LeafElement element) {
            ProgressIndicatorProvider.checkCanceled();
            if (!isIgnored(element)) {
                end = element.copyTo(buffer, end);
            }
        }

        protected boolean isIgnored(LeafElement element) {
            return element instanceof ForeignLeafPsiElement ||
                    (skipWhitespace && element instanceof PsiWhiteSpace) ||
                    (skipComments && element instanceof PsiComment);
        }

        // @Override
        public void visitComposite(CompositeElement composite) {
            if (composite instanceof LazyParseableElement) {
                LazyParseableElement lpe = (LazyParseableElement) composite;
                int lpeResult = lpe.copyTo(buffer, end);
                if (lpeResult >= 0) {
                    end = lpeResult;
                    return;
                }
                assert lpe.isParsed();
            }

            //super.visitComposite(composite);
        }
    }

}
