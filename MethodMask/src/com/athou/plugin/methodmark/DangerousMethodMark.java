package src.com.athou.plugin.methodmark;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

import static com.intellij.codeHighlighting.Pass.UPDATE_ALL;
import static com.intellij.openapi.editor.markup.GutterIconRenderer.Alignment.LEFT;

/**
 * prompt the method is dangerous with an icon on left of the window
 * Created by cai on 2017/3/14.
 */
public class DangerousMethodMark implements LineMarkerProvider {

    private static final String DANGEROUS_METHOD_MARK = "com.athou.plugin.DangerousMethod";

    private static final Icon ICON = IconLoader.getIcon("/icons/dangerous_method.png");

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        if (psiElement instanceof PsiReferenceExpression) {
            PsiReferenceExpression referenceExpression = (PsiReferenceExpression) psiElement;

            if (referenceExpression.resolve() instanceof PsiMethod) {
                PsiMethod method = (PsiMethod) referenceExpression.resolve();
                if (hasAnnotation(method, DANGEROUS_METHOD_MARK)) {
                    return new LineMarkerInfo<PsiElement>(psiElement, psiElement.getTextRange(), ICON,
                            UPDATE_ALL, null, null, LEFT);
                }
            }
        }
        return null;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> collection) {

    }

    public static boolean hasAnnotation(PsiElement element, String annotationName) {
        return findAnnotation(element, annotationName) != null;
    }

    static PsiAnnotation findAnnotation(PsiElement element, String annotationName) {
        if (element instanceof PsiModifierListOwner) {
            PsiModifierListOwner listOwner = (PsiModifierListOwner) element;
            PsiModifierList modifierList = listOwner.getModifierList();

            if (modifierList != null) {
                for (PsiAnnotation psiAnnotation : modifierList.getAnnotations()) {
                    if (annotationName.equals(psiAnnotation.getQualifiedName())) {
                        return psiAnnotation;
                    }
                }
            }

        }
        return null;
    }
}
