package com.mefrreex.prefix.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import com.mefrreex.prefix.Main;

public class PrefixCommand extends Command {

    private Main main;

    public PrefixCommand(Main main) {
        super("prefix", "Установить префикс");
        this.setPermission("prefix.command");

        commandParameters.clear();
        commandParameters.put("default", new CommandParameter[] { 
            CommandParameter.newType("player", false, CommandParamType.TARGET),
            CommandParameter.newType("target", true, CommandParamType.TARGET) 
        });

        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        if (args.length == 1 && !(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Даннную команду можно использовать только в игре.");
            return false;
        }

        if (args.length == 2 && !sender.hasPermission("prefix.others")) {
            sender.sendMessage("У вас нет разрешения на использование этой команды.");;
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("Используйте: /" + label + " <префикс> [игрок]");;
            return false;
        }

        Player player;

        if (args.length == 1) {
            player = (Player) sender;
        } else {
            player = main.getServer().getPlayer(args[1]);
        }

        if (player == null || !player.isOnline()) {
            sender.sendMessage(TextFormat.RED + "Не удалось найти игрока " + args[1]);
            return false;
        }


        if (args[0].length() > 10) {
            sender.sendMessage(TextFormat.RED + "Ваш префикс слишком длинный.");
            return false;
        }

        String prefix = args[0];

        main.setPrefix(player, prefix);
        player.sendMessage("Ваш префикс успешно изменен на " + prefix);

        if (args.length == 2) {
            sender.sendMessage("Префикс игрока " + player.getName() + " успешно изменен на " + prefix);
        }
        
        return true;
    }
    
}
