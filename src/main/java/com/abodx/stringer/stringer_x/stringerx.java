package com.abodx.stringer.stringer_x;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import java.util.Random;

/**
 * Created by Abdulmajid on 2022-12-12.
 *
 * String encoder v1.0
 *
 * email - abodx@gmx.de
 */
public class stringerx extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);

        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        final String selected = selectionModel.getSelectedText();
        if (selected == null) {
            return;
        }
        //New instance of Runnable to make a replacement
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String inp = selected.replaceAll("\"","");
                StringBuilder ob = new StringBuilder();
                Random r = new Random(System.currentTimeMillis());
                byte[] b = inp.getBytes();
                int c = b.length;

                ob.append("(new Object() {int x;public String toString() {byte[] stringerxAbodxtestx = new byte[").append(c).append("];");

                for (int i = 0; i < c; ++i) {
                    int x = r.nextInt();
                    int f = r.nextInt(24) + 1;

                    x = (x & ~(0xff << f)) | (b[i] << f);

                    ob.append("x = ").append(x).append(";").append("stringerxAbodxtestx[").append(i).append("] = (byte) (x >>> ").append(f).append(");");

                }
                ob.append("return new String(stringerxAbodxtestx);");
                ob.append("}}.toString())");

                document.replaceString(start, end, ob.toString());
            }
        };

        //Making the replacement with the encoded string
        WriteCommandAction.runWriteCommandAction(project, runnable);
    }
}

