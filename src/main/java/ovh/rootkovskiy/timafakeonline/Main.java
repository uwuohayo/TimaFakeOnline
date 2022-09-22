package ovh.rootkovskiy.timafakeonline;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.WrappedServerPing;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Plugin enabled");
        Bukkit.getPluginManager().registerEvents(this, (Plugin)this);
        System.out.println(ConsoleUtils.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "Hello :D"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "TimaFakeOnline " + getDescription().getVersion() + " Loaded and Enabled!"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "MC Core Version: " + getServer().getBukkitVersion() +ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "Author: Timur Rootkovskiy (Adminov)"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "Site: www.rootkovskiy.ovh"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "VK: @timurroot"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+ConsoleUtils.ANSI_RESET);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
        System.out.println(ConsoleUtils.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "TimaFakeOnline " + getDescription().getVersion() + " Disabled!"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "MC Core Version: " + getServer().getBukkitVersion() +ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "Author: Timur Rootkovskiy (Adminov)"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "Site: www.rootkovskiy.ovh"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "VK: @timurroot"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_CYAN+   "Goodbye ;p"+ConsoleUtils.ANSI_RESET);
        System.out.println(ConsoleUtils.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+ConsoleUtils.ANSI_RESET);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender.hasPermission("tfo.use"))) {
            sender.sendMessage((getConfig().getString("noperm")).replace("&", "§"));
            return true;
        }

        if(args.length != 1)  {
            sender.sendMessage((getConfig().getString("usage")).replace("&", "§"));
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "on":
                sender.sendMessage((getConfig().getString("on_message")).replace("&", "§"));
                onCount();
                break;
            case "off":
                sender.sendMessage((getConfig().getString("off_message")).replace("&", "§"));
                offCount();
                break;
            default:
                sender.sendMessage((getConfig().getString("usage")).replace("&", "§"));
                break;
        }
        return true;
    }

    public void offCount() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(
                PacketAdapter.params((Plugin)this, new PacketType[] { PacketType.Status.Server.OUT_SERVER_INFO }).optionAsync()) {
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing serverPing = (WrappedServerPing)event.getPacket().getServerPings().read(0);
                serverPing.setPlayersOnline(getPlugin().getServer().getOnlinePlayers().size());
            }
        });
    }

    public void onCount() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(
                PacketAdapter.params((Plugin)this, new PacketType[] { PacketType.Status.Server.OUT_SERVER_INFO }).optionAsync()) {
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing serverPing = (WrappedServerPing)event.getPacket().getServerPings().read(0);
                int number = getPlugin().getServer().getOnlinePlayers().size() * getConfig().getInt("multiplier");
                serverPing.setPlayersOnline(number);
            }
        });
    }
}
