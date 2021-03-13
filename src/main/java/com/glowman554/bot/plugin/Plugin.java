package com.glowman554.bot.plugin;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Plugin {
    private final File plugin;
    private final String data;
    private ScriptEngine scriptEngine;

    public Plugin(File plugin) throws FileNotFoundException {
        this.plugin = plugin;
        this.data = null;

        System.out.println("Loading plugin " + this.plugin.getPath());

        this.scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

        try {
            this.scriptEngine.eval(new FileReader(this.plugin));

            Invocable invocable = (Invocable) this.scriptEngine;

            invocable.invokeFunction("load");
        } catch (ScriptException | NoSuchMethodException e) {
            System.out.println("Plugin load fail: " + e.getMessage());
        }
    }

    public Plugin(String data) {
        this.plugin = null;
        this.data = data;

        System.out.println("Loading plugin <text>");

        this.scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

        try {
            this.scriptEngine.eval(data);

            Invocable invocable = (Invocable) this.scriptEngine;

            invocable.invokeFunction("load");
        } catch (ScriptException | NoSuchMethodException e) {
            System.out.println("Plugin load fail: " + e.getMessage());
        }
    }

    public void enable() {
        try {
            Invocable invocable = (Invocable) this.scriptEngine;
            invocable.invokeFunction("enable");
        } catch (ScriptException | NoSuchMethodException e) {
            System.out.println("Plugin enable fail: " + e.getMessage());
        }
    }

    public void disable() {
        try {
            Invocable invocable = (Invocable) this.scriptEngine;
            invocable.invokeFunction("disable");
        } catch (ScriptException | NoSuchMethodException e) {
            System.out.println("Plugin disable fail: " + e.getMessage());
        }
    }
}
