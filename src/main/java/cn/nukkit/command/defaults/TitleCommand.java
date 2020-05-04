package cn.nukkit.command.defaults;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.locale.TranslationContainer;
import cn.nukkit.player.Player;
import cn.nukkit.utils.TextFormat;

/**
 * @author Tee7even
 */
public class TitleCommand extends VanillaCommand {
    public TitleCommand(String name) {
        super(name, "commands.title.description", "/title <player> <clear|reset>\n/title <player> <|title|subtitle|actionbar> <text>\n/title <player> <times> <fadein> <stay> <fadeOut>");
        this.setPermission("nukkit.command.title");

        this.commandParameters.clear();
        // clear
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("clear", new String[]{"clear"})
        });
        // reset
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("reset", new String[]{"reset"})
        });
        // title
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("title", new String[]{"title"}),
                new CommandParameter("titleText", CommandParamType.STRING, false)
        });
        // subtitle
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("subtitle", new String[]{"subtitle"}),
                new CommandParameter("titleText", CommandParamType.STRING, false)
        });
        // actionbar
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("actionbar", new String[]{"actionbar"}),
                new CommandParameter("titleText", CommandParamType.STRING, false)
        });
        // times
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("times", new String[]{"times"}),
                new CommandParameter("fadeIn", CommandParamType.INT, false),
                new CommandParameter("stay", CommandParamType.INT, false),
                new CommandParameter("fadeOut", CommandParamType.INT, false)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return false;
        }

        Player player = Server.getInstance().getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
            return true;
        }

        if (args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "clear":
                    player.clearTitle();
                    sender.sendMessage(new TranslationContainer("nukkit.command.title.clear", player.getName()));
                    break;
                case "reset":
                    player.resetTitleSettings();
                    sender.sendMessage(new TranslationContainer("nukkit.command.title.reset", player.getName()));
                    break;
                default:
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                    return false;
            }
        } else if (args.length == 3) {
            switch (args[1].toLowerCase()) {
                case "title":
                    player.sendTitle(args[2]);
                    sender.sendMessage(new TranslationContainer("nukkit.command.title.title",
                            TextFormat.clean(args[2]), player.getName()));
                    break;
                case "subtitle":
                    player.setSubtitle(args[2]);
                    sender.sendMessage(new TranslationContainer("nukkit.command.title.subtitle", TextFormat.clean(args[2]), player.getName()));
                    break;
                /*case "actionbar":
                    player.sendActionBarTitle(args[2]);
                    sender.sendMessage(new TranslationContainer("nukkit.command.title.actionbar", new String[]{TextFormat.clean(args[2]), player.getName()}));
                    break;*/
                default:
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                    return false;
            }
        } else if (args.length == 5) {
            if (args[1].toLowerCase().equals("times")) {
                try {
                    /*player.setTitleAnimationTimes(Integer.valueOf(args[2]), //fadeIn
                            Integer.valueOf(args[3]), //stay
                            Integer.valueOf(args[4])); //fadeOut*/
                    sender.sendMessage(new TranslationContainer("nukkit.command.title.times.success",
                            args[2], args[3], args[4], player.getName()));
                } catch (NumberFormatException exception) {
                    sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.exception"));
                }
            } else {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                return false;
            }
        } else {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return false;
        }

        return true;
    }
}
