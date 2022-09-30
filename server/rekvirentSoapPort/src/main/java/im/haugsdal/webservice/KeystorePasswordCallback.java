package im.haugsdal.webservice;

import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class KeystorePasswordCallback implements CallbackHandler {

    private final Map<String, String> passwords;

    public KeystorePasswordCallback(Map<String, String> passwords) {
        this.passwords = passwords;
    }

    public void handle(Callback[] callbacks) {
        for (Callback callback : callbacks) {
            WSPasswordCallback pc = (WSPasswordCallback) callback;

            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }
    }
}
