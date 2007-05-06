package org.p2presenter.instructor.ui;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.p2presenter.instructor.ui.builder.EditPseudocodeAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction newWindowAction;
    private IWorkbenchAction preferencesAction;
    private IWorkbenchAction editPseudocodeAction;
    private IWorkbenchAction showLiveDisplayAction;
    private IContributionItem viewList;
    
    private ConnectAction connectAction;
    
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        
        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(newWindowAction);
        
        preferencesAction = ActionFactory.PREFERENCES.create(window);
        register(preferencesAction);
        
        connectAction = new ConnectAction("Connect to server", window);
        register(connectAction);
        
        editPseudocodeAction = new EditPseudocodeAction(window);
        register(editPseudocodeAction);
        
        showLiveDisplayAction = new LiveDisplayAction("Show &Live Display");
        register(showLiveDisplayAction);
        
        viewList = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
        MenuManager windowMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
        MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
        
        menuBar.add(fileMenu);
        // Add a group marker indicating where action set menus will appear.
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
        
        fileMenu.add(connectAction);
        fileMenu.add(editPseudocodeAction);
        
        fileMenu.add(new Separator());
        fileMenu.add(preferencesAction);
        
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        
        MenuManager viewMenu = new MenuManager("Show &View");
        viewMenu.add(viewList);
        windowMenu.add(viewMenu);
        windowMenu.add(showLiveDisplayAction);
        
        // Help
        helpMenu.add(aboutAction);
    }
    
    @Override
    protected void fillCoolBar(ICoolBarManager coolBar) {

    }
    
}
