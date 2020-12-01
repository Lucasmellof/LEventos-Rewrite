package cf.lucasmellof.eventos.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 01/12/2020
 */
class Text {
    var text = ""
    var hoverText: String? = null
    var runCommandActionText: String? = null
    var suggestCommandAction: String? = null
    var tellRawCommand: String? = null
    var lastColor = ""
    var containsMSG = false
    var recentChanged = true

    constructor()
    constructor(text: String) {
        this.text = text
    }

    constructor(textList: List<String>) {
        text = java.lang.String.join("\n", textList)
    }

    constructor(text: String, hoverText: String) {
        this.text = text
        this.hoverText = hoverText
    }

    constructor(text: String, hoverText: String, runCommandActionText: String) {
        this.text = text
        this.hoverText = hoverText
        this.runCommandActionText = runCommandActionText
    }

    constructor(text: String, hoverText: String, commandActionText: String, suggestionCommand: Boolean) {
        this.text = text
        this.hoverText = hoverText
        if (suggestionCommand) {
            suggestCommandAction = commandActionText
        } else {
            runCommandActionText = commandActionText
        }
    }

    /**
     * Seta o texto dessa FancyMessage, por padrão, o texto é vazio "";
     */
    fun setText(text: String): Text {
        setRecentChanged()
        this.text = text
        return this
    }

    fun setText(textList: List<String>): Text {
        setRecentChanged()
        text = java.lang.String.join("\n", textList)
        return this
    }

    /**
     * Seta o texto dessa FancyMessage, por padrão, o texto é vazio "";
     */
    fun addText(text: String): Text {
        setRecentChanged()
        this.text += text
        return this
    }

    fun addText(textList: List<String>): Text {
        setRecentChanged()
        text += java.lang.String.join("\n", textList)
        return this
    }

    /**
     * Seta o a HoverMessage dessa FancyMessage;
     */
    fun setHoverText(hoverText: String): Text {
        setRecentChanged()
        this.hoverText = hoverText
        return this
    }

    fun setHoverText(hoverTextList: List<String>): Text {
        setRecentChanged()
        hoverText = java.lang.String.join("\n", hoverTextList)
        return this
    }

    /**
     * Seta o a RunCommand dessa FancyMessage;
     * (Comando executado quando o jogador clica na mensagem)
     */
    fun setRunCommandActionText(runCommandActionText: String): Text {
        setRecentChanged()
        this.runCommandActionText = runCommandActionText
        return this
    }

    /**
     * Seta o a SuggestCommand dessa FancyMessage;
     * (Comando sugerido quando o jogador clica na mensagem)
     */
    fun setSuggestCommandAction(suggestCommandAction: String): Text {
        setRecentChanged()
        this.suggestCommandAction = suggestCommandAction
        return this
    }

    fun setRecentChanged() {
        if (!recentChanged) recentChanged = true
    }

