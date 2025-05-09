package com.kam.andromate.utils.ThreadUtils;

import android.util.Log;

import com.kam.andromate.utils.DeviceUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CmdHelper {

    public static String executeCommand(String command, boolean asRoot, CmdObserver cmdObserver) {
        StringBuilder output = new StringBuilder();
        StringBuilder errorOutput = new StringBuilder();
        Process process = null;
        try {
            if (asRoot) {
                // Vérifie si l'appareil est rooté avant d'exécuter en root
                if (DeviceUtils.isDeviceRoot()) {
                    process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
                } else {
                    String error = "L'appareil n'est pas rooté. Impossible d'exécuter la commande en tant que root.";
                    output.append(error).append("\n");
                    if (cmdObserver != null) {
                        cmdObserver.onCommandError(error);
                    }
                    return output.toString();
                }
            } else {
                process = Runtime.getRuntime().exec(command);
            }
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
            process.waitFor();
            if (cmdObserver != null) {
                if(errorOutput.toString().isEmpty())
                    cmdObserver.onCommandSuccess(output.toString());
                else
                    cmdObserver.onCommandError(errorOutput.toString());
            }
            reader.close();
            errorReader.close();
        } catch (Exception e) {
            String errorMsg = "Erreur d'exécution : " + e;
            output.append(errorMsg).append("\n");

            if (cmdObserver != null) {
                cmdObserver.onCommandError(errorMsg);
            }

        } finally {
            if (process != null) process.destroy();
        }

        return output.toString();
    }
}
