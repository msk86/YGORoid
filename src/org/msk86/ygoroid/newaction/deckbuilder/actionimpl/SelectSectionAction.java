package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class SelectSectionAction extends BaseAction {
    public SelectSectionAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        deckBuilder.setCurrentSection(container);
    }
}