    /**
     * Retorna a parte do comando fancytext dessa FancyMessage!
     *
     * Vale salientar que esse método nao retorna o comando correto do fancytext, apenas uma parte dele!
     *
     * Use as funções do FancyText.class ao invés dessa!
     */
    fun getTellRawString(): String? {
        if (tellRawCommand != null && !recentChanged) {
            return tellRawCommand
        }
        recentChanged = false
        text = fixTellrawTextColors(text)
        lastColor = getLastColor(text)
        val tellRaw = StringBuilder("{\"text\":\"$text\"")
        if (hoverText != null) {
            hoverText = fixTellrawTextColors(hoverText)
            tellRaw.append(",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"$hoverText\"}")
        }
        if (runCommandActionText != null) {
            tellRaw.append(",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + fixBrackets(runCommandActionText) + "\"}")
        } else if (suggestCommandAction != null) {
            tellRaw.append(
                ",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + fixBrackets(
                    suggestCommandAction
                ) + "\"}"
            )
        }
        tellRaw.append("}")
        return tellRaw.toString().also { tellRawCommand = it }
    }

    // ------------------------------------------------------------------------------------------------------
    // TellRaw Fixer
    // ------------------------------------------------------------------------------------------------------
    private fun getLastTextColor(): String {
        return lastColor
    }

    private fun getLastColor(text: String): String {
        return ChatColor.getLastColors(text)
    }

    private fun fixTellrawTextColors(theText: String?): String {
        val allWords = theText!!.split(" ").toTypedArray()
        var lastColor = ""
        for (i in allWords.indices) {
            allWords[i] = lastColor + allWords[i]
            lastColor = ChatColor.getLastColors(allWords[i])
        }
        return fixBrackets(java.lang.String.join(" ", *allWords))
    }

    private fun fixBrackets(theText: String?): String {
        return theText!!.replace("\"".toRegex(), "\'\'")
    }

    fun clone(): Text {
        val cloneFancyText = Text()
        cloneFancyText.text = text
        cloneFancyText.hoverText = hoverText
        cloneFancyText.runCommandActionText = runCommandActionText
        cloneFancyText.suggestCommandAction = suggestCommandAction
        cloneFancyText.lastColor = lastColor
        cloneFancyText.recentChanged = recentChanged
        cloneFancyText.containsMSG = containsMSG
        return cloneFancyText
    }

    companion object {
        // ------------------------------------------------------------------------------------------------------
        // Métodos Estáticos para se entregar as mensagens
        // ------------------------------------------------------------------------------------------------------
        /**
         * Recebe um player como argumento e uma List de FancyTextElement
         *
         * Monta o comando para esses FancyTextElement
         * e envia a mensagem para o jogador!
         */
        fun sendTo(commandSender: CommandSender, texts: List<Text>) {
            if (commandSender is Player) {
                val command = tellRawCommandBuilder(texts)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + commandSender.getName() + " " + command)
            } else {
                commandSender.sendMessage(textOnlyTextBuilder(texts))
            }
        }

        /**
         * Monta o comando para esses FancyTextElement
         * e envia a mensagem para todos os jogadores!
         */
        fun tellRawBroadcast(texts: List<Text>) {
            val command = tellRawCommandBuilder(texts)
            for (player in Bukkit.getOnlinePlayers()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.name + " " + command)
            }
            Bukkit.getConsoleSender().sendMessage(textOnlyTextBuilder(texts))
        }

        /**
         * Recebe uma List de FancyTextElement e retorna
         * o comando (String) corresponde a lista!
         */
        fun tellRawCommandBuilder(texts: List<Text>): String {
            val command = StringBuilder("[\"\"")
            var previousLastColor = ""
            for (aFancyTextElement in texts) {
                aFancyTextElement.text = previousLastColor + aFancyTextElement.text
                command.append("," + aFancyTextElement.getTellRawString())
                previousLastColor = aFancyTextElement.getLastTextColor()
            }
            return command.append("]").toString()
        }

        /**
         * Recebe uma List de FancyTextElement e retorna
         * o comando (String) corresponde a lista!
         */
        fun textOnlyTextBuilder(texts: List<Text>): String {
            val text = StringBuilder()
            for (aFancyTextElement in texts) {
                text.append(aFancyTextElement.text)
            }
            return text.toString()
        }

        // ------------------------------------------------------------------------------------------------------
        // Faz a mesma coisa que os de cima, contudo, recebe itens diretamente, nao necessariamente em uma lista!
        // Um vetor simples por exemplo!
        // ------------------------------------------------------------------------------------------------------
        fun sendTo(commandSender: CommandSender, vararg texts: Text) {
            if (commandSender is Player) {
                val command = tellRawCommandBuilder(*texts)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + commandSender.getName() + " " + command)
            } else {
                commandSender.sendMessage(textOnlyTextBuilder(*texts))
            }
        }

        fun tellRawBroadcast(vararg texts: Text) {
            val command = tellRawCommandBuilder(*texts)
            for (player in Bukkit.getOnlinePlayers()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.name + " " + command)
            }
            Bukkit.getConsoleSender().sendMessage(textOnlyTextBuilder(*texts))
        }

        fun tellRawCommandBuilder(vararg texts: Text): String {
            val command = StringBuilder("[\"\"")
            for (aFancyTextElement in texts) {
                command.append("," + aFancyTextElement.getTellRawString())
            }
            command.append("]")
            return command.toString()
        }

        /**
         * Recebe uma List de FancyTextElement e retorna
         * o comando (String) corresponde a lista!
         */
        fun textOnlyTextBuilder(vararg texts: Text): String {
            val text = StringBuilder()
            for (aFancyTextElement in texts) {
                text.append(aFancyTextElement.text)
            }
            return text.toString()
        }
    }
}
