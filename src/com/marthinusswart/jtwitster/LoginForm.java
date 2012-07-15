/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jtwitster;

import com.marthinusswart.jmelib.data.*;
import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Marthinus Swart
 */
public class LoginForm extends Form implements CommandListener {

    private jTwitsterMidlet midlet;
    private Command backCommand;
    private Command loginCommand;
    private TextField userText;
    private TextField passText;
    private ChoiceGroup rememberValuesChoice;
    private Credentials credentials = new Credentials();
    private boolean credentialsIsPersisted = false;

    public LoginForm(jTwitsterMidlet midlet) {
        super("Login");
        userText = new TextField("Username", "", 32, TextField.ANY);
        append(userText);
        passText = new TextField("Password", "", 32, TextField.PASSWORD);
        append(passText);
        String[] labels = {"Save credentials"};
        rememberValuesChoice = new ChoiceGroup("Options", ChoiceGroup.MULTIPLE, labels, null);
        append(rememberValuesChoice);

        this.midlet = midlet;
        backCommand = new Command("Back", Command.BACK, 0);
        addCommand(backCommand);

        loginCommand = new Command("Done", Command.SCREEN, 0);
        addCommand(loginCommand);

        setCommandListener(this);
    }

    public void commandAction(Command c, Displayable s) {
        if (c == backCommand) {
            midlet.getDisplay().setCurrent(midlet.getSplashCanvas());
        } else if (c == loginCommand) {
            handleLogin();
        }
    }

    private void deleteCredentials() {
        DataManagement dataManagement = new DataManagement("jTwitsterCredentials");
        try {
            dataManagement.delete();
        } catch (Exception ex) {
            Alert alert = new Alert("Error", "Failed to delete the credentials: " + ex.toString(),
                    null, AlertType.ERROR);
            midlet.getDisplay().setCurrent(alert, this);
        }
    }

    private void handleLogin() {
        credentials.setUsername(userText.getString());
        credentials.setPassword(passText.getString());
        midlet.setCredentials(credentials);

        if (rememberValuesChoice.isSelected(0)) {
            saveCredentials();
        } else if (credentialsIsPersisted) {
            deleteCredentials();
        }

        midlet.getDisplay().setCurrent(midlet.getSplashCanvas());
    }

    public void loadCredentials() throws RecordStoreException, IOException {
        DataManagement dataManagement = new DataManagement("jTwitsterCredentials");
        dataManagement.load();

        if (dataManagement.hasDataItems()) {
            rememberValuesChoice.setSelectedIndex(0, true);
            DataItem dataItem = dataManagement.getDataItemAt(0);
            credentials.setUsername(dataItem.getString("Username"));
            credentials.setPassword(dataItem.getString("Password"));
            userText.setString(credentials.getUsername());
            passText.setString(credentials.getPassword());
            credentialsIsPersisted = true;
        }
    }

    private void saveCredentials() {
        DataManagement dataManagement = new DataManagement("jTwitsterCredentials");
        DataItem dataItem = new DataItem("Credentials");
        dataItem.add("Username", credentials.getUsername());
        dataItem.add("Password", credentials.getPassword());
        dataManagement.add(dataItem);

        try {
            dataManagement.save();
        } catch (Exception ex) {
            Alert alert = new Alert("Error", "Failed to save the credentials: " + ex.toString(),
                    null, AlertType.ERROR);
            midlet.getDisplay().setCurrent(alert, this);
        }
    }
}
