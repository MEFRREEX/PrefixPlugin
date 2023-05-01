package com.mefrreex.prefix;

import com.mefrreex.prefix.commands.PrefixCommand;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PrefixNode;

public class Main extends PluginBase {

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        this.luckPerms = LuckPermsProvider.get();
        this.getServer().getCommandMap().register("Prefix", new PrefixCommand(this));
    }

    public void setPrefix(Player player, String prefix) {

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        PrefixNode prefixNode = PrefixNode.builder(prefix, 1).build();

        for (Node node : user.data().toCollection()) {
            if (node instanceof PrefixNode) {
                user.data().remove(node);
            }
        }

        user.data().add(prefixNode);
        luckPerms.getUserManager().saveUser(user);
    }
}