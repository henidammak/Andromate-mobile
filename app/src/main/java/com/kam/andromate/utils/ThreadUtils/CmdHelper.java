package com.kam.andromate.utils.ThreadUtils;

import com.kam.andromate.utils.DeviceUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CmdHelper {

    public static void executeCommand(String command, boolean asRoot, CmdObserver cmdObserver) {
        StringBuilder output = new StringBuilder();
        StringBuilder errorOutput = new StringBuilder();
        Process process = null;
        try {
            if (asRoot) {
                if (DeviceUtils.isDeviceRoot()) {
                    process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
                } else {
                    String error = "Permission Denied (no rooted Device)";
                    output.append(error).append("\n");
                    if (cmdObserver != null) {
                        cmdObserver.onCommandError(error);
                    }
                    return;
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

        output.toString();
    }
}
