# FIAP: Trabalho de Avaliação - Java
## bot-telegram 
#

1) Instalar MAVEN:
	mvn -version	

2) Criar projeto com o MAVEN:
	mvn archetype:generate (escolher o archetype sugerido)  
	* _groupId_, Value: fiap.java;
	* _artifactId_, Value: bot-telegram;
	* _version_, Value: 1.0-SNAPSHOT;
	* _package_, Value: fiap.1scjr;

3) Classes auxiliares da Classe Princiapl (main):
   * _Config.java_ - fornece token e id do bot;
   * _CommandsBot.java_ - classe que extende TelegramLongPollingBot, para iterações com usuário;

__consultado__:  
_https://monsterdeveloper.gitbook.io/java-telegram-bot-tutorial/_